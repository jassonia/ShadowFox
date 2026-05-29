package com.bank;

import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();

        while (true) {
            System.out.println("\n===== SMART BANK SYSTEM =====");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Balance");
            System.out.println("6. Transactions");
            System.out.println("7. Apply Interest (Savings)");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt(); sc.nextLine();
                        System.out.print("Name: "); String name = sc.nextLine();
                        System.out.print("Balance: "); double bal = sc.nextDouble();
                        service.addAccount(new SavingsAccount(no, name, bal));
                    }
                    case 2 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt(); sc.nextLine();
                        System.out.print("Name: "); String name = sc.nextLine();
                        System.out.print("Balance: "); double bal = sc.nextDouble();
                        service.addAccount(new CurrentAccount(no, name, bal));
                    }
                    case 3 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt();
                        System.out.print("Amount: "); double amt = sc.nextDouble();
                        BankAccount acc = service.findAccount(no);
                        if (acc != null) acc.deposit(amt);
                    }
                    case 4 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt();
                        System.out.print("Amount: "); double amt = sc.nextDouble();
                        BankAccount acc = service.findAccount(no);
                        if (acc != null) acc.withdraw(amt);
                    }
                    case 5 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt();
                        BankAccount acc = service.findAccount(no);
                        if (acc != null) acc.checkBalance();
                    }
                    case 6 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt();
                        BankAccount acc = service.findAccount(no);
                        if (acc != null) acc.showTransactions();
                    }
                    case 7 -> {
                        System.out.print("Acc No: "); int no = sc.nextInt();
                        BankAccount acc = service.findAccount(no);
                        if (acc instanceof SavingsAccount s) {
                            s.applyInterest();
                        } else if (acc != null) {
                            System.out.println("Interest can only be applied to Savings Accounts!");
                        }
                    }
                    case 8 -> {
                        System.out.println("Exiting System.");
                        sc.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Choice!");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Operation Failed: " + e.getMessage());
            }
        }
    }
}