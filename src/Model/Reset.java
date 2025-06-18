package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reset {

    private final String username;
    private final String password;
    private final String confirmPassword;

    // Constructor
    public Reset(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Method to update password in the database
    public boolean updatePassword() {
        boolean isUpdated = false;

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return false;
        }

        String url = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB name
        String dbUser = ""; // DB username
        String dbPass = "mypassword123";     // DB password
       
        String sql = "UPDATE users SET password = ? WHERE password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, password);     // Store plain password (NOT recommended for real apps)
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            isUpdated = rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return isUpdated;
    }
}
