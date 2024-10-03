package com.graduate.hou.service;


import com.graduate.hou.dto.NotificationDTO;
import com.graduate.hou.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotification();
    Notification createNotification(NotificationDTO notificationDTO);
    Notification updateNotification(Long id, NotificationDTO notificationDTO);
    void deleteNotification(Long id);
}
