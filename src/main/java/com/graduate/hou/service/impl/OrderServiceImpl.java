package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.dto.request.OrderDTO2;
import com.graduate.hou.entity.*;
import com.graduate.hou.enums.OrderStatus;
import com.graduate.hou.enums.PaymentStatus;
import com.graduate.hou.mapper.OrderMapper;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.PaymentRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    static final String TARGET_REAL_DEVICE = "cLJf3z_OSMWF0jJ9vIFc2f:APA91bGM9TztyVPYbD3kK3r2PCi-KRK6k4yk8rSRw34cTzxsDqL_PMUWEEQK5SASj3T1wBr11LEOQ983VLMpHUZ6D5F2_v-YWV4HAzXcBIa92FwnlfTY8Og";
    static final String TARGET_EMULATOR_TOKEN = "e7yi2aAQTiqZYzNZdvRSF8:APA91bGUOMe3cqZLEWoUbPOx9lbtFbCLGtEcR34MIxReeLy5sac2eOETelK0tWbPo6rT1NHhLjUIce0B5aBzaU2WwElhnqzAu-eaJpxiU38s2maKwqExcIY";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FirebaseNotificationService firebaseNotificationService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAllOrdersSortedByIdDesc();
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        User user = usersRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("Chưa đăng nhập"));

        Payment payment = paymentRepository.findById(orderDTO.getPaymentId())
                .orElseThrow(()-> new RuntimeException("Chưa thanh toán"));


        Order order = Order.builder()
                .user(user)
                .totalDiscount(orderDTO.getTotalDiscount())
                .totalPayment(orderDTO.getTotalPayment())
                .totalAmount(orderDTO.getTotalAmount())
                .status(orderDTO.getStatus())
                .address(orderDTO.getAddress())
                .payment(payment)
                .build();
        return orderRepository.save(order);
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean updateOrder(Long id, OrderDTO orderDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        User user = usersRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("Chưa đăng nhập"));

        Payment payment = paymentRepository.findById(orderDTO.getPaymentId())
                .orElseThrow(()-> new RuntimeException("Chưa thanh toán"));

        Order order = optionalOrder.get().builder()
                .user(user)
                .totalDiscount(orderDTO.getTotalDiscount())
                .totalPayment(orderDTO.getTotalPayment())
                .totalAmount(orderDTO.getTotalAmount())
                .status(orderDTO.getStatus())
                .address(orderDTO.getAddress())
                .payment(payment)
                .build();
        return orderRepository.save(order) != null;
    }


    @Override
    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        if(order != null){
            try {
                orderRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Order createOrder2(OrderDTO2 orderDTO) {
        User user = usersRepository.findById(orderDTO.getUserId())
        .orElseThrow(()-> new RuntimeException("Chưa đăng nhập"));

        Payment payment = new Payment();
        payment.setPaymentMethod(orderDTO.getPaymentMethod());
        payment.setPaymentStatus(orderDTO.getPaymentStatus());
        
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setAddress(orderDTO.getFullname() +" - "+ orderDTO.getPhone() +" - "+ orderDTO.getAddress());
        order.setPayment(paymentRepository.save(payment));
        order.setNote(orderDTO.getNote());
        order.setCreatedAt(orderDTO.getOrderDateTime());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setTotalDiscount(orderDTO.getTotalDiscount());
        order.setTotalPayment(orderDTO.getTotalPayment());

        return orderRepository.save(order);
    }


    @Override
    public boolean confirmOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.ON_PROGRESS);
        firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Xác nhận", "Đơn hàng của bạn đã xác nhận");
        return orderRepository.save(order) != null;
    }

    @Override
    public List<Order> getPendingOrder() {
        return orderRepository.findByStatusAndIdDesc(OrderStatus.PENDING);
    }

    @Override
    public boolean updateState(Long id, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.get();
        if(status == OrderStatus.CANCELED) {
            if(order.getPayment().getPaymentStatus() == PaymentStatus.COMPLETED){
                Payment payment = order.getPayment();
                payment.setPaymentStatus(PaymentStatus.REFUNDED);
                paymentRepository.save(payment);
            }
            firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Hủy đơn", "Đơn hàng của bạn được hủy");
        } else if(status == OrderStatus.WILLING_DELIVERY) {
            firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Sãn sàng giao", "Đơn hàng của bạn đã chế biến xong, sãn sàng giao");
            firebaseNotificationService.sendNotification(TARGET_REAL_DEVICE, "Sãn sàng giao", "Có 1 Đơn hàng đang chờ được giao");
        } else if(status == OrderStatus.ON_DELIVERY) {
            firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Đang giao", "Tài xế đã tiếp nhận đơn, đơn hàng của bạn đang được giao");
        } else if (status == OrderStatus.COMPLETED){
            firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Hoàn thành", "Đơn hàng của bạn đã hoàn thành");
            firebaseNotificationService.sendNotification(TARGET_REAL_DEVICE, "Hoàn thành", "Bạn đã hoàn thành giao hàng");
        }
        order.setStatus(status);
        return orderRepository.save(order) != null;
    }

    @Override
    public List<Order> getOrderByStatusList(List<OrderStatus> list) {
        return orderRepository.findByStatusInAndIdDesc(list);
    }

    @Override
    public List<OrderDTO> getOrderDtoByStatusList(List<OrderStatus> list) {
        List<Order> orders = orderRepository.findByStatusInAndIdDesc(list);
        List<OrderDTO> ordersDto = new ArrayList<>();
        for(Order order : orders) {
            ordersDto.add(OrderMapper.toDTO(order));
        }
        return ordersDto;
    }

    @Override
    public List<OrderDTO> getOrderDtoByStatusesAndUserId(List<OrderStatus> list, Long userId) {
        List<Order> orders = orderRepository.findOrdersByUserAndStatuses(userId, list);
        List<OrderDTO> ordersDto = new ArrayList<>();
        for(Order order : orders) {
            ordersDto.add(OrderMapper.toDTO(order));
        }
        return ordersDto;
    }

    @Override
    public List<OrderDTO> getOrderDtoByStatusesAndDeliveryId(List<OrderStatus> list, Long userId) {
        List<Order> orders = orderRepository.findOrdersByDeliveryAndStatuses(userId , list);
        List<OrderDTO> ordersDto = new ArrayList<>();
        for(Order order : orders) {
            ordersDto.add(OrderMapper.toDTO(order));
        }
        return ordersDto;
    }


    @Override
    public boolean saveOrderWithDelivery(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).get();
        User delivery = usersRepository.findById(userId).get();
        order.setDelivery(delivery);
        order.setStatus(OrderStatus.ON_DELIVERY);
        firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Đang giao", "Tài xế đã tiếp nhận đơn, đơn hàng của bạn đang được giao");
        return orderRepository.save(order) != null;
    }

    @Override
    public boolean confirmCompleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.get();
        Payment p = order.getPayment();
        if(p.getPaymentStatus() == PaymentStatus.PENDING){
            p.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        paymentRepository.save(p);
        order.setStatus(OrderStatus.COMPLETED);
        firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Hoàn thành", "Đơn hàng của bạn đã hoàn thành");
        firebaseNotificationService.sendNotification(TARGET_REAL_DEVICE, "Hoàn thành", "Bạn đã hoàn thành giao hàng");
        return orderRepository.save(order) != null;
    }

    @Override
    public boolean updateCancelReason(Long id, String cancelReason) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.CANCELED);
        order.setCancelReason(cancelReason);
        firebaseNotificationService.sendNotification(TARGET_EMULATOR_TOKEN, "Hủy đơn", "Đơn hàng của bạn được hủy");
        return orderRepository.save(order) != null;
    }

    @Override
    public Long getTotalOrdersToday() {
        return orderRepository.countTotalOrdersToday();
    }

    @Override
    public BigDecimal getTotalRevenueToday() {
        return orderRepository.sumTotalRevenueToday();
    }

    @Override
    public Long getTotalSuccessfulOrdersToday() {
        return orderRepository.countTotalSuccessfulOrdersToday();
    }

}
