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
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
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
        order.setStatus(OrderStatus.ON_PROGRESS);
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
        return orderRepository.save(order) != null;
    }

    @Override
    public List<Order> getPendingOrder() {
        return orderRepository.findByStatus(OrderStatus.PENDING);
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
        }
        order.setStatus(status);
        return orderRepository.save(order) != null;
    }

    @Override
    public List<Order> getOrderByStatusList(List<OrderStatus> list) {
        return orderRepository.findByStatusIn(list);
    }

    @Override
    public List<OrderDTO> getOrderDtoByStatusList(List<OrderStatus> list) {
        List<Order> orders = orderRepository.findByStatusIn(list);
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
        return orderRepository.save(order) != null;
    }
}
