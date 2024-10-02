package com.graduate.hou.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal priceAtOrderTime;
}
