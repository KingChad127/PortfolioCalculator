package com.achadaga.portfoliocalculator.app;

import static com.achadaga.portfoliocalculator.app.Constants.WIDTH;

import com.achadaga.portfoliocalculator.app.Constants.MenuHeader;
import com.achadaga.portfoliocalculator.entities.Portfolio;
import com.achadaga.portfoliocalculator.entities.Position;
import com.achadaga.portfoliocalculator.entities.TransactionLog;
import java.util.Collections;

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
    System.out.println("PortfolioTracker pre-release v0.2.1 - Abhinav Chadaga");
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
  public int transactionLogPostMenu() {
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
   * Display the transaction log menu after the user has viewed they're log
   *
   * @return the user's menu choice
   */
  public int transactionLogPreMenu() {
    System.out.println("Options: ");
    System.out.println("\t1. select a valid transaction input file");
    System.out.println("\t2. enter transactions manually");
    System.out.println("\t3. back");
    return AppService.menuChoice(new char[]{'1', '2', '3'});
  }

  /**
   * Display the portfolio menu after user has seen their portfolio
   *
   * @return the user's menu choice
   */
  public int portfolioMenu() {
    System.out.println("Options: ");
    System.out.println("\t1. look up position");
    System.out.println("\t2. list winning positions");
    System.out.println("\t3. list losing positions");
    System.out.println("\t4. return to main menu");
    return AppService.menuChoice(new char[]{'1', '2', '3', '4'});
  }

  public int portfolioValidationMenu() {
    System.out.println("Options: ");
    System.out.println("\t1. edit your transaction file then select this option");
    System.out.println("\t2. add more positions manually");
    System.out.println("\t3. remove transactions manually");
    System.out.println("\t4. quit");
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
        int tPreChoice = transactionLogPreMenu();
        if (tPreChoice != 3) {
          if (tPreChoice == 1) {
            // add transactions from CSV file
            AppService.enterTransactionByFile(transactionLog, usrPortfolio);
          } else {
            // tPreChoice == 2
            boolean cont = AppService.enterTransaction(transactionLog, usrPortfolio);
            // keep entering transactions until the user signals he or she is done
            while (cont) {
              cont = AppService.enterTransaction(transactionLog, usrPortfolio);
            }
          }
        }
        // verify that the user portfolio is valid
        boolean valid = usrPortfolio.validateAndCalculatePortfolio();
        while (!valid) {
          System.out.println("one or more positions were found with less than 0 shares.");
          int vChoice = portfolioValidationMenu();
          if (vChoice == 1) {
            AppService.enterTransactionByFile(transactionLog, usrPortfolio);
          } else if (vChoice == 2) {
            boolean cont = AppService.enterTransaction(transactionLog, usrPortfolio);
            // keep entering transactions until the user signals he or she is done
            while (cont) {
              cont = AppService.enterTransaction(transactionLog, usrPortfolio);
            }
          } else if (vChoice == 3) {
            System.out.println(transactionLog);
            System.out.println(
                AppService.removeTransactions(transactionLog, usrPortfolio).printSubLog());
          } else {
            break;
          }
          valid = usrPortfolio.validateAndCalculatePortfolio();
        }
      } else if (usrChoice == 2) {
        // view transaction history
        System.out.println(transactionLog);
        System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
        // open transaction log sub menu
        int tmChoice = transactionLogPostMenu();
        while (tmChoice != 6) {
          if (tmChoice == 1) {
            System.out.println(AppService.searchByTicker(transactionLog).printSubLog());
          } else if (tmChoice == 2) {
            System.out.println(AppService.searchByDate(transactionLog).printSubLog());
          } else if (tmChoice == 3) {
            System.out.println("all BUY transactions");
            System.out.println(transactionLog.buys().printSubLog());
            System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
          } else if (tmChoice == 4) {
            System.out.println("all SELL transactions");
            System.out.println(transactionLog.sells().printSubLog());
            System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
          } else if (tmChoice == 5) {
            System.out.println(
                AppService.removeTransactions(transactionLog, usrPortfolio).printSubLog());
          }
          tmChoice = transactionLogPostMenu();
        }
      } else if (usrChoice == 3) {
        // print out the log
        System.out.println(usrPortfolio);
        System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
        int pChoice = portfolioMenu();
        while (pChoice != 4) {
          if (pChoice == 1) {
            Position p = AppService.lookUpPosition(usrPortfolio);
            if (p == null) {
              System.out.println("no positions with that ticker were found\n");
            } else {
              System.out.println(p);
            }
            System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
          } else if (pChoice == 2) {
            Portfolio winning = usrPortfolio.winningPositions();
            if (winning.size() == 0) {
              System.out.println("there are 0 winning positions\n");
            } else {
              System.out.println("all winning positions: \n");
              System.out.println(winning);
            }
            System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
          } else if (pChoice == 3) {
            Portfolio losing = usrPortfolio.losingPositions();
            if (losing.size() == 0) {
              System.out.println("there are 0 losing positions\n");
            } else {
              System.out.println("all losing positions: \n");
              System.out.println(losing);
            }
            System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
          }
          pChoice = portfolioMenu();
        }
      }
      usrChoice = mainMenu(MenuHeader.OTHER);
    }
    // ask to save transaction log
    AppService.saveSession(transactionLog);

    AppService.closeKeyboard();
    System.out.println("thank you for using portfoliocalculator and tracker");
  }
}