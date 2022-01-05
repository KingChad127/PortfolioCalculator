package com.achadaga.portfoliocalculator.app;

import java.util.Collections;

public class Constants {

  public static final int WIDTH = 40;
  public static final String LINE = String.join("", Collections.nCopies(WIDTH, "-"));
  public static final String OPTIONS = "Options: ";

  public static String decimalFormat(double decimal) {
    return String.format("%.2f", decimal);
  }

  public enum MenuHeader {
    FIRST_TIME, OTHER
  }
}


