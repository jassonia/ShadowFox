# Banking System

A robust, object-oriented console application built in Java using Maven. The system simulates core banking functionalities like account creation, automated interest computation, and overdraft management with solid data validations and strict unit tests.

## 🚀 Features
- **Polymorphic Accounts:** Distinct logic pathways for `SavingsAccount` (with interest application) and `CurrentAccount` (with overdraft protection up to Rs. 2000).
- **Transaction Logs:** Automatically generates unique transaction IDs, timestamps, and history records for every deposit and withdrawal.
- **Robust Exception Handling:** Gracefully handles negative values, insufficient funds, and overdraft limits.
- **Unit Testing:** 100% test coverage using JUnit 5.

## 🛠️ Prerequisites
- Java JDK 17 or higher
- Apache Maven
## 💻 How to Run the App
To start the interactive console application, run the following command in your terminal:

```bash
mvn compile exec:java -Dexec.mainClass="com.bank.BankApp"