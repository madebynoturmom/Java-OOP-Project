package Final;

import java.util.Scanner;

public class Member extends User {
    private Library library;

    public Member(String username, String password, String name, String email, String contact, int age) {
        super(username, password, name, email, contact, age);
        this.library = new Library(this);
    }

    @Override
    public void showMenu() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*           Member Menu           *");
            System.out.println("***********************************");
            System.out.println("* [1] Borrow Book                 *");
            System.out.println("* [2] Return Book                 *");
            System.out.println("* [3] Search for Books            *");
            System.out.println("* [4] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.borrowBook();
                    break;
                case 2:
                    library.returnBook();
                    break;
                case 3:
                    library.searchBooks();
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
}