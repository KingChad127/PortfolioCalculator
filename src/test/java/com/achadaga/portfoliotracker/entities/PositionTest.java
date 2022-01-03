package com.achadaga.portfoliotracker.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionTest {

  static Transaction[] transactions = {
      new Buy("AEHR", new BigDecimal("13.08"), new BigDecimal("110"), LocalDate.of(2021, 10, 1),
          1),
      new Sell("AEHR", new BigDecimal("22.71"), new BigDecimal("10"), LocalDate.of(2021, 11, 8),
          1),
      new Sell("AEHR", new BigDecimal("22.64"), new BigDecimal("1"), LocalDate.of(2021, 11, 8), 2),
      new Sell("AEHR", new BigDecimal("22.71"), new BigDecimal("92"), LocalDate.of(2021, 11, 8), 3),
      new Sell("AEHR", new BigDecimal("22.71"), new BigDecimal("7"), LocalDate.of(2021, 11, 8), 4),
  };

  static Position aehr = new Position("aehr");
  static {
    for (Transaction t : transactions) {
      aehr.addTransaction(t);
    }
    aehr.calculate();
  }


  @Test
  void addTransaction() {
    Assertions.assertEquals(5, aehr.size());
  }

  @Test
  void totalRealizedGain() {
    Assertions.assertEquals(new BigDecimal("1059.23"), aehr.getRealized());
  }

  @Test
  void totalUnrealizedGain() {
    BigDecimal unrealized = aehr.getUnrealized();
    Assertions.assertTrue(unrealized.doubleValue() >= 0.0);
  }

  @Test
  void testToString() {
    System.out.println(aehr);
  }

}