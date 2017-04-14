import java.util.*;

/**
 * Tester class for the MovieRentalsDatabase class
 * The class accepts user input as database
 * connection parameters, performs some actions and
 * opens and closes the connection
 * 
 * @author Matija Ozetski, Marko Parac, Marko Zivko, Adi Cengic, Fran Seki
 * @version 1.0
 *
 */
public class MovieDatabaseRentals {
	
	public static void main (String [] args){
		
		ArrayList<ArrayList<String>> data;
		Scanner in = new Scanner (System.in);
		
		String userName = null;
		String password = null;
		String dbServer = null;
		String dbName = null;
		String dbPort = null;
		
		System.out.print("Please, enter your userID: ");
		userName = in.nextLine();
		
		System.out.print("Please, enter your password: ");
		password = in.nextLine();
		
		System.out.print("Please, enter the DB server name: ");
		dbServer = in.nextLine();
		
		System.out.print("Please, enter the port number on which your server is configured: ");
		dbPort = in.nextLine();
		
		System.out.print("Please, enter the name of the database: ");
		dbName = in.nextLine();
		
		in.close(); 
		
		MovieRentalsDatabase myDatabase = new MovieRentalsDatabase(userName, password, dbServer, dbPort, dbName);
	
		
		
		myDatabase.connect();
		myDatabase.close();
		
		//equipment object needed for calling printEquipment method
		
	}
}
