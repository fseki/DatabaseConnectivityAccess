package movierentals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 * Class Movie with  methods; JFrame form used
 *
 * @authors Fran Seki, Marko Parac, Matija Ozetski, Adi Cengic & Marko Zivko
 * @version 1.0 
 * ISTE-330.700 
 * prof. Branko Mihaljevic 
 * RIT Croatia, 2017
 */
public class Movie {

    private int movie_id;
    private String title;
    private String release_date;
    private int age_rating;
    private String genre;
    private String studio;
    private int genre_id;
    private int studi0_id;
    private boolean rented = false;
    /**
     * Default constructor 
     * no parameters
     */
    public Movie() {}
    /**
     * Constructor 
     * @param id type integer
     */
    public Movie(int id) {
        movie_id = id;
    }
    /**
     * Constructor
     * @param id type integer
     * @param newTitle type String 
     * @param newDate type String
     * @param rating  type integer
     */
    public Movie(int id, String newTitle, String newDate, int rating) {
        movie_id = id;
        title = newTitle;
        release_date = newDate;
        age_rating = rating;
    }
    /**
     * Method for setting rented movies
     * @param rented 
     */
    public void setRented(boolean rented) {
        this.rented = rented;
    }
    /**
     *  Method to check is movie rented 
     * @return rented type boolean
     */
    public boolean isRented() {
        return rented;
    }

    /*
     * Method which gets all the movies and related info from the database
     * Will be used if the user wishes to see all movies and not perform a search
     * @param db, Database object
     */
    public ArrayList<ArrayList<String>> fetchAll(MovieRentalsDatabase db) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> dataToReturn = new ArrayList<ArrayList<String>>();
        ArrayList<String> values = new ArrayList<String>();

        String query = "select movie_id, movie_title, movie_year, genres.genre_name, age_rating from \n"
                + "movie JOIN genres ON movie.genre_id = genres.genre_id";
        try {
            data = db.getDataWithSpecificNumCols(query, values, 5);

            movie_id = Integer.parseInt(data.get(0).get(0));
            title = data.get(0).get(1);
            release_date = data.get(0).get(2);
            genre = data.get(0).get(3);
            age_rating = Integer.parseInt(data.get(0).get(4));

        } catch (InfoException ie) {
            System.out.println(ie.getInfo());
        }
        return data;
    }

    /*
     * Method which will search for movies based on the entered search term and the filter
     * @param db, Database object
     * @param filter, Will be what the user is looking for - i.e. Title, Genre, Studio etc.
     * @param searchTerm, The search term that the user entered in the field
     */
    public ArrayList<ArrayList<String>> fetchFromSearch(MovieRentalsDatabase db, String searchTerm) {
        ArrayList<ArrayList<String>> data = null;
        ArrayList<ArrayList<String>> dataToReturn = new ArrayList<ArrayList<String>>();
        ArrayList<String> values = new ArrayList<String>();
        values.add(searchTerm);

        String query = "select movie_id, movie_title, movie_year, genres.genre_name, age_rating from \n"
                + "movie JOIN genres ON movie.genre_id = genres.genre_id WHERE movie_title LIKE ?";

        try {
            data = db.getDataWithSpecificNumCols(query, values, 5);

            movie_id = Integer.parseInt(data.get(0).get(0));
            title = data.get(0).get(1);
            release_date = data.get(0).get(2);
            genre = data.get(0).get(3);
            age_rating = Integer.parseInt(data.get(0).get(4));

        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        } catch (IndexOutOfBoundsException obe) {
            JOptionPane.showMessageDialog(null, "No movies pertaining your search found...");
        }

        return data;
    }
    /**
     * Method fetchTitle
     * @param db type MovieRentalDatabase
     * @param id type integer
     * @return title
     */
    public String fetchTitle(MovieRentalsDatabase db, int id) {
        ArrayList<ArrayList<String>> data = null;
        ArrayList<ArrayList<String>> dataToReturn = new ArrayList<ArrayList<String>>();
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(id);

        String query = "SELECT movie_title from \n"
                + "movie  WHERE movie_id = " + id;

        try {
            data = db.getData(query);

            title = data.get(0).get(1);
        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }catch(IndexOutOfBoundsException ioex){
            //JOptionPane.showMessageDialog(null, "Movie ID doesn't exist!");
            System.out.println("Movie ID doesn't exist!");
        }

        return title;
    }

    /*
     * Method which will be used to insert new movies form the administrator interface
     * @param db, Database object
     * @param title, The title of the new movie
     * @param date, The release date of the new movie
     * @param ageRating, The age rating of the new movie
     */
    public boolean addMovie(MovieRentalsDatabase db, String title, String date, int genre, int studio, int ageRating) {
        boolean success = false;
        ArrayList<String> values = new ArrayList<String>();

        values.add(title);
        values.add(date);
        values.add("" + genre);
        values.add("" + studio);
        values.add("" + ageRating);

        String query = "INSERT INTO movie(movie_title, movie_year, genre_id, studio_id, age_rating) VALUES(?, ?, ?, ?, ?)";
        try {
            success = db.setData(query, values);
            if (success) {
                System.out.println("INSERTED SUCCESSFULLY");
            } else {
                System.out.println("FAILED TO INSERT");
                return false;
            }
        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }
        return true;
    }

    /*
     * Method which will be used to delete movies from the administrator interface
     * @param db, Database object
     */
    public boolean deleteMovie(MovieRentalsDatabase db, int movie_id) {
        this.movie_id = movie_id;
        boolean success = false;
        try {

            ArrayList<String> values = new ArrayList<String>();

            values.add("" + movie_id);

            String query = "DELETE FROM movie WHERE movie_id = ?";

            success = db.setData(query, values);

            if (success) {
                System.out.println("DELETED SUCCESSFULLY");
            } else {
                System.out.println("FAILED TO DELETE");
                return false;
            }
        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        }
        return true;
    }

    /*
     * Method which performs multiple queries to get all the needed information
     * about a particular movie
     * @param db, Database object
     * @param movieName, The name of the movie for which information will be gathered
     * @return output, The movie information
     */
    public String getMovieInfo(MovieRentalsDatabase db, String movieName) {
        String output = "\nRented: ";

        try {
            ArrayList<ArrayList<String>> data;
            ArrayList<String> values = new ArrayList<String>();
            values.add(movieName);

            //checking if the movie has been rented
            String query = "SELECT returned FROM movies_on_loan JOIN movie ON movies_on_loan.movie_id = "
                    + "movie.movie_id WHERE movie_title = ?";

            data = db.getDataWithSpecificNumCols(query, values, 1);

            if (data == null || data.size() == 0) {
                output += "Available for rent\n\n";
                setRented(false);
            } else {
                String rented = data.get(0).get(0);

                //a movie can be in the rented movies table
                //and have a returned value of true
                if (rented.equalsIgnoreCase("1")) {
                    output += "Available for rent\n\n";
                    setRented(false);
                } else {
                    output += "Yes\n\n";
                    setRented(true);
                }
            }

            //getting movie information
            query = "SELECT movie_id, movie_title, movie_year, genres.genre_name, age_rating, "
                    + "studio.studio_name FROM movie JOIN genres ON movie.genre_id = genres.genre_id JOIN "
                    + "studio ON movie.studio_id = studio.studio_id WHERE movie_title = ?";

            data = db.getDataWithSpecificNumCols(query, values, 6);

            movie_id = Integer.parseInt(data.get(0).get(0));

            for (int i = 0; i < data.size(); i++) {
                output += "Name: " + data.get(i).get(1) + "\n";
                output += "Release Year: " + data.get(i).get(2) + "\n";
                output += "Genre: " + data.get(i).get(3) + "\n";
                output += "Age Rating: " + data.get(i).get(4) + "\n";
                output += "Studio: " + data.get(i).get(5) + "\n";
            }

            //getting director information
            query = "select concat(director.director_fname, \" \", director.director_lname)\n"
                    + "FROM movie JOIN director_movie on movie.movie_id = \n"
                    + "director_movie.movie_id JOIN director on director_movie.director_id = \n"
                    + "director.director_id WHERE movie.movie_id = ?";
            values.clear();
            values.add("" + movie_id);
            data = db.getDataWithSpecificNumCols(query, values, 1);

            output += "Director: " + data.get(0).get(0) + "\n";

            //getting the actors for the specific movie
            query = "SELECT actors_movie.actor_id FROM actors_movie JOIN movie ON actors_movie.movie_id = "
                    + "movie.movie_id WHERE movie.movie_id = ?";

            values.clear();
            values.add("" + movie_id);
            data = db.getDataWithSpecificNumCols(query, values, 1);
            ArrayList<Integer> actorId = new ArrayList<Integer>();

            for (int i = 0; i < data.size(); i++) {
                int id = Integer.parseInt(data.get(i).get(0));
                actorId.add(id);
            }

            query = "SELECT CONCAT(actor_fname, \" \", actor_lname) FROM actor WHERE ";
            values.clear();

            for (int i = 0; i < actorId.size(); i++) {
                values.add("" + actorId.get(i));
                query += "actor_id = ? ";
                if (i < actorId.size() && i + 1 != actorId.size()) {
                    query += " || ";
                }
            }

            data = db.getDataWithSpecificNumCols(query, values, 1);
            output += "Actor(s): \n";
            for (int i = 0; i < data.size(); i++) {
                output += "-- " + data.get(i).get(0) + "\n";
            }

        } catch (InfoException ex) {
            System.out.println(ex.getInfo());
        } catch (IndexOutOfBoundsException ibe) {
            JOptionPane.showMessageDialog(null, "Oops, it seems like there is some information missing for this movie.");
        }
        return output;
    }
    /**
     * Method to print Movies
     * @param data type ArrayList<ArrayList<String>>
     * @return result type String
     */
    public String printMovies(ArrayList<ArrayList<String>> data) {
        String result = "";

        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                for (int m = 0; m < data.get(i).size(); m++) {
                    result += data.get(i).get(m) + ", ";
                }
                result += "\n";
            }
        }
        return result;
    }
}
