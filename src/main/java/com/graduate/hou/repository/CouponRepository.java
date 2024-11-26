package com.graduate.hou.repository;

import com.graduate.hou.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByExpirationDateBeforeAndEnabledTrue(LocalDateTime now);
}
