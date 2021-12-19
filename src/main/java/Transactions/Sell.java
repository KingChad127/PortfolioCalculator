package Transactions;

import java.time.LocalDate;

public class Sell extends Transaction {
    public Sell(String ticker, double salePrice, double quantity, LocalDate sellDate) {
        super(ticker, salePrice, quantity, sellDate);
    }

    @Override
    public String toString() {
        double roundedPrice = Math.round(getPrice() * 100) / 100.0;
        StringBuilder output = new StringBuilder();
        output.append("\n").append(getDate().toString()).append("\n\tSELL ").append(getTicker())
                .append("\n\tsale price: $").append(String.format("%.2f", roundedPrice))
                .append("\n\tshares ").append("sold: ").append(getQuantity()).append("\n");
        for (int i = 0; i < WIDTH; i++) {
            output.append('-');
        }
        return output.toString();
    }
}

