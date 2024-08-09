package Final;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Member extends User {
    private List<Event> joinedEvents;
    private EventManager eventManager;
    private ReviewManager reviewManager;

    // Constructor for Participant class
    public Member(String username, String password, String name, String email, String contact, int age, String profileIcon) {
        super(username, password, name, email, contact, age, profileIcon);
        this.joinedEvents = new ArrayList<>();
        this.eventManager = new EventManager(this);
        this.reviewManager = new ReviewManager(this); 
    }

    // Method to join an event
    public void joinEvent(Event event) {
        if (!joinedEvents.contains(event)) {
            joinedEvents.add(event);
            System.out.println("You have successfully joined the event: " + event.getTitle());
        } else {
            System.out.println("You have already joined this event.");
        }
    }

    // Getter method for joined events
    public List<Event> getJoinedEvents() {
        return joinedEvents;
    }

    // Method to display available events in a simple table
    private void displayAvailableEvents() {
        List<Event> availableEvents = eventManager.displayEvents();
        if (availableEvents.isEmpty()) {
            System.out.println("No available events at the moment.");
            return;
        }

        System.out.println("***********************************");
        System.out.println("*          Available Events       *");
        System.out.println("***********************************");
        System.out.println("| ID |        Title        |    Date    |   Time   | Location |");
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < availableEvents.size(); i++) {
            Event event = availableEvents.get(i);
            System.out.printf("| %2d | %-18s | %10s | %7s | %-8s |\n",
                i + 1,
                event.getTitle(),
                event.getDateTime().toLocalDate(),
                event.getDateTime().toLocalTime(),
                event.getLocation()
            );
        }
        System.out.println("--------------------------------------------------------------");
    }

    // Method to display the participant's menu and handle user input
    @Override
    public void showMenu() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("***********************************");
            System.out.println("*           User Menu             *");
            System.out.println("***********************************");
            System.out.println("* [1] Join Event                  *");
            System.out.println("* [2] Write Review                *");
            System.out.println("* [3] View Joined Events          *");
            System.out.println("* [4] Cancel Event Participation  *");
            System.out.println("* [5] Logout                      *");
            System.out.println("***********************************");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAvailableEvents();
                    System.out.print("Enter the number of the event to join: ");
                    int eventNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    List<Event> availableEvents = eventManager.displayEvents();
                    if (eventNumber < 1 || eventNumber > availableEvents.size()) {
                        System.out.println("Invalid event number. Please try again.");
                    } else {
                        Event eventToJoin = availableEvents.get(eventNumber - 1);
                        joinEvent(eventToJoin);
                    }
                    break;
                case 2:
                    reviewManager.writeReview(); // Write a review using ReviewManager
                    break;
                case 3:
                    eventManager.viewJoinedEvents(); // View joined events using EventManager
                    break;
                case 4:
                    eventManager.cancelEventParticipation(); // Cancel participation using EventManager
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}