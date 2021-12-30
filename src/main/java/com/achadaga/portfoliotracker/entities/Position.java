package com.achadaga.portfoliotracker.entities;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.TreeSet;
import yahoofinance.YahooFinance;

public class Position implements Comparable<Position> {

  private final String ticker;
  private final Set<Transaction> history; // contains all transactions of this ticker
  private BigDecimal totalCostOfPurchasedShares;
  private BigDecimal totalSharesHeld;
  private BigDecimal totalSharesBought;
  private BigDecimal avgCostPerShare;
  private BigDecimal totalRealizedGain;

  public Position(String ticker) {
    this.ticker = ticker;
    this.history = new TreeSet<>();
    this.totalCostOfPurchasedShares = new BigDecimal("0.0");
    this.totalSharesHeld = new BigDecimal("0.0");
    this.avgCostPerShare = new BigDecimal("0.0");
    this.totalRealizedGain = new BigDecimal("0.0");
    this.totalSharesBought = new BigDecimal("0.0");
  }

  /**
   * Add a transaction to this position
   *
   * @param t transaction
   */
  public void addTransaction(Transaction t) {
    history.add(t);
    if (t instanceof Buy) {
      totalCostOfPurchasedShares = totalCostOfPurchasedShares.add(
          t.getPrice().multiply(t.getQuantity()));
      totalSharesHeld = totalSharesHeld.add(t.getQuantity());
      totalSharesBought = totalSharesBought.add(t.getQuantity());
      avgCostPerShare = totalCostOfPurchasedShares.divide(totalSharesBought, RoundingMode.HALF_UP);
    } else if (t instanceof Sell) {
      totalSharesHeld = totalSharesHeld.subtract(t.getQuantity());
      //      totalCostOfPurchasedShares =
      //          totalCostOfPurchasedShares.subtract(t.getQuantity().multiply(t.getPrice()));
      BigDecimal diff = t.getPrice().subtract(avgCostPerShare);
      totalRealizedGain = totalRealizedGain.add(diff.multiply(t.getQuantity()));

    }
  }

  /**
   * Remove a single transaction from this position
   *
   * @param t the transaction to remove
   */
  public void removeTransaction(Transaction t) {
    history.remove(t);
    // recalculate
    if (t instanceof Buy) {
      totalCostOfPurchasedShares = totalCostOfPurchasedShares.subtract(
          t.getPrice().multiply(t.getQuantity()));
      totalSharesHeld = totalSharesHeld.subtract(t.getQuantity());
      avgCostPerShare = totalCostOfPurchasedShares.divide(totalSharesHeld, RoundingMode.HALF_UP);
    } else if (t instanceof Sell) {
      totalSharesHeld = totalSharesHeld.add(t.getQuantity());
      BigDecimal diff = t.getPrice().subtract(avgCostPerShare);
      totalRealizedGain = totalRealizedGain.subtract(diff.multiply(t.getQuantity()));
    }
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
  public BigDecimal getUnrealized() {
    return currentPrice().subtract(avgCostPerShare).multiply(totalSharesHeld);
  }

  /**
   * @return the realized gain for this position. The realized gain is the actual money made from
   * selling shares of this position
   */
  public BigDecimal getRealized() {
    return totalRealizedGain;
  }

  /**
   * @return the total number of shares currently held
   */
  public BigDecimal getTotalSharesHeld() {
    return totalSharesHeld;
  }

  /**
   * @return the average purchase cost per share
   */
  public BigDecimal getAvgCostPerShare() {
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
  public BigDecimal currentPrice() {
    try {
      return YahooFinance.get(ticker).getQuote().getPrice();
    } catch (IOException e) {
      System.out.println("Error");
      return null;
    }
  }

  @Override
  public int compareTo(Position o) {
    return this.ticker.compareTo(o.ticker);
  }

  @Override
  public String toString() {
    return ticker + "\n\taverage cost per share: $" + avgCostPerShare + "\n\tcurrent price: $"
        + currentPrice() + "\n\tshares held: " + totalSharesHeld + "\n\trealized gain: $"
        + totalRealizedGain + "\n\tunrealized gain: $" + getUnrealized() + "\n";
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
