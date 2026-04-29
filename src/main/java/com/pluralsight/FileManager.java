package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private String fileName;


    public FileManager(String fileName) {

        this.fileName = fileName;
    }


    public List<Transaction> loadTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufread = new BufferedReader(new FileReader(fileName))) {

            String line = bufread.readLine(); // Skip header

            while ((line = bufread.readLine()) != null) {

                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String desc = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, desc, vendor, amount));
            }

        } catch (IOException e) {
            System.out.println("No existing ledger file found. Starting fresh.");
        }

        return transactions;
    }


    public void saveTransaction(Transaction t) {

        try (BufferedWriter bufwrite = new BufferedWriter(new FileWriter(fileName, true))) {

            bufwrite.write(t.toCsvString());
            bufwrite.newLine();

        } catch (IOException e) {
            System.out.println("Error saving to file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in CSV.");
            ;
        }
    }
}

