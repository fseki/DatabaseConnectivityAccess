/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierentals;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Class with methods and attributes to connect to a database, manipulate
 * database data, and close the connection
 *
 * @authors Fran Seki, Marko Parac, Matija Ozetski, Adi Cengic & Marko Zivko
 * @version 1.0 
 * ISTE-330.700 
 * prof. Branko Mihaljevic 
 * RIT Croatia, 2017
 */
public class MovieRentalsDatabase {

    //attributes which store the properties needed
    //for database connection
    private String userName = "";
    private String password = "";
    private String dbServer = "";
    private String dbName = "";
    private String port = "";

    //other database related objects
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private DatabaseMetaData metaData = null;
    private PreparedStatement pStatement = null;

    /**
     * Parameterized constructor which sets the attributes
     * @param userName, the userID
     * @param password, the user's password
     * @param dbServer, DB server name
     * @param dbName, database name
     */
    public MovieRentalsDatabase(String userName, String password, String dbServer, String port, String dbName) {

        this.userName = userName;
        this.password = password;
        this.dbServer = dbServer;
        this.dbName = dbName;
        this.port = port;
    }

    /**
     * Method to setUserName
     * @param newUserName type String
     */
    public void setUserName(String newUserName) {
        userName = newUserName;
    }
    /**
     * Method to setPassword
     * @param newPassword type String
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    /**
     * Method to setDatabase server
     * @param newDBServer  type String
     */
    public void setDBServer(String newDBServer) {
        dbServer = newDBServer;
    }
    /**
     * Method to setDatabseName
     * @param newDBName type String
     */
    public void setDBName(String newDBName) {
        dbName = newDBName;
    }
    /**
     * Method to setPort
     * @param newPort type String
     */
    public void setPort(String newPort) {
        port = newPort;
    }

    /**
     * Method to connect to a database
     * @return true or false, depending on connection success
     */
    public boolean connect() throws InfoException {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String mysqlURI = "jdbc:mysql://" + dbServer + ":" + port + "/" + dbName + "?useSSL=false";

            Class.forName(driver);

            connection = DriverManager.getConnection(mysqlURI, userName, password);
            System.out.println("\nA connection to the database was successfully established!");

            metaData = connection.getMetaData();

            statement = connection.createStatement();
            return true;
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (ClassNotFoundException cnf) {
            System.out.println("\nSomething went wrong. A connection to the database was not established.");
            return false;
        } catch (Exception e) {
            System.out.println("\nSomething went wrong. A connection to the database was not established.");
            return false;
        }
    }

    /**
     * Method to close the database connection
     * @return true or false, depending on closing success
     */
    public boolean close() throws InfoException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                statement.close();
                System.out.println("Connection to the database was successfully closed!");
                return true;
            } else {
                System.out.println("The connection might have already been closed or it simply doesn't exist.");
                return false;
            }

        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());

        } catch (Exception e) {
            System.out.println("Something went wrong. The connection could not be closed.");
            return false;
        }
    }

    /**
     * Method that queries the database with the given query
     * Also stores query results into an array
     * @param query, Query to be executed
     * @return results, The array populated with query results
     */
    public ArrayList<ArrayList<String>> getData(String query) throws InfoException {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

        int numCols = 4;
        int m = 0; //in case there are more than 1 rows of data returned

        try {
            resultSet = statement.executeQuery(query);
            boolean row = resultSet.next();
            if (row == false) {
                System.out.println("EMPTY SET: There is no data!");
            } else {
                while (row) {
                    results.add(new ArrayList<String>());
                    for (int i = 1; i <= numCols; i++) {
                        resultSet.getString(1);
                        results.get(m).add(resultSet.getString(1));
                    }
                    row = resultSet.next();
                    m++;
                }
            }
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (IndexOutOfBoundsException ibe) {
            System.out.println("An error occured whilst storing the query results.");

        } catch (Exception e) {
            System.out.println("There was an error with querying the database.");

        }
        return results;
    }

    /**
     * Method that queries the database with the given query
     * Also stores query results into an array, with an option to store column names 
     * @param query, Query to be executed
     * @param incColName, boolean for including the column names
     * @return results, The array populated with query results
     */
    public ArrayList<ArrayList<String>> getData(String query, boolean incColName) throws InfoException {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

        try {
            //column names included
            if (incColName) {
                results = getData(query);
                results.add(new ArrayList<String>()); //where column names will be stored

                ResultSetMetaData tmpMeta = resultSet.getMetaData();
                String tableName = tmpMeta.getTableName(1); //gets the table name from the current query
                //will only work if one table in query

                ResultSet result = metaData.getColumns(null, null, tableName, null);

                //adds column names to the end of the array
                while (result.next()) {
                    String colName = result.getString(4);
                    results.get(results.size() - 1).add(colName);
                }
                //rotates the array so that the column names are first	
                Collections.rotate(results, 1);

                result.close();
            } //column names not included
            else {
                results = getData(query);
            }
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: Could not get data.");
        }

        return results;
    }

    /**
     * Method which updates the database with the given query string
     * @return true or false, depending on the success of the query
     */
    public boolean setData(String query) throws InfoException {
        try {
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected == 0) {
                System.out.println("The database was not updated. 0 rows affected.");
                return false;
            } else {
                System.out.println("The database was updated. " + rowsAffected + " row(s) affected.");
                return true;
            }
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());

        } catch (Exception e) {
            System.out.println("An error occurred while the database was being updated.");
            return false;
        }
    }

    /**
     * Method which initializes the PreparedStatement object for use in other methods
     * @param statement, The query to be executed
     * @param values, ArrayList with values to be used in the query
     * @return pStatement, The PreparedStatement object
     */
    public PreparedStatement prepare(String statement, ArrayList<String> values) throws InfoException {
        try {
            pStatement = connection.prepareStatement(statement);
            for (int i = 0; i < values.size(); i++) {
                try {
                    int n = Integer.parseInt(values.get(i));
                    pStatement.setInt(i + 1, n);
                } catch (NumberFormatException nfe) {
                    pStatement.setString(i + 1, values.get(i));
                }

            }

        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred...");
        }
        return pStatement;
    }

    /**
     * Method that is used to get data from the databse in cases where a select from
     * multiple tables is being performed
     * Allows for the user to specify the number of columns based on the query
     * Uses prepared statements for security
     * @param statement, Query to be executed
     * @param values, Values to be bound to the statement
     * @param numCols, Number of columns involved in the statement
     */
    public ArrayList<ArrayList<String>> getDataWithSpecificNumCols(String statement, ArrayList<String> values, int numCols) throws InfoException {
        ArrayList<ArrayList<String>> data = null;
        int m = 0;

        try {
            PreparedStatement prepStatement = prepare(statement, values);
            resultSet = prepStatement.executeQuery();
            data = new ArrayList<ArrayList<String>>();

            boolean row = resultSet.next();
            if (row == false) {
                System.out.println("EMPTY SET: There is no data!");
            } else {
                //adds resultSet data to the array
                while (row) {
                    data.add(new ArrayList<String>());
                    for (int i = 1; i <= numCols; i++) {
                        resultSet.getString(i);
                        data.get(m).add(resultSet.getString(i));
                    }
                    row = resultSet.next();
                    m++;
                }
            }
            prepStatement.close();
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: Could not get the data.");
        }
        return data;
    }

    /**
     * Method that queries the database with the given query, using a prepared statement
     * Also stores query results into an array
     * @param statement, The query to be executed
     * @param values, ArrayList of values that fill the query
     * @return data, 2D ArrayList filled with query results
     */
    public ArrayList<ArrayList<String>> getData(String statement, ArrayList<String> values) throws InfoException {
        ArrayList<ArrayList<String>> data = null;
        int numCols = 0;
        int m = 1;

        try {
            PreparedStatement prepStatement = prepare(statement, values);
            resultSet = prepStatement.executeQuery();
            data = new ArrayList<ArrayList<String>>();

            //needed for column names
            ResultSetMetaData tmpMeta = resultSet.getMetaData();
            String tableName = tmpMeta.getTableName(1);
            System.out.println("table name: " + tableName);

            ResultSet result = metaData.getColumns(null, null, tableName, null);

            boolean row = resultSet.next();
            if (row == false) {
                System.out.println("EMPTY SET: There is no data!");
            } else {
                data.add(new ArrayList<String>());
                //adds column names to the beginning of the array
                while (result.next()) {
                    String colName = result.getString(4);
                    data.get(0).add(colName);
                    numCols++; //getting the number of columns
                }
                //adds resultSet data to the array
                while (row) {
                    data.add(new ArrayList<String>());
                    for (int i = 1; i <= numCols; i++) {
                        resultSet.getString(i);
                        data.get(m).add(resultSet.getString(i));
                    }
                    row = resultSet.next();
                    m++;
                }
            }
            result.close();
            prepStatement.close();
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: Could not get the data.");
        }
        return data;
    }

    /**
     * Method which performs an update on a table based on the given query
     * Uses prepared statement
     * @param statement, The update query to be executed
     * @param values, The ArrayList with query values
     * @return true or false, depending on the success of the query
     */
    public boolean setData(String statement, ArrayList<String> values) throws InfoException {
        boolean successful = false;
        try {
            PreparedStatement prepStatement = prepare(statement, values);
            int rowsAffected = prepStatement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("The database was not updated. 0 rows affected.");
            } else {
                System.out.println("The database was updated. " + rowsAffected + " row(s) affected.");
                successful = true;
            }
            prepStatement.close();
        } catch (SQLException se) {
            try {
                com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException violationEx = (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) se;
                JOptionPane.showMessageDialog(null, "Username already in use.\nPlease select another username.");
            } catch (ClassCastException cce) {
                System.out.println("An error occurred...");
                throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());

            }
        } catch (Exception e) {
            //System.out.println("Could not update the database.");
            e.printStackTrace();
        }
        return successful;
    }

    /**
     * Method which configures the database to not use auto-commits
     * Equivalent of starting a transaction
     */
    public void startTrans() throws InfoException {
        try {
            connection.setAutoCommit(false);
            System.out.println("Transaction started...");
        } catch (SQLException se) {
            throw new InfoException(se, se.getSQLState(), se.getErrorCode(), se.getMessage());
        } catch (Exception e) {
            System.out.println("Could not start the transaction");
        }
    }

    /**
     * Method which commits all changes made and ends the transaction
     * by enabling auto-commits again
     */
    public void endTrans() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Changes committed and transaction ended...");
        } catch (SQLException se) {
            System.out.println("Could not end the transaction");
        } catch (Exception e) {
            System.out.println("Could not end the transaction");
        }
    }

    /**
     * Method which rolls back the database to the previous state
     * in case something goes wrong when executing statements
     */
    public void rollbackTrans() {
        try {
            connection.rollback();
            System.out.println("Changes rolled back...");
        } catch (SQLException se) {
            System.out.println("ERROR: Could not rollback changes...");
            endTrans();
        } catch (Exception e) {
            System.out.println("ERROR: Could not rollback changes...");
            endTrans();
        }
    }
}
