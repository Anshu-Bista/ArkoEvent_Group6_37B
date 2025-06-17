package Database;

import java.sql.*;

public class MySqlConnection implements Database {

    @Override
    public Connection openConnection() {
        try {
            String username = "root";
            String password = "ektathapa9848";
            String database = "ArkoEvent";

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + database, username, password
            );

            if (connection == null) {
                System.out.println("Database connection failed");
            } else {
                System.out.println("Database connection successful");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection error: " + e);
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
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Query error: " + e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Update error: " + e);
            return -1;
        }
    }
}
    
