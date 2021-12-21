package achadaga.stockportfolio.app;

public class App {

  public static final int ENTER_TRANSACTIONS = 1;
  public static final int VIEW_TRANSACTIONS = 2;
  public static final int SEE_PORTFOLIO = 3;
  public static final int QUIT = 4;

  public static void main(String[] args) {
    Menu.intro();
    int usrChoice = Menu.displayMenuOptions(MenuHeader.FIRST_TIME);
    while (usrChoice != 4) {
      if (usrChoice == ENTER_TRANSACTIONS) {


      } else if (usrChoice == VIEW_TRANSACTIONS) {

      } else if (usrChoice == SEE_PORTFOLIO) {

      }
      usrChoice = Menu.displayMenuOptions(MenuHeader.OTHER);
    }
    System.out.println("thank you for using stockportfolio and tracker");
  }
}