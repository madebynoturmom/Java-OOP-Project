package Restaurant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<MenuItem> menuItems;
    private List<String> reservations;

    public Restaurant() {
        this.menuItems = new ArrayList<>();
        this.reservations = new ArrayList<>();
        // Load menu items from file
        loadMenuItemsFromFile("Data\\MenuItems.txt");
    }

    private void loadMenuItemsFromFile(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String itemId = parts[0];
                    String name = parts[1];
                    String description = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    boolean available = Boolean.parseBoolean(parts[4]);
                    menuItems.add(new MenuItem(itemId, name, description, price, available));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu items from file: " + e.getMessage());
        }
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void removeMenuItem(String itemId) {
        menuItems.removeIf(item -> item.getItemId().equals(itemId));
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (MenuItem item : menuItems) {
            System.out.println(item);
            System.out.println("-----------------------------------");
        }
    }

    public void makeReservation(String reservation) {
        reservations.add(reservation);
    }

    public void displayReservations() {
        System.out.println("Reservations:");
        for (String reservation : reservations) {
            System.out.println(reservation);
        }
    }
}