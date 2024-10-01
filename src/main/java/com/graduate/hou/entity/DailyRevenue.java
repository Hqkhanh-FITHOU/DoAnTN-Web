package com.graduate.hou.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

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

    private Time openTime;

    private Time closeTime;

    @Column(nullable = false)
    private Date revenueDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private Integer totalOrders;
}
