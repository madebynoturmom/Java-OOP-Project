package Restaurant;

import java.util.Scanner;

public class Manager extends User {
    private Restaurant restaurant;

    public Manager(String username, String name, String email, String contact, int age) {
        super(username, name, email, contact, age, false);
        this.restaurant = new Restaurant();
    }

    @Override
    public void showMenu() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*           Manager Menu          *");
            System.out.println("***********************************");
            System.out.println("* [1] View Reservations           *");
            System.out.println("* [2] Manage Menu                 *");
            System.out.println("* [3] Manage Staff                *");
            System.out.println("* [4] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewReservations();
                    break;
                case 2:
                    manageMenu();
                    break;
                case 3:
                    manageStaff();
                    break;
                case 4:
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

    public void viewReservations() {
        restaurant.displayReservations();
    }

    public void manageMenu() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*           Manage Menu           *");
            System.out.println("***********************************");
            System.out.println("* [1] Add Menu Item               *");
            System.out.println("* [2] View Menu Items             *");
            System.out.println("* [3] Remove Menu Item            *");
            System.out.println("* [4] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter menu item ID: ");
                    String itemId = scanner.nextLine();
                    System.out.print("Enter menu item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter menu item description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter menu item price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Is the item available (true/false): ");
                    boolean available = scanner.nextBoolean();
                    MenuItem item = new MenuItem(itemId, name, description, price, available);
                    restaurant.addMenuItem(item);
                    System.out.println("Menu item added successfully!");
                    break;
                case 2:
                    restaurant.displayMenu();
                    break;
                case 3:
                    System.out.print("Enter menu item ID to remove: ");
                    String itemIdToRemove = scanner.nextLine();
                    restaurant.removeMenuItem(itemIdToRemove);
                    System.out.println("Menu item removed successfully!");
                    break;
                case 4:
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

    public void manageStaff() {
        // Implement staff management functionality here
        System.out.println("Managing staff functionality not implemented yet.");
    }
}