
from problem1 import ProducerConsumer
from problem2 import HelloSynchronization


def print_header():
    """Print program header"""
    print("\n" + "╔" + "="*50 + "╗")
    print("║" + " "*2 + "SEMAPHORE SYNCHRONIZATION PROBLEMS SOLUTION" + " "*5 + "║")
    print("╚" + "="*50 + "╝")


def print_menu():
    """Print menu options"""
    print("\nSelect problem to run:")
    print("  1. Problem 1: Producer-Consumer")
    print("  2. Problem 2: HELLO Synchronization")
    print("  3. Run both problems")
    print("  4. Exit")


def main():
    """Main program function"""
    print_header()
    
    while True:
        print_menu()
        
        try:
            choice = input("\nEnter choice (1-4): ").strip()
            
            if choice == '1':
                pc = ProducerConsumer()
                pc.run()
            
            elif choice == '2':
                hello = HelloSynchronization()
                hello.run()
            
            elif choice == '3':
                # Run Problem 1
                pc = ProducerConsumer()
                pc.run()
                
                print("\n\n")
                
                # Run Problem 2
                hello = HelloSynchronization()
                hello.run()
            
            elif choice == '4':
                print("\nExiting program. Goodbye!")
                break
            
            else:
                print("\nInvalid choice! Please enter 1-4.")
                continue
            
            print("\n" + "╔" + "="*50 + "╗")
            print("║" + " "*14 + "SIMULATION COMPLETE" + " "*17 + "║")
            print("╚" + "="*50 + "╝\n")
            
            # Ask if user wants to run again
            again = input("Run another simulation? (y/n): ").strip().lower()
            if again != 'y':
                print("\nExiting program. Goodbye!")
                break
        
        except KeyboardInterrupt:
            print("\n\nProgram interrupted. Exiting...")
            break
        except Exception as e:
            print(f"\nError: {e}")
            print("Please try again.")


if __name__ == "__main__":
    main()