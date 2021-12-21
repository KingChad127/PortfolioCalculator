package achadaga.stockportfolio.app;

import java.math.BigDecimal;
import java.util.Scanner;
import yahoofinance.YahooFinance;

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

  public static char enterTransaction() {
    System.out.print("what type of transaction would you like to log? (BUY or SELL): ");
    String type = usrInput.nextLine();
    if (!type.equalsIgnoreCase("buy") && !type.equalsIgnoreCase("sell")) {
      System.out.print("please enter BUY or SELL: ");
      type = usrInput.nextLine();
    }
    String[] words = new String[2];
    if (type.equalsIgnoreCase("buy")) {
      words[0] = "purchase";
      words[1] = "bought";
    } else {
      words[0] = "sale";
      words[1] = "sold";
    }
    System.out.print("ticker symbol: ");
    String inp = usrInput.nextLine();
    while (!validTicker(inp)) {
      System.out.println("there was an error in retrieving that ticker");
      System.out.print("please try another: ");
      inp = usrInput.nextLine();
    }
    inp = inp.toLowerCase();
    System.out.print(words[0] + "price: $");
    inp = usrInput.nextLine();
    while (!validBD(inp)) {
      System.out.print("please enter a valid " + words[0] + " price: ");
      inp = usrInput.nextLine();
    }
    BigDecimal price = new BigDecimal(inp);
    System.out.println("number of shares " + words[1] + ": ");
    inp = usrInput.nextLine();
    while (!validBD(inp)) {
      System.out.print("please enter a valid number of shares " + words[1] + ": ");
      inp = usrInput.nextLine();
    }
    BigDecimal quantity = new BigDecimal(inp);
    System.out.print(words[0] + " date (YYYY-MM-DD): ");
    inp = usrInput.nextLine();
  }

  private static boolean validTicker(String ticker) {
    try {
      YahooFinance.get(ticker);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean validBD(String price) {
    try {
      new BigDecimal(price);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean validDate(String date) {
    Scanner sc = new Scanner(date);
    String year = "";
    sc.useDelimiter(",|-|/|\s");
  }
}
