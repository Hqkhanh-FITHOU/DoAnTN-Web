package com.graduate.hou.service;


import com.graduate.hou.dto.PaymentDTO;
import com.graduate.hou.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayment();
    Payment createPayment(PaymentDTO paymentDTO);
    Payment updatePayment(Long id, PaymentDTO paymentDTO);
    void deletePayment(Long id);
}
