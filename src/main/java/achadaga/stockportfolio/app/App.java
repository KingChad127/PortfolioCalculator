package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;
import achadaga.stockportfolio.transactions.TransactionLog;

public class App {

  public static final int ENTER_TRANSACTIONS = 1;
  public static final int VIEW_TRANSACTIONS = 2;
  public static final int SEE_PORTFOLIO = 3;
  public static final int QUIT = 4;

  public static void main(String[] args) {
    Portfolio usrPortfolio = Menu.intro();
    TransactionLog transactionLog = new TransactionLog();
    int usrChoice = Menu.displayMenuOptions(MenuHeader.FIRST_TIME);
    while (usrChoice != 4) {
      if (usrChoice == ENTER_TRANSACTIONS) {
        char cont = Service.enterTransaction(transactionLog, usrPortfolio);
        while (cont == 'y') {
          cont = Service.enterTransaction(transactionLog, usrPortfolio);
        }
      } else if (usrChoice == VIEW_TRANSACTIONS) {
        System.out.println(transactionLog);
      } else if (usrChoice == SEE_PORTFOLIO) {
        System.out.println(usrPortfolio);
      }
      usrChoice = Menu.displayMenuOptions(MenuHeader.OTHER);
    }
    System.out.println("thank you for using stockportfolio and tracker");
  }
}