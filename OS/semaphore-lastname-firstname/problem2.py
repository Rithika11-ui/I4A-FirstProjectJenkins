import threading
import time

class HelloSynchronization:
    def __init__(self):
        # Semaphores: a = 1, b = 0, c = 0
        self.a = threading.Semaphore(1)  # Process 1 can start
        self.b = threading.Semaphore(0)  # Process 2 must wait
        self.c = threading.Semaphore(0)  # Process 3 must wait
        
        self.done = False
    
    def process1(self):
        """Process 1: Prints H and E"""
        self.a.acquire()  # Wait for semaphore a
        
        print("H")  # H on new line
        time.sleep(1)
        
        print("E")  # E on new line
        time.sleep(1)
        
        self.b.release()  # Signal process 2
    
    def process2(self):
        """Process 2: Prints L twice"""
        self.b.acquire()  # Wait for semaphore b
        
        print("L")  # First L on new line
        time.sleep(1)
        
        self.b.release()  # Signal self for second L
        
        self.b.acquire()  # Wait for own signal
        
        print("L")  # Second L on new line
        time.sleep(1)
        
        self.c.release()  # Signal process 3
    
    def process3(self):
        """Process 3: Prints O"""
        self.c.acquire()  # Wait for semaphore c
        
        print("O")  # O on new line
        
        self.done = True
    
    def run(self):
        """Run the HELLO synchronization"""
        print("\n" + "="*50)
        print("PROBLEM 2: HELLO Synchronization")
        print("="*50)
        
        print("\nPart (A) - Without semaphores:")
        print("-" * 35)
        print("  LEHO: NO")
        print("    Reason: Process 1 prints H before E in sequence.")
        print("            Cannot have E before H.\n")
        
        print("  HLOE: YES")
        print("    Reason: P1 prints H, switches to P2 prints L,")
        print("            P3 prints O, back to P1 prints E.\n")
        
        print("  LOL: YES")
        print("    Reason: P2 prints L, P3 prints O,")
        print("            P2 prints L again in next iteration.\n")
        
        print("Part (B) - With semaphores:")
        print("-" * 35)
        print("Semaphores: a = 1, b = 0, c = 0\n")
        
        print("Process flow:")
        print("  1. P1: wait(a), print H, print E, signal(b)")
        print("  2. P2: wait(b), print L, signal(b)")
        print("  3. P2: wait(b), print L, signal(c)")
        print("  4. P3: wait(c), print O\n")
        
        print("Output:")
        
        # Create threads
        t1 = threading.Thread(target=self.process1)
        t2 = threading.Thread(target=self.process2)
        t3 = threading.Thread(target=self.process3)
        
        # Start threads
        t1.start()
        t2.start()
        t3.start()
        
        # Wait for all threads to complete
        t1.join()
        t2.join()
        t3.join()
        
        print("\n=== Problem 2 Complete ===")
        print("HELLO was printed exactly once in correct order!")


# Allow running this file independently
if __name__ == "__main__":
    print("Running Problem 2 only...")
    hello = HelloSynchronization()
    hello.run()