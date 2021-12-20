package achadaga.stockportfolio.service;

import achadaga.stockportfolio.portfolio.Portfolio;
import java.io.IOException;
import java.util.Scanner;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class App {

  static Scanner keyboard;

  public static Portfolio intro() {
    System.out.println("Portfolio Tracker 1.0 - created by Abhinav Chadaga");
    keyboard = new Scanner(System.in);
    System.out.print("User name: ");
    String user = keyboard.nextLine();
    return new Portfolio(user);
  }

  public static int menu() {
    System.out.println("What would you like to do today?");
    System.out.println("\t1. enter transactions");
    System.out.println("\t2. view transaction history");
    System.out.println("\t3. view portfolio");
    System.out.println("\t4. exit");
    return keyboard.nextInt();
  }

  public static boolean tickerIsValid(String ticker) {
    try {
      Stock stock = YahooFinance.get(ticker);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static void main(String[] args) {
    Portfolio userPortfolio = intro();
    int choice = menu();
  }
}


