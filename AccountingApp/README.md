# Accounting Ledger CLI Application

## Description of the Project
This is a Java-based command-line application that simulates a basic accounting ledger. Users can record deposits and payments, view their full ledger, and generate reports based on filters like date ranges, transaction type, vendor, and more.

All data is stored in a pipe-separated `transactions.csv` file, and the application loads this file on startup and appends new transactions during execution.

---
## Features

### ✅ Main Menu
- **D** - Add Deposit
- **P** - Make Payment (stored as a negative amount)
- **L** - View Ledger (with filtering options)
- **X** - Exit

### ✅ Ledger Menu
- **A** - View All Transactions
- **D** - View Deposits
- **P** - View Payments
- **R** - Reports Menu
- **H** - Return to Home Menu

### ✅ Reports Menu
- **1** - Month-To-Date
- **2** - Previous Month
- **3** - Year-To-Date
- **4** - Previous Year
- **5** - Search by Vendor
- **6** - Custom Search (filter by date, vendor, amount, etc.)
- **0** - Back to Ledger Menu

---

## Technologies Used
- Java 17+
- `BufferedReader`, `BufferedWriter` for file I/O
- `LocalDate` and `LocalTime` for date/time handling
- Stored CSV file: `src/main/resources/transactions.csv`  --> file path 

---

## File Format
All transactions are stored in a CSV file with the following pipe-separated format:

```
date|time|description|vendor|amount
2023-04-15|10:13:25|Ergonomic Keyboard|Amazon|-89.50
2023-04-15|11:15:00|Invoice 1001 Paid|Joe|1500.00
```

- Dates use `yyyy-MM-dd`
- Times use `HH:mm:ss`
- Amounts for payments are stored as negative numbers 
- Whereas deposits are stored as positive numbers

---

## How to Run
1. Open project in your IDE (e.g. IntelliJ or Eclipse)
2. Ensure Java SDK 17+ is configured
3. Run the `MainApp.java` class
4. Interact with the menu in your terminal

---
## Interesting Code Example
```java
private static void filterTransactionsCustom(LocalDate startDate, LocalDate endDate, String description, String vendor, Double amount) {
    for (Transactions transactions : transactions) {
        boolean matches = true;
        if (startDate != null && transactions.getDate().isBefore(startDate)) matches = false;
        if (endDate != null && transactions.getDate().isAfter(endDate)) matches = false;
        if (description != null && !description.isEmpty() && !transactions.getDescription().contains(description)) matches = false;
        if (vendor != null && !vendor.isEmpty() && !transactions.getVendor().equalsIgnoreCase(vendor)) matches = false;
        if (amount != null && transactions.getAmount() != amount) matches = false;

        if (matches) {
            System.out.println(transactions);
        }
    }
}
```
**Why it’s interesting:** This method supports optional filters without requiring all fields to be filled in. It demonstrates clean control over dynamic query logic.

---


---

## Team Member(s)

- Enes Uzun
