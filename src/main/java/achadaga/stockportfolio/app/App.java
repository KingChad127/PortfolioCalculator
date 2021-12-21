package achadaga.stockportfolio.app;

public class App {

  public static void main(String[] args) {
    Menu.intro();
    int usrChoice = Menu.displayMenuOptions(MenuHeader.FIRST_TIME);
    while (usrChoice != 4) {
      System.out.println("\ncomputing...\n");
      usrChoice = Menu.displayMenuOptions(MenuHeader.OTHER);
    }
    System.out.println("thank you for using stockportfolio and tracker");
  }
}