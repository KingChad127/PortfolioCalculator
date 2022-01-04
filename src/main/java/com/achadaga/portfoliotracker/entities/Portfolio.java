package com.achadaga.portfoliotracker.entities;

import static com.achadaga.portfoliotracker.app.Constants.decimalFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Portfolio implements Iterable<Position> {

  // internal storage container
  private final Map<String, Position> portfolio;
  private final String user;

  /**
   * Construct an empty portfolio
   *
   * @param user the name of the owner of the portfolio
   */
  public Portfolio(String user) {
    this.portfolio = new TreeMap<>();
    this.user = user;
  }

  /**
   * Construct a portfolio with no owner from an existing map
   *
   * @param map the map from which to build this portfolio
   */
  public Portfolio(Map<String, Position> map) {
    this.portfolio = new TreeMap<>(map);
    this.user = "";
  }

  /**
   * Add a new Position to this portfolio if this transaction is for a new ticker. Otherwise, add
   * the transaction to an existing Position in the Portfolio
   *
   * @param t the transaction to add
   */
  public void addTransaction(Transaction t) {
    if (!portfolio.containsKey(t.getTicker())) {
      portfolio.put(t.getTicker(), new Position(t.getTicker()));
    }
    portfolio.get(t.getTicker()).addTransaction(t);
  }

  /**
   * Empty out the user's portfolio while keeping the user's name
   */
  public void resetPortfolio() {
    portfolio.clear();
  }

  /**
   * @return the number of positions the user holds
   */
  public int size() {
    return portfolio.size();
  }

  public Position findPosition(String ticker) {
    return portfolio.get(ticker);
  }

  /**
   * @return the total realized gains from this portfolio
   */
  private double totalRealized() {
    double total = 0.0;
    for (Position p : portfolio.values()) {
      total += p.getRealized();
    }
    return total;
  }

  /**
   * @return the total unrealized gains from this portfolio
   */
  private double totalUnrealized() {
    double total = 0.0;
    for (Position p : portfolio.values()) {
      total += p.getUnrealized();
    }
    return total;
  }

  /**
   * @return a sub portfolio of all the positions that are currently making >= $0 for the user
   */
  public Portfolio winningPositions() {
    Map<String, Position> result = new TreeMap<>();
    for (Position p : this) {
      double r = p.getRealized() + p.getUnrealized();
      if (r >= 0.0) {
        result.put(p.getTicker(), p);
      }
    }
    return new Portfolio(result);
  }

  /**
   * @return a sub portfolio of all the positions that are currently making < $0 for the user.
   */
  public Portfolio losingPositions() {
    Map<String, Position> result = new TreeMap<>();
    for (Position p : this) {
      double r = p.getRealized() + p.getUnrealized();
      if (r < 0.0) {
        result.put(p.getTicker(), p);
      }
    }
    return new Portfolio(result);
  }

  /**
   * Validate the user portfolio by ensuring that all positions have a non-negative number of
   * shares and that transaction history never went negative
   *
   * @return true if this portfolio is valid, false otherwise
   */
  public boolean validateAndCalculatePortfolio() {
    for (Position p : this) {
      boolean valid = p.calculate();
      if (!valid) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Iterator<Position> iterator() {
    return portfolio.values().iterator();
  }

  @Override
  public String toString() {
    StringBuilder output =
        user.equals("") ? new StringBuilder() : new StringBuilder(user + "'s " + "Portfolio\n\n");
    for (Position p : portfolio.values()) {
      output.append(p.toString()).append('\n');
    }
    output.append("total realized gains/losses: $").append(decimalFormat(totalRealized()));
    output.append("\ntotal unrealized gains/losses: $").append(decimalFormat(totalUnrealized()));
    return output.toString();
  }
}
