package com.graduate.hou.dto;

import com.graduate.hou.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;
import java.security.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private Long couponId;

    private String code;

    private DiscountType discountType;

    private BigDecimal discountValue;

    private Timestamp expirationDate;

    private BigDecimal minPurchase;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
