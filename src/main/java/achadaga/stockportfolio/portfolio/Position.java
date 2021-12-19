package achadaga.stockportfolio.portfolio;

import achadaga.stockportfolio.transactions.Buy;
import achadaga.stockportfolio.transactions.Transaction;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

public class Position {
    private final String ticker;
    private final Set<Transaction> history; // contains all transactions of this ticker
    private double marketValue;
    private double totalShares;
    private double averageCost;
    private double totalRealizedReturn;
    private double totalUnrealizedReturn;
    private double portfolioDiversity;

    public Position(String ticker) {
        this.ticker = ticker;
        this.history = new TreeSet<>();
    }

    public void addBuy(Buy t) {
        history.add(t);

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

}
