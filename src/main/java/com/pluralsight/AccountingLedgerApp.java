package com.pluralsight;

import java.util.Scanner;

public class AccountingLedgerApp {
    public static void main(String[] args) {
        //HOME SCREEN

        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("\n--- Add Deposit ---");
                    break;

                case "P":
                    System.out.println("\n--- Make Payment (Debit) ---");
                    break;

                case "L":
                    System.out.println("\n--- Ledger ---");
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
    }
}
