package movierentals;

import java.util.*;

public class Movie {
    
    private int movie_id;
    private String title;
    private String release_date;
    private int age_rating;
    
    private String genre;
    private String studio;
    private int genre_id;
    private int studii_id;
    
    public Movie(){
        
    }
    
    public Movie(int id){
        movie_id = id;
    }
    
    public Movie(int id, String newTitle, String newDate, int rating){
        movie_id = id;
        title = newTitle;
        release_date = newDate;
        age_rating = rating;
    }
    
    /*
     * Method which gets all the movies and related info from the database
     * Will be used if the user wishes to see all movies and not perform a search
     * @param db, Database object
    */
    public ArrayList<ArrayList<String>> fetchAll(MovieRentalsDatabase db){
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> values = new ArrayList<String>();
        
        String query = "select movie_id, movie_title, movie_year, genres.genre_name, age_rating from \n" +
            "movie JOIN genres ON movie.genre_id = genres.genre_id";
        
        data = db.getDataWithSpecificNumCols(query, values, 5);
        movie_id = Integer.parseInt(data.get(0).get(0));
        title = data.get(0).get(1);
        release_date = data.get(0).get(2);
        genre = data.get(0).get(3);
        age_rating = Integer.parseInt(data.get(0).get(4));
        
        return data;
    }
    
    /*
     * Method which will search for movies based on the entered search term and the filter
     * @param db, Database object
     * @param filter, Will be what the user is looking for - i.e. Title, Genre, Studio etc.
     * @param searchTerm, The search term that the user entered in the field
    */
    public ArrayList<ArrayList<String>> fetchFromSearch(MovieRentalsDatabase db, String searchTerm){
         ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
         ArrayList<String> values = new ArrayList<String>();
         values.add(searchTerm);
         
         String query = "select movie_title, movie_year, genres.genre_name, age_rating from \n" +
            "movie JOIN genres ON movie.genre_id = genres.genre_id WHERE movie_title LIKE ?";
         
         data = db.getDataWithSpecificNumCols(query, values, 4);
         
         return data;
    }
    
    /*
     * Method which will be used to insert new movies form the administrator interface
     * @param db, Database object
     * @param title, The title of the new movie
     * @param date, The release date of the new movie
     * @param ageRating, The age rating of the new movie
    */
    public void addMovie(MovieRentalsDatabase db, String title, String date, int ageRating){
        boolean success = false;
        ArrayList<String> values = new ArrayList<String>();
        
        values.add(title);
        values.add(date);
        values.add("" + ageRating);
        
        String query = "INSERT INTO movie(title, release_year, age_rating) VALUES(?, ?, ?)";
        success = db.setData(query, values);
        
        if (success){
            System.out.println("INSERTED SUCCESSFULLY");
        }
        else{
            System.out.println("FAILED TO INSERT");
        }
    }
    
    /*
     * Method which will be used to delete movies from the administrator interface
     * @param db, Database object
    */
    public void deleteMovie(MovieRentalsDatabase db){
        boolean success = false;
        ArrayList<String> values = new ArrayList<String>();
        
        values.add("" + movie_id);
        
        String query = "DELETE FROM movie WHERE movie_id = ?";
        
        success = db.setData(query, values);
        
        if (success){
            System.out.println("DELETED SUCCESSFULLY");
        }
        else {
            System.out.println("FAILED TO DELETE");
        }
    }
    
    public void getMovieInfo(String movieName){
        
    }
    
    public String printMovies(ArrayList<ArrayList<String>> data){
        String result = "";
        
        if (data != null && data.size() > 0){
            for (int i = 0; i < data.size(); i++){
                for (int m = 0; m < data.get(i).size(); m++){
                    result += data.get(i).get(m) + ", ";
                }
                result += "\n";
            }
        }
        
        return result;
    }
}
