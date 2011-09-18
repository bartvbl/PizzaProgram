package pizzaProgram.database;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String DATABASE_HOST = "jdbc:mysql://mysql.stud.ntnu.no/henninwo_it1901";
    public static final String DATABASE_USERNAME = "henninwo_it1901";
    public static final String DATABASE_PASSWORD = "pizzalulz";

    public DatabaseConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(DATABASE_HOST, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Tilkoblingen fungerte.");
        } catch (SQLException ex) {
            System.out.println("Tilkobling feilet: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Feilet under driverlasting: " + ex.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Epic fail: " + ex.getMessage());
            }
        }
    }

    public void connect() {

    }

    public void disconnect() {
        //bye!
    }

    public void isConnected() {

    }

    public DatabaseTable databaseQuery(String tutorial) {
        return null;
    }

    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
    }
}