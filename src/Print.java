import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class only exists to contain my 3 print methods. Since they're not important for the actual program to function,
 * I thought it would be best to keep them tucked away in here instead of cluttering up the main class.
 */
public class Print {

    /**
     * Prints the user's list to console
     *
     * @param list list to print
     * @param listTitle title of the list
     */
    public static void list(ArrayList<String> list, String listTitle) {
        int itemDisplayNumber; // the number displayed with each list item ("1.", "2.", etc.)

        // prints a fun border around the list, to make it more visually distinct from the two menus
        System.out.println("\n\n✫・゜・。.\n" + listTitle + ":");

        // loops over the list to print each item with its corresponding number
        for (int i = 0; i < list.size(); i++) {
            // the number is the index plus 1
            itemDisplayNumber = i + 1;
            System.out.println(itemDisplayNumber + ". " + list.get(i));
        }

        System.out.println(".・。.・゜✭\n");
    }

    /**
     * This prints the main menu to console, then gets the user's choice.
     * Almost identical to the options menu print method, but with different text.
     *
     * @return user's choice from the menu as a String
     */
    public static String printMainMenu() {
        Scanner in = new Scanner(System.in);
        String menuChoice;
        final int FULL_WIDTH = 34;

        // top border
        System.out.println();
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // menu header
        System.out.printf("\n%19s\n", "MENU");

        // divider line 1
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // instantiates menu option text stored as array
        String[][] text = {{"O", "\nOpen a text file from disk"},
                {"A", "\nAdd a list"},
                {"Q", "\nQuit"}};
        
        // prints menu options
        int numDots; // # of dots to print as filler
        for (int i = 0; i < 3; i++) {
            numDots = FULL_WIDTH - text[i][1].length();
            System.out.print(text[i][1]);

            for (int j = 0; j < numDots; j++) {
                System.out.print(".");
            }
            System.out.print(text[i][0]);
        }

        // divider line 2
        System.out.println();
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // gets user's choice from menu to return
        menuChoice = SafeInput.getRegExString(in, "\nEnter choice", "[OoAaQq]");

        // bottom border
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("=");
        }
        System.out.println();

        return menuChoice; // returns user's choice
    }

    /**
     * This prints the options menu to console, then gets the user's choice.
     * Almost identical to the main menu print method, but with different text.
     *
     * @return user's choice from the menu as a String
     */
    public static String printOptionsMenu() {
        Scanner in = new Scanner(System.in);
        String menuChoice;
        final int FULL_WIDTH = 34;

        // top border
        System.out.println();
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // options menu header
        System.out.printf("\n%13sOPTIONS\n", " ");

        // divider line 1
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // this is an array containing all of the option Strings
        String[][] text = {{"V", "\nView this list"},
                {"A", "\nAdd an item to this list"},
                {"D", "\nDelete an item from this list"},
                {"C", "\nClear this list"},
                {"S", "\nSave this list to disk"},
                {"Q", "\nQuit (Exit this list"}};

        // prints the options
        int numDots;
        for (int i = 0; i < 6; i++) {
            numDots = FULL_WIDTH - text[i][1].length();
            System.out.print(text[i][1]);

            for (int j = 0; j < numDots; j++) {
                System.out.print(".");
            }
            System.out.print(text[i][0]);
        }

        // divider line 2
        System.out.println();
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("═");
        }

        // gets user's choice
        menuChoice = SafeInput.getRegExString(in, "\nEnter choice", "[VvAaDdCcSsQq]");

        // bottom border
        for (int i = 0; i < FULL_WIDTH; i++) {
            System.out.print("=");
        }
        System.out.println();

        return menuChoice; // returns the user's choice
    }
}

