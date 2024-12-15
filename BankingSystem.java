package BankingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BankingSystem {
    private static final String DATA_FILE = "accounts.txt";
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) throws Exception{
        loadAccountsFromFile();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n Simple Banking System \n");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    Thread.sleep(3000);
                    break;
                case 2:
                    performTransaction(scanner, "deposit");
                    Thread.sleep(3000);
                    break;
                case 3:
                    performTransaction(scanner, "withdraw");
                    Thread.sleep(3000);
                    break;
                case 4:
                    checkBalance(scanner);
                    Thread.sleep(3000);
                    break;
                case 5:
                    saveAccountsToFile();
                    System.out.println("Thank you for using the Simple Banking System.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); 

        String accountNumber = BankAccount.generateAccountNumber();
        BankAccount account = new BankAccount(accountNumber, name, initialBalance);
        accounts.put(accountNumber, account);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
    }

    private static void performTransaction(Scanner scanner, String type) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        if (type.equals("deposit")) {
            account.deposit(amount);
        } else if (type.equals("withdraw")) {
            account.withdraw(amount);
        }
    }

    private static void checkBalance(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
        } else {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            account.displayBalance();
        }
    }

    private static void loadAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String accountNumber = parts[0];
                    String name = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.put(accountNumber, new BankAccount(accountNumber, name, balance));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing account data found.");
        }
    }

    private static void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (BankAccount account : accounts.values()) {
                writer.write(account.getAccountNumber() + "," + account.getAccountHolderName() + "," + account.getBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }
}


