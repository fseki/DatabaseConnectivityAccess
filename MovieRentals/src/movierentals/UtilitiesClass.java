package movierentals;

import java.io.*;
import java.util.*;

/*
 * Class whose object will be in charge of handling some of the program's extra
 * features, like exporting a list of movies to an external file
*/
public class UtilitiesClass {
    
    private ArrayList<ArrayList<String>> data;
    private File fileToExport;
    
    public UtilitiesClass(File newFile){
        fileToExport = newFile;
    }
    
    public void exportMoviesToFile(ArrayList<ArrayList<String>> dataToExport){
        data = dataToExport;
     
        try{
            PrintWriter pw = new PrintWriter(fileToExport);
           
            for (int i = 0; i < data.size(); i++){
                for (int m = 0; m < data.get(i).size(); m++){
                    pw.println(data.get(i).get(m) + "\t");
                    pw.flush();
                }
            }
            pw.close();
        
        }
        catch (FileNotFoundException fnf){
            System.out.println("The file to which you want to export has not been found.");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public void exportMovieInfoToFile(){
        
    }
}
