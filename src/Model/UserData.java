package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserData {

    public static boolean ConfirmPassword(String username, String password) {
        boolean isUpdated = false;

        String url = "jdbc:mysql://localhost:3306/your_database_name"; // CHANGE THIS
        String dbUser = "root"; // CHANGE IF NEEDED
        String dbPass = "";     // CHANGE IF YOU HAVE PASSWORD

        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, password);
            stmt.setString(2, username);

            int rows = stmt.executeUpdate();
            isUpdated = rows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }

        return isUpdated;
    }
}
