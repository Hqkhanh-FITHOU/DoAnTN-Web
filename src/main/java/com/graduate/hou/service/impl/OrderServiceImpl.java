package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.dto.request.OrderDTO2;
import com.graduate.hou.entity.*;
import com.graduate.hou.enums.OrderStatus;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.PaymentRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
