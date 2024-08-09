package Restaurant;

import java.io.*;
import java.util.Scanner;

public class UserManager {

    public static void createNewCustomer() {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        ConsoleUtils.clearConsole();
        System.out.println("+-----------------------------------+");
        System.out.println("|        Create New Customer        |");
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

        String filepath = "Data/Customer_Login.txt";

        try (FileWriter fw = new FileWriter(filepath, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(username + "," + password + "," + name + "," + email + "," + contact + "," + age);
            System.out.println("Customer created successfully!");
        } catch (IOException e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    public static void createNewStaff() {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        ConsoleUtils.clearConsole();
        System.out.println("+-----------------------------------+");
        System.out.println("|         Create New Staff          |");
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

        String filepath = "Data/Staff_Login.txt";

        try (FileWriter fw = new FileWriter(filepath, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(username + "," + password + "," + name + "," + email + "," + contact + "," + age);
            System.out.println("Staff created successfully!");

            // Write staff name to Staff.txt
            try (FileWriter tfw = new FileWriter("Data/Staff.txt", true); PrintWriter tpw = new PrintWriter(tfw)) {
                tpw.println(name);
            }
        } catch (IOException e) {
            System.out.println("Error creating staff: " + e.getMessage());
        }
    }

    public static User loginUser(String filepath, boolean isCustomer) {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    if (isCustomer) {
                        return new Customer(username, userDetails[2], userDetails[3], userDetails[4], Integer.parseInt(userDetails[5]));
                    } else {
                        return new Manager(username, userDetails[2], userDetails[3], userDetails[4], Integer.parseInt(userDetails[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return null;
    }
}