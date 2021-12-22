package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Transaction;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.sound.sampled.Port;

public class Portfolio implements Iterable<Position> {

  // internal storage container
  private final Map<String, Position> portfolio;
  private final String user;

  public Portfolio(String user) {
    this.portfolio = new TreeMap<>();
    this.user = user;
  }

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
   * @return the number of positions the user holds
   */
  public int size() {
    return portfolio.size();
  }

  public Position findPosition(String ticker) {
    return portfolio.get(ticker);
  }

  private BigDecimal totalRealized() {
    BigDecimal total = new BigDecimal("0.0");
    for (Position p : portfolio.values()) {
      total = total.add(p.getRealized());
    }
    return total;
  }

  private BigDecimal totalUnrealized() {
    BigDecimal total = new BigDecimal("0.0");
    for (Position p : portfolio.values()) {
      total = total.add(p.getUnrealized());
    }
    return total;
  }

  public Portfolio winningPositions() {
    Map<String, Position> result = new TreeMap<>();
    for (Position p : this) {
      BigDecimal r = p.getRealized().add(p.getUnrealized());
      if (r.signum() >= 0) {
        result.put(p.getTicker(), p);
      }
    }
    return new Portfolio(result);
  }

  public Portfolio losingPositions() {
    Map<String, Position> result = new TreeMap<>();
    for (Position p : this) {
      BigDecimal r = p.getRealized().add(p.getUnrealized());
      if (r.signum() < 0) {
        result.put(p.getTicker(), p);
      }
    }
    return new Portfolio(result);
  }

  @Override
  public Iterator<Position> iterator() {
    return portfolio.values().iterator();
  }

  @Override
  public String toString() {
    StringBuilder output = user.equals("")? new StringBuilder() : new StringBuilder(user + "'s "
        + "Portfolio\n\n");
    for (Position p : portfolio.values()) {
      output.append(p.toString()).append('\n');
    }
    output.append("total realized gains/losses: $").append(totalRealized());
    output.append("\ntotal unrealized gains/losses: $").append(totalUnrealized());
    return output.toString();
  }
}
