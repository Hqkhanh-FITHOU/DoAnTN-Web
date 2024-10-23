package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.PaymentDTO;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.Payment;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.PaymentRepository;
import com.graduate.hou.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("order không tồn tại"));

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentStatus(paymentDTO.getPaymentStatus())
                // .paymentDate(paymentDTO.getPaymentDate())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, PaymentDTO paymentDTO) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("order không tồn tại"));

        Payment payment = optionalPayment.get().builder()
                .order(order)
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentStatus(paymentDTO.getPaymentStatus())
                // .paymentDate(paymentDTO.getPaymentDate())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
