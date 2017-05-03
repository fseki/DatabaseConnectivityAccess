package movierentals;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Class whose object handles the business side of the login and
 * registration process
 */
public class User {

    private int user_id;
    private String username;
    private String password;
    private String confPassword;
    private String email;
    private String fName;
    private String lName;
    private String role;

    private MovieRentalsDatabase database;

    private final String SALT = "random";

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
    
    public int getUserID(){
        return user_id;
    }

    public boolean login(String newUsername, String newPassword) {
        username = newUsername;
        String hashedPassword = hashPassword(newPassword, SALT);
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

            if (password.equals(hashedPassword)) {
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

    public boolean register(String newUsername, String newPassword, String newConfPassword, String newEmail, String newFname, String newLname) {
        username = newUsername;

        email = newEmail;
        fName = newFname;
        lName = newLname;
        role = "Public";

        boolean success = false;

        if (!newConfPassword.equals("")) {
            
            if (!(newPassword.equals(newConfPassword))) {
                JFrame jfwrong = new JFrame();
                JPanel jpwrong = new JPanel();
                JLabel jlwrong = new JLabel("Passwords do not match");
                jpwrong.add(jlwrong);
                jfwrong.add(jpwrong);
                jfwrong.setVisible(true);
                jfwrong.setLocationRelativeTo(null);
                jfwrong.pack();
                return false;
            } else {
                password = hashPassword(newPassword, SALT);
                confPassword = hashPassword(newConfPassword, SALT);
            }
            
        }
        ArrayList<String> values = new ArrayList<String>();
        values.add(username);
        values.add(password);
        values.add(email);
        values.add(fName);
        values.add(lName);
        values.add(role);

        success = database.setData("INSERT INTO user(username, password, email, fname, lname, role) VALUES(?, ?, ?, ?, ?, ?)", values);

        if (success) {
            System.out.println("Successfully registered");
            return true;
        } else {
            System.out.println("Failed to register");
            return false;
        }

    }

    public String hashPassword(String passwordToHash, String salt) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException nsa) {
            nsa.printStackTrace();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        return hashedPassword;

    }
    
    
}
