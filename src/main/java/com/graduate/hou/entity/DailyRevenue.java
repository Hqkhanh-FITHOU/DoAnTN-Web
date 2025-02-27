package com.graduate.hou.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_daily_revenue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyRevenueId;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Column(nullable = false)
    private LocalDate revenueDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private Long totalOrders;
}
