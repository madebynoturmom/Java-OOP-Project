package Final;

import java.io.*;
import java.util.Scanner;

public class UserManager {

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

        System.out.println("Are you a Member or a Librarian?");
        System.out.println("[1] Member");
        System.out.println("[2] Librarian");
        int userType = input.nextInt();

        String filepath;
        boolean isLibrarian = false;
        if (userType == 1) {
            filepath = "Data/Member_Login.txt";
        } else {
            filepath = "Data/Librarian_Login.txt";
            isLibrarian = true;
        }

        try (FileWriter fw = new FileWriter(filepath, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(username + "," + password + "," + name + "," + email + "," + contact + "," + age);
            System.out.println("User created successfully!");

            if (isLibrarian) {
                // Write librarian name to Librarians.txt
                try (FileWriter tfw = new FileWriter("Data/Librarians.txt", true); PrintWriter tpw = new PrintWriter(tfw)) {
                    tpw.println(name);
                } catch (IOException e) {
                    System.out.println("Error writing librarian to file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        // Wait for 2 seconds before returning to the main menu
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

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
                if (data.length >= 6 && data[0].equals(username) && data[1].equals(password)) {
                    if (isMember) {
                        return new Member(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]));
                    } else {
                        return new Librarian(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return null;
    }
}
