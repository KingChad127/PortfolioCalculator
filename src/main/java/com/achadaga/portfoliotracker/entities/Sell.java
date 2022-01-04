package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.WIDTH;

import java.time.LocalDate;
import java.util.Collections;

public class Sell extends Transaction {

  public Sell(String ticker, double salePrice, double quantity, LocalDate sellDate, int dayOrder) {
    super(ticker, salePrice, quantity, sellDate, dayOrder);
  }

  @Override
  public String toString() {
    return super.toString() + "\n\tSELL " + getTicker() + "\n\tsale price: $" + String.format(
        "%.2f", getPrice()) + "\n\tshares " + "sold: " + getNumShares() + "\n" + String.join("",
        Collections.nCopies(WIDTH, "-"));
  }
}

