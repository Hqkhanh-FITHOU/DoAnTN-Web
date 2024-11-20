package com.graduate.hou.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.graduate.hou.enums.OrderStatus;
import com.graduate.hou.enums.PaymentMethod;
import com.graduate.hou.enums.PaymentStatus;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO2 {

    private Long userId;

    private BigDecimal totalAmount;

    private BigDecimal totalDiscount;

    private BigDecimal totalPayment;

    private OrderStatus status;

    private String fullname;

    private String phone;

    private String address;

    private String note;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;
}
