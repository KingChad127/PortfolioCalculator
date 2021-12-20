package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Buy;
import achadaga.stockportfolio.transactions.Sell;
import achadaga.stockportfolio.transactions.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortfolioTest {

  static Transaction[] transactions = {
      new Buy("aehr", new BigDecimal("5.35"), new BigDecimal("45"), LocalDate.of(2021, 7, 15)),
      new Buy("nvda", new BigDecimal("334.45"), new BigDecimal("100"), LocalDate.of(2021, 5, 4)),
      new Sell("aehr", new BigDecimal("24.15"), new BigDecimal("28"), LocalDate.of(2021, 8, 25)),
      new Buy("aapl", new BigDecimal("125.06"), new BigDecimal("58"), LocalDate.of(2021, 2, 7)),
      new Sell("nvda", new BigDecimal("280.68"), new BigDecimal("20"), LocalDate.of(2021, 7, 12)),
      new Buy("amd", new BigDecimal("80.78"), new BigDecimal("3.6745"), LocalDate.of(2021, 7, 12)),
      new Buy("aapl", new BigDecimal("120.50"), new BigDecimal("100"), LocalDate.of(2021, 3, 15)),
      new Buy("aapl", new BigDecimal("128.50"), new BigDecimal("300"), LocalDate.of(2021, 5, 1)),
      new Buy("aapl", new BigDecimal("135.50"), new BigDecimal("50"), LocalDate.of(2021, 7, 25)),
      new Sell("aapl", new BigDecimal("148.50"), new BigDecimal("508"), LocalDate.of(2021, 7, 29)),
      new Buy("bptrx", new BigDecimal("200.00"), new BigDecimal("5"), LocalDate.of(2021, 9, 29))};
  static Portfolio portfolio = new Portfolio("Abhinav");

  static {
    for (Transaction t : transactions) {
      portfolio.addTransaction(t);
    }
  }

  @Test
  void testAddTransaction() {
    Assertions.assertEquals(5, portfolio.size());
    String[] tickers = {"aapl", "aehr", "amd", "bptrx", "nvda"};
    int i = 0;
    for (Position ticker : portfolio) {
      Assertions.assertEquals(tickers[i], ticker.getTicker());
      i++;
    }
  }

  @Test
  void testToString() {
    System.out.println(portfolio);
  }

}