package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Coupon;
import com.graduate.hou.repository.CouponRepository;
import com.graduate.hou.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.InsetsUIResource;
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
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).get();
    }

    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {
        Coupon coupon = new Coupon();

        coupon.setCode(couponDTO.getCode());
        coupon.setDiscountType(couponDTO.getDiscountType());
        coupon.setDiscountValue(couponDTO.getDiscountValue());
        coupon.setExpirationDate(couponDTO.getExpirationDate());
        coupon.setMinPurchase(couponDTO.getMinPurchase());
        // coupon.setCreatedAt(couponDTO.getCreatedAt());
        // coupon.setUpdatedAt(couponDTO.getUpdatedAt());

        return couponRepository.save(coupon);
    }

    @Override
    public boolean updateCoupon(Long id, CouponDTO couponDTO) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        Coupon coupon = optionalCoupon.get();

        if (coupon != null){
            coupon.setCode(couponDTO.getCode());
            coupon.setDiscountType(couponDTO.getDiscountType());
            coupon.setDiscountValue(couponDTO.getDiscountValue());
            coupon.setExpirationDate(couponDTO.getExpirationDate());
            coupon.setMinPurchase(couponDTO.getMinPurchase());
            try {
                couponRepository.save(coupon);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return false;

    }

    @Override
    public boolean deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id).get();
        if(coupon != null){
            try {
                couponRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Override
    public void disableCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        coupon.setEnabled(false);
        save(coupon);
    }

    @Override
    public void enableCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        coupon.setEnabled(true);
        save(coupon);
    }
}
