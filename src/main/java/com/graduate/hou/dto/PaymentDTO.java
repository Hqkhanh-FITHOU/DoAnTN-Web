package com.graduate.hou.dto;

import com.graduate.hou.entity.Order;
import com.graduate.hou.enums.PaymentMethod;
import com.graduate.hou.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId;

    private Long order;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Timestamp paymentDate;
}
