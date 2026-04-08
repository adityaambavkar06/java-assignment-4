import java.io.*;
import java.util.*;

class BalanceException extends Exception {
    BalanceException(String msg) {
        super(msg);
    }
}

class RangeException extends Exception {
    RangeException(String msg) {
        super(msg);
    }
}

class NegativeAmountException extends Exception {
    NegativeAmountException(String msg) {
        super(msg);
    }
}

class BankAccount {
    int id;
    String name;
    double balance;

    BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    void validate() throws Exception {
        if (id < 1 || id > 20)
            throw new RangeException("Invalid Customer ID");
        if (balance < 1000)
            throw new BalanceException("Minimum balance not maintained");
        if (balance <= 0)
            throw new NegativeAmountException("Amount must be positive");
    }

    void withdraw(double amt) throws Exception {
        if (amt <= 0)
            throw new NegativeAmountException("Invalid withdrawal amount");
        if (amt > balance)
            throw new BalanceException("Not enough balance");
        balance -= amt;
    }

    void saveRecord() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("bankdata.txt", true));
        bw.write(id + "," + name + "," + balance);
        bw.newLine();
        bw.close();
    }
}

public class BankingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount acc = null;

        while (true) {
            System.out.println("\n1.Create\n2.Withdraw\n3.Exit");
            int ch = sc.nextInt();

            try {
                if (ch == 1) {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Name: ");
                    String name = sc.next();

                    System.out.print("Enter Amount: ");
                    double amt = sc.nextDouble();

                    acc = new BankAccount(id, name, amt);
                    acc.validate();
                    acc.saveRecord();

                    System.out.println("Account created");
                } 
                else if (ch == 2) {
                    if (acc == null) {
                        System.out.println("No account found");
                        continue;
                    }

                    System.out.print("Enter amount: ");
                    double w = sc.nextDouble();

                    acc.withdraw(w);
                    System.out.println("Balance: " + acc.balance);
                } 
                else if (ch == 3) {
                    break;
                } 
                else {
                    System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
