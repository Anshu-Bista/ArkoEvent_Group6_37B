package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author Ekta Thapa
 */


public class MySqlConnection implements Database {

    @Override
    public Connection openConnection() {
        try {
            String username = "root";
            String password = "password";
            String database = " demo";
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/" + database, username, password
            );

            if (connection == null) {
                System.out.println("Database connection failed");
            } else {
                System.out.println("Database connection successful");
            }
            return connection;
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
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
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmp = conn.createStatement();
            return stmp.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) { // fixed typo
        try {
            Statement stmp = conn.createStatement();
            return stmp.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }
}

