package com.graduate.hou.service.impl;

import com.graduate.hou.dto.DailyRevenueDTO;
import com.graduate.hou.entity.DailyRevenue;
import com.graduate.hou.repository.DailyRevenueRepository;
import com.graduate.hou.service.DailyRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyRevenueServiceImpl implements DailyRevenueService {
    @Autowired
    private DailyRevenueRepository dailyRevenueRepository;
    @Override
    public List<DailyRevenue> getAllDailyRevenue() {
        return dailyRevenueRepository.findAll();
    }

    @Override
    public DailyRevenue createDailyRevenue(DailyRevenueDTO dailyRevenueDTO) {
        DailyRevenue dailyRevenue = new DailyRevenue();

        dailyRevenue.setOpenTime(dailyRevenueDTO.getOpenTime());
        dailyRevenue.setCloseTime(dailyRevenueDTO.getCloseTime());
        dailyRevenue.setRevenueDate(dailyRevenueDTO.getRevenueDate());
        dailyRevenue.setTotalRevenue(dailyRevenueDTO.getTotalRevenue());
        dailyRevenue.setTotalOrders(dailyRevenueDTO.getTotalOrders());

        return dailyRevenueRepository.save(dailyRevenue);
    }

    @Override
    public DailyRevenue updateDailyRevenue(Long id, DailyRevenueDTO dailyRevenueDTO) {
        Optional<DailyRevenue> optionalDailyRevenue = dailyRevenueRepository.findById(id);
        DailyRevenue dailyRevenue = optionalDailyRevenue.get();

        dailyRevenue.setOpenTime(dailyRevenueDTO.getOpenTime());
        dailyRevenue.setCloseTime(dailyRevenueDTO.getCloseTime());
        dailyRevenue.setRevenueDate(dailyRevenueDTO.getRevenueDate());
        dailyRevenue.setTotalRevenue(dailyRevenueDTO.getTotalRevenue());
        dailyRevenue.setTotalOrders(dailyRevenueDTO.getTotalOrders());

        return dailyRevenueRepository.save(dailyRevenue);
    }

    @Override
    public void deleteDailyRevenue(Long id) {
        dailyRevenueRepository.deleteById(id);
    }
}
