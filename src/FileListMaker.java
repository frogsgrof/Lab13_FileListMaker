import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileListMaker {
    public static void main(String[] args) {

        // sets the default directory to (hopefully) the documents folder
        File file = new File(System.getProperty("user.home") + "/documents/");

        // if that line for whatever reason didn't point to the user's documents folder, it just sets it to home
        if (!file.exists()) {
            file = new File(System.getProperty("user.home"));
        }

        // declarations for files:
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(file);
        Path path;

        // declarations for the ArrayList:
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        String listTitle; // stores the name of the list
        String menuChoice; // stores user's choice; used for both menus

        do {

            // uses a method inside the Print class to print the main menu to console and get input
            menuChoice = Print.printMainMenu();
            /* preview:
                O - Open a list file from disk
                A - Add a list
                Q - Quit
             */

            // if they chose to open a file:
            if (menuChoice.equals("o")) {

                // opens JFileChooser and checks if they selected a file
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    path = file.toPath(); // feeds that file into the path variable to pass into the reader later on

                    // the name of the file gets saved as a String, for when the list is printed
                    listTitle = path.getFileName().toString();
                    // checks if the String ends with a .txt extension
                    if (listTitle.endsWith(".txt")) {
                        // if it does, it deletes the last 4 characters (the length of ".txt").
                        // I couldn't find a better String method to do it, so this is what I went with.
                        listTitle = listTitle.substring(0, listTitle.length() - 4);
                    }

                    // reads the file to the list
                    try {
                        // instantiates the stream and reader
                        InputStream stream = new BufferedInputStream(Files.newInputStream(path, CREATE));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                        // reads each line into its own element in the ArrayList
                        while (reader.ready()) {
                            list.add(reader.readLine());
                        }
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /* IMPORTANT:
                    The optionsMenu() method contains every single bit of code to do with editing the lists.
                    This is because it's pretty bulky and it's the exact same whether the user opened a list
                    with JFileChooser or created a new one. I just didn't want two identical mountains of code
                    cluttering up my main method.
                     */
                    optionsMenu(list, listTitle, path);
                }

            } else if (menuChoice.equals("a")) { // if they chose to add a new list:

                list = new ArrayList<>(); // initializes the list in case it's null

                // prompts for a title
                listTitle = SafeInput.getNonZeroLenString(in, "What would you like to name this list?");

                // passes that title into the path as the name of the file
                path = Paths.get(file.getPath() + "/" + listTitle + ".txt");

                // forces them to add something
                System.out.println("Add something to it:");
                list.add(in.nextLine());

                // finally loops the list edit menu
                optionsMenu(list, listTitle, path);

            } else { // if else, they chose to quit
                // prompts for confirmation first
                if (SafeInput.getYNConfirm(in, "Quit?")) {
                    System.out.println("See you next time!");
                    System.exit(0);
                }
            }
        } while (true); // loops "forever", since the user can quit
    }


    /**
     * Loops through the entire list edit process, showing the menu and carrying out each of its options.
     * Used twice within the main menu loop
     *
     * @param list ArrayList being edited
     * @param listTitle title of the list
     * @param path path to the list's corresponding file
     */
    private static void optionsMenu(ArrayList<String> list, String listTitle, Path path) {
        Scanner in = new Scanner(System.in);
        String menuChoice;
        boolean dirty = false;
        int deleteIndex;

        // loops over the list edit menu and options while the list is not null
        // whenever the user decides to quit, it just sets the list to null
        while (list != null) {

            // uses a method inside the Print class to print the menu
            menuChoice = Print.printOptionsMenu();

            switch (menuChoice) {
                case "s" -> {
                    // if they chose to save, calls the save method
                    try {
                        save(path, list);
                        dirty = false; // list is now clean
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    // if they chose to clear, asks for confirmation first
                    if (SafeInput.getYNConfirm(in, "Are you sure?")) {

                        list.clear(); // clears all elements from list
                        dirty = true; // list is now dirty

                    } else { // if they say no to the confirmation, the dirty flag is unaffected
                        System.out.println("Canceled.");
                    }
                }
                case "a" -> {
                    // if they chose to add something, it just asks for input and then adds it to the list
                    System.out.println("What would you like to add?");
                    list.add(in.nextLine());
                    dirty = true; // the list is now dirty
                }
                case "d" -> { // if they chose to delete something, first checks if the list has 0 or 1 items

                    if (list.size() == 0) {
                        // if it's empty, kicks them out
                        System.out.println("Nothing to delete.");

                    } else if (list.size() == 1) {
                        // if there's one item, asks them for confirmation
                        System.out.println("This list only has one thing.");
                        if (SafeInput.getYNConfirm(in, "Delete it?")) {
                            list.clear();
                            dirty = true;
                        }

                    } else {
                        //otherwise, shows them the list and asks what number they want to delete
                        Print.list(list, listTitle);

                        // uses the getRangedInt method, with the min being 0 and the max being the size of the list.
                        // then subtracts one to get the actual index of the item they want to delete
                        deleteIndex = SafeInput.getRangedInt(in, "Enter the number of the item you'd like to delete, or 0 to cancel", 0, list.size()) - 1;
                        list.remove(deleteIndex); // deletes that item
                        dirty = true; // the list is now dirty
                    }
                }
                case "v" ->
                    // if they chose to view the list, calls the view method
                    // The view method is very simple. The only reason I turned
                    // it into a method is because I reuse it a few times.
                        Print.list(list, listTitle);

                default -> { // they chose to exit out of the list:
                    // checks if the list is dirty
                    if (dirty) {

                        // prompts them to save before quitting
                        if (SafeInput.getYNConfirm(in, "Would you like to save?")) {
                            // saves
                            try {
                                save(path, list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    list.clear(); // clears the list

                    // resets the dirty flag, list, and title, thus breaking the loop/method
                    dirty = false;
                    listTitle = null;
                    list = null;
                }
            }
        } // loops while list is not null
    }

    /**
     * Saves an ArrayList into a text file. If the list originally came from a text file, it deletes the old file and
     * creates a new one in the same path.
     *
     * @param path location and name of the new file
     * @param list ArrayList to be saved
     * @throws IOException If an output exception occurs
     */
    private static void save(Path path, ArrayList<String> list) throws IOException {

        try {
            // if the file exists, deletes the old file
            if (path.toFile().exists()) {
                Files.delete(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // (re)creates a file at that path
            Files.createFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // instantiates output stream and writer
        OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        // writes each string in the list into the file
        for (String s : list) {
            writer.write(s, 0, s.length());
            writer.newLine();
        }
        writer.close(); // close the writer
    }
}