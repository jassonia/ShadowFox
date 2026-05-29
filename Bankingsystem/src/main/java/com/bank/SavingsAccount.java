package com.bank;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(int accNo, String name, double balance) {
        super(accNo, name, balance);
    }

    @Override
    public double calculateInterest() {
        return balance * 0.04;
    }

    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        transactions.add(new Transaction("INTEREST", interest));
        System.out.println("Interest of Rs" + interest + " applied successfully.");
    }
}