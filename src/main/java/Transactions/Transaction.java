package Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transaction implements Comparable<Transaction>{
    private final String ticker;
    private final BigDecimal price;
    private final double quantity; // support fractional shares as well
    private final LocalDate date;

    public Transaction(String ticker, BigDecimal price, double quantity, LocalDate date) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getPrice() {
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
     * occured on the same date, and negative otherwise
     */
    @Override
    public int compareTo(Transaction other) {
        return other.date.compareTo(this.getDate());
    }
}
