
// ============================================================
// File 3: DeadlockFixed.java (THE SOLUTION - No Deadlock)
// ============================================================

class TransferThreadFixed extends Thread {
    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final int amount;
    private final String threadName;

    public TransferThreadFixed(String name, BankAccount from, BankAccount to, int amount) {
        this.threadName = name;
        this.fromAccount = from;
        this.toAccount = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            // ✅ SOLUTION: Always lock in FIXED ORDER (alphabetically by name)
            BankAccount firstLock, secondLock;

            // Determine order: min(account1, account2)
            if (fromAccount.getName().compareTo(toAccount.getName()) < 0) {
                firstLock = fromAccount;
                secondLock = toAccount;
            } else {
                firstLock = toAccount;
                secondLock = fromAccount;
            }

            System.out.println("\n" + threadName + ": 🔓 Locking in order: " +
                    firstLock.getName() + " → " + secondLock.getName());

            // wait(lock[a]) - Lock first account
            synchronized (firstLock.getLock()) {
                System.out.println(threadName + ": ✅ Locked " + firstLock.getName());
                Thread.sleep(300);

                // wait(lock[b]) - Lock second account
                System.out.println(threadName + ": 🔓 Now locking " + secondLock.getName());
                synchronized (secondLock.getLock()) {
                    System.out.println(threadName + ": ✅ Locked " + secondLock.getName());

                    // Transfer money
                    System.out.println(threadName + ": 💸 Transferring $" + amount);
                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                    System.out.println(threadName + ": ✅ Transfer COMPLETED!");

                    // signal(lock[b]) - Auto-released
                }
                System.out.println(threadName + ": 🔓 Released " + secondLock.getName());

                // signal(lock[a]) - Auto-released
            }
            System.out.println(threadName + ": 🔓 Released " + firstLock.getName());

        } catch (InterruptedException e) {
            System.out.println(threadName + ": ❌ Interrupted!");
        }
    }
}

public class DeadlockFixed {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        ✅ DEADLOCK SOLUTION (FIXED CODE) ✅             ║");
        System.out.println("║        Cooperating Processes - Fixed Ordering            ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        BankAccount account1 = new BankAccount("Account1", 1000);
        BankAccount account2 = new BankAccount("Account2", 1000);

        System.out.println("📊 INITIAL STATE:");
        System.out.println("   " + account1.getName() + ": $" + account1.getBalance());
        System.out.println("   " + account2.getName() + ": $" + account2.getBalance());

        System.out.println("\n✅ THE SOLUTION:");
        System.out.println("   int a = min(account1, account2);  // Account1");
        System.out.println("   int b = max(account1, account2);  // Account2");
        System.out.println("   wait(lock[a]);  // Lock a first");
        System.out.println("   wait(lock[b]);  // Lock b second");
        System.out.println("   // Transfer");
        System.out.println("   signal(lock[b]);");
        System.out.println("   signal(lock[a]);\n");

        System.out.println("📝 RESULT:");
        System.out.println("   Thread-1: Lock Account1 → Account2 ✅");
        System.out.println("   Thread-2: Lock Account1 → Account2 ✅");
        System.out.println("   → SAME ORDER! No deadlock!\n");

        System.out.println("🚀 STARTING THREADS...");
        System.out.println("════════════════════════════════════════════════════════════");

        TransferThreadFixed thread1 = new TransferThreadFixed(
                "Thread-1", account1, account2, 100);
        TransferThreadFixed thread2 = new TransferThreadFixed(
                "Thread-2", account2, account1, 50);

        thread1.start();
        Thread.sleep(100);
        thread2.start();

        // Wait for completion
        thread1.join();
        thread2.join();

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("\n✅ SUCCESS! NO DEADLOCK!\n");

        System.out.println("📊 FINAL STATE:");
        System.out.println("   " + account1.getName() + ": $" + account1.getBalance());
        System.out.println("   " + account2.getName() + ": $" + account2.getBalance());

        System.out.println("\n💡 WHY IT WORKED:");
        System.out.println("   ✅ Fixed lock ordering (always Account1 → Account2)");
        System.out.println("   ✅ No circular wait condition");
        System.out.println("   ✅ wait() for balance checking");
        System.out.println("   ✅ signal() (notifyAll) to wake threads");

        System.out.println("\n✅ Program completed successfully!\n");
    }
}