package com.graduate.hou.service;


import com.graduate.hou.dto.request.NotificationDTO;
import com.graduate.hou.entity.Notification1;

import java.util.List;

public interface NotificationService {
    List<Notification1> getAllNotification();
    Notification1 createNotification(NotificationDTO notificationDTO);
    Notification1 updateNotification(Long id, NotificationDTO notificationDTO);
    void deleteNotification(Long id);
}
