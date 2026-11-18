import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MovieCollection {
    private final List<Movie> collection = new ArrayList<>();
    private final Scanner scanner;
    private static final String FILE_NAME = "data/library.txt";
    private boolean hasUnsavedChanges = false;

    // scanner constructor
    public MovieCollection(Scanner scanner) {
        this.scanner = scanner;
    }

    // Holds unsaved changes status
    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    // Checks list for values
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    // MENU FUNCTIONS
    public void add() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Director: ");
            String director = scanner.nextLine();
            System.out.print("Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Rating (0-10): ");
            double rating = Double.parseDouble(scanner.nextLine().trim());

            collection.add(new Movie(title, director, genre, year, rating));
            hasUnsavedChanges = true;
            System.out.println("Movie added!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for year and rating. Movie was not added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Movie was not added.");
        }
    }

    public void edit(int i) {
        if (isEmptyWithMessage()) return;
        if (i < 0 || i >= collection.size()) {
            System.out.println("No movie with that index.");
            return;
        }

        Movie m = collection.get(i);
        System.out.println("Editing: " + m.getTitle() + " (press Enter to keep current value)");

        try {
            System.out.print("Title [" + m.getName() + "]: ");
            String title = scanner.nextLine();
            if (!title.trim().isEmpty()) m.setTitle(title);

            System.out.print("Director [" + m.getDirector() + "]: ");
            String director = scanner.nextLine();
            if (!director.trim().isEmpty()) m.setDirector(director);

            System.out.print("Genre [" + m.getGenre() + "]: ");
            String genre = scanner.nextLine();
            if (!genre.trim().isEmpty()) m.setGenre(genre);

            System.out.print("Year [" + m.getYear() + "]: ");
            String yearStr = scanner.nextLine().trim();
            if (!yearStr.isEmpty()) m.setYear(Integer.parseInt(yearStr));

            System.out.print("Rating [" + m.getRating() + "]: ");
            String ratingStr = scanner.nextLine().trim();
            if (!ratingStr.isEmpty()) m.setRating(Double.parseDouble(ratingStr));

            hasUnsavedChanges = true;
            System.out.println("Movie updated!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Some changes may not have been saved.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void remove(int i) {
        if (isEmptyWithMessage()) return;
        if (i < 0 || i >= collection.size()) {
            System.out.println("No movie with that index.");
            return;
        }
        Movie removed = collection.remove(i);
        hasUnsavedChanges = true;
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
        for (Movie movie : collection) {
            System.out.print(movie.getDetails());
            System.out.println("|==========================|");
        }
    }

    public void printList() {
        if (isEmptyWithMessage()) return;
        for (int i = 0; i < collection.size(); i++) {
            Movie m = collection.get(i);
            System.out.println((i + 1) + " - " + m.getTitle() + " [" + m.getRating() + "/10]");
        }
    }

    public void sort() {
        if (isEmptyWithMessage()) return;

        System.out.println("|===== SORT MOVIES =====|");
        System.out.println("| 1 - title (A-Z)       |");
        System.out.println("| 2 - year (oldest)     |");
        System.out.println("| 3 - rating (highest)  |");
        System.out.println("| 0 - back              |");
        System.out.println("|=======================|");
        System.out.print(" CHOOSE AN OPTION: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                collection.sort(Comparator.comparing(m -> m.getName().toLowerCase()));
                hasUnsavedChanges = true;
                System.out.println("Sorted by title.");
                break;
            case "2":
                collection.sort(Comparator.comparingInt(Movie::getYear));
                hasUnsavedChanges = true;
                System.out.println("Sorted by year.");
                break;
            case "3":
                collection.sort(Comparator.comparingDouble(Movie::getRating).reversed());
                hasUnsavedChanges = true;
                System.out.println("Sorted by rating (highest first).");
                break;
            case "0":
                break;
            default:
                System.out.println("Unknown option.");
        }
    }

    public void searchMenu() {
        if (isEmptyWithMessage()) return;

        System.out.println("|============= SEARCH MOVIES =============|");
        System.out.println("| 1 - phrase (title, director or genre)   |");
        System.out.println("| 2 - year                                |");
        System.out.println("| 0 - back                                |");
        System.out.println("|=========================================|");
        System.out.print(" CHOOSE AN OPTION: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Enter phrase: ");
                search(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter year: ");
                try {
                    searchByYear(Integer.parseInt(scanner.nextLine().trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number for the year.");
                }
                break;
            case "0":
                break;
            default:
                System.out.println("Unknown option.");
        }
    }

    public void search(String phrase) {
        if (isEmptyWithMessage()) return;
        if (phrase == null || phrase.trim().isEmpty()) {
            System.out.println("Search phrase cannot be empty.");
            return;
        }

        String[] words = phrase.trim().toLowerCase().split("\\s+");
        boolean found = false;

        for (int i = 0; i < collection.size(); i++) {
            Movie m = collection.get(i);
            String name = m.getName().toLowerCase();
            String director = m.getDirector().toLowerCase();
            String genre = m.getGenre().toLowerCase();

            for (String w : words) {
                if (name.contains(w) || director.contains(w) || genre.contains(w)) {
                    System.out.println((i + 1) + " - " + m.getTitle()
                            + " (" + m.getGenre() + ", dir. " + m.getDirector() + ")");
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No movies found for \"" + phrase.trim() + "\".");
        }
    }

    public void searchByYear(int year) {
        if (isEmptyWithMessage()) return;

        boolean found = false;

        for (int i = 0; i < collection.size(); i++) {
            Movie m = collection.get(i);
            if (m.getYear() == year) {
                System.out.println((i + 1) + " - " + m.getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No movies found from " + year + ".");
        }
    }

    // SAVING/LOADING METHODS
    public void read(boolean missing) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            if (!missing) {
                System.out.println("File not found: " + FILE_NAME);
            }
            return;
        }

        List<Movie> loaded = new ArrayList<>();
        int lineNumber = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.matches("^\\d+\\. .*;$")) {
                    line = line.substring(line.indexOf(". ") + 2, line.length() - 1);
                }

                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String title = parts[0].trim();
                    String director = parts[1].trim();
                    String genre = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    double rating = Double.parseDouble(parts[4].trim());

                    loaded.add(new Movie(title, director, genre, year, rating));
                } else {
                    System.out.println("Skipping malformed line " + lineNumber + ".");
                }
            }

            collection.clear();
            collection.addAll(loaded);
            hasUnsavedChanges = false;
            System.out.println("Loaded " + collection.size() + " movie(s) from " + FILE_NAME + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file name.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error on line " + lineNumber + ": " + e.getMessage());
        }
    }

    public void save() {
        File file = new File(FILE_NAME);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < collection.size(); i++) {
                Movie m = collection.get(i);
                writer.println((i + 1) + ". "
                        + m.getName() + "; "
                        + m.getDirector() + "; "
                        + m.getGenre() + "; "
                        + m.getYear() + "; "
                        + m.getRating() + ";");
            }
            hasUnsavedChanges = false;
            System.out.println("Saved " + collection.size() + " movie(s) to " + FILE_NAME + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Could not save file: " + e.getMessage());
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