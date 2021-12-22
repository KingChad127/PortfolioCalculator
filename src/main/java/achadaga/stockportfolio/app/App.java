package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;
import achadaga.stockportfolio.transactions.TransactionLog;

public class App {

  public static void main(String[] args) {
    App stockportfolio = new App();
    stockportfolio.run();
  }

  public Portfolio intro() {
    System.out.println("stockportfolio and tracker 1.0 - Abhinav Chadaga");
    String userName = AppService.username();
    return new Portfolio(userName);
  }

  public int mainMenu(MenuHeader header) {
    if (header == MenuHeader.FIRST_TIME) {
      System.out.println("\nWhat would you like to do today?");
    } else {
      System.out.println("\nAnything else?");
    }
    System.out.println("\t1. enter transactions");
    System.out.println("\t2. view transaction log");
    System.out.println("\t3. see portfolio profit and losses");
    System.out.println("\t4. quit app");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4'});
  }

  public int transactionLogMenu() {
    System.out.println("\nOptions: ");
    System.out.println("\t1. search transactions by ticker");
    System.out.println("\t2. search transactions by date");
    System.out.println("\t3. list all BUY transactions");
    System.out.println("\t4. list all SELL transactions");
    System.out.println("\t5. remove existing transaction(s)");
    System.out.println("\t6. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4', '5', '6'});
  }

  public int portfolioMenu() {
    System.out.println("\nOptions: ");
    System.out.println("\t1. look up position");
    System.out.println("\t2. list winning positions");
    System.out.println("\t3. list losing positions");
    System.out.println("\t4. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4'});
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
        System.out.println("\n" + transactionLog);
        // open transaction log sub menu
        int tmChoice = transactionLogMenu();
        while (tmChoice != 6) {
          if (tmChoice == 1) {
            System.out.println("\n" + AppService.searchByTicker(transactionLog));
          } else if (tmChoice == 2) {
            System.out.println("\n" + AppService.searchByDate(transactionLog));
          } else if (tmChoice == 3) {
            System.out.println("\n" + transactionLog.buys());
          } else if (tmChoice == 4) {
            System.out.println("\n" + transactionLog.sells());
          } else if (tmChoice == 5) {
            System.out.println("\n" + AppService.removeTransactions(transactionLog, usrPortfolio));
          }
          tmChoice = transactionLogMenu();
        }
      } else if (usrChoice == 3) {
        // print out the log
        System.out.println("\n" + usrPortfolio);
        int pChoice = portfolioMenu();
        while (pChoice != 4) {
          if (pChoice == 1) {
            System.out.println(AppService.lookUpPostion(usrPortfolio));
          } else if (pChoice == 2) {
            System.out.println(usrPortfolio.winningPositions());
          } else if (pChoice == 3) {
            System.out.println(usrPortfolio.losingPositions());
          }
          pChoice = portfolioMenu();
        }

      }
      usrChoice = mainMenu(MenuHeader.OTHER);
    }
    System.out.println("thank you for using stockportfolio and tracker");
  }
}