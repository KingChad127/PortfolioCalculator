package com.achadaga.portfoliocalculator.entities;

import java.time.LocalDate;

public class Buy extends Transaction {

  public Buy(String ticker, double price, double quantity, LocalDate purchaseDate, int dayOrder) {
    super(ticker, price, quantity, purchaseDate, dayOrder);
  }

  @Override
  public String toString() {
    return super.toString() + "\n\tBUY " + getTicker() + "\n\tpurchase price: $" + String.format(
        "%.2f", getPrice()) + "\n\tshares " + "bought: " + getNumShares() + "\n";
  }

}
