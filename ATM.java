import java.util.*;

class Transaction {
    String type;
    double amount;
    double balance;
    
    Transaction(String type, double amount, double balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }
    
    @Override
    public String toString() {
        return type + " - Amount: $" + amount + " - Balance: $" + balance;
    }
}

class Account {
    private double balance;
    private List<Transaction> transactions;
    
    Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }
    
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
        System.out.println("Deposited: $" + amount);
    }
    
    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount, balance));
        System.out.println("Withdrawn: $" + amount);
        return true;
    }
    
    public boolean transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer", amount, balance));
            System.out.println("Transferred: $" + amount);
            return true;
        }
        return false;
    }
    
    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
    
    public double getBalance() {
        return balance;
    }
}

class User {
    private String userId;
    private String userPin;
    private Account account;
    
    User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.account = new Account(initialBalance);
    }
    
    public boolean authenticate(String id, String pin) {
        return userId.equals(id) && userPin.equals(pin);
    }
    
    public Account getAccount() {
        return account;
    }
}

public class ATM {
    static Scanner scanner = new Scanner(System.in);
    static User user = new User("admin", "1234", 1000);
    
    public static void main(String[] args) {
        System.out.println("Welcome to ATM");
        
        if (!login()) {
            System.out.println("Authentication failed! Exiting...");
            return;
        }
        
        int choice;
        do {
            System.out.println("\n1. Transaction History\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Quit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    user.getAccount().showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    user.getAccount().withdraw(scanner.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    user.getAccount().deposit(scanner.nextDouble());
                    break;
                case 4:
                    System.out.print("Enter recipient's ID (dummy transfer): ");
                    scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    user.getAccount().transfer(new Account(0), scanner.nextDouble());
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }
    
    private static boolean login() {
        System.out.print("Enter User ID: ");
        String id = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        return user.authenticate(id, pin);
    }
}