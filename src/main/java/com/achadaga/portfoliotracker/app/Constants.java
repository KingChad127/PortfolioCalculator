package com.achadaga.portfoliotracker.app;

import java.math.BigDecimal;

public class Constants {

  public static final int WIDTH = 40;
  public static final BigDecimal zero = new BigDecimal("0.0");

  public static String decimalFormat(double decimal) {
    return String.format("%.2f", decimal);
  }

  public enum MenuHeader {
    FIRST_TIME, OTHER
  }
}


