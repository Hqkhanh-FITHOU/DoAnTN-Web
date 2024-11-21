package com.graduate.hou.controller.rest;


import com.graduate.hou.dto.request.CouponDTO;
import com.graduate.hou.entity.Base;
import com.graduate.hou.entity.Coupon;
import com.graduate.hou.service.impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponServiceImpl couponService;

    @PostMapping("/new")
    Coupon createCoupon(@RequestBody CouponDTO couponDTO){
        return couponService.createCoupon(couponDTO);
    }

    @GetMapping
    List<Coupon> getAllCoupon(){
        return couponService.getAllCoupon();
    }

    @PutMapping("/{id}")
    Boolean updateCoupon (@PathVariable Long id, @RequestBody CouponDTO couponDTO){
        return couponService.updateCoupon(id, couponDTO);
    }

    @DeleteMapping("/{id}")
    String deleteCoupon(@PathVariable Long id){
        couponService.deleteCoupon(id);
        return "Xóa thành công";
    }
}
