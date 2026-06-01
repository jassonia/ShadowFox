package com.bank;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private final String type;
    private final double amount;
    private final LocalDateTime time;

    public Transaction(String type, double amount) {
        this.transactionId = UUID.randomUUID().toString().substring(0, 8); // Clean, short ID
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }

    public void display() {
        System.out.println(transactionId + " | " + type + " | Rs" + amount + " | " + time);
    }
}