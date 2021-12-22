package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Transaction;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Portfolio implements Iterable<Position> {

  // internal storage container
  private final Map<String, Position> portfolio;
  private final String user;

  public Portfolio(String user) {
    this.user = user;
    this.portfolio = new TreeMap<>();
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
      total = total.add(p.getTotalRealized());
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

  @Override
  public Iterator<Position> iterator() {
    return portfolio.values().iterator();
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder(user + "'s Portfolio\n\n");
    for (Position p : portfolio.values()) {
      output.append(p.toString()).append('\n');
    }
    output.append("\ntotal realized gains/losses: $").append(totalRealized()).append("\n");
    output.append("\ntotal unrealized gains/losses: $").append(totalUnrealized()).append("\n");
    return output.toString();
  }
}
