package Restaurant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        while (true) {
            ConsoleUtils.printTitle("Restaurant Management");
            ConsoleUtils.printMenuOption(1, "Login");
            ConsoleUtils.printMenuOption(2, "Create New Customer");
            ConsoleUtils.printMenuOption(3, "Exit");
            ConsoleUtils.printDivider();
            
            int initialChoice = input.nextInt();

            if (initialChoice == 1) {
                login();
            } else if (initialChoice == 2) {
                UserManager.createNewCustomer();
            } else if (initialChoice == 3) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void login() {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        while (true) {
            ConsoleUtils.clearConsole();
            ConsoleUtils.printTitle("Are you a Customer or Staff?");
            ConsoleUtils.printMenuOption(1, "Customer");
            ConsoleUtils.printMenuOption(2, "Staff");
            ConsoleUtils.printDivider();
            
            int userType = input.nextInt();

            if (userType == 1) {
                User loggedInUser = UserManager.loginUser("Data/Customer_Login.txt", true);
                if (loggedInUser != null) {
                    System.out.println("Login successful");
                    ((Customer) loggedInUser).showMenu();  // Cast to Customer and show menu
                }
            } else if (userType == 2) {
                User loggedInStaff = UserManager.loginUser("Data/Staff_Login.txt", false);
                if (loggedInStaff != null) {
                    System.out.println("Login successful");
                    if (loggedInStaff instanceof Manager) {
                        ((Manager) loggedInStaff).showMenu();  // Cast to Manager and show menu
                    } else {
                        // Handle other staff roles if needed
                    }
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}