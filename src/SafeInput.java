import java.util.Scanner;
import java.util.regex.Pattern;

public class SafeInput {

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {

        String retString;  // Set this to zero length. Loop runs until it isn’t
        do
        {
            System.out.print("\n" + prompt + ":\n"); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return any integer
     */
    public static int getInt(Scanner pipe, String prompt) {

        int retInt = 0;  // stores valid int
        boolean validInput = false; // loop ender; is set to true when input is an integer
        String trash; // stores bad input

        // loop
        do {
            // prompt user
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt(); // store good input
                pipe.nextLine(); // clear pipe
                validInput = true; // end loop
            } else {
                trash = pipe.nextLine(); // store trash
                System.out.println("ERROR: '" + trash + "' is not an integer."); // print error
            }

        } while (!validInput);

        return retInt;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return any double
     */
    public static double getDouble(Scanner pipe, String prompt) {

        double retDouble = 0.0; // stores input
        boolean validInput = false; // loop ender; is set to true when input is an integer
        String trash; // stores bad input

        // loop
        do {
            // prompt user
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble(); // store good input
                pipe.nextLine(); // clear pipe
                validInput = true; // end loop
            } else {
                trash = pipe.nextLine(); // store trash
                System.out.println("ERROR: '" + trash + "' is not a double."); // print error
            }

        } while (!validInput);

        return retDouble;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user -- method automatically appends the range onto the end of the prompt, so don't include it in the string.
     * @param low min value for input (inclusive)
     * @param high max value for input (inclusive)
     * @return integer within specified range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){

        int tempInt; // stores "temporary input" (integer to be tested for range)
        int retInt = 0; // stores final valid input (integer that is within range)
        boolean validInput = false; // loop ender; is set to true when input is an integer
        String trash; // stores bad input

        // loop
        do {
            // prompt user
            System.out.print("\n" + prompt + " (" + low + " - " + high + "):\n");

            // initial check for integer
            if (pipe.hasNextInt()) {
                tempInt = pipe.nextInt(); // store int
                pipe.nextLine(); // clear pipe

                if (tempInt >= low) {
                    if (tempInt <= high) { // if fits both criteria

                        retInt = tempInt; // store temporary value in return variable
                        validInput = true; // end loop


                    } else { // if below min, print error
                        System.out.println("ERROR: " + tempInt + " is above the accepted range.");
                    }

                } else { // if above max, print error
                    System.out.println("ERROR: " + tempInt + " is below the accepted range.");
                }

            } else { // if fails integer test
                trash = pipe.nextLine(); // store trash
                System.out.println("ERROR: '" + trash + "' is not an integer."); // print error
            }

        } while (!validInput);

        return retInt;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user -- method automatically appends the range onto the end of the prompt, so don't include it in the string.
     * @param low min value for input (inclusive)
     * @param high max value for input (inclusive)
     * @return double within specified range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {

        double tempDouble; // stores "temporary input" (integer to be tested for range)
        double retDouble = 0.0; // stores final valid input (integer that is within range)
        boolean validInput = false; // loop ender; is set to true when input is an integer
        String trash; // stores bad input

        // loop
        do {
            // prompt user
            System.out.print("\n" + prompt + " (" + low + " - " + high + "): ");

            // initial check for double
            if (pipe.hasNextDouble()) {
                tempDouble = pipe.nextDouble(); // store double
                pipe.nextLine(); // clear pipe

                if (tempDouble >= low) {
                    if (tempDouble <= high) { // if fits both criteria

                        retDouble = tempDouble; // store temporary value in return variable
                        validInput = true; // end loop


                    } else { // if below min, print error
                        System.out.println("ERROR: " + tempDouble + " is above the accepted range.");
                    }

                } else { // if above max, print error
                    System.out.println("ERROR: " + tempDouble + " is below the accepted range.");
                }

            } else { // if fails double test
                trash = pipe.nextLine(); // store trash
                System.out.println("ERROR: '" + trash + "' is not a double."); // print error
            }

        } while (!validInput);

        return retDouble;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return boolean; true if user says yes, false if user says no
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt) {

        String inputString; // stores input
        boolean retBool = false; // stores yes/no answer as boolean to be returned
        boolean validInput = false; // loop ender

        do {
            System.out.print("\n" + prompt + "\n"); // prompt user
            inputString = pipe.next(); // store input
            pipe.nextLine(); // clear pipe

            // if it is one character:
            if (inputString.length() == 1) {

                // proceed to check what the answer is.
                if (inputString.equalsIgnoreCase("Y")) { // if answer is yes
                    retBool = true; // return variable set to true
                    validInput = true; // end loop

                } else if (inputString.equalsIgnoreCase("N")) { // if answer is no
                    // return variable is already set to false
                    validInput = true; // end loop

                } else { // if not yes and not no
                    System.out.println("ERROR: '" + inputString + "' could not be understood. (Enter Y or N.)"); // print error
                }

            } else { // if fails char test
                System.out.println("ERROR: '" + inputString + "' is too long."); // print error
            }

        } while (!validInput);

        return retBool;
    }


    /**
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param regEx RegEx pattern (in String format) that the input should match
     * @return input String that matches the RegEx pattern
     */
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String retString; // stores input
        boolean matchFound; // loop ender

        do {
            System.out.print(prompt + ": "); // print prompt
            retString = pipe.next(); // store input
            pipe.nextLine(); // clear pipe

            /*
            take the String version of a regex pattern that was passed into the method & turn it into
            an actual regex pattern.
            within the same statement, check if it matches the string collected from the user.
            end loop and return that match if it does.
             */
            matchFound = Pattern.compile(regEx).matcher(retString).matches();

            // if it doesn't match, print error.
            if (!matchFound) {
                System.out.print("Error: '" + retString + "' is in the wrong format.\n");
            }

        } while (!matchFound);

        return retString;
    }


    /**
     * prints a given message centered within a lovely header (max width is 60 characters)
     *
     * @param msg the message to be displayed in the header
     */
    public static void prettyHeader(String msg) {

        // top line
        for (int i = 0; i < 66; i++) {
            System.out.print("*");
        }

        // skip a line
        System.out.println();

        // 3 asterisks on left end
        for (int i = 0; i < 3; i++) {
            System.out.print("*");
        }

        /*
        loop over length of white space on left, printing spaces.
        the total amount of white space is 60 minus the length of the message.
        if you divide that by 2, you get the amount of white space on the left.
        however, java always rounds down when dividing ints, so if the message length is odd,
        we need to add 1 extra space to the right side.
         */

        // print left side spaces:
        for (int i = 0; i < (60 - msg.length()) / 2; i++) {
            System.out.print(" ");
        }

        // print message
        System.out.print(msg);

        // check if the message length is odd or even
        if (msg.length() % 2 == 0) {

            // if it is even, print the same number of spaces as before.
            for (int i = 0; i < (60 - msg.length()) / 2; i++) {
                System.out.print(" ");
            }
        } else {

            // if it is odd, add 1 to the middle argument in the for loop
            for (int i = 0; i < ((60 - msg.length()) / 2) + 1; i++) {
                System.out.print(" ");
            }
        }

        // 3 asterisks on right
        for (int i = 0; i < 3; i++) {
            System.out.print("*");
        }

        // skip a line
        System.out.println();

        // bottom line
        for (int i = 0; i < 66; i++) {
            System.out.print("*");
        }

        // skip a line at the end
        System.out.println();
    }
}
