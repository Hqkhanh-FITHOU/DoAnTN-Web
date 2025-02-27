package com.graduate.hou.service;


import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupon();
    List<Coupon> getActiveCoupons();
    Coupon getCouponById(Long id);
    Coupon createCoupon(CouponDTO couponDTO);
    boolean updateCoupon(Long id, CouponDTO couponDTO);
    boolean deleteCoupon(Long id);
    void disableCoupon(Long id);
    void enableCoupon(Long id);
}
