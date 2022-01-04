package com.achadaga.portfoliocalculator.app;

import static com.achadaga.portfoliocalculator.app.Constants.WIDTH;

import com.achadaga.portfoliocalculator.entities.Buy;
import com.achadaga.portfoliocalculator.entities.Portfolio;
import com.achadaga.portfoliocalculator.entities.Position;
import com.achadaga.portfoliocalculator.entities.Sell;
import com.achadaga.portfoliocalculator.entities.Transaction;
import com.achadaga.portfoliocalculator.entities.TransactionLog;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
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
    System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
    return usrChoice - '0';
  }

  /**
   * Allow a user to enter a correctly formatted CSV file to enter all transactions at once.
   *
   * @param transactionLog to add to
   * @param portfolio      to add to
   */
  public static void enterTransactionByFile(TransactionLog transactionLog, Portfolio portfolio) {
    File f = collectFile();
    if (f == null) {
      System.out.println("No transactions were added");
    } else {
      // try to add contents of file
      try {
        FileReader fileReader = new FileReader(f);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
        String[] first = csvReader.readNext();
        String[] magicVal = {"transactiontype", "ticker", "price", "quantity", "date", "dayorder"};
        if (!Arrays.equals(first, magicVal)) {
          System.out.println("File not identified as a transaction csv file.");
          return;
        }
        String[] line;
        while ((line = csvReader.readNext()) != null) {
          String ticker = line[1];
          double price = Double.parseDouble(line[2]);
          double quantity = Double.parseDouble(line[3]);
          int[] d = strArrToIntArr(splitDate(line[4]));
          LocalDate date = LocalDate.of(d[2], d[0], d[1]);
          int dayOrder = Integer.parseInt(line[5]);
          if (!line[0].equalsIgnoreCase("buy") && !line[0].equalsIgnoreCase("sell")) {
            System.out.println("Invalid File");
            return;
          }
          Transaction t =
              line[0].equalsIgnoreCase("buy") ? new Buy(ticker, price, quantity, date, dayOrder)
                  : new Sell(ticker, price, quantity, date, dayOrder);
          transactionLog.addTransaction(t);
          portfolio.addTransaction(t);
        }
        csvReader.close();
      } catch (Exception e) {
        System.out.println("There was an error in reading the file");
      }
    }
  }

  /**
   * @return a user selected file. If null, the user cancelled the operation
   */
  private static File collectFile() {
    CSVFileChooser CSVFileChooser = new CSVFileChooser();
    boolean cont = true;
    File file = CSVFileChooser.openFile();
    while (file == null && cont) {
      System.out.print("Would you like to try again? (Y/n) ");
      char input = usrInput.nextLine().charAt(0);
      if (input == 'n' || input == 'N') {
        cont = false;
      } else {
        file = CSVFileChooser.openFile();
      }
    }
    return file;
  }

  /**
   * Allow a user to enter a transaction
   *
   * @param transactionLog add this transaction to this log
   * @param portfolio      add this transaction to this portfolio
   * @return true if the user wants to keep adding transactions, false if the user is done
   */
  public static boolean enterTransaction(TransactionLog transactionLog, Portfolio portfolio) {
    // collect type of transaction
    System.out.print("what type of transaction would you like to log? (BUY or SELL): ");
    String type = usrInput.nextLine();
    while (!type.equalsIgnoreCase("buy") && !type.equalsIgnoreCase("sell")) {
      System.out.print("please enter BUY or SELL: ");
      type = usrInput.nextLine();
    }
    // used for print statements
    String[] words = new String[2];
    if (type.equalsIgnoreCase("buy")) {
      words[0] = "purchase";
      words[1] = "bought";
    } else {
      words[0] = "sale";
      words[1] = "sold";
    }
    String prompt, reprompt;
    // collect ticker symbol
    prompt = "ticker symbol: ";
    String ticker = collectTicker(prompt);

    // collect transaction price
    prompt = words[0] + " price: $";
    reprompt = "please enter a valid " + words[0] + " price: $";
    double price = collectDouble(prompt, reprompt);

    // collect number of shares
    prompt = "number of shares " + words[1] + ": ";
    reprompt = "please enter a valid number of shares " + words[1] + ": ";
    double quantity = collectDouble(prompt, reprompt);

    // collect date of transactions
    prompt = words[0] + " date (MM-DD-YYYY): ";
    reprompt = "please enter a valid date (MM-DD-YYYY): ";
    LocalDate date = collectDate(prompt, reprompt);

    // collect the day order of transaction
    prompt = "transaction order in day (enter 1, 2, 3...): ";
    reprompt = "please enter an integer greater 0: ";
    int ofDay = collectInt(prompt, reprompt);

    // we have the information needed to generate a transaction
    // create a new transaction based on the user input
    Transaction t;
    t = type.equalsIgnoreCase("buy") ? new Buy(ticker, price, quantity, date, ofDay)
        : new Sell(ticker, price, quantity, date, ofDay);

    // add this transaction to both the transaction log and the user portfolio
    transactionLog.addTransaction(t);
    portfolio.addTransaction(t);

    // confirm whether the user wants to add more transactions
    System.out.print("add more? (Y/n) ");
    String inp = usrInput.nextLine().substring(0, 1);
    while (!inp.equalsIgnoreCase("y") && !inp.equalsIgnoreCase("n")) {
      System.out.print("(Y/n) ");
      inp = usrInput.nextLine().substring(0, 1);
    }
    System.out.println(String.join("", Collections.nCopies(WIDTH, "-")));
    return inp.charAt(0) == 'y' || inp.charAt(0) == 'Y';
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
    String prompt, reprompt;
    if (inp.equalsIgnoreCase("range")) {
      // collect start date
      prompt = "enter the start date (MM-DD-YYYY): ";
      reprompt = "please enter a valid date (MM-DD-YYYY): ";
      LocalDate start = collectDate(prompt, reprompt);
      // collect end date
      prompt = "enter the end date (MM-DD-YYYY): ";
      reprompt = "please enter a valid date (MM-DD-YYYY): ";
      LocalDate end = collectDate(prompt, reprompt);

      System.out.println("transactions that meet your criteria: ");
      return transactionLog.searchByDate(start, end);
    } else {
      // collect single date
      prompt = "enter date (MM-DD-YYYY): ";
      reprompt = "please enter a valid date (MM-DD-YYYY): ";
      LocalDate d = collectDate(prompt, reprompt);

      System.out.println("transactions that meet your criteria: ");
      return transactionLog.searchByDate(d);
    }
  }

  /**
   * Search the transaction by a specified ticker
   *
   * @param transactionLog the transaction log to search through
   * @return a transaction log containing all transactions with the specified ticker
   */
  public static TransactionLog searchByTicker(TransactionLog transactionLog) {
    String prompt = "enter ticker: ";
    String ticker = collectTicker(prompt);
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
   * Return the Position matching the user inputted ticker symbol
   *
   * @param p the Portfolio to look up from
   * @return the matching Position
   */
  public static Position lookUpPosition(Portfolio p) {
    String prompt = "enter ticker: ";
    String ticker = collectTicker(prompt);
    return p.findPosition(ticker);
  }

  /**
   * Save the contents of this transaction log to an external CSV file to be loaded into another
   * session
   *
   * @param transactionLog the transaction log from which to read from
   */
  public static void saveSession(TransactionLog transactionLog) {
    System.out.print("Would you like to save your session? (Y/n) ");
    String inp = usrInput.nextLine().substring(0, 1);
    while (!inp.equalsIgnoreCase("y") && !inp.equalsIgnoreCase("n")) {
      System.out.print("(Y/n) ");
      inp = usrInput.nextLine().substring(0, 1);
    }

    if (inp.equalsIgnoreCase("y")) {
      // save the results to a CSV file
      CSVFileChooser fileChooser = new CSVFileChooser();
      File f = fileChooser.saveFile();
      try {
        FileWriter writer = new FileWriter(f);
        ICSVWriter csvWriter = new CSVWriterBuilder(writer).withSeparator(',').build();
        csvWriter.writeNext(
            new String[]{"transactiontype", "ticker", "price", "quantity", "date", "dayorder"},
            false);
        for (Transaction transaction : transactionLog) {
          String[] line = new String[6];
          line[0] = transaction instanceof Buy ? "buy" : "sell";
          line[1] = transaction.getTicker();
          line[2] = Double.toString(transaction.getPrice());
          line[3] = Double.toString(transaction.getNumShares());
          LocalDate date = transaction.getDate();
          line[4] = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
          line[5] = String.valueOf(transaction.getOfDay());
          csvWriter.writeNext(line, false);
        }
        csvWriter.close();
      } catch (Exception e) {
        System.out.println("Error in writing the file");
      }
    }
  }

  /**
   * Close the keyboard after the user is done with the program
   */
  public static void closeKeyboard() {
    usrInput.close();
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
   * Prompt the user, collect and verify a valid ticker symbol
   *
   * @return a valid ticker
   */
  private static String collectTicker(String prompt) {
    System.out.print(prompt);
    String ticker = usrInput.nextLine();
    while (invalidTicker(ticker)) {
      System.out.println("there was an error in retrieving that ticker");
      System.out.print("please try another: ");
      ticker = usrInput.nextLine();
    }
    return ticker.toUpperCase();
  }

  /**
   * Prompt the user, collect and verify a valid LocalDate value
   *
   * @param prompt   the first prompt the user sees
   * @param reprompt a reprompt should the user enter an invalid value
   * @return a valid LocalDate
   */
  private static LocalDate collectDate(String prompt, String reprompt) {
    System.out.print(prompt);
    String inp = usrInput.nextLine();
    String[] d = splitDate(inp);
    while (invalidDate(d)) {
      System.out.print(reprompt);
      inp = usrInput.nextLine();
      d = splitDate(inp);
    }
    int[] date = strArrToIntArr(d);
    return LocalDate.of(date[2], date[0], date[1]);
  }

  /**
   * Prompt the user, collect and verify a valid int
   *
   * @param prompt   the first prompt the user sees
   * @param reprompt a reprompt should the user enter an invalid value
   * @return a valid LocalDate
   */
  private static int collectInt(String prompt, String reprompt) {
    System.out.print(prompt);
    String inp = usrInput.nextLine();
    while (invalidInt(inp.substring(0, 1))) {
      System.out.print(reprompt);
      inp = usrInput.nextLine();
    }
    return Integer.parseInt(inp.substring(0, 1));
  }

  /**
   * Prompt the user, collect and verify a valid BigDecimal value
   *
   * @param prompt   the first prompt the user sees
   * @param reprompt the reprompt should the user enter an invalid value
   * @return a valid BigDecimal
   */
  private static double collectDouble(String prompt, String reprompt) {
    System.out.print(prompt);
    String p = usrInput.nextLine();
    while (isValidDouble(p)) {
      System.out.print(reprompt);
      p = usrInput.nextLine();
    }
    return Double.parseDouble(p);
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
   * Validate the user inputted double. Used for validating user input of price and quantity
   *
   * @param s string representation of a possible double
   * @return false if s can be converted to a double, true otherwise.
   */
  private static boolean isValidDouble(String s) {
    try {
      Double.parseDouble(s);
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
    sc.useDelimiter("[,|/|\\-|\\s|.]");
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
    if (date[2].length() != 4) {
      return true;
    }
    try {
      int[] d = strArrToIntArr(date);
      LocalDate localDate = LocalDate.of(d[2], d[0], d[1]);
      if (localDate.isAfter(LocalDate.now())) {
        return false;
      }
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * Verify whether a string can be converted into an integer
   *
   * @param s to be turned into an int
   * @return true if s cannot be turned into an int, false if otherwise
   */
  private static boolean invalidInt(String s) {
    try {
      int x = Integer.parseInt(s);
      return x < 1;
    } catch (Exception e) {
      return true;
    }
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
