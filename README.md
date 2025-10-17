# Accounting Ledger Application

An accounting ledger application built with Java that allows users to track financial transactions for business or personal use.

## Features

- **Add Deposits** - Record incoming payments and deposits
- **Make Payments** - Track outgoing payments and debits
- **View Ledger** - Display all transactions, deposits only, or payments only
- **View Balance** - Check current account balance
- **Reports** - Generate various financial reports:
  - Month to Date
  - Previous Month
  - Year to Date
  - Previous Year
  - Search by Vendor

## How It Works

All transactions are stored in a CSV file (`transactions.csv`) with the following format:
```
date|time|description|vendor|amount
```

Example:
```
2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00
```
## Usage

### Home Screen
- **D** - Add Deposit
- **P** - Make Payment (Debit)
- **L** - View Ledger
- **B** - View Balance
- **X** - Exit

### Ledger Screen
- **A** - All Entries
- **D** - Deposits Only
- **P** - Payments Only
- **R** - Reports
- **H** - Home

### Reports Screen
- **1** - Month To Date
- **2** - Previous Month
- **3** - Year To Date
- **4** - Previous Year
- **5** - Search by Vendor
- **0** - Back

## Technologies Used

- Java
- File I/O (BufferedReader/BufferedWriter)
- LocalDate and LocalTime for date/time handling

## Project Structure

- `AccountingLedgerApp.java` - Main application with all menu logic
- `Transaction.java` - Transaction object class
- `transactions.csv` - Data storage file

## Code I found interesting
I find it interesting that I can create an interactive menu with this code!

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

## Author

Catrina Lee

## Acknowledgments

Developed as a capstone project for the Year Up Java Development Program.
