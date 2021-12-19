package achadaga.stockportfolio.transactions;

import java.time.LocalDate;

public class Sell extends Transaction {
    public Sell(String ticker, double salePrice, double quantity, LocalDate sellDate) {
        super(ticker, salePrice, quantity, sellDate);
    }

    @Override
    public String toString() {
        double roundedPrice = Math.round(getPrice() * 100) / 100.0;
        return super.toString() + "\n\tSELL " + getTicker() + "\n\tsale price: $" +
                String.format("%.2f", roundedPrice) + "\n\tshares " + "sold: " + getQuantity() +
                "\n" + "-".repeat(WIDTH);
    }
}

