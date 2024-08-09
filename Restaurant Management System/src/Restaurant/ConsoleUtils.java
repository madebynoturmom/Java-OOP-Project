package Restaurant;

public class ConsoleUtils {
    
    // Method to clear the console
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    // Method to print a formatted title
    public static void printTitle(String title) {
        System.out.println("+-----------------------------------+");
        System.out.println("| " + title);
        System.out.println("+-----------------------------------+");
    }

    // Method to print a menu option
    public static void printMenuOption(int number, String option) {
        System.out.println("| [" + number + "] " + option);
    }

    // Method to print a divider line
    public static void printDivider() {
        System.out.println("+-----------------------------------+");
    }

    // Method to pause and wait for the user to press Enter
    public static void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}