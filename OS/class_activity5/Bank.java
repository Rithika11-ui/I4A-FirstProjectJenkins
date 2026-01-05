/**
 * Bank.java
 * Demonstrates thread synchronization and race conditions
 * 
 * WITHOUT synchronization: Race conditions occur, inconsistent balance
 * WITH synchronization: Thread-safe execution, correct balance
 */
public class Bank {
    // Static variable shared across all threads
    private static int balance = 0;
    
    // Uncomment this line to use explicit lock object
    // private static final Object lock = new Object();
    
    /**
     * Simulates depositing money into the account
     */
    public void deposit() {
        // Uncomment to simulate processing delay (makes race condition more visible)
        // try {
        //     Thread.sleep(1);
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        // }
        Bank.balance += 100;
    }
    
    /**
     * Simulates withdrawing money from the account
     */
    public void withdraw() {
        Bank.balance -= 100;
    }
    
    /**
     * Returns the current balance
     */
    public int getValue() {
        return Bank.balance;
    }
    
    /**
     * Main operation: deposit then withdraw
     * 
     * WITHOUT synchronized keyword: Race condition - multiple threads interfere
     * WITH synchronized keyword: Thread-safe - each thread completes atomically
     */
    public void run() {
    // public synchronized void run() {  // <- Uncomment this line to fix race condition
    
        // Alternative: Use explicit lock (uncomment the lock object above first)
        // synchronized(lock) {
        
            deposit();
            System.out.println("Value for Thread after deposit " + 
                             Thread.currentThread().getName() + ": " + getValue());
            
            withdraw();
            System.out.println("Value for Thread after withdraw " + 
                             Thread.currentThread().getName() + ": " + getValue());
        // }
    }
}