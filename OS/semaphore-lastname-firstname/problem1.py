import threading
import time

class ProducerConsumer:
    def __init__(self):
        self.BUFFER_SIZE = 100
        self.NUM_PRODUCERS = 3
        
        # Shared buffer
        self.particle_buffer = [0] * self.BUFFER_SIZE
        self.in_idx = 0
        self.out_idx = 0
        
        # Semaphores and initial values:
        self.empty = threading.Semaphore(50)  # 50 pair slots available
        self.full = threading.Semaphore(0)    # 0 pairs initially
        self.mutex = threading.Semaphore(1)   # Mutual exclusion
        
        self.running = True
        self.producer_iterations = 5
        self.consumer_iterations = 15
    
    def producer(self, producer_id):
        """Producer thread function"""
        count = 0
        
        while self.running and count < self.producer_iterations:
            # Produce pair P1, P2
            P1 = (producer_id * 100) + count * 2
            P2 = P1 + 1
            
            print(f"Producer {producer_id}: Produced pair ({P1}, {P2})")
            
            # Wait for space for one pair (2 particles)
            self.empty.acquire()
            
            # Enter critical section
            self.mutex.acquire()
            
            # Place P1 in buffer
            self.particle_buffer[self.in_idx] = P1
            print(f"Producer {producer_id}: Placed P1={P1} at buffer[{self.in_idx}]")
            self.in_idx = (self.in_idx + 1) % self.BUFFER_SIZE
            
            # Place P2 in buffer (consecutive location)
            self.particle_buffer[self.in_idx] = P2
            print(f"Producer {producer_id}: Placed P2={P2} at buffer[{self.in_idx}]")
            self.in_idx = (self.in_idx + 1) % self.BUFFER_SIZE
            
            # Exit critical section
            self.mutex.release()
            
            # Signal that one pair is available
            self.full.release()
            
            count += 1
            time.sleep(1)  # Simulate production time
    
    def consumer(self):
        """Consumer thread function"""
        count = 0
        
        while count < self.consumer_iterations:
            # Wait for at least one pair (2 particles)
            self.full.acquire()
            
            # Enter critical section
            self.mutex.acquire()
            
            # Fetch P1 from buffer
            P1 = self.particle_buffer[self.out_idx]
            print(f"Consumer: Fetched P1={P1} from buffer[{self.out_idx}]")
            self.out_idx = (self.out_idx + 1) % self.BUFFER_SIZE
            
            # Fetch P2 from buffer
            P2 = self.particle_buffer[self.out_idx]
            print(f"Consumer: Fetched P2={P2} from buffer[{self.out_idx}]")
            self.out_idx = (self.out_idx + 1) % self.BUFFER_SIZE
            
            # Exit critical section
            self.mutex.release()
            
            # Signal that space for one pair is available
            self.empty.release()
            
            # Package and ship
            print(f"Consumer: Packaged and shipped pair ({P1}, {P2})\n")
            
            count += 1
            time.sleep(1)  # Simulate packaging time
        
        self.running = False
    
    def run(self):
        """Run the producer-consumer simulation"""
        print("\n" + "="*50)
        print("PROBLEM 1: Producer-Consumer Simulation")
        print("="*50)
        print(f"Buffer capacity: {self.BUFFER_SIZE} particles ({self.BUFFER_SIZE//2} pairs)")
        print(f"Number of producers: {self.NUM_PRODUCERS}")
        print("\nSemaphores:")
        print("  empty = 50  (available pair slots)")
        print("  full = 0    (filled pairs ready)")
        print("  mutex = 1   (mutual exclusion)\n")
        
        # Create threads
        producer_threads = []
        for i in range(1, self.NUM_PRODUCERS + 1):
            thread = threading.Thread(target=self.producer, args=(i,))
            producer_threads.append(thread)
            thread.start()
        
        consumer_thread = threading.Thread(target=self.consumer)
        consumer_thread.start()
        
        # Wait for all threads to complete
        consumer_thread.join()
        for thread in producer_threads:
            thread.join()
        
        print("\n=== Problem 1 Complete ===")


# Allow running this file independently
if __name__ == "__main__":
    print("Running Problem 1 only...")
    pc = ProducerConsumer()
    pc.run()