package movierentals;

import java.util.*;

/*
 * Class whose object handles the business side of the login and
 * registration process
 */
public class User {

    private int user_id;
    private String username;
    private String password;
    private String email;
    private String fName;
    private String lName;
    private String role;
    
    private MovieRentalsDatabase database;

    public User(MovieRentalsDatabase db) {
        database = db;
    }

    public User(int id) {
        user_id = id;
    }

    public User(MovieRentalsDatabase db, int id, String username, String password, String fName, String lName, String role) {
        database = db;
        user_id = id;
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
    }

    public boolean login(String newUsername, String newPassword) {
        username = newUsername;

        boolean valid = false;
        ArrayList<ArrayList<String>> data;
        ArrayList<String> values = new ArrayList<String>();
        values.add(username);

        data = database.getData("SELECT * FROM user WHERE username = ?", values);
        if (data == null || data.size() == 0) {
            valid = false;
            System.out.println("Invalid username or password");
        } else {
            password = data.get(1).get(2);

            if (password.equals(newPassword)) {
                valid = true;
                user_id = Integer.parseInt(data.get(1).get(0));
                email = data.get(1).get(3);
                fName = data.get(1).get(4);
                lName = data.get(1).get(5);
                role = data.get(1).get(6);
                System.out.println(fName + " " + lName + ", your role is " + role);
            } else {
                System.out.println("Username or password not valid....");
            }
        }

        return valid;
    }
    
    public void register(String newUsername, String newPassword, String newEmail, String newFname, String newLname){
        username = newUsername;
        password = newPassword;
        email = newEmail;
        fName = newFname;
        lName = newLname;
        role = "Public";
        
        boolean success = false;
        
        ArrayList<String> values = new ArrayList<String>();
        values.add(username);
        values.add(password);
        values.add(email);
        values.add(fName);
        values.add(lName);
        values.add(role);
        
        success = database.setData("INSERT INTO user(username, password, email, fname, lname, role) VALUES(?, ?, ?, ?, ?, ?)", values);
        
        if (success){
            System.out.println("Successfully registered");
        }
        else{
            System.out.println("Failed to register");
        }
    }
}
