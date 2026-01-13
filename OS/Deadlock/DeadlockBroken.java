// ============================================================
// File 2: DeadlockBroken.java (THE PROBLEM - Has Deadlock)
// ============================================================

class TransferThreadBroken extends Thread {
    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final int amount;
    private final String threadName;

    public TransferThreadBroken(String name, BankAccount from, BankAccount to, int amount) {
        this.threadName = name;
        this.fromAccount = from;
        this.toAccount = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            // ❌ PROBLEM: Lock in the order given (can be different for each thread)
            System.out.println("\n" + threadName + ": 🔓 Trying to lock " + fromAccount.getName());

            // Lock fromAccount first (whatever was passed in)
            synchronized (fromAccount.getLock()) {
                System.out.println(threadName + ": ✅ Locked " + fromAccount.getName());
                Thread.sleep(300);

                System.out.println(threadName + ": 🔓 Trying to lock " + toAccount.getName());
                System.out.println(threadName + ": ⏰ WAITING for " + toAccount.getName() + "...");

                // Lock toAccount second - DEADLOCK HAPPENS HERE!
                synchronized (toAccount.getLock()) {
                    System.out.println(threadName + ": ✅ Locked " + toAccount.getName());

                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                    System.out.println(threadName + ": ✅ Transfer COMPLETED!");
                }
            }

        } catch (InterruptedException e) {
            System.out.println(threadName + ": ❌ Interrupted!");
        }
    }
}

public class DeadlockBroken {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        ❌ DEADLOCK PROBLEM (BROKEN CODE) ❌             ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        BankAccount account1 = new BankAccount("Account1", 1000);
        BankAccount account2 = new BankAccount("Account2", 1000);

        System.out.println("📊 INITIAL STATE:");
        System.out.println("   " + account1.getName() + ": $" + account1.getBalance());
        System.out.println("   " + account2.getName() + ": $" + account2.getBalance());

        System.out.println("\n❌ THE PROBLEM:");
        System.out.println("   Thread-1: Lock Account1 → Lock Account2");
        System.out.println("   Thread-2: Lock Account2 → Lock Account1");
        System.out.println("   → OPPOSITE ORDER = DEADLOCK!\n");

        System.out.println("🚀 STARTING THREADS...");
        System.out.println("════════════════════════════════════════════════════════════");

        TransferThreadBroken thread1 = new TransferThreadBroken(
                "Thread-1", account1, account2, 100);
        TransferThreadBroken thread2 = new TransferThreadBroken(
                "Thread-2", account2, account1, 50);

        thread1.start();
        Thread.sleep(100);
        thread2.start();

        Thread.sleep(2000);

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("\n🔴 DEADLOCK OCCURRED!");
        System.out.println("   Both threads frozen - press Ctrl+C to exit\n");
    }
}