package com.graduate.hou.dto.request;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal priceAtOrderTime;
}
