package Restaurant;

public abstract class User {
    private String username;
    private String name;
    private String email;
    private String contact;
    private int age;
    private boolean isCustomer;

    public User(String username, String name, String email, String contact, int age, boolean isCustomer) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.isCustomer = isCustomer;
    }

    // Getters and setters for each field
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public int getAge() {
        return age;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", age=" + age +
                ", isCustomer=" + isCustomer +
                '}';
    }

    // Abstract method to be overridden by subclasses
    public abstract void showMenu();
}