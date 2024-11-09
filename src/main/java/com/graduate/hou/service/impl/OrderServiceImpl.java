package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.*;
import com.graduate.hou.repository.AddressRepository;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.PaymentRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private AddressRepository addressRepository;

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

        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(()-> new RuntimeException("Chưa chọn địa chỉ"));

        Order order = Order.builder()
                .user(user)
                .totalAmount(orderDTO.getTotalAmount())
                .status(orderDTO.getStatus())
                .payment(payment)
                .address(address)
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

        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(()-> new RuntimeException("Chưa chọn địa chỉ"));

        Order order = optionalOrder.get();
        if(order != null){
            order.setUser(user);
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setStatus(orderDTO.getStatus());
            order.setPayment(payment);
            order.setAddress(address);
            try {
                orderRepository.save(order);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
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
}
