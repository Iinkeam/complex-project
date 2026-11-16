import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class collection {
    private List<Movie> collection = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void add() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Director: ");
            String director = scanner.nextLine();
            System.out.print("Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Rating (0-10): ");
            double rating = Double.parseDouble(scanner.nextLine());

            collection.add(new Movie(title, director, genre, year, rating));
            System.out.println("Movie added!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number for year and rating. Movie was not added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Movie was not added.");
        }
    }

    public void remove(int i) {
        if (isEmptyWithMessage()) return;
        if (i < 0 || i >= collection.size()) {
            System.out.println("No movie with that index.");
            return;
        }
        Movie removed = collection.remove(i);
        System.out.println("Removed: " + removed.getTitle());
    }

    public void printOne(int i) {
        if (isEmptyWithMessage()) return;
        if (i < 0 || i >= collection.size()) {
            System.out.println("No movie with that index.");
            return;
        }
        System.out.println(collection.get(i).getDescription());
    }

    public void printAll() {
        if (isEmptyWithMessage()) return;
        System.out.println("|======= MOVIE INFO =======|");
        for (int i = 0; i < collection.size(); i++) {
            System.out.print(collection.get(i).getDetails());
        }
        System.out.println("|==========================|");
    }

    public void printList() {
        if (isEmptyWithMessage()) return;
        for (int i = 0; i < collection.size(); i++) {
            System.out.println((i + 1) + " - " + collection.get(i).getTitle());
        }
    }

    private boolean isEmptyWithMessage() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty.");
            return true;
        }
        return false;
    }
}