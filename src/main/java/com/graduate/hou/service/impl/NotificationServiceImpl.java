package com.graduate.hou.service.impl;


import com.graduate.hou.dto.request.NotificationDTO;
import com.graduate.hou.entity.Notification1;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.NotificationRepository;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    //private final FirebaseMessaging firebaseMessaging;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
	private UsersRepository usersRepository;

    @Override
    public List<Notification1> getAllNotification() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification1 createNotification(NotificationDTO notificationDTO) {
        Order order = orderRepository.findById(notificationDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("không tìm thấy hàng đặt"));

        User user = usersRepository.findById(notificationDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("bạn phải đăng nhập"));

        Notification1 notification = Notification1.builder()
                .order(order)
                .user(user)
                .message(notificationDTO.getMessage())
                .notificationType(notificationDTO.getNotificationType())
                .isRead(notificationDTO.isRead())
                .createdAt(notificationDTO.getCreatedAt())
                .build();

        return notificationRepository.save(notification);
    }

    @SuppressWarnings("static-access")
    @Override
    public Notification1 updateNotification(Long id, NotificationDTO notificationDTO) {
        Optional<Notification1> optionalNotification = notificationRepository.findById(id);

        Order order = orderRepository.findById(notificationDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("không tìm thấy hàng đặt"));

        User user = usersRepository.findById(notificationDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("bạn phải đăng nhập"));

        Notification1 notification = optionalNotification.get().builder()
                .order(order)
                .user(user)
                .message(notificationDTO.getMessage())
                .notificationType(notificationDTO.getNotificationType())
                .isRead(notificationDTO.isRead())
                .createdAt(notificationDTO.getCreatedAt())
                .build();

        return notificationRepository.save(notification);
    }


    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

}
