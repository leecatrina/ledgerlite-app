package com.pluralsight;

public class Transaction {
    String date;
    String time;
    String description;
    String vendor;
    double amount;

    //constructor: Lets you make a transaction
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //format
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
