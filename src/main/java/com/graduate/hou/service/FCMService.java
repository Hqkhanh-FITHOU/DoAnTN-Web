package com.graduate.hou.service;

public interface FCMService {
    public void sendNotification(String title, String body, String deviceToken);
}
