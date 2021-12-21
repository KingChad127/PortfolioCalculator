package achadaga.stockportfolio.app;

import java.util.Scanner;

public class Service {

  private static final Scanner usrInput = new Scanner(System.in);

  public static String username() {
    System.out.print("enter your user name: ");
    String user = usrInput.nextLine();
    System.out.println("Confirm the current user name: " + user);
    System.out.print("Y/n? ");
    char confirmation = usrInput.nextLine().charAt(0);
    while (confirmation != 'Y' && confirmation != 'y') {
      System.out.print("enter your user name: ");
      user = usrInput.nextLine();
      System.out.println("Confirm the current user name: " + user);
      System.out.print("Y/n? ");
      confirmation = usrInput.nextLine().charAt(0);
    }
    return user;
  }
}
