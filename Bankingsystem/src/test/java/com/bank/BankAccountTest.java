package com.bank;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void testSavingsDepositAndWithdrawal() {
        BankAccount savings = new SavingsAccount(101, "Alice", 5000.0);
        
        savings.deposit(1000.0);
        assertEquals(6000.0, savings.getBalance());

        savings.withdraw(2000.0);
        assertEquals(4000.0, savings.getBalance());
    }

    @Test
    public void testSavingsOverdraftPrevention_ThrowsException() {
        BankAccount savings = new SavingsAccount(101, "Alice", 5000.0);
        
        assertThrows(IllegalStateException.class, () -> {
            savings.withdraw(6000.0); // Savings can't go below 0
        });
    }

    @Test
    public void testCurrentAccountOverdraftAllowed() {
        BankAccount current = new CurrentAccount(102, "Bob", 1000.0);
        
        // Allowed to withdraw past balance up to an extra Rs 2000 overdraft limit
        current.withdraw(2500.0);
        assertEquals(-1500.0, current.getBalance());
    }

    @Test
    public void testCurrentAccountExceedingOverdraft_ThrowsException() {
        BankAccount current = new CurrentAccount(102, "Bob", 1000.0);
        
        assertThrows(IllegalStateException.class, () -> {
            current.withdraw(4000.0); // Exceeds balance + 2000 limit
        });
    }

    @Test
    public void testTransactionHistoryLogging() {
        BankAccount savings = new SavingsAccount(103, "Charlie", 3000.0);
        savings.deposit(500.0);
        
        List<Transaction> history = savings.getTransactions();
        
        assertEquals(2, history.size());
        assertEquals("ACCOUNT_CREATED", history.get(0).getType());
        assertEquals(3000.0, history.get(0).getAmount());
        
        assertEquals("DEPOSIT", history.get(1).getType());
        assertEquals(500.0, history.get(1).getAmount());
    }
}