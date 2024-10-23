package com.graduate.hou.service.impl;


import com.graduate.hou.dto.request.NotificationDTO;
import com.graduate.hou.entity.Notification;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.NotificationRepository;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
	private UsersRepository usersRepository;

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification createNotification(NotificationDTO notificationDTO) {
        Order order = orderRepository.findById(notificationDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("không tìm thấy hàng đặt"));

        User user = usersRepository.findById(notificationDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("bạn phải đăng nhập"));

        Notification notification = Notification.builder()
                .order(order)
                .user(user)
                .message(notificationDTO.getMessage())
                .notificationType(notificationDTO.getNotificationType())
                .isRead(notificationDTO.isRead())
                .createdAt(notificationDTO.getCreatedAt())
                .updatedAt(notificationDTO.getUpdatedAt())
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long id, NotificationDTO notificationDTO) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);

        Order order = orderRepository.findById(notificationDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("không tìm thấy hàng đặt"));

        User user = usersRepository.findById(notificationDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("bạn phải đăng nhập"));

        Notification notification = optionalNotification.get().builder()
                .order(order)
                .user(user)
                .message(notificationDTO.getMessage())
                .notificationType(notificationDTO.getNotificationType())
                .isRead(notificationDTO.isRead())
                .createdAt(notificationDTO.getCreatedAt())
                .updatedAt(notificationDTO.getUpdatedAt())
                .build();

        return notificationRepository.save(notification);
    }


    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
