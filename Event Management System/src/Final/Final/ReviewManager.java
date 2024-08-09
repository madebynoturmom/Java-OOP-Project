package Final;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ReviewManager {
    private Member Member;

    // Constructor that accepts a Member object
    public ReviewManager(Member Member) {
        this.Member = Member;
    }

    // Method to write a review for an event
    public void writeReview() {
        ConsoleUtils.clearConsole();
        List<Event> joinedEvents = Member.getJoinedEvents();

        if (joinedEvents.isEmpty()) {
            System.out.println("You have not joined any events to review.");
            return;
        }

        System.out.println("Select an event to review:");
        for (int i = 0; i < joinedEvents.size(); i++) {
            Event event = joinedEvents.get(i);
            System.out.println((i + 1) + ". " + event.getTitle());
        }

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int eventIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (eventIndex < 1 || eventIndex > joinedEvents.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        Event eventToReview = joinedEvents.get(eventIndex - 1);

        System.out.print("Enter your review: ");
        String reviewText = scanner.nextLine();

        System.out.print("Enter your rating (1-5): ");
        int rating = scanner.nextInt();

        saveReview(eventToReview, reviewText, rating);
    }

    // Method to save the review to a file
    private void saveReview(Event event, String reviewText, int rating) {
        try (FileWriter fw = new FileWriter("Data/Reviews.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(Member.getUsername() + "," + event.getTitle() + "," + reviewText + "," + rating);
            System.out.println("Your review has been submitted.");
        } catch (IOException e) {
            System.out.println("Error writing review to file: " + e.getMessage());
        }
    }

    // Method to display all reviews for an event
    public List<String> getReviewsForEvent(Event event) {
        List<String> reviews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Reviews.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[1].equals(event.getTitle())) {
                    reviews.add("User: " + data[0] + " | Review: " + data[2] + " | Rating: " + data[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file: " + e.getMessage());
        }
        return reviews;
    }
}