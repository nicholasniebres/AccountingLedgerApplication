package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Create an object from Ledger class
    private static Ledger ledger = new Ledger();
    // Open connection to csv
    private static FileManager fileManager = new FileManager("transactions.csv");
    // Encapsulated scanner for overall class
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Get the list of transactions from the file
        List<Transaction> savedTransactions = fileManager.loadTransactions();

        // 2. Loop through them and add each one to the ledger
        for (Transaction t : savedTransactions) {
            ledger.addTransaction(t);
        }
        System.out.println("\nWelcome to Bank of the Bay!");

        while (true) {
            System.out.println("\n--- HOME SCREEN ---\n");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("\nSelect an option: ");
            // Store the input into a variable and force uppercase it
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    handleEntry(true);
                    break;
                case "P":
                    handleEntry(false);
                    break;
                case "L":
                    showLedgerMenu();
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Please choose a valid option.");
            }
        }
    }


    private static void handleEntry(boolean isDeposit) {
        // Get input on entries
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine()); // Convert the line to with outer double parser method

        if (!isDeposit) {
            amount *= -1; // For a payment withdrawal, we can convert the amount to negative
        }
        // Instantiating a new transaction object using the following parameters
        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), desc, vendor, amount);

        ledger.addTransaction(t); // Calling addTransaction method(from Ledger class), passing t as argument of object.

        fileManager.saveTransaction(t); // Does the same but for saveTransactions method in FileManager

        System.out.println("Transaction save successful.");
    }


    private static void showLedgerMenu() {
        // Instead of ending the program with System.exit(0), this would graceful exit current loop and return to home screen.
        boolean inLedger = true;
        // So when stores as while true it run until false
        while (inLedger) {

            System.out.println("\n--- LEDGER MENU ---\n");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("\nSelect an option: ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {

                case "A":
                    // 1. Since it's a public list, the list can be used from the Ledger class
                    List<Transaction> allTransactions = ledger.getAll();
                    // 2. Loop through and print each one
                    for (Transaction t : allTransactions) {
                        System.out.println(t);
                    }
                    break;
                case "D":

                    List<Transaction> deposits = ledger.getDeposits();

                    for (Transaction t : deposits) {
                        System.out.println(t);
                    }
                    break;
                case "P":

                    List<Transaction> payments = ledger.getPayments();

                    for (Transaction t : payments) {
                        System.out.println(t);
                    }
                    break;
                case "R":

                    showReportsMenu(); // Shows new menu for reports
                    break;
                case "H":

                    inLedger = false; // Exits to Home Screen
                    break;
                default:

                    System.out.println("Invalid choice: " + input);
                    break;
            }
        }
    }


    private static void showReportsMenu() {

        boolean inReports = true;

        while (inReports) {
            System.out.println("\n--- REPORTS ---\n");
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year to Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Vendor Search");
            System.out.println("O) Back");
            System.out.print("\nSelect an option: ");
            String reportChoice = scanner.nextLine(); // No toUpperCase needed for numbers
            LocalDate now = LocalDate.now();

            switch (reportChoice) {
                case "1":
                    // Month To Date (From the 1st of this month until today)
                    LocalDate startOfMonth = now.withDayOfMonth(1);

                    List<Transaction> mtd = ledger.getTransactionsByDate(startOfMonth, now);

                    displayReport("Month To Date", mtd);

                    break;

                case "2": // Previous Month

                    LocalDate firstOfLastMonth = now.minusMonths(1).withDayOfMonth(1);

                    LocalDate lastOfLastMonth = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());

                    List<Transaction> lastMonth = ledger.getTransactionsByDate(firstOfLastMonth, lastOfLastMonth);

                    displayReport("Previous Month", lastMonth);

                    break;

                case "3": // Year To Date (From January 1st until today)

                    LocalDate startOfYear = now.withDayOfYear(1);

                    List<Transaction> ytd = ledger.getTransactionsByDate(startOfYear, now);

                    displayReport("Year To Date", ytd);

                    break;

                case "4": // Previous Year

                    LocalDate lastYearStart = now.minusYears(1).withDayOfYear(1);

                    LocalDate lastYearEnd = now.minusYears(1).withDayOfYear(now.minusYears(1).lengthOfYear());

                    List<Transaction> lastYear = ledger.getTransactionsByDate(lastYearStart, lastYearEnd);

                    displayReport("Previous Year", lastYear);

                    break;
                case "5":

                    System.out.print("Enter Vendor: ");

                    String v = scanner.nextLine();
                    // 1. Get the filtered list from the ledger
                    List<Transaction> vendorResults = ledger.getTransactionsByVendor(v);
                    // 2. Loop through the results and print them one by one
                    for (Transaction t : vendorResults) {
                        System.out.println(t);
                    }

                    break;
                case "0":

                    inReports = false; // Goes back to Ledger Menu

                    break;
                default:

                    System.out.println("Invalid choice in Reports: " + reportChoice);

                    break;
            }
        }
    }


    private static void displayReport(String title, List<Transaction> reportData) {

        System.out.println("\n( " + title + " )\n");

        if (reportData.isEmpty()) {
            System.out.println("No transactions found for this period.");

        }

            // Print each transaction using that formatting string
            for (Transaction t : reportData) {
                System.out.println("   Date   |   Time   |   Description   |   Vendor   |   Amount   ");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }
