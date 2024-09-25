package com.vivektiwary.CryptoNotification.service;

import com.vivektiwary.CryptoNotification.dto.NotificationRequest;
import com.vivektiwary.CryptoNotification.model.Notification;
import com.vivektiwary.CryptoNotification.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationService {
  private final AtomicInteger currentId = new AtomicInteger(1);
  Map<Integer, Notification> notifications = new HashMap<>();

  private final EmailService emailService;

  @Autowired
  public NotificationService(EmailService emailService) {
    this.emailService = emailService;
  }

  public Notification create(NotificationRequest request) {
    Notification notification =
        Notification.builder()
            .id(currentId.getAndIncrement())
            .bitcoinCurrentPrice(request.getBitcoinCurrentPrice())
            .dailyPercentageChange(request.getDailyPercentageChange())
            .status(Status.PENDING)
            .tradingVolume(request.getTradingVolume())
            .build();
    notifications.put(notification.getId(), notification);
    return notification;
  }

  public void sendNotification(String id) {
    if (!notifications.containsKey(Integer.parseInt(id))) {
      throw new IllegalStateException("ID does not exist");
    }

    Notification notification = notifications.get(Integer.parseInt(id));
    emailService.send(notification);
    notification.setStatus(Status.SENT);
  }

  public List<Notification> getAllNotifications() {
    List<Notification> response = new ArrayList<>();
    for (Map.Entry<Integer, Notification> entry : notifications.entrySet()) {
      response.add(entry.getValue());
    }
    return response;
  }
}
