package achadaga.stockportfolio.app;

import java.util.Scanner;

public class Service {

  private static final Scanner usrInput = new Scanner(System.in);

  public static String username() {
    System.out.print("enter your user name: ");
    String user = usrInput.nextLine();
    System.out.println("confirm the current user name: " + user);
    System.out.print("Y/n? ");
    char confirmation = usrInput.nextLine().charAt(0);
    while (confirmation != 'Y' && confirmation != 'y') {
      System.out.print("enter your user name: ");
      user = usrInput.nextLine();
      System.out.println("confirm the current user name: " + user);
      System.out.print("Y/n? ");
      confirmation = usrInput.nextLine().charAt(0);
    }
    return user;
  }

  public static int menuChoice() {
    System.out.print("select an option 1 - 4: ");
    char usrChoice = usrInput.nextLine().charAt(0);
    while (!validMenuChoice(usrChoice)) {
      System.out.print("please select a valid option 1 - 4: ");
      usrChoice = usrInput.nextLine().charAt(0);
    }
    return usrChoice - '0';
  }

  private static boolean validMenuChoice(char userChoice) {
    char[] choices = {Menu.ENTER_TRANSACTIONS, Menu.VIEW_TRANSACTIONS, Menu.SEE_PORTFOLIO,
        Menu.QUIT};
    for (char valid : choices) {
      if (userChoice == valid) {
        return true;
      }
    }
    return false;
  }

}
