package com.vivektiwary.CryptoNotification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
  private int id;
  private double bitcoinCurrentPrice;
  private double dailyPercentageChange;
  private double tradingVolume;
  private Status status;
}
