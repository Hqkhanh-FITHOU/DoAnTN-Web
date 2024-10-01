package com.graduate.hou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
