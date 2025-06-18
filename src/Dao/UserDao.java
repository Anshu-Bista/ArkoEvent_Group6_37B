package Dao;

import Database.Database;
import Database.MySqlConnection;
import Model.UserData;

import java.sql.*;

public class UserDao {

    private final Database db = new MySqlConnection();

   public boolean login(UserData userData) {
    Connection conn = db.openConnection();
    if (conn == null) {
        System.out.println("DB connection failed.");
        return false;
    }

    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, userData.getUsername());
        stmt.setString(2, userData.getPassword());

        System.out.println("Executing query with username = " + userData.getUsername() + " and password = " + userData.getPassword());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Login error: " + e.getMessage());
        return false;
    } finally {
        db.closeConnection(conn);
    }
}

    }

