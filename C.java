import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class User {
    private String username;
    private String password;
    private String pin;
    private Map<String, Account> accounts;
    private List<String> transactionHistory;

    public User(String username, String password, String pin) {
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.accounts = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    public String getPin() {
        return pin;
    }

    public void addAccount(String accountNumber, String accountType) {
        accounts.put(accountNumber, new Account(accountNumber, accountType));
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addToTransactionHistory(String transaction) {
        transactionHistory.add(transaction);
    }
}

public class C {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static AtomicInteger accountNumberCounter = new AtomicInteger(1000);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Online Banking System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the Online Banking System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, pin));
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            System.out.println("Login successful!");
            currentUser = user;
            showMainMenu(scanner);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    private static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Account Management");
            System.out.println("2. Transaction History");
            System.out.println("3. Funds Transfer");
            System.out.println("4. Balance Enquiry");
            System.out.println("5. Loan Management");
            System.out.println("6. Logout");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    accountManagement(scanner);
                    break;
                case 2:
                    transactionHistory();
                    break;
                case 3:
                    fundsTransfer(scanner);
                    break;
                case 4:
                    balanceEnquiry();
                    break;
                case 5:
                    loanManagement(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void accountManagement(Scanner scanner) {
        while (true) {
            System.out.println("\nAccount Management:");
            System.out.println("1. Create Account");
            System.out.println("2. View Account Balances");
            System.out.println("3. Back to Main Menu");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    viewAccountBalances();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        int accountNumber = accountNumberCounter.incrementAndGet();
        System.out.print("Enter account type (e.g., Savings, Checking): ");
        String accountType = scanner.nextLine();

        currentUser.addAccount(String.valueOf(accountNumber), accountType);
        System.out.println("Account created successfully.");
    }

    private static void viewAccountBalances() {
        Map<String, Account> accounts = currentUser.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts yet.");
        } else {
            System.out.println("Your Account Balances:");
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                String accountNumber = entry.getKey();
                Account account = entry.getValue();
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Balance: $" + account.getBalance());
                System.out.println();
            }
        }
    }

    private static void transactionHistory() {
        List<String> transactions = currentUser.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transaction history to display.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private static void fundsTransfer(Scanner scanner) {
        // Implement funds transfer functionality
    }

    private static void balanceEnquiry() {
        // Implement balance enquiry functionality
    }

    private static void loanManagement(Scanner scanner) {
        // Implement loan management functionality
    }
}