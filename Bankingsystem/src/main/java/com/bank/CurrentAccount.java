package com.bank;

public class CurrentAccount extends BankAccount {
    private final double overdraftLimit = 2000;

    public CurrentAccount(int accNo, String name, double balance) {
        super(accNo, name, balance);
    }

    @Override
    public double calculateInterest() {
        return 0;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdraw amount!");
        }
        if (amount > balance + overdraftLimit) {
            throw new IllegalStateException("Overdraft limit exceeded!");
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount));
    }
}