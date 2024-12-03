package com.graduate.hou.controller.rest;

import com.google.firebase.messaging.BatchResponse;
import com.graduate.hou.dto.Notice;
import com.graduate.hou.dto.request.NotificationDTO;
import com.graduate.hou.entity.Notification1;
import com.graduate.hou.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationService;


    // @PostMapping("/send-notification")
    // public BatchResponse sendNotification(@RequestBody Notice notice){
    //     return notificationService.sendNotification(notice);
    // }

    @PostMapping
    Notification1 createNotification(@RequestBody NotificationDTO notificationDTO){
        return notificationService.createNotification(notificationDTO);
    }

    @GetMapping
    List<Notification1> getAllNotification(){
        return notificationService.getAllNotification();
    }

    @PutMapping("/{id}")
    Notification1 updateNotification (@PathVariable Long id, @RequestBody NotificationDTO notificationDTO){
        return notificationService.updateNotification(id, notificationDTO);
    }

    @DeleteMapping("/{id}")
    String deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return "Xóa thành công";
    }
}
