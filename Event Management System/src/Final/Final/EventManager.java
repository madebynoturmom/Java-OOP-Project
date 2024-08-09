package Final;

import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private Organizer organizer;
    private Member participant;

    // Constructor that accepts an Organizer object
    public EventManager(Organizer organizer) {
        this.organizer = organizer;
    }

    // Constructor that accepts a Participant object
    public EventManager(Member participant) {
        this.participant = participant;
    }

    // Method to create and add a new event (for Organizer)
    public void createEvent(String eventName, String eventDate, String eventTime, String eventLocation) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy'T'HHmm");
    LocalDateTime eventDateTime = LocalDateTime.parse(eventDate + "T" + eventTime, formatter);
    Event newEvent = new Event(generateEventId(), eventName, "No Description", eventDateTime, eventLocation, organizer);
    addEvent(newEvent);
}

    // Method to join an event (for Participant)
    public void joinEvent(String eventId) {
        if (participant == null) {
            System.out.println("Only a participant can join an event.");
            return;
        }
        Event event = findEventById(eventId);
        if (event != null) {
            participant.joinEvent(event);
            System.out.println("Successfully joined the event: " + event.getTitle());
        } else {
            System.out.println("Event not found.");
        }
    }

    public void viewJoinedEvents() {
        if (participant == null) {
            System.out.println("Only a participant can view joined events.");
            return;
        }
        ConsoleUtils.clearConsole();
        System.out.println("***********************************");
        System.out.println("*         Your Joined Events      *");
        System.out.println("***********************************");
        List<Event> joinedEvents = participant.getJoinedEvents();
        if (joinedEvents.isEmpty()) {
            System.out.println("No joined events found.");
            return;
        }
        for (Event event : joinedEvents) {
            System.out.println("Event: " + event.getTitle());
            System.out.println("Date: " + event.getDateTime().toLocalDate());
            System.out.println("Time: " + event.getDateTime().toLocalTime());
            System.out.println("Location: " + event.getLocation());
            System.out.println("***********************************");
        }
    }

    public void cancelEventParticipation() {
        if (participant == null) {
            System.out.println("Only a participant can cancel event participation.");
            return;
        }
        ConsoleUtils.clearConsole();
        List<Event> joinedEvents = participant.getJoinedEvents();
        if (joinedEvents.isEmpty()) {
            System.out.println("No joined events to cancel.");
            return;
        }

        System.out.println("Select the number of the event to cancel:");
        for (int i = 0; i < joinedEvents.size(); i++) {
            Event event = joinedEvents.get(i);
            System.out.println((i + 1) + ". Event: " + event.getTitle() + ", Date: " + event.getDateTime().toLocalDate() + ", Time: " + event.getDateTime().toLocalTime());
        }

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int eventIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (eventIndex < 1 || eventIndex > joinedEvents.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        Event eventToCancel = joinedEvents.get(eventIndex - 1);
        joinedEvents.remove(eventToCancel);
        System.out.println("Successfully cancelled participation in the event: " + eventToCancel.getTitle());
    }

    // Method to display all available events
    public List<Event> displayEvents() {
        List<Event> events = getEvents();
        if (events.isEmpty()) {
            System.out.println("No events available.");
        } else {
            System.out.println("***********************************");
            System.out.println("*        Available Events         *");
            System.out.println("***********************************");
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                System.out.println((i + 1) + ". " + event.getTitle() + " - " + event.getDateTime());
            }
            System.out.println("***********************************");
        }
        return events;
    }

    // Helper method to find an event by its ID
    private Event findEventById(String eventId) {
        for (Event event : getEvents()) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    // Method to add an event to a file or a list
    private void addEvent(Event event) {
        try (FileWriter fw = new FileWriter("Data/Events.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(event.getEventId() + "," + event.getTitle() + "," + event.getDateTime() + "," + event.getLocation() + "," + event.getOrganizer().getUsername());
        } catch (IOException e) {
            System.out.println("Error writing to events file: " + e.getMessage());
        }
    }

    // Method to retrieve the list of events from a file or a database
    private List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Events.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Event event = new Event(data[0], data[1], "No Description", java.time.LocalDateTime.parse(data[2]), data[3], new User(data[4], "", "", "", "", 0, ""));
                    events.add(event);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading events file: " + e.getMessage());
        }
        return events;
    }

    // Helper method to generate a unique event ID
    private String generateEventId() {
        return "E" + System.currentTimeMillis();
    }

    // Helper method to get events organized by a specific organizer
    private List<Event> getEventsByOrganizer(Organizer organizer) {
        List<Event> events = getEvents();
        List<Event> organizedEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer().getUsername().equals(organizer.getUsername())) {
                organizedEvents.add(event);
            }
        }
        return organizedEvents;
    }
}