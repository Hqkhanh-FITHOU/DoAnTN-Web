package com.graduate.hou.repository;

import com.graduate.hou.entity.DailyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRevenueRepository extends JpaRepository<DailyRevenue, Long> {
}
