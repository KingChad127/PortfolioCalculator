package achadaga.stockportfolio.transactions;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction implements Comparable<Transaction>{
    private final String ticker;
    private final double price;
    private final double quantity; // support fractional shares as well
    private final LocalDate date;
    private final UUID transactionID;

    public static final int WIDTH = 40;

    public Transaction(String ticker, double price, double quantity, LocalDate date) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.transactionID = UUID.randomUUID();
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * @param other transaction to compare this one to
     * @return a positive number if this transaction is older than other, 0 if this transaction
     * occurred on the same date, and negative otherwise
     */
    @Override
    public int compareTo(Transaction other) {
        int dateComparison = other.date.compareTo(this.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        } else {
            return other.transactionID.compareTo(this.transactionID);
        }
    }

    @Override
    public String toString() {
        return "\n" + getDate().toString() + "\n\ttransactionID: " + transactionID;
    }
}
