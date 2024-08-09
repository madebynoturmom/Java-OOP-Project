package Final;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("+------------------------------------+");
            System.out.println("|    Welcome to Counseling System    |");
            System.out.println("| [1] Login                          |");
            System.out.println("| [2] Sign-Up                        |");
            System.out.println("| [3] Exit                           |");
            System.out.println("+------------------------------------+");
            int initialChoice = input.nextInt();

            if (initialChoice == 3) {
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
            }

            if (initialChoice == 2) {
                signUpUser(input);
            } else if (initialChoice == 1) {
                loginUser(input);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void loginUser(Scanner input) {
        while (true) {
            ConsoleUtils.clearConsole();
            System.out.println("+-----------------------------------+");
            System.out.println("|    Select your role:              |");
            System.out.println("| [1] Member                        |");
            System.out.println("| [2] Organizer                     |");
            System.out.println("| [3] Exit                          |");
            System.out.println("+-----------------------------------+");
            int userType = input.nextInt();

            if (userType == 3) {
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
            }

            boolean isMember = (userType == 1);

            User loggedInUser = UserManager.loginUser(
                isMember ? "Data/User_Login.txt" : "Data/Organizer_Login.txt", 
                isMember
            );

            if (loggedInUser != null) {
                System.out.println("Login successful");
                // Wait for 1 second and clear the console
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ConsoleUtils.clearConsole();
                loggedInUser.showMenu();
                break;
            } else {
                System.out.println("Invalid username or password, please try again.");
                // Wait for 2 seconds and then clear the console
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ConsoleUtils.clearConsole();
            }
        }
    }

    private static void signUpUser(Scanner input) {
        ConsoleUtils.clearConsole();
        System.out.println("+-----------------------------------+");
        System.out.println("|    Select your role:              |");
        System.out.println("| [1] Member                        |");
        System.out.println("| [2] Organizer                     |");
        System.out.println("+-----------------------------------+");
        int userType = input.nextInt();
        input.nextLine(); // Consume newline
    
        boolean isMember = (userType == 1);
        String filePath = isMember ? "Data/User_Login.txt" : "Data/Organizer_Login.txt";
    
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        System.out.print("Enter your contact number: ");
        String contact = input.nextLine();
        System.out.print("Enter your age: ");
        int age = input.nextInt();
        input.nextLine(); // Consume newline
    
        // Gender selection using numbers
        String gender = "";
        while (true) {
            System.out.println("Select your gender:");
            System.out.println("[1] Male");
            System.out.println("[2] Female");
            int genderChoice = input.nextInt();
            input.nextLine(); // Consume newline
    
            if (genderChoice == 1) {
                gender = "Male";
                break;
            } else if (genderChoice == 2) {
                gender = "Female";
                break;
            } else {
                System.out.println("Invalid choice, please select again.");
            }
        }
    
        // Create a new user object and save it to the appropriate file
        if (UserManager.addNewUser(filePath, username, password, name, email, contact, age, gender, isMember)) {
            System.out.println("Sign-up successful. You can now log in.");
        } else {
            System.out.println("Sign-up failed. The username might already be taken.");
        }
    
        // Wait for 2 seconds and then clear the console
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConsoleUtils.clearConsole();
    }
}