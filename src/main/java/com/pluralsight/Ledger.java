package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Ledger {

    private List<Transaction> transactions;


    public Ledger() {
        // Whenever Ledger is called, create a new empty ArrayList for transactions
        this.transactions = new ArrayList<>();
    }


    public void addTransaction(Transaction t) {
        // Adds the specific transaction to the ledger
        transactions.add(t);
    }


    public List<Transaction> getAll() {

        // 1. Create a bucket for all transactions
        List<Transaction> allTransactions = new ArrayList<>();

        // 2. Use a loop to copy every item from the main list
        for (int i = 0; i < transactions.size(); i++) {
            allTransactions.add(transactions.get(i));
        }

        // 3. Organize the list using a manual method
        sortByDate(allTransactions);

        return allTransactions;
    }


    public List<Transaction> getDeposits() {

        List<Transaction> deposits = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            // Return element as specific part of list
            Transaction t = transactions.get(i);
            // Passes it and uses a the getter method for amount
            if (t.getAmount() > 0) {
                // If deposits is > 0, it'll add to the instance object deposits
                deposits.add(t);
            }
        }
        // Sorts through object list of deposits by date
        sortByDate(deposits);

        return deposits;
    }


    public List<Transaction> getPayments() {

        List<Transaction> payments = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getAmount() < 0) {
                payments.add(t);
            }
        }
        // Sort and return payments when method calls
        sortByDate(payments);

        return payments;
    }

    /** Bubble sort method */
    private void sortByDate(List<Transaction> list) {
        // Assigns the whole list to variable n
        int n = list.size();
        // Repeats (n - 1) times starting from inx 0 as i
        for (int i = 0; i < n - 1; i++) {
            // Repeats up to (n - i - 2) starting at index 0 as j
            for (int j = 0; j < n - i - 1; j++) {
                // Sets the transaction at the current index (j) to t1
                Transaction t1 = list.get(j);
                // Sets the transaction at the next index (j + 1) to t2
                Transaction t2 = list.get(j + 1);

                // If t1 happened before t2, this swaps them to bring the newer t2 to the front
                if (t1.getDate().isBefore(t2.getDate())) {
                    list.set(j, t2);
                    list.set(j + 1, t1);
                }
                // If the dates are identical, check the time
                else if (t1.getDate().equals(t2.getDate())) {
                    // Another nested conditional checking/sorting by time
                    if (t1.getTime().isBefore(t2.getTime())) {
                        list.set(j, t2);
                        list.set(j + 1, t1);
                    }
                }
            }
        }
    }


    public List<Transaction> getTransactionsByDate(LocalDate start, LocalDate end) {

        List<Transaction> filtered = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            // Date range check
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                filtered.add(t);
            }
        }

        sortByDate(filtered);

        return filtered;
    }


    public List<Transaction> getTransactionsByVendor(String vendor) {

        List<Transaction> filtered = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            if (t.getVendor().equalsIgnoreCase(vendor)) {
                filtered.add(t);
            }
        }

        sortByDate(filtered);

        return filtered;
    }
}
