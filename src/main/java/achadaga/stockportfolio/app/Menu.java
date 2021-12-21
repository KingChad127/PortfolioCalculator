package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;

public class Menu {

  // Menu options
  public static final char ENTER_TRANSACTIONS = '1';
  public static final char VIEW_TRANSACTIONS = '2';
  public static final char SEE_PORTFOLIO = '3';
  public static final char QUIT = '4';

  public static String userName;

  public static Portfolio intro() {
    System.out.println("stockportfolio and tracker 1.0 - Abhinav Chadaga");
    userName = Service.username();
    return new Portfolio(userName);
  }

  public static int displayMenuOptions(MenuHeader header) {
    if (header == MenuHeader.FIRST_TIME) {
      System.out.println("What would you like to do today?");
    } else {
      System.out.println("Anything else?");
    }
    System.out.println("\t1. enter transactions");
    System.out.println("\t2. view transaction log");
    System.out.println("\t3. see portfolio profit and losses");
    System.out.println("\t4. quit app");
    return Service.menuChoice();
  }
}
