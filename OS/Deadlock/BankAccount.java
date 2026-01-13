

public class BankAccount {
    private int balance;
    private final String name;
    private final Object lock = new Object();
    
    public BankAccount(String name, int initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }
    
    public Object getLock() {
        return lock;
    }
    
    public void withdraw(int amount) throws InterruptedException {
        synchronized(lock) {
            while (balance < amount) {
                System.out.println("        ⏰ " + name + ": Insufficient balance, waiting...");
                lock.wait();
            }
            balance -= amount;
            System.out.println("        💸 " + name + ": Withdrew $" + amount + " | Balance: $" + balance);
            lock.notifyAll();
        }
    }
    
    public void deposit(int amount) {
        synchronized(lock) {
            balance += amount;
            System.out.println("        💰 " + name + ": Deposited $" + amount + " | Balance: $" + balance);
            lock.notifyAll();
        }
    }
    
    public int getBalance() {
        synchronized(lock) {
            return balance;
        }
    }
    
    public String getName() {
        return name;
    }
}