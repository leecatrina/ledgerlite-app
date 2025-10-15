package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    //constructor: creates a new transaction
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //getters: returns date

    public String getDate() {
        return date;
    }
    //returns time
    public String getTime() {
        return time;
    }
    //returns description
    public String getDescription() {
        return description;
    }
    //returns vendor
    public String getVendor() {
        return vendor;
    }
    //returns amount
    public double getAmount() {
        return amount;
    }
    //No Setters because we do NOT need to change any transaction details once they're recorded!

    //toString Method: formats transaction
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
