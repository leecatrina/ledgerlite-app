package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    //constructor: creates a new transaction
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //getters: returns date

    public LocalDate getDate() {
        return date;
    }
    //returns time
    public LocalTime getTime() {
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
    @Override
    public String toString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
