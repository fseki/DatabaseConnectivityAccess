package movierentals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class InfoException for logging the exceptions
 *
 * @authors Fran Seki, Marko Parac, Matija Ozetski, Adi Cengic & Marko Zivko
 * @version 1.0 
 * ISTE-330.700 
 * prof. Branko Mihaljevic 
 * RIT Croatia, 2017
 */
public class InfoException extends Exception {

    //attributes to store the exception messages, the 
    //time format for a log timestamp, and SQLException specifics
    private String message = "Unable to complete operation.";
    private String sqlState = null;
    private int errorCode = 0;
    private String exceptionMessage = null;
    private final DateFormat DF = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    /*
	 * Parameterized constructor which sets the superclass
	 * attribute, and calls the log method
	 * @param e, The exception that occurred
     */
    public InfoException(Exception e) {
        super(e.getMessage());
        log(e);
    }

    /*
	 * Parameterized constructor which sets the attributes
	 * and calls the log method
	 * @param e, The exception that occurred
	 * @param state, The SQL state
	 * @param code, SQL eror code
	 * @param message, SQL Error message
     */
    public InfoException(Exception e, String state, int code, String message) {
        super(e.getMessage());
        sqlState = state;
        errorCode = code;
        exceptionMessage = message;
        log(e);
    }

    /*
    * Method which returns the generic exception message
    * @return message, Exception message
     */
    public String getInfo() {
        return message;
    }

    /*
     * Method which logs exception information to a file
    * @param ex, The exception that occured
     */
    public void log(Exception ex) {
        try {
            //IO Object creation
            File file = new File("log.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            //getting the current time, date and setting its format
            Date date = new Date();
            String timeStamp = DF.format(date);

            pw.println("Time: " + timeStamp);

            //checking if sqlexception specific data exists
            if (sqlState != null) {
                pw.println("SQL State: " + sqlState);
                pw.println("Error code: " + errorCode);
                pw.println("Message: " + exceptionMessage);
            } else {
                pw.println("Message: " + ex.getMessage());
            }
            pw.println("Stack trace: ");
            ex.printStackTrace(pw);
            pw.println();
            pw.flush();
            pw.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("The log file could not be found.");
        } catch (IOException ioe) {
            System.out.println("A problem occured while logging.");

        } catch (Exception e) {
            System.out.println("A problem occured while logging");
        }
    }
}
