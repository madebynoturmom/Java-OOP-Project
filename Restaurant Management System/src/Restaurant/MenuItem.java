package Restaurant;

public class MenuItem {
    private String itemId;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public MenuItem(String itemId, String name, String description, double price, boolean available) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("Item ID: %s, Name: %s, Description: %s, Price: %.2f, Available: %s",
                itemId, name, description, price, available ? "Yes" : "No");
    }
}