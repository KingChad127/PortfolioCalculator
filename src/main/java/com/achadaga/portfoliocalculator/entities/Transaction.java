package com.achadaga.portfoliocalculator.entities;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction implements Comparable<Transaction> {

  // data
  private final String ticker; // UPPER CASE
  private final double price;
  private final double numShares; // support fractional shares as well
  private final LocalDate date;
  private final int dayOrder;
  private final UUID transactionID;

  public Transaction(String ticker, double price, double numShares, LocalDate date,
      int dayOrder) {
    this.ticker = ticker;
    this.price = price;
    this.numShares = numShares;
    this.date = date;
    this.dayOrder = dayOrder;
    this.transactionID = UUID.randomUUID();
  }

  /**
   * @return ticker of this transaction
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * @return the price of the ticker during this transaction
   */
  public double getPrice() {
    return price;
  }

  /**
   * @return the number of shares being traded
   */
  public double getNumShares() {
    return numShares;
  }

  /**
   * @return The date of the transaction in MM/DD/YYYY form
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * @return the transaction order within a specific day - used for ordering ties that occur
   * within a specific day.
   */
  public int getOfDay() {
    return dayOrder;
  }

  /**
   * @return the UUID for this transaction
   */
  public UUID getTransactionID() {
    return transactionID;
  }

  /**
   * @param other transaction to compare this one to
   * @return a positive number if this transaction is newer than other, negative otherwise. Break
   * ties using the UUID but in theory shouldn't be used
   */
  @Override
  public int compareTo(Transaction other) {
    int dateComparison = this.date.compareTo(other.getDate());
    if (dateComparison != 0) {
      return dateComparison;
    }
    if (this.dayOrder != other.getOfDay()) {
      return this.dayOrder - other.dayOrder;
    }
    return this.transactionID.compareTo(other.transactionID);
  }

  @Override
  public String toString() {
    String date =
        getDate().getMonthValue() + "-" + getDate().getDayOfMonth() + "-" + getDate().getYear();
    return "\n" + date + "\n\ttransaction ID: " + transactionID;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Transaction)) {
      return false;
    }
    Transaction other = (Transaction) obj;
    return this.getTransactionID().equals(other.getTransactionID());
  }
}
