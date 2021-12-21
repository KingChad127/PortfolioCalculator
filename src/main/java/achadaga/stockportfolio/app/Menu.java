package achadaga.stockportfolio.app;

public class Menu {
  // Menu options
  public static final int ENTER_TRANSACTIONS = 1;
  public static final int VIEW_TRANSACTIONS = 2;
  public static final int SEE_PORTFOLIO = 3;
  public static final int QUIT = 4;

  public static void intro() {
    System.out.println("stockportfolio and tracker 1.0 - Abhinav Chadaga");
    String user = Service.username();
    // DEBUG!!!!
    System.out.println("Success! User \"" + user + "\" created");
  }
}
