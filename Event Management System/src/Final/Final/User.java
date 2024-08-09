package Final;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String contact;
    protected int age;
    protected String gender;

    public User(String username, String password, String name, String email, String contact, int age, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public boolean verifyLogin(String password) {
        return this.password.equals(password);
    }

    public void showMenu() {
        // To be overridden by subclasses
    }

    // Static method to add a new user
    public static boolean addNewUser(String filePath, String username, String password, String name, String email, String contact, int age, String gender, boolean isMember) {
        User newUser;
        if (isMember) {
            newUser = new Member(username, password, name, email, contact, age, gender);
        } else {
            newUser = new Organizer(username, password, name, email, contact, age, gender);
        }

        return saveNewUser(filePath, newUser);
    }

    // Helper method to save a new user to a file
    private static boolean saveNewUser(String filePath, User user) {
        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(user.username + "," + user.password + "," + user.name + "," + user.email + "," + user.contact + "," + user.age + "," + user.gender);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}