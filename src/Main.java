import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.logic();
    }
}
class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final MovieCollection collection = new MovieCollection(scanner);

    private static final String[] MENU_ITEMS = {
            "\n|===== MOVIE COLLECTION =====|",
            "| 1 - print movies           |",
            "| 2 - add new movie          |",
            "| 3 - edit movie             |",
            "| 4 - remove movie           |",
            "| 5 - sort movies            |",
            "| 6 - search                 |",
            "| 7 - save to file           |",
            "| 8 - reload from file       |",
            "| 0 - EXIT                   |",
            "|============================|"
    };

    private void print() {
        for (String s : MENU_ITEMS) {
            System.out.println(s);
        }
        System.out.print(" CHOOSE AN OPTION: ");
    }

    public void logic() {
        collection.read(true);

        boolean running = true;
        while (running) {
            print();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printMenu();
                    break;
                case "2":
                    collection.add();
                    break;
                case "3":
                    edit();
                    break;
                case "4":
                    remove();
                    break;
                case "5":
                    collection.sort();
                    break;
                case "6":
                    collection.searchMenu();
                    break;
                case "7":
                    collection.save();
                    break;
                case "8":
                    reload();
                    break;
                case "0":
                    exit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private void printMenu() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        System.out.println("|========== PRINT MOVIE ==========|");
        System.out.println("| 1 - Short list (titles only)    |");
        System.out.println("| 2 - Full info (all movies)      |");
        System.out.println("| 3 - Single movie details        |");
        System.out.println("| 0 - BACK                        |");
        System.out.println("|=================================|");
        System.out.print(" CHOOSE AN OPTION: ");

        String subChoice = scanner.nextLine().trim();
        switch (subChoice) {
            case "1":
                collection.printList();
                break;
            case "2":
                collection.printAll();
                break;
            case "3":
                collection.printList();
                System.out.print("Enter movie number: ");
                try {
                    int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    collection.printOne(index);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
                break;
            case "0":
                break;
            default:
                System.out.println("Unknown option.");
        }
    }

    private void edit() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }
        collection.printList();
        System.out.print("Enter movie number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            collection.edit(index);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void remove() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }
        collection.printList();
        System.out.print("Enter movie number to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            collection.remove(index);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void reload() {
        if (collection.hasUnsavedChanges()) {
            System.out.print("You have unsaved changes that will be lost. Reload anyway? (Y/N): ");
            String answer = scanner.nextLine().trim();
            if (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("yes")) {
                System.out.println("Reload cancelled.");
                return;
            }
        }
        collection.read(false);
    }

    private void exit() {
        if (!collection.hasUnsavedChanges()) {
            System.out.println("Goodbye!");
            return;
        }

        while (true) {
            System.out.print("You have unsaved changes. Save before exiting? (Y/N): ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                collection.save();
                System.out.println("Goodbye!");
                return;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                System.out.println("Exiting without saving. Goodbye!");
                return;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }
}