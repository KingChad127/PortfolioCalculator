package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.WIDTH;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;

public class Sell extends Transaction {

  public Sell(String ticker, BigDecimal salePrice, BigDecimal quantity, LocalDate sellDate,
      int dayOrder) {
    super(ticker, salePrice, quantity, sellDate, dayOrder);
  }

  @Override
  public String toString() {
    BigDecimal roundedPrice = getPrice().setScale(2, RoundingMode.HALF_UP);
    return super.toString() + "\n\tSELL " + getTicker() + "\n\tsale price: $" + String.format(
        "%.2f", roundedPrice) + "\n\tshares " + "sold: " + getNumShares() + "\n" + String.join("",
        Collections.nCopies(WIDTH, "-"));
  }
}

