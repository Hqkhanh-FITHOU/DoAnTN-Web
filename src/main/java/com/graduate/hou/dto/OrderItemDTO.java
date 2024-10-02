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

    private Long order;

    private Long product;

    private Integer quantity;

    private BigDecimal priceAtOrderTime;
}
