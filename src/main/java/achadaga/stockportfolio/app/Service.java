package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;
import achadaga.stockportfolio.transactions.Buy;
import achadaga.stockportfolio.transactions.Sell;
import achadaga.stockportfolio.transactions.Transaction;
import achadaga.stockportfolio.transactions.TransactionLog;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  public static char enterTransaction(TransactionLog transactionLog, Portfolio portfolio) {
    System.out.print("what type of transaction would you like to log? (BUY or SELL): ");
    String type = usrInput.nextLine();
    while (!type.equalsIgnoreCase("buy") && !type.equalsIgnoreCase("sell")) {
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
    String ticker = usrInput.nextLine();
    while (!validTicker(ticker)) {
      System.out.println("there was an error in retrieving that ticker");
      System.out.print("please try another: ");
      ticker = usrInput.nextLine();
    }
    ticker = ticker.toLowerCase();
    System.out.print(words[0] + " price: $");
    String p = usrInput.nextLine();
    while (validBD(p)) {
      System.out.print("please enter a valid " + words[0] + " price: ");
      p = usrInput.nextLine();
    }
    BigDecimal price = new BigDecimal(p);
    System.out.print("number of shares " + words[1] + ": ");
    String q = usrInput.nextLine();
    while (validBD(q)) {
      System.out.print("please enter a valid number of shares " + words[1] + ": ");
      q = usrInput.nextLine();
    }
    BigDecimal quantity = new BigDecimal(q);
    System.out.print(words[0] + " date (YYYY-MM-DD): ");
    String d = usrInput.nextLine();
    String[] dSplit = splitDate(d);
    while (!validDate(dSplit)) {
      System.out.print("please enter a valid date (YYYY-MM-DD): ");
      d = usrInput.nextLine();
      dSplit = splitDate(d);
    }
    LocalDate date = LocalDate.of(Integer.parseInt(dSplit[0]), Integer.parseInt(dSplit[1]),
        Integer.parseInt(dSplit[2]));
    Transaction t;
    t = type.equalsIgnoreCase("buy") ? new Buy(ticker, price, quantity, date)
        : new Sell(ticker, price, quantity, date);
    transactionLog.addTransaction(t);
    portfolio.addTransaction(t);
    System.out.print("add more? (Y/n) ");
    String inp = usrInput.nextLine().substring(0, 1);
    while (!inp.equalsIgnoreCase("y") && !inp.equalsIgnoreCase("n")) {
      System.out.print("(Y/n) ");
      inp = usrInput.nextLine().substring(0, 1);
    }
    return inp.charAt(0);
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
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  private static String[] splitDate(String date) {
    Scanner sc = new Scanner(date);
    String year = "";
    sc.useDelimiter(",|-|/|\s");
    String[] d = new String[3];
    int i = 0;
    while (sc.hasNext()) {
      d[i] = sc.next();
      i++;
    }
    return d;
  }

  private static boolean validDate(String[] date) {
    if (date[0].length() != 4) {
      return false;
    }
    try {
      LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
