package com.achadaga.stockportfolio.app;

import static com.achadaga.stockportfolio.transactions.Transaction.WIDTH;

import com.achadaga.stockportfolio.portfolio.Portfolio;
import com.achadaga.stockportfolio.portfolio.Position;
import com.achadaga.stockportfolio.transactions.TransactionLog;

public class App {

  public static void main(String[] args) {
    App stockportfolio = new App();
    stockportfolio.run();
  }

  /**
   * Introduce the program. Collect user's name and create an empty portfolio with the user's
   * chosen name
   *
   * @return an empty portfolio
   */
  public Portfolio intro() {
    System.out.println("stockportfolio and tracker pre-release v0.1.0 - Abhinav Chadaga");
    String userName = AppService.username();
    return new Portfolio(userName);
  }

  /**
   * Display the main menu options
   *
   * @param header determines what header is printed depending on how many times the user has
   *               seen the menu
   * @return the user's menu choice
   */
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

  /**
   * Display the transaction log menu after the user has viewed they're log
   *
   * @return the user's menu choice
   */
  public int transactionLogMenu() {
    //System.out.println("-".repeat(WIDTH));
    System.out.println("Options: ");
    System.out.println("\t1. search transactions by ticker");
    System.out.println("\t2. search transactions by date");
    System.out.println("\t3. list all BUY transactions");
    System.out.println("\t4. list all SELL transactions");
    System.out.println("\t5. remove existing transaction(s)");
    System.out.println("\t6. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4', '5', '6'});
  }

  /**
   * Display the portfolio menu after user has seen their portfolio
   *
   * @return the user's menu choice
   */
  public int portfolioMenu() {
    //System.out.println("-".repeat(WIDTH));
    System.out.println("Options: ");
    System.out.println("\t1. look up position");
    System.out.println("\t2. list winning positions");
    System.out.println("\t3. list losing positions");
    System.out.println("\t4. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4'});
  }

  /**
   * Driver for the app. Controls main app/menu logic and runs the appropriate the AppServices
   */
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
        System.out.println("-".repeat(WIDTH));
        // open transaction log sub menu
        int tmChoice = transactionLogMenu();
        while (tmChoice != 6) {
          if (tmChoice == 1) {
            System.out.println(AppService.searchByTicker(transactionLog).printSubLog());
          } else if (tmChoice == 2) {
            System.out.println(AppService.searchByDate(transactionLog).printSubLog());
          } else if (tmChoice == 3) {
            System.out.println("all BUY transactions");
            System.out.println(transactionLog.buys().printSubLog());
            System.out.println("-".repeat(WIDTH));
          } else if (tmChoice == 4) {
            System.out.println("all SELL transactions");
            System.out.println(transactionLog.sells().printSubLog());
            System.out.println("-".repeat(WIDTH));
          } else if (tmChoice == 5) {
            System.out.println(AppService.removeTransactions(transactionLog, usrPortfolio).printSubLog());
          }
          tmChoice = transactionLogMenu();
        }
      } else if (usrChoice == 3) {
        // print out the log
        System.out.println(usrPortfolio);
        System.out.println("-".repeat(WIDTH));
        int pChoice = portfolioMenu();
        while (pChoice != 4) {
          if (pChoice == 1) {
            Position p = AppService.lookUpPosition(usrPortfolio);
            if (p == null) {
              System.out.println("no positions with that ticker were found\n");
            } else {
              System.out.println(p);
            }
            System.out.println("-".repeat(WIDTH));
          } else if (pChoice == 2) {
            Portfolio winning = usrPortfolio.winningPositions();
            if (winning.size() == 0) {
              System.out.println("there are 0 winning positions\n");
            } else {
              System.out.println("all winning positions: \n");
              System.out.println(winning);
            }
            System.out.println("-".repeat(WIDTH));
          } else if (pChoice == 3) {
            Portfolio losing = usrPortfolio.losingPositions();
            if (losing.size() == 0) {
              System.out.println("there are 0 losing positions\n");
            } else {
              System.out.println("all losing positions: \n");
              System.out.println(losing);
            }
            System.out.println("-".repeat(WIDTH));
          }
          pChoice = portfolioMenu();
        }

      }
      usrChoice = mainMenu(MenuHeader.OTHER);
    }
    AppService.closeKeyboard();
    System.out.println("thank you for using stockportfolio and tracker");
  }
}