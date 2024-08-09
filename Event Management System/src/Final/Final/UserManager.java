package Final;

import java.io.*;
import java.util.Scanner;

public class UserManager {

    // Method to create a new user (either Member or Organizer)
    public static void createNewUser() {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        ConsoleUtils.clearConsole();
        System.out.println("+-----------------------------------+");
        System.out.println("|        Create New User            |");
        System.out.println("+-----------------------------------+");
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your email address: ");
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

        System.out.println("Are you a member or an organizer?");
        System.out.println("[1] Member");
        System.out.println("[2] Organizer");
        int userType = input.nextInt();
        input.nextLine(); // Consume newline

        String filePath = userType == 1 ? "Data/User_Login.txt" : "Data/Organizer_Login.txt";
        boolean isMember = userType == 1;

        boolean success = addNewUser(filePath, username, password, name, email, contact, age, gender, isMember);

        if (success) {
            System.out.println("User created successfully!");
        } else {
            System.out.println("Error: User creation failed. The username might already be taken.");
        }

        // Wait for 2 seconds before returning to the main menu
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    // Method to log in a user (either Member or Organizer)
    public static User loginUser(String filepath, boolean isMember) {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        ConsoleUtils.clearConsole();
        System.out.println("+----------------------------------+");
        System.out.println("+   Please enter your username     +");
        System.out.println("+----------------------------------+");
        String username = input.next();

        ConsoleUtils.clearConsole();
        System.out.println("+----------------------------------+");
        System.out.println("+   Please enter your password     +");
        System.out.println("+----------------------------------+");
        String password = input.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7 && data[0].equals(username) && data[1].equals(password)) {
                    if (isMember) {
                        return new Member(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), data[6]);
                    } else {
                        return new Organizer(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), data[6]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return null;
    }

    // Method to add a new user to the system (used during sign-up)
    public static boolean addNewUser(String filePath, String username, String password, String name, String email, String contact, int age, String gender, boolean isMember) {
        User newUser;
        if (isMember) {
            newUser = new Member(username, password, name, email, contact, age, gender);
        } else {
            newUser = new Organizer(username, password, name, email, contact, age, gender);
        }

        return saveUserToFile(filePath, newUser);
    }

    // Helper method to save user data to a file
    private static boolean saveUserToFile(String filePath, User user) {
        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(user.getUsername() + "," + user.password + "," + user.name + "," + user.email + "," + user.contact + "," + user.age + "," + user.gender);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}