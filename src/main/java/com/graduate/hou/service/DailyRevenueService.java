package com.graduate.hou.service;


import com.graduate.hou.dto.DailyRevenueDTO;
import com.graduate.hou.entity.DailyRevenue;

import java.util.List;

public interface DailyRevenueService {
    List<DailyRevenue> getAllDailyRevenue();
    DailyRevenue createDailyRevenue(DailyRevenueDTO dailyRevenueDTO);
    DailyRevenue updateDailyRevenue(Long id, DailyRevenueDTO dailyRevenueDTO);
    void deleteDailyRevenue(Long id);
}
