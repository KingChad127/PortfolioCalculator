package com.achadaga.portfoliocalculator.app;

import java.math.BigDecimal;
import java.util.Collections;

public class Constants {

  public static final int WIDTH = 40;
  public static final String line = String.join("", Collections.nCopies(WIDTH, "-"));

  public static String decimalFormat(double decimal) {
    return String.format("%.2f", decimal);
  }

  public enum MenuHeader {
    FIRST_TIME, OTHER
  }
}


