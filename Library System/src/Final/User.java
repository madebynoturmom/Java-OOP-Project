package Final;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String contact;
    protected int age;
    protected String profileIcon;

    public User(String username, String password, String name, String email, String contact, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public boolean verifyLogin(String password) {
        return this.password.equals(password);
    }

    public abstract void showMenu();
}