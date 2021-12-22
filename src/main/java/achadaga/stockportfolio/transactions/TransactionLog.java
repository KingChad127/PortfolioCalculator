package achadaga.stockportfolio.transactions;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TransactionLog implements Iterable<Transaction> {

  private final Set<Transaction> log;

  /**
   * Construct an empty transactionLog
   */
  public TransactionLog() {
    log = new TreeSet<>();
  }

  /**
   * Construct a new transaction log from an existing TreeSet
   * @param ts TreeSet from which to construct a new transaction log
   */
  public TransactionLog(TreeSet<Transaction> ts) {
    log = new TreeSet<>(ts);
  }

  /**
   * Adds a new transaction in sorted order
   *
   * @param t Transaction to add
   */
  public void addTransaction(Transaction t) {
    log.add(t);
  }

  // TODO: remove transaction by ID

  /**
   * @return the total number of transactions made by the user
   */
  public int totalTransactions() {
    return log.size();
  }

  /**
   * @param start the starting date
   * @param end   the ending date
   * @return a list of all Transactions that were made in this date range
   */
  public TransactionLog searchByDate(LocalDate start, LocalDate end) {
    TreeSet<Transaction> results = new TreeSet<>();
    for (Transaction t : log) {
      if (t.getDate().compareTo(start) >= 0 && t.getDate().compareTo(end) <= 0) {
        results.add(t);
      }
    }
    return new TransactionLog(results);
  }

  /**
   * @param tgt single date to list all transactions for
   * @return a treeSet of all Transactions that were made in this date range
   */
  public TransactionLog searchByDate(LocalDate tgt) {
    return searchByDate(tgt, tgt);
  }

  /**
   * @param ticker the target ticker to list all transactions for
   * @return a list of all transactions of this stock
   */
  public TransactionLog searchByTicker(String ticker) {
    TreeSet<Transaction> results = new TreeSet<>();
    for (Transaction t : log) {
      if (t.getTicker().equals(ticker)) {
        results.add(t);
      }
    }
    return new TransactionLog(results);
  }

  /**
   * @return a list of all buy transactions
   */
  public TransactionLog buys() {
    TreeSet<Transaction> results = new TreeSet<>();
    for (Transaction t : log) {
      if (t instanceof Buy) {
        results.add(t);
      }
    }
    return new TransactionLog(results);
  }

  /**
   * @return a list of all sale transactions
   */
  public TransactionLog sells() {
    TreeSet<Transaction> results = new TreeSet<>();
    for (Transaction t : log) {
      if (t instanceof Sell) {
        results.add(t);
      }
    }
    return new TransactionLog(results);
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder("Transaction History: \n");
    for (Transaction t : log) {
      output.append(t.toString());
    }
    output.append("\ntotal number of transactions: ").append(totalTransactions());
    return output.toString();
  }

  @Override
  public Iterator<Transaction> iterator() {
    return log.iterator();
  }
}
