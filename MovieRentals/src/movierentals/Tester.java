/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierentals;

/**
 *
 * @author Fran
 */
public class Tester {
    public static void main(String [] args){
        
        MovieRentalsDatabase db = new MovieRentalsDatabase("root", "pass", "localhost", "3306", "movie_rentals");
        /*db.connect();
        
        MoviesOnLoan loan = new MoviesOnLoan();
        //loan.rentMovie(db, "Taken", false, 1);
        UtilitiesClass util = new UtilitiesClass();
        util.notifyUser(db, 1);
        
        db.close();*/
    }
}
