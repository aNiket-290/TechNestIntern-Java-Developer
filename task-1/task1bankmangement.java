

// ---------------- ABSTRACT CLASS (Abstraction) ----------------
abstract class BankAccount {
    // Encapsulated fields (Encapsulation)
    private String accountHolderName;
    private String accountNumber;
    protected double balance; 

    // Constructor (Initialization)
    public BankAccount(String accountHolderName, String accountNumber, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getter methods
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Abstract methods (Abstraction)
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    // Common method 
    public void displayAccountDetails() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance:  " + balance);
    }
}

// ---------------- SUBCLASS 1: Savings Account ----------------
class SavingsAccount extends BankAccount {
    private double interestRate;

    // Constructor Overloading
    public SavingsAccount(String holder, String accNo, double balance) {
        super(holder, accNo, balance);
        this.interestRate = 4.0; // default 
    }

    public SavingsAccount(String holder, String accNo, double balance, double interestRate) {
        super(holder, accNo, balance);
        this.interestRate = interestRate;
    }

    // Overriding abstract methods (Polymorphism)
    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited  " + amount + " to Savings Account.");
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= 1000) { // maintain minimum balance
            balance -= amount;
            System.out.println("Withdrew  " + amount + " from Savings Account.");
        } else {
            System.out.println("Insufficient balance! Minimum  1000 must be maintained.");
        }
    }

    // Additional method specific to SavingsAccount
    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        System.out.println("Interest of  " + interest + " added to Savings Account.");
    }
}

// ---------------- SUBCLASS 2: Current Account ----------------
class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    // Constructor
    public CurrentAccount(String holder, String accNo, double balance, double overdraftLimit) {
        super(holder, accNo, balance);
        this.overdraftLimit = overdraftLimit;
    }

    // Overriding abstract methods (Polymorphism)
    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited  " + amount + " to Current Account.");
    }

    @Override
    public void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            System.out.println("Withdrew Rs " + amount + " from Current Account.");
        } else {
            System.out.println("Overdraft limit exceeded!");
        }
    }

    // Additional method specific to CurrentAccount
    public void showOverdraftLimit() {
        System.out.println("Overdraft limit:  " + overdraftLimit);
    }
}

// ---------------- MAIN CLASS ----------------
public class task1bankmangement {
    public static void main(String[] args) {
        // Creating objects (Object Creation)
        SavingsAccount s1 = new SavingsAccount("Riya Sharma", "SAV12345", 5000, 5.0);
        CurrentAccount c1 = new CurrentAccount("Amit Kumar", "CUR67890", 10000, 5000);

        System.out.println("----- Savings Account Operations -----");
        s1.displayAccountDetails();
        s1.deposit(2000);
        s1.withdraw(1500);
        s1.applyInterest();
        s1.displayAccountDetails();

        System.out.println("\n----- Current Account Operations -----");
        c1.displayAccountDetails();
        c1.deposit(5000);
        c1.withdraw(13000);
        c1.showOverdraftLimit();
        c1.displayAccountDetails();

        // Demonstrating Polymorphism (Runtime)
        BankAccount accountRef;
        accountRef = s1;
        accountRef.withdraw(500);
        accountRef = c1;
        accountRef.deposit(1000);
    }
}

