package Final;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("+-----------------------------------+");
            System.out.println("|     Library Management System     |");
            System.out.println("| [1] Login                         |");
            System.out.println("| [2] Exit                          |");
            System.out.println("+-----------------------------------+");
            int initialChoice = input.nextInt();

            if (initialChoice == 1) {
                login();
            } else if (initialChoice == 2) {
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
            System.out.println("+-----------------------------------+");
            System.out.println("|  Are you a Member or a Librarian? |");
            System.out.println("| [1] Member                        |");
            System.out.println("| [2] Librarian                     |");
            System.out.println("+-----------------------------------+");
            int userType = input.nextInt();

            if (userType == 1) {
                User loggedInUser = UserManager.loginUser("Data/Member_Login.txt", true);
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
                    System.out.println("Invalid username or password, please try again");
                }
            } else if (userType == 2) {
                User loggedInUser = UserManager.loginUser("Data/Librarian_Login.txt", false);
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
                    System.out.println("Invalid username or password, please try again");
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}