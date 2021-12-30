package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.WIDTH;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;

public class Buy extends Transaction {

  public Buy(String ticker, BigDecimal price, BigDecimal quantity, LocalDate purchaseDate) {
    super(ticker, price, quantity, purchaseDate);
  }

  @Override
  public String toString() {
    BigDecimal roundedPrice = getPrice().setScale(2, RoundingMode.HALF_UP);
    return super.toString() + "\n\tBUY " + getTicker() + "\n\tpurchase price: $" + String.format(
        "%.2f", roundedPrice) + "\n\tshares " + "bought: " + getQuantity() + "\n" + String.join("",
        Collections.nCopies(WIDTH, "-"));
  }

}
