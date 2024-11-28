package com.graduate.hou.repository;

import com.graduate.hou.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    //lấy ra các coupon đang enabled nhưng đã hết hạn
    List<Coupon> findByExpirationDateBeforeAndEnabledTrue(LocalDateTime now);

    //lấy ra các coupon đang enable và còn đang sử dụng
    List<Coupon> findByEnabledTrueAndExpirationDateAfter(LocalDateTime now);
}
