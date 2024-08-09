package Final;

import java.io.*;
import java.util.*;

public class Library {
    private User user;

    public Library(User user) {
        this.user = user;
    }

    public void borrowBook() {
        ConsoleUtils.clearConsole();
        List<Book> books = loadBooks();
        if (books.isEmpty()) {
            System.out.println("No books available to borrow.");
            return;
        }

        displayBooksTable(books);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Book ID of the book you want to borrow: ");
        String bookId = scanner.nextLine();

        Book bookToBorrow = null;
        for (Book book : books) {
            if (book.getBookId().equals(bookId) && book.isAvailable()) {
                bookToBorrow = book;
                break;
            }
        }

        if (bookToBorrow != null) {
            bookToBorrow.setAvailable(false);
            saveBooks(books);
            saveBorrowedBook(bookToBorrow);
            System.out.println("You have successfully borrowed: " + bookToBorrow.getTitle());
        } else {
            System.out.println("Invalid Book ID or the book is not available. Please try again.");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    public void returnBook() {
        ConsoleUtils.clearConsole();
        List<Book> borrowedBooks = loadBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("You have no borrowed books to return.");
            return;
        }

        displayBooksTable(borrowedBooks);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Book ID of the book you want to return: ");
        String bookId = scanner.nextLine();

        Book bookToReturn = null;
        for (Book book : borrowedBooks) {
            if (book.getBookId().equals(bookId)) {
                bookToReturn = book;
                break;
            }
        }

        if (bookToReturn != null) {
            List<Book> allBooks = loadBooks();
            for (Book book : allBooks) {
                if (book.getBookId().equals(bookId)) {
                    book.setAvailable(true);
                    break;
                }
            }
            saveBooks(allBooks);
            removeBorrowedBook(bookToReturn);
            System.out.println("You have successfully returned: " + bookToReturn.getTitle());
        } else {
            System.out.println("Invalid Book ID. Please try again.");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    public void searchBooks() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title or author of the book to search: ");
        String query = scanner.nextLine().toLowerCase();

        List<Book> books = loadBooks();
        List<Book> searchResults = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query)) {
                searchResults.add(book);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            displayBooksTable(searchResults);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    public void viewAllBooks() {
        ConsoleUtils.clearConsole();
        List<Book> books = loadBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            displayBooksTable(books);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    public void viewBorrowedBooks() {
        ConsoleUtils.clearConsole();
        List<Book> borrowedBooks = loadBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently borrowed.");
        } else {
            displayBooksTable(borrowedBooks);
        }

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to return to the menu...");
        scanner.nextLine();

        ConsoleUtils.clearConsole();
    }

    public void addBook() {
        ConsoleUtils.clearConsole();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter the Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the Author: ");
        String author = scanner.nextLine();

        try (FileWriter fw = new FileWriter("Data/Library.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(bookId + "," + title + "," + author + ",Yes");
            System.out.println("Book added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    public void removeBook() {
        ConsoleUtils.clearConsole();
        List<Book> books = loadBooks();
        if (books.isEmpty()) {
            System.out.println("No books available to remove.");
            return;
        }

        displayBooksTable(books);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Book ID of the book you want to remove: ");
        String bookId = scanner.nextLine();

        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                bookToRemove = book;
                break;
            }
        }

        if (bookToRemove != null) {
            books.remove(bookToRemove);
            saveBooks(books);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Invalid Book ID. Please try again.");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error waiting: " + e.getMessage());
        }
        ConsoleUtils.clearConsole();
    }

    private List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Data/Library.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    books.add(new Book(data[0], data[1], data[2], data[3].equalsIgnoreCase("yes")));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }
        return books;
    }

    private List<Book> loadBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Data/BorrowedBooks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    borrowedBooks.add(new Book(data[0], data[1], data[2], data[3].equalsIgnoreCase("yes")));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowed books file: " + e.getMessage());
        }
        return borrowedBooks;
    }

    private void saveBooks(List<Book> books) {
        try (FileWriter fw = new FileWriter("Data/Library.txt"); PrintWriter pw = new PrintWriter(fw)) {
            for (Book book : books) {
                pw.println(book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + (book.isAvailable() ? "Yes" : "No"));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void saveBorrowedBook(Book book) {
        try (FileWriter fw = new FileWriter("Data/BorrowedBooks.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + (book.isAvailable() ? "Yes" : "No") + "," + user.getUsername());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void removeBorrowedBook(Book book) {
        List<Book> borrowedBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Data/BorrowedBooks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!(data[0].equals(book.getBookId()) && data[4].equals(user.getUsername()))) {
                    borrowedBooks.add(new Book(data[0], data[1], data[2], data[3].equalsIgnoreCase("yes")));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowed books file: " + e.getMessage());
        }
        try (FileWriter fw = new FileWriter("Data/BorrowedBooks.txt"); PrintWriter pw = new PrintWriter(fw)) {
            for (Book b : borrowedBooks) {
                pw.println(b.getBookId() + "," + b.getTitle() + "," + b.getAuthor() + "," + (b.isAvailable() ? "Yes" : "No") + "," + user.getUsername());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void displayBooksTable(List<Book> books) {
        String leftAlignFormat = "| %-15s | %-30s | %-30s | %-12s |%n";
        System.out.format("+-----------------+--------------------------------+--------------------------------+--------------+%n");
        System.out.format("| Book ID         | Title                          | Author                         | Availability |%n");
        System.out.format("+-----------------+--------------------------------+--------------------------------+--------------+%n");
        for (Book book : books) {
            System.out.format(leftAlignFormat, book.getBookId(), book.getTitle(), book.getAuthor(), book.isAvailable() ? "Yes" : "No");
        }
        System.out.format("+-----------------+--------------------------------+--------------------------------+--------------+%n");
    }
}