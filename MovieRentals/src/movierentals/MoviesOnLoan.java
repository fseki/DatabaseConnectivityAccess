package movierentals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class Movies on loan check which movies are on loan
 *
 * @authors Fran Seki, Marko Parac, Matija Ozetski, Adi Cengic & Marko Zivko
 * @version 1.0 
 * ISTE-330.700 
 * prof. Branko Mihaljevic 
 * RIT Croatia, 2017
 */
public class MoviesOnLoan {

    private int movie_id;
    private int user_id;
    private String date_due;
    private boolean returned;
    /**
     * Default constructor 
     * no parameters
     */
    public MoviesOnLoan() {}
    /**
     * Constructor 
     * @param mId type integer
     * @param uId type integer
     */
    public MoviesOnLoan(int mId, int uId) {
        movie_id = mId;
        user_id = uId;
    }
    /**
     * Constructor
     * @param mId type integer 
     * @param uId type integer
     * @param newDate type String
     * @param newReturned type boolean
     */
    public MoviesOnLoan(int mId, int uId, String newDate, boolean newReturned) {
        movie_id = mId;
        user_id = uId;
        date_due = newDate;
        returned = newReturned;
    }
    /**
     * Method getUserId
     * @return 
     */
    public int getUserId() {
        return user_id;
    }
    /**
     * Method that check which movies are rented 
     * @param db type MovieRentalsDatabase
     * @param movieName type String
     * @param userID type integer
     * @return  rented type boolean
     */
    public boolean hasRentedMovie(MovieRentalsDatabase db, String movieName, int userID) {
        boolean rented = false;
        try {

            ArrayList<String> values = new ArrayList<String>();
            String query = "SELECT movie_id FROM movie WHERE movie_title = ?";
            values.add(movieName);
            ArrayList<ArrayList<String>> data = db.getDataWithSpecificNumCols(query, values, 1);
            movie_id = Integer.parseInt(data.get(0).get(0));
            query = "SELECT returned from movies_on_loan WHERE movie_id = ? && user_id = ?";
            values.clear();
            values.add("" + movie_id);
            values.add("" + userID);
            data = db.getDataWithSpecificNumCols(query, values, 1);
            if (data != null && data.size() != 0) {
                rented = true;
            }

        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }
        return rented;
    }

    /*
     * Method which performs updates on the database in order to display a movie
     * as rented, and save information about the user which rented the movie
     * @param db, Database object
     * @param movieName, Name of the movie to be rented
     * @param forWeek, Whether the user wants to rent the movie for a week or day
     * @param userId, Id of the user that is renting the movie
     */
    public void rentMovie(MovieRentalsDatabase db, String movieName, boolean forWeek, int userId) {
        try {
            user_id = userId;

            String query = "INSERT INTO movies_on_loan VALUES(?, ?, ?, ?)";
            ArrayList<String> values = new ArrayList<String>();

            String query2 = "SELECT movie_id FROM movie WHERE movie_title = ?";
            values.add(movieName);
            ArrayList<ArrayList<String>> data = db.getDataWithSpecificNumCols(query2, values, 1);
            movie_id = Integer.parseInt(data.get(0).get(0));

            long currentTime = System.currentTimeMillis();

            if (forWeek) {
                currentTime += 604800000; //adds a week
            } else {
                currentTime += 86400000; //adds a day
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(currentTime);
            date_due = sdf.format(date);

            values.clear();
            values.add("" + movie_id);
            values.add("" + user_id);
            values.add(date_due);
            values.add("" + 0);

            boolean success = db.setData(query, values);

            if (success) {
                System.out.println("Successfully rented the movie");
            } else {
                System.out.println("Could not rent the movie");
            }
        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }
    }

    /*
     * Method which returns a movie that had previously been rented
     * by a user
     * @param db, Database object
     * @param movieName, Name of the movie to return
     * @param userId, Id of the user that is returning the movie
     */
    public void returnMovie(MovieRentalsDatabase db, String movieName, int userId) {
        try {
            String query = "DELETE FROM movies_on_loan WHERE user_id = ? && movie_id = ?";

            ArrayList<ArrayList<String>> data;
            ArrayList<String> values = new ArrayList<String>();
            values.add(movieName);

            user_id = userId;

            String query2 = "SELECT movie_id FROM movie WHERE movie_title = ?";
            data = db.getDataWithSpecificNumCols(query2, values, 1);
            movie_id = Integer.parseInt(data.get(0).get(0));

            values.clear();
            values.add("" + user_id);
            values.add("" + movie_id);

            boolean success = db.setData(query, values);

            if (success) {
                System.out.println("Movie returned successfully");
            } else {
                System.out.println("Movie could not be returned");
            }
        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }
    }
}
