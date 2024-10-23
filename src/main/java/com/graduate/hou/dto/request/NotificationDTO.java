package com.graduate.hou.dto.request;

import com.graduate.hou.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long orderNotificationId;

    private Long orderId;

    private Long userId;

    private String message;

    private NotificationType notificationType;

    private boolean isRead;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
