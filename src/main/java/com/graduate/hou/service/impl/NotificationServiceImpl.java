package com.graduate.hou.service.impl;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.graduate.hou.dto.Notice;
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

import java.util.ArrayList;
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

    // @SuppressWarnings({ "null", "deprecation" })
    // public BatchResponse sendNotification(Notice notice) {
    //     List<String> registrationTokens=notice.getRegistrationTokens();
    //     Notification notification = Notification.builder().setTitle(notice.getSubject())
    //             .setBody(notice.getContent())
    //             .setImage(notice.getImage())
    //             .build();

    //     MulticastMessage message = MulticastMessage.builder()
    //             .addAllTokens(registrationTokens)
    //             .setNotification(notification)
    //             .putAllData(notice.getData())
    //             .build();

    //     BatchResponse batchResponse = null;
    //     try {
    //         batchResponse = firebaseMessaging.sendMulticast(message);
    //     } catch (FirebaseMessagingException e) {
    //         log.info("Firebase error {}", e.getMessage());
    //     }
    //     if (batchResponse.getFailureCount() > 0) {
    //         List<SendResponse> responses = batchResponse.getResponses();
    //         List<String> failedTokens = new ArrayList<>();
    //         for (int i = 0; i < responses.size(); i++) {
    //             if (!responses.get(i).isSuccessful()) {
    //                 failedTokens.add(registrationTokens.get(i));
    //             }
    //         }
    //         log.info("List of tokens that caused failures: " + failedTokens);
    //     }
    //     return batchResponse;
    // }
}
