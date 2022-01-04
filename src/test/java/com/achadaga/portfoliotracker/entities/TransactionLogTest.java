package com.achadaga.portfoliotracker.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionLogTest {

  static Transaction[] transactions = {new Buy("AEHR", 5.35, 4, LocalDate.of(2021, 7, 15), 1),
      new Buy("NVDA", 334.45, 2, LocalDate.of(2021, 5, 4), 1),
      new Sell("AEHR", 24.15, 4, LocalDate.of(2021, 8, 25), 1),
      new Buy("AAPL", 125.06, 6, LocalDate.of(2021, 2, 7), 1),
      new Sell("NVDA", 280.68, 1, LocalDate.of(2021, 7, 12), 1),
      new Buy("AMD", 80.78, 2.7, LocalDate.of(2021, 7, 12), 2)};

  static TransactionLog log = new TransactionLog();
  static Transaction[] sorted = Arrays.copyOf(transactions, transactions.length);

  static {
    for (Transaction t : transactions) {
      log.addTransaction(t);
    }
    Arrays.parallelSort(sorted);
  }

  @Test
  void testAddTransaction() {
    int index = 0;
    for (Transaction t : log) {
      Assertions.assertEquals(t, sorted[index]);
      index++;
    }
  }

  @Test
  void testTotalTransactions() {
    Assertions.assertEquals(6, log.size());
  }

  @Test
  void testSearchByDate() {
    Assertions.assertEquals(
        new TransactionLog(new TreeSet<>(Arrays.asList(transactions[4], transactions[5]))),
        log.searchByDate(LocalDate.of(2021, 7, 12)));
  }

  @Test
  void testSearchByTicker() {
    Assertions.assertEquals(
        new TransactionLog(new TreeSet<>(Arrays.asList(transactions[2], transactions[0]))),
        log.searchByTicker("AEHR"));
    Assertions.assertEquals(
        new TransactionLog(new TreeSet<>(Arrays.asList(transactions[4], transactions[1]))),
        log.searchByTicker("NVDA"));
  }

  @Test
  void testTotalBuysAndSells() {
    Assertions.assertEquals(new TransactionLog(new TreeSet<>(
            Arrays.asList(transactions[3], transactions[5], transactions[1], transactions[0]))),
        log.buys());
    Assertions.assertEquals(
        new TransactionLog(new TreeSet<>(Arrays.asList(transactions[4], transactions[2]))),
        log.sells());
  }

  @Test
  void testPrintOutPut() {
    System.out.println(log);
  }
}