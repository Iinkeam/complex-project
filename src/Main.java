import java.util.Scanner;

public class Main
{
    // program entry point: creates the menu and starts it
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.logic();
    }
}

class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final MovieCollection collection = new MovieCollection(scanner);

    private static final String[] MENU_1 = {
            "WELCOME TO MOVIE COLLECTION!",
            "     PRESS ANY BUTTON       "
    };

    private static final String[] MENU_2 = {
            "DO YOU WANT TO LOAD FROM FILES?",
            "           (YES/NO)            "
    };

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

    // prints every line of a menu array
    private void print(String[] arr) {
        for (String s : arr) {
            System.out.println(s);
        }
    }

    // prints the "choose an option" prompt
    private void promptOption() {
        System.out.print(" CHOOSE AN OPTION: ");
    }

    // clears the screen
    private void clearScreen() {
        System.out.println("\n".repeat(75));
    }

    // waits for Enter, then clears the screen
    private void enterAndClear() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        clearScreen();
    }

    // asks a yes/no question, returns true for yes
    private boolean askYesNo(String question) {
        while (true) {
            System.out.print(question);
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }

    // main program: welcome screen, load question, main menu loop
    public void logic() {
        print(MENU_1);
        scanner.nextLine();
        clearScreen();

        print(MENU_2);
        if (askYesNo(" YOUR ANSWER: ")) {
            collection.read(true);
        } else {
            System.out.println("Starting with a new file!");
        }
        enterAndClear();

        boolean running = true;
        while (running) {
            print(MENU_ITEMS);
            promptOption();
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

            if (running) {
                enterAndClear();
            }
        }
    }

    // submenu for printing: short list, full info, or one movie
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
        promptOption();

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

    // asks which movie to edit (by its list number) and passes the index to the collection
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

    // asks which movie to remove (by its list number) and passes the index to the collection
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

    // reloads movies from the file
    private void reload() {
        if (collection.hasUnsavedChanges()) {
            if (!askYesNo("You have unsaved changes that will be lost. Reload anyway? (Y/N): ")) {
                System.out.println("Reload cancelled.");
                return;
            }
        }
        collection.read(false);
    }

    // exits the program with save option
    private void exit() {
        if (collection.hasUnsavedChanges()) {
            if (askYesNo("You have unsaved changes. Save before exiting? (Y/N): ")) {
                collection.save();
            } else {
                System.out.println("Exiting without saving.");
            }
        }
        System.out.println("Goodbye!");
    }
}