package com.vivektiwary.CryptoNotification.controller;

import com.vivektiwary.CryptoNotification.dto.NotificationRequest;
import com.vivektiwary.CryptoNotification.model.Notification;
import com.vivektiwary.CryptoNotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  @Autowired
  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping()
  public ResponseEntity<List<Notification>> getNotifications() {
    List<Notification> notifications = notificationService.getAllNotifications();
    return new ResponseEntity<>(notifications, HttpStatus.OK);
  }

  // for returning NotificationResponse we just have to convert the model to DTO using MapStruct or
  // similar tool,
  // I am excluding that right now ffor simplicity
  @PostMapping()
  public ResponseEntity<Notification> createNotification(
      @RequestBody NotificationRequest notification) {
    Notification notificationRes = notificationService.create(notification);
    return new ResponseEntity<>(notificationRes, HttpStatus.CREATED);
  }

  @PutMapping("/{id}/send")
  public ResponseEntity<String> sendNotification(@PathVariable String id) {
    try {
      notificationService.sendNotification(id);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }
}
