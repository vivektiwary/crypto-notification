package com.vivektiwary.CryptoNotification.dto;

import lombok.Data;

@Data
public class NotificationRequest {
  private double bitcoinCurrentPrice;
  private double dailyPercentageChange;
  private double tradingVolume;
}
