package com.graduate.hou.service.impl;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FirebaseNotificationService {

    public String sendNotification(String token, String title, String body) {
        try {
            // Tạo thông báo
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Tạo message
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            // Gửi thông báo
            String response = FirebaseMessaging.getInstance().send(message);
            return "Message sent successfully: " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending message: " + e.getMessage();
        }
    }


}
