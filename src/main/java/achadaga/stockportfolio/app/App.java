package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;
import achadaga.stockportfolio.transactions.Transaction;
import achadaga.stockportfolio.transactions.TransactionLog;
import java.util.TreeSet;

public class App {

  public Portfolio intro() {
    System.out.println("stockportfolio and tracker 1.0 - Abhinav Chadaga");
    String userName = AppService.username();
    return new Portfolio(userName);
  }

  public int mainMenu(MenuHeader header) {
    if (header == MenuHeader.FIRST_TIME) {
      System.out.println("What would you like to do today?");
    } else {
      System.out.println("Anything else?");
    }
    System.out.println("\t1. enter transactions");
    System.out.println("\t2. view transaction log");
    System.out.println("\t3. see portfolio profit and losses");
    System.out.println("\t4. quit app");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4'});
  }

  public int transactionLogMenu() {
    System.out.println("Options: ");
    System.out.println("\t1. search transactions by ticker");
    System.out.println("\t2. search transactions by date");
    System.out.println("\t3. list all BUY transactions");
    System.out.println("\t4. list all SELL transactions");
    System.out.println("\t5. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4', '5'});
  }

  public void searchTransactionsByDate() {
    System.out.println("search transactions by date:");

  }

  public void run() {
    Portfolio usrPortfolio = intro();
    TransactionLog transactionLog = new TransactionLog();
    int usrChoice = mainMenu(MenuHeader.FIRST_TIME);
    while (usrChoice != 4) {
      // enter transactions
      if (usrChoice == 1) {
        boolean cont = AppService.enterTransaction(transactionLog, usrPortfolio);
        // keep entering transactions until the user signals he or she is done
        while (cont) {
          cont = AppService.enterTransaction(transactionLog, usrPortfolio);
        }
      } else if (usrChoice == 2) {
        // view transaction history
        System.out.println(transactionLog);
        // open transaction log sub menu
        int tmChoice = transactionLogMenu();
        while (tmChoice != 5) {
          if (tmChoice == 1) {
            System.out.println(AppService.searchByTicker(transactionLog));
          } else if (tmChoice == 2) {
            System.out.println(AppService.searchByDate(transactionLog));
          } else if (tmChoice == 3) {
            System.out.println(transactionLog.buys());
          } else if (tmChoice == 4) {
            System.out.println(transactionLog.sells());
          }
          tmChoice = transactionLogMenu();
        }
      } else if (usrChoice == 3) {
        System.out.println(usrPortfolio);
      }
      usrChoice = mainMenu(MenuHeader.OTHER);
    }
    System.out.println("thank you for using stockportfolio and tracker");
  }
}