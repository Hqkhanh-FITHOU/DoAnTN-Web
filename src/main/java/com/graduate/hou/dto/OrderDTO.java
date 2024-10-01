package com.graduate.hou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduate.hou.entity.Address;
import com.graduate.hou.entity.OrderItem;
import com.graduate.hou.entity.Payment;
import com.graduate.hou.entity.User;
import com.graduate.hou.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;

    private Long user;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Long payment;

    private List<OrderItemDTO> orderItemDTOS;

    private Long address;
}
