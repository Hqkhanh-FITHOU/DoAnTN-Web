package com.graduate.hou.controller.rest;


import com.graduate.hou.dto.request.DailyRevenueDTO;
import com.graduate.hou.entity.DailyRevenue;
import com.graduate.hou.service.impl.DailyRevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/dailyRevenue")
public class DailyRevenueController {
    @Autowired
    private DailyRevenueServiceImpl dailyRevenueService;

    @PostMapping
    DailyRevenue createDailyRevenue(@RequestBody DailyRevenueDTO dailyRevenueDTO){
        return dailyRevenueService.createDailyRevenue(dailyRevenueDTO);
    }

    @GetMapping
    List<DailyRevenue> getAllDailyRevenue(){
        return dailyRevenueService.getAllDailyRevenue();
    }

    @PutMapping("/{id}")
    DailyRevenue updateDailyRevenue (@PathVariable Long id, @RequestBody DailyRevenueDTO dailyRevenueDTO){
        return dailyRevenueService.updateDailyRevenue(id, dailyRevenueDTO);
    }

    @DeleteMapping("/{id}")
    String deleteDailyRevenue(@PathVariable Long id){
        dailyRevenueService.deleteDailyRevenue(id);
        return "Xóa thành công";
    }
}
