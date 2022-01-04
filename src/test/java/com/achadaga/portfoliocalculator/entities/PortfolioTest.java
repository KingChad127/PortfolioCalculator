package com.achadaga.portfoliocalculator.entities;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortfolioTest {

  static Transaction[] transactions = {new Buy("aehr", 5.35, 45, LocalDate.of(2021, 7, 15), 1),
      new Buy("nvda", 334.45, 100, LocalDate.of(2021, 5, 4), 1),
      new Sell("aehr", 24.15, 28, LocalDate.of(2021, 8, 25), 1),
      new Buy("aapl", 125.06, 58, LocalDate.of(2021, 2, 7), 1),
      new Sell("nvda", 280.68, 20, LocalDate.of(2021, 7, 12), 1),
      new Buy("amd", 80.78, 3.6745, LocalDate.of(2021, 7, 12), 1),
      new Buy("aapl", 120.50, 100, LocalDate.of(2021, 3, 15), 1),
      new Buy("aapl", 128.50, 300, LocalDate.of(2021, 5, 1), 1),
      new Buy("aapl", 135.50, 50, LocalDate.of(2021, 7, 25), 1),
      new Sell("aapl", 148.50, 508, LocalDate.of(2021, 7, 29), 1),
      new Buy("bptrx", 200.00, 5, LocalDate.of(2021, 9, 29), 1)};
  static Portfolio portfolio = new Portfolio("Abhinav");

  static {
    for (Transaction t : transactions) {
      portfolio.addTransaction(t);
    }
    portfolio.validateAndCalculatePortfolio();
  }

  @Test
  void testAddTransaction() {
    Assertions.assertEquals(5, portfolio.size());
    String[] tickers = {"aapl", "aehr", "amd", "bptrx", "nvda"};
    int i = 0;
    for (String ticker : tickers) {
      Assertions.assertEquals(ticker, portfolio.findPosition(ticker).getTicker());
    }
  }

  @Test
  void testToString() {
    System.out.println(portfolio);
  }

}