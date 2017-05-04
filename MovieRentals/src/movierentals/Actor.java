package movierentals;

import java.util.*;

public class Actor {
    
    private int id;
    private String firstName;
    private String lastName;
    private String dob;
    private int age;
    private String placeOfBirth;
    
    public Actor(){
        
    }
    
    private int getActorId(){
        return id;
    }
    
    private void setActorId(int actorId){
        id = actorId;
    }
    
    public String getActorInfo(MovieRentalsDatabase db, int movie_id){
        String info = "";
        ArrayList<ArrayList<String>> data;
        ArrayList<ArrayList<String>> tmpData;
        ArrayList<String> values = new ArrayList<String>();
        
        String query = "SELECT actor_id FROM actors_movie WHERE movie_id = ?";
        values.add("" + movie_id);
        
        try{
            data = db.getDataWithSpecificNumCols(query, values, 1);
            for (int i = 0; i < data.size(); i++){
                id = Integer.parseInt(data.get(i).get(0));
                
                values.clear();
                values.add("" + id);
                String query2 = "SELECT actor_fname, actor_lname, actor_dob, actor_age, actor_place_of_birth FROM actor WHERE actor_id = ?";
                tmpData = db.getDataWithSpecificNumCols(query2, values, 5);
                
                for (int m = 0; m < tmpData.size(); m++){
                    info += "Name: " + tmpData.get(m).get(0) + " " + tmpData.get(m).get(1) + "\n";
                    info += "Date of birth: " + tmpData.get(m).get(2) + "\n" ;
                    info += "Age: " + tmpData.get(m).get(3) + "\n" ;
                    info += "Place of birth: " + tmpData.get(m).get(4) + "\n\n" ;
                }
            }
        }
        catch (InfoException ie){
            System.out.println(ie.getInfo());
        }
        
        return info;
    }
}
