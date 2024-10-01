package com.graduate.hou.service;

import com.graduate.hou.dto.CategoryDTO;
import com.graduate.hou.dto.CouponDTO;
import com.graduate.hou.entity.Category;
import com.graduate.hou.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupon();
    Coupon createCoupon(CouponDTO couponDTO);
    Coupon updateCoupon(Long id, CouponDTO couponDTO);
    void deleteCoupon(Long id);
}
