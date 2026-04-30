# Bank of Nicholas - Accounting Ledger Application

Welcome to the **Bank of Nicholas**! This is a command-line Java application designed to help users track their financial health. It allows for recording deposits and payments, viewing transaction history (ledger), and generating detailed financial reports.

## Features

### 1. Transaction Management
- **Add Deposits:** Record incoming money with descriptions, vendors, and amounts.
- **Make Payments:** Record outgoing expenses (automatically handled as negative values).
- **Persistent Storage:** All transactions are automatically saved to and loaded from a `transactions.csv` file, ensuring data persists across application restarts.

### 2. Ledger Viewing
- **All Transactions:** View a complete history of every entry.
- **Deposits Only:** Filter the list to only see incoming funds.
- **Payments Only:** Filter the list to only see outgoing expenses.

### 3. Financial Reporting
Generate custom reports based on specific timeframes or criteria:
- **Month to Date:** Transactions from the 1st of the current month.
- **Previous Month:** A summary of the entire last month.
- **Year to Date:** All transactions since January 1st.
- **Previous Year:** A summary of the entire last calendar year.
- **Vendor Search:** Filter transactions by a specific vendor name.

## Project Structure

The application is built using Object-Oriented Programming (OOP) principles:

- **`Main.java`**: The entry point of the application. Handles the user interface, menu navigation, and user input.
- **`Ledger.java`**: Manages the collection of transactions and contains the logic for filtering and retrieving specific data sets.
- **`Transaction.java`**: A POJO (Plain Old Java Object) representing a single financial record (Date, Time, Description, Vendor, Amount).
- **`FileManager.java`**: Handles File I/O operations, specifically reading from and writing to the `transactions.csv` file.

## How to Run

1.  **Prerequisites:** Ensure you have Java (JDK) installed on your system.
2.  **Compilation:**
    ```bash
    javac com/pluralsight/*.java
    ```
3.  **Execution:**
    ```bash
    java com.pluralsight.Main
    ```

## Usage

When you run the application, you will be presented with the **Home Screen**:
- Press `D` to add a deposit.
- Press `P` to record a payment.
- Press `L` to access the Ledger and Reports menus.
- Press `X` to safely exit the application.

Inside the **Ledger Menu**, you can select `R` to run specific reports to analyze your spending habits and income over time.

## Data Format
The application expects and produces a `transactions.csv` file with the following structure:
`Date|Time|Description|Vendor|Amount`
