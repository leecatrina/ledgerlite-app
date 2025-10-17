package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountingLedgerApp {
    //Keeps all transaction in memory (you can filter and display them easily)
    //Class field = it's stored for the whole program

    private static final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions(); //Fills up the ArrayList with data from the CSV
        Scanner scanner = new Scanner(System.in);

        //========== HOMESCREEN MENU ============

        boolean running = true;

        while (running) {
            System.out.println("\n==================================");
            System.out.println("       ACCOUNTING LEDGER APP");
            System.out.println("==================================");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) View Ledger");
            System.out.println("B) View Balance");
            System.out.println("X) Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit(scanner); //User inputs info
                    break;

                case "P":
                    makePayment(scanner);
                    break;

                case "L":
                    ledgerMenu(scanner);
                    break;

                case "B":
                    double currentBalance = calculateBalance();
                    System.out.println("==================================");
                    System.out.printf("Current Balance: $%.2f%n", currentBalance);
                    System.out.println("==================================");
                    break;

                case "X":
                    System.out.println("\nExiting the application...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
        scanner.close();
    }

    //RUNS AT STARTUP/READS THE LIST. TRANSACTION LIST HAS EVERYTHING FROM THE FILE
    private static void loadTransactions() {
        transactions.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //skips the header row and any empty lines
                if (line.trim().isEmpty() || line.toLowerCase().startsWith("date")) continue;

                Transaction t = parseTransaction(line);
                if (t != null) transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("No transactions found yet.");
        }
    }

    //========== LEDGER MENU ===========

    private static void ledgerMenu(Scanner scanner) {
        boolean inLedger = true;

        while (inLedger) {
            System.out.println("\n========== LEDGER ==========");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice.toUpperCase()) {
                case "A":
                    viewAllEntries(); //User views info only
                    break;
                case "D":
                    viewDeposits();
                    break;
                case "P":
                    viewPayments();
                    break;
                case "R":
                    viewReportsMenu(scanner);
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    //========== REPORT MENU ============

    private static void viewReportsMenu(Scanner scanner) {
        boolean inReports = true;
        while (inReports) {
            System.out.println("\n========== REPORTS MENU ==========");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showMonthToDate();
                    // Month to date
                    break;
                case "2":
                    showPreviousMonth();
                    // Previous month
                    break;
                case "3":
                    showYearToDate();
                    // Year To Date
                    break;
                case "4":
                    showPreviousYear();
                    // Previous Year
                    break;
                case "5":
                    searchByVendor(scanner);
                    // Search by Vendor
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    //========== HOME SCREEN FUNCTIONS ===========

    // (ADD DEPOSIT)

    private static void addDeposit(Scanner scanner) {
        System.out.println("\n--- Add Deposit ---");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        //PAYMENT IS POSITIVE, CREATES A NEW TRANSACTION OBJECT AND WRITES IT INTO THE FILE
        saveTransaction(description, vendor, amount);
    }

    // (MAKE PAYMENT)

    private static void makePayment(Scanner scanner) {
        System.out.println("\n--- Make Payment (Debit) ---");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // PAYMENT IS NEGATIVE, CREATES A NEW TRANSACTION OBJECT AND WRITES IT INTO THE FILE
        saveTransaction(description, vendor, -Math.abs(amount));
    }

    // (SAVE TRANSACTION: SAVES THE TRANSACTION OBJECT TO THE CSV FILE)

    private static void saveTransaction(String description, String vendor, double amount) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        // (WRITE THE TRANSACTION TO THE CSV FILE (APPEND MODE))

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(transaction.toString() + "\n");
            transactions.add(transaction);
            System.out.println("Transaction saved!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // (CURRENT/VIEW BALANCE)
    //TURNS EACH LINE BACK INTO A TRANSACTION OBJECT
    private static Transaction parseTransaction(String line) {
        try {
            String[] parts = line.split("\\|"); //Splits text wherever there's a pipe |
            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);
            return new Transaction(date, time, description, vendor, amount);

        } catch (Exception e) {
            System.out.println("Error parsing transaction: " + line);
            e.printStackTrace();
            return null;
        }
    }
    //CALCULATE BALANCE

    private static double calculateBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {  // Loop through the ArrayList
            balance += t.getAmount();
        }
        return balance;
    }

    //========== LEDGER MENU FUNCTIONS ===========

    //VIEW ALL ENTRIES

    public static void viewAllEntries() {
        if (transactions.isEmpty()) { //Checks if there are NO transaction
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n--- ALL TRANSACTIONS ---");
            for (int i = transactions.size() - 1; i >= 0; i--) { // NEWEST FIRST, Loops it backwards
                System.out.println(transactions.get(i));
            }
        }
    }
    //VIEW DEPOSITS

    public static void viewDeposits() {
        System.out.println("\n--- DEPOSITS ---");
        for (int i = transactions.size() - 1; i >= 0; i--) { //Loops backwards to show NEWEST
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) { //Checks if the amount is POSITIVE (Money coming in)
                System.out.println(t);
            }
        }
    }
    //VIEW PAYMENTS

    public static void viewPayments() {
        System.out.println("\n--- PAYMENTS ---");
        for (int i = transactions.size() - 1; i >= 0; i--) { //Loops backwards to show NEWEST
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) { //Checks if amount is NEGATIVE (Money going out)
                System.out.println(t);
            }
        }
    }

//========== REPORT MENU FUNCTIONS ===========

    // 1) MONTH TO DATE
    private static void showMonthToDate() {
        System.out.println("\n--- MONTH TO DATE ---");
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1); //first day of the current month

        for (int i = transactions.size() - 1; i >= 0; i--) { //loops backwards
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(startOfMonth) && !t.getDate().isAfter(today)) {
                System.out.println(t); //prints from the start of this month to today
            }
        }
    }

    // 2) PREVIOUS MONTH
    private static void showPreviousMonth() {
        System.out.println("\n--- PREVIOUS MONTH ---");
        LocalDate today = LocalDate.now();
        LocalDate startOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = today.withDayOfMonth(1).minusDays(1);

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(startOfLastMonth) && !t.getDate().isAfter(endOfLastMonth)) {
                System.out.println(t); //prints transactions that fall in the previous month
            }
        }
    }

    // 3) YEAR TO DATE
    private static void showYearToDate() {
        System.out.println("\n--- YEAR TO DATE ---");
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(startOfYear) && !t.getDate().isAfter(today)) {
                System.out.println(t); //prints transactions from Jan 1 to today
            }
        }
    }

    // 4) PREVIOUS YEAR
    private static void showPreviousYear() {
        System.out.println("\n--- PREVIOUS YEAR ---");
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;
        LocalDate startOfLastYear = LocalDate.of(lastYear, 1, 1);
        LocalDate endOfLastYear = LocalDate.of(lastYear, 12, 31);

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(startOfLastYear) && !t.getDate().isAfter(endOfLastYear)) {
                System.out.println(t); //prints transactions only from last year
            }
        }
    }

    // 5) SEARCH BY VENDOR
    private static void searchByVendor(Scanner scanner) {
        System.out.print("\nEnter vendor name: ");
        String vendorSearch = scanner.nextLine().trim();

        System.out.println("\n--- TRANSACTIONS FOR: " + vendorSearch + " ---");
        boolean found = false;

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getVendor().equalsIgnoreCase(vendorSearch)) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for vendor: " + vendorSearch);
        }
    }
}