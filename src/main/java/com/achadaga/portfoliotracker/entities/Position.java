package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.WIDTH;
import static com.achadaga.portfoliotracker.app.Constants.decimalFormat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import yahoofinance.YahooFinance;

public class Position implements Comparable<Position> {

  private final String ticker;
  private final Set<Transaction> history; // contains all transactions of this ticker
  private double totalCostOfPurchasedShares;
  private double totalSharesHeld;
  private double totalSharesBought;
  private double avgCostPerShare;
  private double totalRealizedGain;

  public Position(String ticker) {
    this.ticker = ticker;
    this.history = new HashSet<>();
  }

  /**
   * Add a transaction to this position
   *
   * @param t transaction
   */
  public void addTransaction(Transaction t) {
    history.add(t);
  }

  /**
   * Remove a single transaction from this position
   *
   * @param t the transaction to remove
   */
  public void removeTransaction(Transaction t) {
    history.remove(t);
  }

  /**
   * Calculate the stats that go with this position
   *
   * @return true if this position is valid, false if otherwise
   */
  public boolean calculate() {
    TreeSet<Transaction> sorted = new TreeSet<>(history);
    Iterator<Transaction> reverseIt = sorted.descendingIterator();
    while (reverseIt.hasNext()) {
      Transaction t = reverseIt.next();
      double numShares = t.getNumShares();
      double price = t.getPrice();

      if (t instanceof Buy) {
        totalSharesHeld += numShares;
        totalSharesBought += numShares;
        totalCostOfPurchasedShares += (price * numShares);
        avgCostPerShare = totalCostOfPurchasedShares / totalSharesBought;
      }
      if (t instanceof Sell) {
        totalSharesHeld -= numShares;
        // totalSharesHeld should never go below zero
        if (totalSharesHeld < 0.0) {
          return false;
        }
        double diff = price - avgCostPerShare;
        totalRealizedGain += (diff * numShares);
      }
    }
    return true;
  }

  /**
   * @return the ticker for this position
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * @return the transaction history
   */
  public Set<Transaction> getHistory() {
    return history;
  }

  /**
   * @return the unrealized gain for this position. the unrealized gain is the money that could
   * be made from selling the current shares held.
   */
  public double getUnrealized() {
    double diff = currentPrice() - avgCostPerShare;
    return diff * totalSharesHeld;
  }

  /**
   * @return the realized gain for this position. The realized gain is the actual money made from
   * selling shares of this position
   */
  public double getRealized() {
    return totalRealizedGain;
  }

  /**
   * @return the total number of shares currently held
   */
  public double getTotalSharesHeld() {
    return totalSharesHeld;
  }

  /**
   * @return the average purchase cost per share
   */
  public double getAvgCostPerShare() {
    return avgCostPerShare;
  }

  /**
   * @return the total number of transactions
   */
  public int size() {
    return history.size();
  }

  /**
   * Get the current price of the position
   *
   * @return the current price as a BigDecimal
   */
  public double currentPrice() {
    try {
      return YahooFinance.get(ticker).getQuote().getPrice().doubleValue();
    } catch (IOException e) {
      System.out.println("Error");
      return -1.0;
    }
  }

  @Override
  public int compareTo(Position o) {
    return this.ticker.compareTo(o.ticker);
  }

  @Override
  public String toString() {
    return ticker + "\n\taverage cost per share: $" + decimalFormat(avgCostPerShare)
        + "\n\tcurrent price: $" + decimalFormat(currentPrice()) + "\n\tshares held: "
        + decimalFormat(totalSharesHeld) + "\n" + "\trealized gain: $" + decimalFormat(
        totalRealizedGain) + "\n\tunrealized gain: $" + decimalFormat(getUnrealized()) + "\n"
        + String.join("", Collections.nCopies(WIDTH, "-"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position other = (Position) o;
    return this.history.equals(other.history);
  }
}
