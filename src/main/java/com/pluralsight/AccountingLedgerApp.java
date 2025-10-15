package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AccountingLedgerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // READ AND DISPLAY EXISTING TRANSACTIONS FROM FILE
        try {
            FileReader fileReader = new FileReader("Transaction.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //HOME SCREEN

        boolean running = true;

        while (running) {
            System.out.println("\n==================================");
            System.out.println("       ACCOUNTING LEDGER APP");
            System.out.println("==================================");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) View Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit(scanner); //Call a method
                    break;

                case "P":
                    makePayment(scanner);
                    break;

                case "L":
                    System.out.println("\n--- Ledger ---");
                    System.out.println("Ledger display");
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

    //ADD DEPOSIT
    static void addDeposit(Scanner scanner) {
        System.out.println("\n--- Add Deposit ---");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        saveTransaction(description, vendor, amount); //PAYMENT IS POSITIVE
    }

    //MAKE PAYMENT
    static void makePayment(Scanner scanner) {
        System.out.println("\n--- Make Payment (Debit) ---");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        saveTransaction(description, vendor, -Math.abs(amount)); //PAYMENT IS NEGATIVE
    }

    //SAVE TRANSACTION: SAVES THE TRANSACTION OBJECT TO THE CSV FILE
    static void saveTransaction(String description, String vendor, double amount) {
        String date = LocalDate.now().toString();
        String time = LocalTime.now().withNano(0).toString();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        //WRITE THE TRANSACTION TO THE CSV FILE (APPEND MODE)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(transaction.toString() + "\n");
            System.out.println("Transaction saved!");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
    //READ AND DISPLAY LEDGER

}