package Transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Buy extends Transaction {
    public Buy(String ticker, BigDecimal price, double quantity, LocalDate purchaseDate) {
        super(ticker, price, quantity, purchaseDate);
    }

    public String toString() {
        return getDate().toString() + "\n\tBUY " + getTicker() + "\n\tpurchase price: " +
                getPrice() + "\n\tshares sold: " + getQuantity();
    }
}
