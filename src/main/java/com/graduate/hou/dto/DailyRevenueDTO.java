package com.graduate.hou.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueDTO {
    private Long dailyRevenueId;

    private Time openTime;

    private Time closeTime;

    private Date revenueDate;

    private BigDecimal totalRevenue;

    private Integer totalOrders;
}
