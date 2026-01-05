/**
 * Main.java
 * Main class to initialize and run threads
 * 
 * Creates three threads that execute bank operations concurrently
 * Demonstrates the need for synchronization in multi-threaded environments
 */
public class Main {
    public static void main(String[] args) {
        // Create a single Bank instance shared by all threads
        Bank bankInstance = new Bank();

        // Create three threads, each running the bank operation
        // Using lambda expression to pass the run() method as target
        Thread t1 = new Thread(() -> bankInstance.run(), "Thread1");
        Thread t2 = new Thread(() -> bankInstance.run(), "Thread2");
        Thread t3 = new Thread(() -> bankInstance.run(), "Thread3");

        // Start all threads (they run concurrently)
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to complete before main thread exits
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println("\n========================================");
        System.out.println("All threads completed execution.");
        System.out.println("Final balance: " + new Bank().getValue());
        System.out.println("========================================");
    }
}