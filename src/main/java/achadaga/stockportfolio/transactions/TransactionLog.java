package achadaga.stockportfolio.transactions;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TransactionLog implements Iterable<Transaction> {
    private final Set<Transaction> log;

    public TransactionLog() {
        log = new TreeSet<>();
    }

    public static void main(String[] args) {
        Transaction t1 = new Buy("AAPL", 139.5, 3, LocalDate.of(2021, 7, 20));
        Transaction t2 = new Buy("AAPL", 140.5, 3, LocalDate.of(2021, 7, 6));
        Transaction t3 = new Sell("AAPL", 142.3367, 6, LocalDate.of(2021, 8, 4));
        Transaction t4 = new Sell("AAPL", 145.70, 1, LocalDate.of(2021, 10, 17));
        Transaction t5 = new Sell("AAPL", 171.50, 3, LocalDate.of(2021, 11, 19));
        Transaction t6 = new Sell("AAPL", 172.50, 5, LocalDate.of(2021, 11, 19));
        TransactionLog log = new TransactionLog();
        log.addTransaction(t1);
        log.addTransaction(t2);
        log.addTransaction(t3);
        log.addTransaction(t4);
        log.addTransaction(t5);
        log.addTransaction(t6);
        System.out.println(log);
    }

    /**
     * Adds a new transaction in sorted order
     *
     * @param t Transaction to add
     */
    public void addTransaction(Transaction t) {
        log.add(t);
    }

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
    public TreeSet<Transaction> searchByDate(LocalDate start, LocalDate end) {
        TreeSet<Transaction> results = new TreeSet<>();
        for (Transaction t : log) {
            if (t.getDate().compareTo(start) >= 0 && t.getDate().compareTo(end) <= 0) {
                results.add(t);
            }
        }
        return results;
    }

    /**
     * @param ticker the target ticker to list all transactions for
     * @return a list of all transactions of this stock
     */
    public TreeSet<Transaction> searchByTicker(String ticker) {
        TreeSet<Transaction> results = new TreeSet<>();
        for (Transaction t : log) {
            if (t.getTicker().equals(ticker)) {
                results.add(t);
            }
        }
        return results;
    }

    /**
     * @return a list of all buy transactions
     */
    public TreeSet<Transaction> buys() {
        TreeSet<Transaction> results = new TreeSet<>();
        for (Transaction t : log) {
            if (t instanceof Buy) {
                results.add(t);
            }
        }
        return results;
    }

    /**
     * @return a list of all sale transactions
     */
    public TreeSet<Transaction> sells() {
        TreeSet<Transaction> results = new TreeSet<>();
        for (Transaction t : log) {
            if (t instanceof Sell) {
                results.add(t);
            }
        }
        return results;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Transaction History: \n");
        for (Transaction t : log) {
            output.append(t.toString());
        }
        return output.toString();
    }

    @Override
    public Iterator<Transaction> iterator() {
        return log.iterator();
    }
}
