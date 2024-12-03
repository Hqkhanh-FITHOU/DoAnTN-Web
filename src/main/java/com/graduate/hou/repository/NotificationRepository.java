package com.graduate.hou.repository;

import com.graduate.hou.entity.Notification1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification1, Long> {
}
