package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.PaymentDTO;
import com.graduate.hou.entity.Payment;
import com.graduate.hou.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping
    Payment createPayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService.createPayment(paymentDTO);
    }

    @GetMapping
    List<Payment> getAllPayment(){
        return paymentService.getAllPayment();
    }

    @PutMapping("/{id}")
    Payment updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO){
        return paymentService.updatePayment(id, paymentDTO);
    }

    @DeleteMapping("/{id}")
    String deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return "Xóa thành công";
    }
}
