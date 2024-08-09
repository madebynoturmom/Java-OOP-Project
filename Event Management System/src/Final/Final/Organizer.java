package Final;

import java.util.Scanner;
import java.io.*;

public class Organizer extends User {
    private EventManager eventManager;

    public Organizer(String username, String password, String name, String email, String contact, int age, String profileIcon) {
        super(username, password, name, email, contact, age, profileIcon);
        this.eventManager = new EventManager(this);
    }

    @Override
    public void showMenu() {
        ConsoleUtils.clearConsole();
        System.out.println("***********************************");
        System.out.println("*         Organizer MENU          *");
        System.out.println("***********************************");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*             WELCOME             *");
            System.out.println("***********************************");
            System.out.println("* Would you like to:              *");
            System.out.println("* [1] View organized events       *");
            System.out.println("* [2] View event reviews          *");
            System.out.println("* [3] Add a new event             *");
            System.out.println("* [4] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewOrganizedEvents();
                    break;
                case 2:
                    selectEventForReviews();
                    break;
                case 3:
                    addNewEvent();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    runMain();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewOrganizedEvents() {
        ConsoleUtils.clearConsole();
        System.out.println("Events organized by " + getUsername() + ":");
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Events.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[4].equals(getUsername())) {
                    System.out.println("Event ID: " + data[0] + ", Event: " + data[1] + ", Date: " + data[2] + ", Location: " + data[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading events file: " + e.getMessage());
        }
    }

    private void selectEventForReviews() {
        ConsoleUtils.clearConsole();
        System.out.println("Select an event to view reviews:");
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Events.txt"))) {
            String line;
            int index = 1;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[4].equals(getUsername())) {
                    System.out.println("[" + index + "] Event: " + data[1] + ", Date: " + data[2] + ", Location: " + data[3]);
                    index++;
                }
            }
            
            if (index == 1) {
                System.out.println("No events organized by you were found.");
                return;
            }

            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose an event number to view its reviews: ");
            int eventChoice = scanner.nextInt();

            if (eventChoice < 1 || eventChoice >= index) {
                System.out.println("Invalid selection. Returning to menu.");
                return;
            }

            index = 1;
            br.close();  // Close and reopen the file to reset the pointer
            try (BufferedReader br2 = new BufferedReader(new FileReader("Data/Events.txt"))) {
                while ((line = br2.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[4].equals(getUsername())) {
                        if (index == eventChoice) {
                            viewEventReviews(data[0], data[1]);
                            break;
                        }
                        index++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading events file: " + e.getMessage());
        }
    }

    private void viewEventReviews(String eventId, String eventName) {
        ConsoleUtils.clearConsole();
        System.out.println("Reviews for event: " + eventName);
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Reviews.txt"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(eventId)) {
                    System.out.println("Participant: " + data[1] + ", Review: " + data[2] + ", Rating: " + data[3]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No reviews found for this event.");
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file: " + e.getMessage());
        }
    }

    private void addNewEvent() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Enter event date (YYYY-MM-DD): ");
        String eventDate = scanner.nextLine();

        System.out.print("Enter event time (HH:MM): ");
        String eventTime = scanner.nextLine();

        System.out.print("Enter event location: ");
        String eventLocation = scanner.nextLine();

        eventManager.createEvent(eventName, eventDate, eventTime, eventLocation);

        System.out.println("Event created successfully!");
    }

    private void runMain() {
        String[] args = {};
        Main.main(args);
    }
}