package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Buy;
import achadaga.stockportfolio.transactions.Sell;
import achadaga.stockportfolio.transactions.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionTest {

  static Transaction[] transactions = {
      new Buy("aapl", new BigDecimal("120.50"), new BigDecimal("100"), LocalDate.of(2021, 3, 15)),
      new Buy("aapl", new BigDecimal("128.50"), new BigDecimal("300"), LocalDate.of(2021, 5, 1)),
      new Buy("aapl", new BigDecimal("135.50"), new BigDecimal("50"), LocalDate.of(2021, 7, 25)),
      new Sell("aapl", new BigDecimal("148.50"), new BigDecimal("250"), LocalDate.of(2021, 7, 29))};

  static Position aapl = new Position("aapl");

  static {
    for (Transaction t : transactions) {
      aapl.addTransaction(t);
    }
  }

  @Test
  void addTransaction() {
    Assertions.assertEquals(4, aapl.totalTransactions());
  }

  @Test
  void totalRealizedGain() {
    Assertions.assertEquals(new BigDecimal("5250.00"), aapl.getTotalRealized());
  }

  @Test
  void totalUnrealizedGain() {
    BigDecimal unrealized = aapl.getTotalUnrealized();
    Assertions.assertTrue(unrealized.doubleValue() >= 0.0);
  }

  @Test
  void testToString() {
    System.out.println(aapl);
  }

}