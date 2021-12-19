package achadaga.stockportfolio.transactions;

import java.time.LocalDate;

public class Buy extends Transaction {
    public Buy(String ticker, double price, double quantity, LocalDate purchaseDate) {
        super(ticker, price, quantity, purchaseDate);
    }

    @Override
    public String toString() {
        double roundedPrice = Math.round(getPrice() * 100) / 100.0;
        return super.toString() + "\n\tBUY " + getTicker() + "\n\tpurchase price: $" +
                String.format("%.2f", roundedPrice) + "\n\tshares " + "bought: " + getQuantity() +
                "\n" + "-".repeat(WIDTH);
    }

}
