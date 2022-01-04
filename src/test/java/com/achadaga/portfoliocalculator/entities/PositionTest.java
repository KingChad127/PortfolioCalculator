package com.achadaga.portfoliocalculator.entities;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionTest {

  static Transaction[] transactions = {
      new Buy("AEHR", 13.08, 110, LocalDate.of(2021, 10, 1),
          1),
      new Sell("AEHR", 22.71, 10, LocalDate.of(2021, 11, 8),
          1),
      new Sell("AEHR", 22.64, 1, LocalDate.of(2021, 11, 8), 2),
      new Sell("AEHR", 22.71, 92, LocalDate.of(2021, 11, 8), 3),
      new Sell("AEHR", 22.71, 7, LocalDate.of(2021, 11, 8), 4),
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
    Assertions.assertEquals(1059.23, aehr.getRealized());
  }

  @Test
  void totalUnrealizedGain() {
    double unrealized = aehr.getUnrealized();
    Assertions.assertTrue(unrealized >= 0.0);
  }

  @Test
  void testToString() {
    System.out.println(aehr);
  }

}