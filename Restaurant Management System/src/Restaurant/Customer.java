package Restaurant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User {
    private Restaurant restaurant;
    private List<String> orders;

    public Customer(String username, String name, String email, String contact, int age) {
        super(username, name, email, contact, age, true);
        this.restaurant = new Restaurant();
        this.orders = new ArrayList<>();
    }

    @Override
    public void showMenu() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*          Customer Menu          *");
            System.out.println("***********************************");
            System.out.println("* [1] Make a Reservation          *");
            System.out.println("* [2] View Menu                   *");
            System.out.println("* [3] View My Reservations        *");
            System.out.println("* [4] Order Menu Item             *");
            System.out.println("* [5] View My Orders              *");
            System.out.println("* [6] Cancel Reservation          *");
            System.out.println("* [7] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    viewMenu();
                    break;
                case 3:
                    viewMyReservations();
                    break;
                case 4:
                    orderMenuItem();
                    break;
                case 5:
                    viewMyOrders();
                    break;
                case 6:
                    cancelReservation();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error waiting: " + e.getMessage());
                    }
                    ConsoleUtils.clearConsole();
                    Main.main(new String[0]);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void makeReservation() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available dates for reservation:");
        String[] availableDates = {"1. 2024-08-10", "2. 2024-08-11", "3. 2024-08-12"};
        for (String date : availableDates) {
            System.out.println(date);
        }

        System.out.print("Choose a date by entering the number: ");
        int dateChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        String selectedDate = availableDates[dateChoice - 1].substring(3);  // Extract date string

        System.out.println("Available times for reservation:");
        String[] availableTimes = {"1. 18:00", "2. 19:00", "3. 20:00"};
        for (String time : availableTimes) {
            System.out.println(time);
        }

        System.out.print("Choose a time by entering the number: ");
        int timeChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        String selectedTime = availableTimes[timeChoice - 1].substring(3);  // Extract time string

        System.out.print("Enter your reservation details: ");
        String reservationDetails = scanner.nextLine();

        String reservation = String.format("Username: %s, Date: %s, Time: %s, Details: %s", getUsername(), selectedDate, selectedTime, reservationDetails);
        restaurant.makeReservation(reservation);

        // Write reservation to file
        try (FileWriter fw = new FileWriter("Data\\Reservations.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(reservation);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        System.out.println("Reservation made successfully!");

        // Display reservation details in a table format
        System.out.println("+----------------------+----------------------+--------------------------------+");
        System.out.println("|        Date          |         Time         |            Details             |");
        System.out.println("+----------------------+----------------------+--------------------------------+");
        System.out.printf("| %-20s | %-20s | %-30s |\n", selectedDate, selectedTime, reservationDetails);
        System.out.println("+----------------------+----------------------+--------------------------------+");

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }

    @SuppressWarnings("resource")
    public void viewMenu() {
        ConsoleUtils.clearConsole();
        System.out.println("Menu:");
        boolean hasMenuItems = false;

        try (BufferedReader br = new BufferedReader(new FileReader("Data\\MenuItems.txt"))) {
            String line;
            // Print table header
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");
            System.out.println("| ItemID | Name              | Description                                     | Price   | Available  |");
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");

            while ((line = br.readLine()) != null) {
                hasMenuItems = true;
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String itemId = parts[0];
                    String name = parts[1];
                    String description = parts[2];
                    String price = parts[3];
                    String available = parts[4].equals("true") ? "Yes" : "No";
                    System.out.printf("| %-6s | %-17s | %-47s | %-7s | %-10s |\n", itemId, name, description, price, available);
                }
            }

            // Print table footer
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!hasMenuItems) {
            System.out.println("No menu items available.");
        }

        System.out.println("Press Enter to continue...");
        new Scanner(System.in).nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }

    @SuppressWarnings("resource")
    public void viewMyReservations() {
        ConsoleUtils.clearConsole();
        System.out.println("Your Reservations:");
        boolean hasReservations = false;

        try (BufferedReader br = new BufferedReader(new FileReader("Data\\Reservations.txt"))) {
            String line;
            // Print table header
            System.out.println("+----------------------+----------------------+--------------------------------+");
            System.out.println("|        Date          |         Time         |            Details             |");
            System.out.println("+----------------------+----------------------+--------------------------------+");

            while ((line = br.readLine()) != null) {
                if (line.contains("Username: " + getUsername())) {
                    hasReservations = true;
                    String[] reservationDetails = line.split(", ");
                    String date = reservationDetails[1].split(": ")[1];
                    String time = reservationDetails[2].split(": ")[1];
                    String details = reservationDetails[3].split(": ")[1];
                    System.out.printf("| %-20s | %-20s | %-30s |\n", date, time, details);
                }
            }

            // Print table footer
            System.out.println("+----------------------+----------------------+--------------------------------+");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!hasReservations) {
            System.out.println("You have no reservations.");
        }

        System.out.println("Press Enter to continue...");
        new Scanner(System.in).nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }

    public void cancelReservation() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cancel a Reservation:");
        boolean hasReservations = false;
        List<String> reservations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Data\\Reservations.txt"))) {
            String line;
            // Print table header
            System.out.println("+----+----------------------+----------------------+--------------------------------+");
            System.out.println("| No |        Date          |         Time         |            Details             |");
            System.out.println("+----+----------------------+----------------------+--------------------------------+");

            int count = 1;
            while ((line = br.readLine()) != null) {
                if (line.contains("Username: " + getUsername())) {
                    hasReservations = true;
                    reservations.add(line);
                    String[] reservationDetails = line.split(", ");
                    String date = reservationDetails[1].split(": ")[1];
                    String time = reservationDetails[2].split(": ")[1];
                    String details = reservationDetails[3].split(": ")[1];
                    System.out.printf("| %-2d | %-20s | %-20s | %-30s |\n", count++, date, time, details);
                }
            }

            // Print table footer
            System.out.println("+----+----------------------+----------------------+--------------------------------+");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!hasReservations) {
            System.out.println("You have no reservations.");
        } else {
            System.out.print("Enter the number of the reservation you want to cancel: ");
            int reservationNumber = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (reservationNumber > 0 && reservationNumber <= reservations.size()) {
                reservations.remove(reservationNumber - 1);
                System.out.println("Reservation cancelled successfully!");

                // Write updated reservations back to file
                try (FileWriter fw = new FileWriter("Data\\Reservations.txt"); PrintWriter pw = new PrintWriter(fw)) {
                    for (String reservation : reservations) {
                        pw.println(reservation);
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid reservation number.");
            }
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }

    public void orderMenuItem() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.println("Menu:");
        List<MenuItem> availableItems = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Data\\MenuItems.txt"))) {
            String line;
            // Print table header
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");
            System.out.println("| ItemID | Name              | Description                                     | Price   | Available  |");
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String itemId = parts[0];
                    String name = parts[1];
                    String description = parts[2];
                    String price = parts[3];
                    boolean available = Boolean.parseBoolean(parts[4]);
                    if (available) {
                        availableItems.add(new MenuItem(itemId, name, description, Double.parseDouble(price), available));
                    }
                    System.out.printf("| %-6s | %-17s | %-47s | %-7s | %-10s |\n", itemId, name, description, price, available ? "Yes" : "No");
                }
            }

            // Print table footer
            System.out.println("+--------+-------------------+-------------------------------------------------+---------+------------+");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (availableItems.isEmpty()) {
            System.out.println("No menu items available for ordering.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();  // Wait for the user to press Enter
            showMenu();  // Return to main menu
            return;
        }

        System.out.print("Enter the ItemID of the menu item you want to order: ");
        String itemId = scanner.nextLine();
        MenuItem selectedItem = null;

        for (MenuItem item : availableItems) {
            if (item.getItemId().equals(itemId)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem != null) {
            orders.add(String.format("Username: %s, ItemID: %s, Name: %s, Price: %.2f", getUsername(), selectedItem.getItemId(), selectedItem.getName(), selectedItem.getPrice()));
            System.out.println("Order placed successfully!");

            // Write order to file
            try (FileWriter fw = new FileWriter("Data\\Orders.txt", true); PrintWriter pw = new PrintWriter(fw)) {
                pw.println(String.format("Username: %s, ItemID: %s, Name: %s, Price: %.2f", getUsername(), selectedItem.getItemId(), selectedItem.getName(), selectedItem.getPrice()));
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid ItemID. No order placed.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }

    public void viewMyOrders() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your Orders:");
        boolean hasOrders = false;
        double totalPrice = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader("Data\\Orders.txt"))) {
            String line;
            // Print table header
            System.out.println("+--------+-------------------+---------+");
            System.out.println("| ItemID | Name              | Price   |");
            System.out.println("+--------+-------------------+---------+");

            while ((line = br.readLine()) != null) {
                if (line.contains("Username: " + getUsername())) {
                    hasOrders = true;
                    String[] orderDetails = line.split(", ");
                    String itemId = orderDetails[1].split(": ")[1];
                    String name = orderDetails[2].split(": ")[1];
                    double price = Double.parseDouble(orderDetails[3].split(": ")[1]);
                    totalPrice += price;
                    System.out.printf("| %-6s | %-17s | %-7.2f |\n", itemId, name, price);
                }
            }

            // Print table footer
            System.out.println("+--------+-------------------+---------+");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!hasOrders) {
            System.out.println("You have no orders.");
        } else {
            System.out.printf("Total Price: %.2f\n", totalPrice);
            System.out.print("Do you want to check out? (Y/N): ");
            String checkoutChoice = scanner.nextLine().trim().toUpperCase();
            if (checkoutChoice.equals("Y")) {
                System.out.println("Processing payment...");
                // Dummy payment process
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Error waiting: " + e.getMessage());
                }
                System.out.println("Payment successful! Thank you for your order.");
                // Clear orders after payment (dummy process)
                orders.clear();
            }
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Wait for the user to press Enter

        showMenu();  // Return to main menu
    }
}