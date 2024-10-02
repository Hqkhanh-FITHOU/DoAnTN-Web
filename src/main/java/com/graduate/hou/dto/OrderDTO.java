package com.graduate.hou.dto;


import com.graduate.hou.enums.OrderStatus;
import lombok.*;

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
