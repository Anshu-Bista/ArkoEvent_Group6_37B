package Dao;

import Database.Database;
import Database.MySqlConnection;
import Model.UserData;
import java.sql.*;

public class Userdao {

    private final Database db = new MySqlConnection();

    // Check if the user exists
    public boolean checkUser(UserData userData) {
        Connection conn = db.openConnection();
        if (conn == null) return false;

        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userData.getUsername());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Check user error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // Login method
    public boolean Login(UserData userData) {
        Connection conn = db.openConnection();
        if (conn == null) return false;

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userData.getUsername());
            stmt.setString(2, userData.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // Register method (optional)
    public boolean register(UserData userData) {
        if (checkUser(userData)) return false;

        Connection conn = db.openConnection();
        if (conn == null) return false;

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userData.getUsername());
            stmt.setString(2, userData.getPassword());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }
}
