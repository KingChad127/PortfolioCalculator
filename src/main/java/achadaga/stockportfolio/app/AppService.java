package achadaga.stockportfolio.app;

import achadaga.stockportfolio.portfolio.Portfolio;
import achadaga.stockportfolio.transactions.Buy;
import achadaga.stockportfolio.transactions.Sell;
import achadaga.stockportfolio.transactions.Transaction;
import achadaga.stockportfolio.transactions.TransactionLog;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class AppService {

  private static final Scanner usrInput = new Scanner(System.in);

  /**
   * Collect and validate user's name
   *
   * @return the user's confirmed username
   */
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

  /**
   * Confirm and validate the user's menu choice
   *
   * @return a valid menu option
   */
  public static int menuChoice(char[] menu) {
    System.out.print("select an option 1 - " + menu.length + ": ");
    char usrChoice = usrInput.nextLine().charAt(0);
    while (!validMenuChoice(menu, usrChoice)) {
      System.out.print("please select a valid option 1 - " + menu.length + ": ");
      usrChoice = usrInput.nextLine().charAt(0);
    }
    return usrChoice - '0';
  }

  /**
   * Validate the user's menu choice
   *
   * @param menuChoices char array of all valid menu options
   * @param userChoice  the user's pre-validation choice
   * @return true if this is a valid choice, false otherwise
   */
  private static boolean validMenuChoice(char[] menuChoices, char userChoice) {
    for (char valid : menuChoices) {
      if (userChoice == valid) {
        return true;
      }
    }
    return false;
  }

  /**
   * Allow a user to enter a transaction
   *
   * @param transactionLog add this transaction to this log
   * @param portfolio      add this transaction to this portfolio
   * @return true if the user wants to keep adding transactions, false if the user is done
   */
  public static boolean enterTransaction(TransactionLog transactionLog, Portfolio portfolio) {
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
    while (invalidTicker(ticker)) {
      System.out.println("there was an error in retrieving that ticker");
      System.out.print("please try another: ");
      ticker = usrInput.nextLine();
    }
    ticker = ticker.toLowerCase();
    System.out.print(words[0] + " price: $");
    String p = usrInput.nextLine();
    while (inValidDB(p)) {
      System.out.print("please enter a valid " + words[0] + " price: ");
      p = usrInput.nextLine();
    }
    BigDecimal price = new BigDecimal(p);
    System.out.print("number of shares " + words[1] + ": ");
    String q = usrInput.nextLine();
    while (inValidDB(q)) {
      System.out.print("please enter a valid number of shares " + words[1] + ": ");
      q = usrInput.nextLine();
    }
    BigDecimal quantity = new BigDecimal(q);
    System.out.print(words[0] + " date (YYYY-MM-DD): ");
    String d = usrInput.nextLine();
    String[] dSplit = splitDate(d);
    while (invalidDate(dSplit)) {
      System.out.print("please enter a valid date (YYYY-MM-DD): ");
      d = usrInput.nextLine();
      dSplit = splitDate(d);
    }
    int[] dIntSplit = strArrToIntArr(dSplit);
    LocalDate date = LocalDate.of(dIntSplit[0], dIntSplit[1], dIntSplit[2]);
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
    return inp.charAt(0) == 'y';
  }

  /**
   * Validate the user inputted ticker symbol using the YahooFinance API
   *
   * @param ticker user inputted ticker symbol
   * @return false if this is a valid ticker, true otherwise
   */
  private static boolean invalidTicker(String ticker) {
    try {
      Stock stock = YahooFinance.get(ticker);
      return !stock.isValid();
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * Validate the user inputted BigDecimal. Used for validating user input of price and quantity
   *
   * @param s string representation of a possible BigDecimal
   * @return false if s can be converted to a BigDecimal, true otherwise.
   */
  private static boolean inValidDB(String s) {
    try {
      new BigDecimal(s);
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * split date string into YYYY-MM-DD.
   *
   * @param date user input string
   * @return an array of three strings splitting the date to YYYY-MM-DD.
   */
  private static String[] splitDate(String date) {
    Scanner sc = new Scanner(date);
    String year = "";
    sc.useDelimiter("[,|/|\\-|\\s]");
    String[] d = new String[3];
    int i = 0;
    while (sc.hasNext() && i < 3) {
      d[i] = sc.next();
      i++;
    }
    return d;
  }

  /**
   * Verify whether a split date can be used to create a LocalDate
   *
   * @param date split date string array
   * @return false if date can be used to create a LocalDate, true otherwise
   */
  private static boolean invalidDate(String[] date) {
    if (date[0].length() != 4) {
      return true;
    }
    try {
      int[] d = strArrToIntArr(date);
      LocalDate localDate = LocalDate.of(d[0], d[1], d[2]);
      if (localDate.isAfter(LocalDate.now())) {
        return false;
      }
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * Search the existing transaction log for transactions that fall on a specific date, or in a
   * range of dates
   *
   * @param transactionLog look for valid transactions in this log
   */
  public static TransactionLog searchByDate(TransactionLog transactionLog) {
    System.out.print("single date or range of dates (SINGLE or RANGE): ");
    String inp = usrInput.nextLine();
    while (!inp.equalsIgnoreCase("single") && !inp.equalsIgnoreCase("range")) {
      System.out.print("please enter SINGLE or RANGE");
      inp = usrInput.nextLine();
    }
    if (inp.equalsIgnoreCase("range")) {
      System.out.print("enter the start date (YYYY-MM-DD): ");
      String start = usrInput.nextLine();
      String[] startSplit = splitDate(start);
      while (invalidDate(startSplit)) {
        System.out.print("please enter a valid date (YYYY-MM-DD): ");
        start = usrInput.nextLine();
        startSplit = splitDate(start);
      }
      int[] s = strArrToIntArr(startSplit);
      System.out.print("enter the end date (YYYY-MM-DD): ");
      String end = usrInput.nextLine();
      String[] endSplit = splitDate(end);
      while (invalidDate(endSplit)) {
        System.out.print("please enter a valid date (YYYY-MM-DD): ");
        end = usrInput.nextLine();
        endSplit = splitDate(end);
      }
      int[] e = strArrToIntArr(endSplit);
      return transactionLog.searchByDate(LocalDate.of(s[0], s[1], s[2]),
          LocalDate.of(e[0], e[1], e[2]));
    } else {
      System.out.print("enter date (YYYY-MM-DD): ");
      String date = usrInput.nextLine();
      String[] dateSplit = splitDate(date);
      while (invalidDate(dateSplit)) {
        System.out.print("please enter a valid date (YYYY-MM-DD): ");
        date = usrInput.nextLine();
        dateSplit = splitDate(date);
      }
      int[] d = strArrToIntArr(dateSplit);
      System.out.println("transactions that meet your criteria: ");
      return transactionLog.searchByDate(LocalDate.of(d[0], d[1], d[2]));
    }
  }

  public static TransactionLog searchByTicker(TransactionLog transactionLog) {
    System.out.print("enter ticker: ");
    String ticker = usrInput.nextLine();
    while (invalidTicker(ticker)) {
      System.out.println("there was an error in retrieving that ticker");
      System.out.print("please try another: ");
      ticker = usrInput.nextLine();
    }
    System.out.println("transactions that meet your criteria: ");
    return transactionLog.searchByTicker(ticker);
  }

  /**
   * Remove transactions from the log using UUID
   *
   * @param transactionLog the transaction log to remove from
   * @return a TransactionLog of all the removed transactions
   */
  public static TransactionLog removeTransactions(TransactionLog transactionLog, Portfolio p) {
    System.out.println("enter the id's of transactions to remove (separate by space): ");
    String inp = usrInput.nextLine();
    Scanner sc = new Scanner(inp);
    List<String> ids = new ArrayList<>();
    while (sc.hasNext()) {
      ids.add(sc.next());
    }
    List<UUID> uuids = strToUUIDs(ids);
    System.out.println("Here are the transactions that you removed: ");
    return transactionLog.removeTransactionsByID(uuids, p);
  }

  /**
   * Convert a String array to an int array
   *
   * @param a String array to convert
   * @return return an int array
   */
  private static int[] strArrToIntArr(String[] a) {
    int[] result = new int[a.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = Integer.parseInt(a[i]);
    }
    return result;
  }

  /**
   * Convert a list of ids as Strings to a list of UUIDs as uuids. Omits UUIDs that can't be
   * converted
   *
   * @param ids a list of ids in String form
   * @return a list of uuids as UUID objects
   */
  private static List<UUID> strToUUIDs(List<String> ids) {
    List<UUID> uuids = new ArrayList<>();
    for (String id : ids) {
      try {
        UUID uuid = UUID.fromString(id);
        uuids.add(uuid);
      } catch (Exception ignored) {
        // ignore strings that can't be converted
      }
    }
    return uuids;
  }
}
