package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.MySqlConnection;

import Model.UserData;

public class UserDao {

    private final MySqlConnection mysql = new MySqlConnection();

    private Connection openConnection() {
        return mysql.openConnection();
    }

    // Sign Up
    public boolean signUp(UserData user) {
        String sql = "INSERT INTO users(username, email, password, phone, account_status) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, "active");
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
    }

    // Checks if the user is present
    public boolean checkUser(UserData user) {
        String sql = "SELECT * FROM users WHERE email = ? OR username = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());
            try (ResultSet result = pstmt.executeQuery()) {
                return result.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Log In
    public UserData login(String email, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        System.out.println("Login attempt with username: '" + email + "', password: '" + password + "'");
        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("User found: " + rs.getString("username"));
                UserData user = new UserData();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("account_status"));
                // set other fields as needed
                return user;
            } else {
                System.out.println("No matching user found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Forgot Password - check if email exists
    public boolean checkEmail(UserData user) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            try (ResultSet result = pstmt.executeQuery()) {
                return result.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Update password and clear reset code and timestamp
    public boolean updatePassword(String email, String newPassword) {
        
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Get user profile by ID
    public UserData getProfileById(int id) {
        String sql = "SELECT username, email, phone, account_status, profile_image, registration_date, role FROM users WHERE id = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet result = pstmt.executeQuery()) {
                if (result.next()) {
                    UserData user = new UserData();
                    user.setUsername(result.getString("username"));
                    user.setEmail(result.getString("email"));
                    user.setPhone(result.getString("phone"));
                    user.setStatus(result.getString("account_status"));
                    user.setImagePath(result.getString("profile_image"));
                    user.setRegistrationDate(result.getTimestamp("registration_date"));
                    user.setRole(result.getString("role"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "Error retrieving user profile by ID", ex);
        }
        return null;
    }

    // Update user profile by ID
    public boolean updateProfileById(UserData user) {
        String sql = "UPDATE users SET username = ?, phone = ?, profile_image = ? WHERE id = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getImagePath()); // Assuming this is a String path
            pstmt.setInt(4, user.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



    // Get all user only
    public ArrayList<UserData> getUsersOnly() {
        ArrayList<UserData> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";

        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "user"); 

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserData user = new UserData();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setStatus(rs.getString("account_status"));
                    user.setImagePath(rs.getString("profile_image"));
                    users.add(user);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }


    // Ban/Unban Users
    public boolean updateUserStatus(int userId, String newStatus) {
        String sql = "UPDATE users SET account_status = ? WHERE id = ?";
        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Total Users for Admin Dashboard
    public int getUserCount() {
        String query = "SELECT COUNT(*) FROM users";
        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
