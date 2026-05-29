package com.bank;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private final List<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount acc) {
        accounts.add(acc);
        System.out.println("Account added successfully.");
    }

    public BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        System.out.println("Account Not Found!");
        return null;
    }
}
