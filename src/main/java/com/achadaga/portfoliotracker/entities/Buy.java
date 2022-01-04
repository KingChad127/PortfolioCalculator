package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.WIDTH;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;

public class Buy extends Transaction {

  public Buy(String ticker, double price, double quantity, LocalDate purchaseDate,
      int dayOrder) {
    super(ticker, price, quantity, purchaseDate, dayOrder);
  }

  @Override
  public String toString() {
    return super.toString() + "\n\tBUY " + getTicker() + "\n\tpurchase price: $" + String.format(
        "%.2f", getPrice()) + "\n\tshares " + "bought: " + getNumShares() + "\n" + String.join("",
        Collections.nCopies(WIDTH, "-"));
  }

}
