package com.achadaga.portfoliotracker.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction implements Comparable<Transaction> {

  // data
  private final String ticker;
  private final BigDecimal price;
  private final BigDecimal quantity; // support fractional shares as well
  private final LocalDate date;
  private final UUID transactionID;

  public Transaction(String ticker, BigDecimal price, BigDecimal quantity, LocalDate date) {
    this.ticker = ticker;
    this.price = price;
    this.quantity = quantity;
    this.date = date;
    this.transactionID = UUID.randomUUID();
  }

  public String getTicker() {
    return ticker;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public LocalDate getDate() {
    return date;
  }

  public UUID getTransactionID() {
    return transactionID;
  }

  public BigDecimal getTotalCost() {
    return this.price.multiply(this.quantity);
  }

  /**
   * @param other transaction to compare this one to
   * @return a positive number if this transaction is older than other, 0 if this transaction
   * occurred on the same date, and negative otherwise. Break ties by going to the UUIDs
   */
  @Override
  public int compareTo(Transaction other) {
    int dateComparison = other.date.compareTo(this.getDate());
    if (dateComparison != 0) {
      return dateComparison;
    } else {
      return other.transactionID.compareTo(this.transactionID);
    }
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
