package com.graduate.hou.dto.request;


import com.graduate.hou.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;

    private Long userId;

    private BigDecimal totalAmount;

    private BigDecimal totalDiscount;

    private BigDecimal totalPayment;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long paymentId;

    private String address;
}
