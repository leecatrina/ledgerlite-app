package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    //constructor: create a new transaction
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

    //format transaction
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
