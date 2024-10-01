package com.graduate.hou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.User;
import com.graduate.hou.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long orderNotificationId;

    private Long order;

    private Long user;

    private String message;

    private NotificationType notificationType;

    private boolean isRead;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
