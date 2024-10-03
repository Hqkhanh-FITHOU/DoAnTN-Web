package com.graduate.hou.dto;

import com.graduate.hou.enums.DiscountType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime expirationDate;

    private BigDecimal minPurchase;

}
