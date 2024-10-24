package com.graduate.hou.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueDTO {
    private Long dailyRevenueId;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalDate revenueDate;

    private BigDecimal totalRevenue;

    private Long totalOrders;
}
