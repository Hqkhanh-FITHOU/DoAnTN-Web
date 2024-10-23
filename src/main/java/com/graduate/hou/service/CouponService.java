package com.graduate.hou.service;


import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupon();
    Coupon createCoupon(CouponDTO couponDTO);
    Coupon updateCoupon(Long id, CouponDTO couponDTO);
    void deleteCoupon(Long id);
}
