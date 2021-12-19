package Transactions;

import java.time.LocalDate;
import java.util.*;

public class TransactionLog implements Iterable<Transaction>{
    Set<Transaction> log;

    public TransactionLog() {
        log = new TreeSet<>();
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
    public List<Transaction> searchByDate(LocalDate start, LocalDate end) {
        List<Transaction> results = new ArrayList<>();
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
    public List<Transaction> listBuy() {
        List<Transaction> results = new ArrayList<>();
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
    public List<Transaction> listSell() {
        List<Transaction> results = new ArrayList<>();
        for (Transaction t : log) {
            if (t instanceof Sell) {
                results.add(t);
            }
        }
        return results;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Transaction History\n");
        for (Transaction t : log) {
            output.append(t.toString()).append("\n");
        }
        return output.toString();
    }

    @Override
    public Iterator<Transaction> iterator() {
        return log.iterator();
    }
}
