package movierentals;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

/*
 * Class whose object will be in charge of handling some of the program's extra
 * features, like exporting a list of movies to an external file
 */
public class UtilitiesClass {

    private ArrayList<ArrayList<String>> data;
    private String movieInfo;
    private File fileToExportMovies;
    private File fileToExportMovieInfo;

    public UtilitiesClass() {
        fileToExportMovies = new File("movies_list.txt");

        fileToExportMovieInfo = new File("movie_info.txt");
    }

    public void exportMoviesToFile(ArrayList<ArrayList<String>> dataToExport) {
        data = dataToExport;
        try {
            PrintWriter pw = new PrintWriter(fileToExportMovies);
            pw.printf("%-25s%-15s%-15s%-10s", "Movie Name", "Release Date", "Genre", "Age Rating");
            pw.println("\n");
            pw.flush();
            
            String line = "";
            for (int i = 0; i < data.size(); i++) {
                pw.printf("%-25s%-15s%-15s%-10s", data.get(i).get(0), data.get(i).get(1), data.get(i).get(2), data.get(i).get(3));
                pw.println();
                pw.flush();
            }
            pw.close();
            JOptionPane.showMessageDialog(null, "Movies exported successfully!");
        } catch (FileNotFoundException fnf) {
            System.out.println("The file to which you want to export has not been found.");
        }
    }

    public void exportMovieInfoToFile(String infoToExport) {
        movieInfo = infoToExport;
        try {
            PrintWriter pw = new PrintWriter(fileToExportMovieInfo);
            
            pw.println(movieInfo);
            pw.flush();
            pw.close();
            System.out.println("Successfully exported movie info.");
        } catch (FileNotFoundException fnf) { 
            System.out.println("The file to which you want to export has not been found.");
        }
    }
    
    /*
     * Method which checks whether a user has rented a movie, and if a movie has been rented
     * checks if the movie has to be returned soon (tomorrow).
     * Notifies the user if it does have to be returned soon
     * @param db, Database object
     * @param userId, Id of the user that has logged in
    */
    public void notifyUser(MovieRentalsDatabase db, int userId){
        long currentTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> data;
        ArrayList<String> values = new ArrayList<String>();
        values.add("" + userId);
        
        String query = "SELECT date_due, movie_id FROM movies_on_loan WHERE user_id = ?";
        
        data = db.getDataWithSpecificNumCols(query, values, 2);
        Date date = null;
        if (data != null){
            String date_due = data.get(0).get(0);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            date = new Date(currentTime + 86400000);
            String date2 = df.format(date);
            
            System.out.println("Date due: " + date_due);
            System.out.println("Current date: " + date2);
            
            if ((date_due.equals(date2))){
                System.out.println("The user needs to return a movie tomorrow");
                values.clear();
                values.add(data.get(0).get(1));
                String query2 = "SELECT movie_title FROM movie WHERE movie_id = ?";
                data = db.getDataWithSpecificNumCols(query2, values, 1);
                String movieName = data.get(0).get(0);
                JOptionPane.showMessageDialog(null, "REMINDER: The movie you have rented, " + movieName + ", has to be returned tomorrow.");
            }
        }
        else{
            System.out.println("No movies rented. No need to notify");
        }
    }
}
