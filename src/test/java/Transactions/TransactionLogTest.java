package Transactions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionLogTest {

    @Test
    public void testAdd() {
        Transaction t1 = new Buy("AAPL", new BigDecimal("139.50"), 3, LocalDate.of(2021, 7, 20));
        Transaction t2 = new Sell("AAPL", new BigDecimal("145.70"), 1, LocalDate.of(2021, 7, 30));
        TransactionLog log = new TransactionLog();
        log.addTransaction(t1);
        log.addTransaction(t2);
        assertEquals(2, log.totalTransactions());
        int[] hashcodes = {t2.hashCode(), t1.hashCode()};
        int i = 0;
        for (Transaction t : log) {
            assertEquals(hashcodes[i], t.hashCode());
            i++;
        }
    }

}