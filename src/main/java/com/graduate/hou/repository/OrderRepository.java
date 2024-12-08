package com.graduate.hou.repository;

import com.graduate.hou.entity.Order;
import com.graduate.hou.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    Long countTotalOrdersToday();

    @Query("SELECT SUM(o.totalPayment) FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    BigDecimal sumTotalRevenueToday();

    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE AND o.payment.paymentStatus = 'COMPLETED'")
    Long countTotalSuccessfulOrdersToday();
}
