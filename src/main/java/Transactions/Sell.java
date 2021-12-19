package Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sell extends Transaction {
    public Sell(String ticker, BigDecimal salePrice, double quantity, LocalDate sellDate) {
        super(ticker, salePrice, quantity, sellDate);
    }

    public String toString() {
        return getDate().toString() + "\n\tSELL " + getTicker() + "\n\tsale price: " +
                getPrice() + "\n\tshares sold: " + getQuantity() + "\n";
    }
}
