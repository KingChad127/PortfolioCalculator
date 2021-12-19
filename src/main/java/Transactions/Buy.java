package Transactions;

import java.time.LocalDate;

public class Buy extends Transaction {
    public Buy(String ticker, double price, double quantity, LocalDate purchaseDate) {
        super(ticker, price, quantity, purchaseDate);
    }

    @Override
    public String toString() {
        double roundedPrice = Math.round(getPrice() * 100) / 100.0;
        StringBuilder output = new StringBuilder();
        output.append("\n").append(getDate().toString()).append("\n\tBUY ").append(getTicker())
                .append("\n\tpurchase price: $").append(String.format("%.2f", roundedPrice))
                .append("\n\tshares ").append("bought: ").append(getQuantity()).append("\n");
        for (int i = 0; i < WIDTH; i++) {
            output.append('-');
        }
        return output.toString();
    }

}
