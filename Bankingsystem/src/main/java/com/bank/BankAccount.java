package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BankAccount {
    protected int accountNumber;
    protected String accountHolder;
    protected double balance;
    protected List<Transaction> transactions = new ArrayList<>();

    public BankAccount(int accNo, String name, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative!");
        }
        this.accountNumber = accNo;
        this.accountHolder = name;
        this.balance = balance;
        transactions.add(new Transaction("ACCOUNT_CREATED", balance));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount!");
        }
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdraw amount!");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance!");
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount));
    }

    public void checkBalance() {
        System.out.println("Balance: Rs" + balance);
    }

    public void showTransactions() {
        System.out.println("\n--- TRANSACTIONS ---");
        for (Transaction t : transactions) {
            t.display();
        }
    }

    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return Collections.unmodifiableList(transactions); }
    public abstract double calculateInterest();
}