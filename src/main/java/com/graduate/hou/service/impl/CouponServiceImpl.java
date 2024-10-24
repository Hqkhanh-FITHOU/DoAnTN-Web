package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Coupon;
import com.graduate.hou.repository.CouponRepository;
import com.graduate.hou.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Override
    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {
        Coupon coupon = new Coupon();

        coupon.setCode(couponDTO.getCode());
        coupon.setDiscountType(couponDTO.getDiscountType());
        coupon.setDiscountValue(couponDTO.getDiscountValue());
        // coupon.setExpirationDate(couponDTO.getExpirationDate());
        coupon.setMinPurchase(couponDTO.getMinPurchase());
        // coupon.setCreatedAt(couponDTO.getCreatedAt());
        // coupon.setUpdatedAt(couponDTO.getUpdatedAt());

        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, CouponDTO couponDTO) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        Coupon coupon = optionalCoupon.get();

        coupon.setCode(couponDTO.getCode());
        coupon.setDiscountType(couponDTO.getDiscountType());
        coupon.setDiscountValue(couponDTO.getDiscountValue());
        // coupon.setExpirationDate(couponDTO.getExpirationDate());
        coupon.setMinPurchase(couponDTO.getMinPurchase());
        // coupon.setCreatedAt(couponDTO.getCreatedAt());
        // coupon.setUpdatedAt(couponDTO.getUpdatedAt());

        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}
