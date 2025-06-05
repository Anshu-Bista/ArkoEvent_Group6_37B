package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnection implements Database {

    @Override
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String username = "root";
            String password = "anshu123bista"; 
            String database = "arkoevent";
            String url = "jdbc:mysql://localhost:3306/" + database;

            Connection conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                System.out.println("Database connection successful");
            } else {
                System.out.println("Database connection failed");
            }
            return conn;
        } catch (Exception e) {
            System.out.println("Connection error: " + e.toString());
            e.printStackTrace();  // This prints the full stack trace to help debug
            return null;
        }
        
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            System.out.println("Closing error: " + e.getMessage());
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Query error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
            return -1;
        }
    }  
}
