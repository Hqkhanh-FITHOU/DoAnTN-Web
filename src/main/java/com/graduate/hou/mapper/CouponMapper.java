package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Coupon;

public class CouponMapper {
    public static CouponDTO toDTO(Coupon coupon) {
        CouponDTO couponDTO = new CouponDTO();

        couponDTO.setCouponId(coupon.getCouponId());
        couponDTO.setCode(coupon.getCode());
        couponDTO.setDiscountType(coupon.getDiscountType());
        couponDTO.setDiscountValue(coupon.getDiscountValue());
        couponDTO.setExpirationDate(coupon.getExpirationDate());
        couponDTO.setMinPurchase(coupon.getMinPurchase());

        return couponDTO;
    }
}
