package com.graduate.hou.dto;

import com.graduate.hou.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
