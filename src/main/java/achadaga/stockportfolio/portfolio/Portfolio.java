package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Transaction;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Portfolio implements Iterable<Position> {

  private final Map<String, Position> positions;
  private final String user;

  public Portfolio(String user) {
    this.user = user;
    this.positions = new TreeMap<>();
  }

  /**
   * Add a new Position to this portfolio if this transaction is for a new ticker. Otherwise, add
   * the transaction to an existing Position in the Portfolio
   *
   * @param t the transaction to add
   */
  public void addTransaction(Transaction t) {
    if (!positions.containsKey(t.getTicker())) {
      positions.put(t.getTicker(), new Position(t.getTicker()));
    }
    positions.get(t.getTicker()).addTransaction(t);
  }

  /**
   * @return the number of positions the user holds
   */
  public int size() {
    return positions.size();
  }

  @Override
  public Iterator<Position> iterator() {
    return positions.values().iterator();
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder(user + "'s Portfolio\n\n");
    for (Position p : positions.values()) {
      output.append(p.toString()).append('\n');
    }
    return output.toString();
  }
}
