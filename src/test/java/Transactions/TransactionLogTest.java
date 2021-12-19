package Transactions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionLogTest {

    @Test
    void testAddTransaction() {
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

    @Test
    void testSearchByTicker() {
        Transaction t1 = new Buy("AAPL", new BigDecimal("139.50"), 3, LocalDate.of(2021, 7, 20));
        Transaction t2 = new Buy("AAPL", new BigDecimal("140.50"), 3, LocalDate.of(2021, 7, 6));
        Transaction t3 = new Sell("AAPL", new BigDecimal("142.33"), 6, LocalDate.of(2021, 8, 4));
        Transaction t4 = new Sell("AAPL", new BigDecimal("145.70"), 1, LocalDate.of(2021, 10, 17));
        TransactionLog log = new TransactionLog();
        log.addTransaction(t1);
        log.addTransaction(t2);
        log.addTransaction(t3);
        log.addTransaction(t4);
        assertEquals(new TreeSet<>(Arrays.asList(t1, t2, t3, t4)),
                log.searchByTicker("AAPL"));
    }

}