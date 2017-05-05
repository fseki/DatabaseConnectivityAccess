package movierentals;

import java.util.*;
/**
 * Class Studio which get studio information
 *
 * @authors Fran Seki, Marko Parac, Matija Ozetski, Adi Cengic & Marko Zivko
 * @version 1.0 
 * ISTE-330.700 
 * prof. Branko Mihaljevic 
 * RIT Croatia, 2017
 */
public class Studio {
    
    private int studio_id;
    private String name;
    private String city;
    /**
     * Default Constructor
     */
    public Studio(){}
    /**
     * Method getStudioInfo
     * @param db type MovieRentalsDatabase 
     * @param movie_id type integer
     * @return 
     */
    public String getStudioInfo(MovieRentalsDatabase db, int movie_id){
        String info = "";
        ArrayList<ArrayList<String>> data;
        ArrayList<String> values = new ArrayList<String>();
        String query = "SELECT studio_id FROM movie WHERE movie_id = ?";
        values.add("" + movie_id);
        
        try{
            data = db.getDataWithSpecificNumCols(query, values, 1);
            studio_id = Integer.parseInt(data.get(0).get(0));
            
            query = "SELECT studio_name, studio_city FROM studio WHERE studio_id = ?";
            values.clear();
            values.add("" + studio_id);
            data = db.getDataWithSpecificNumCols(query, values, 2);
            
            info += "Studio name: " + data.get(0).get(0) + "\n";
            info += "Based in: " + data.get(0).get(1);
        }
        catch (InfoException ie){
            System.out.println(ie.getInfo());
        }
        return info;
    }
}
