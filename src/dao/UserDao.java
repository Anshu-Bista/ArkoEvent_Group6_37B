/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.MySqlConnection;
import Model.UserData;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class UserDao {

    MySqlConnection mysql = new MySqlConnection();
    Connection conn = mysql.openConnection();

    // Sign Up
    public boolean signUp(UserData user) {

        String sql = "INSERT into users(username, email, password, phone, account_status) values(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, "active");
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Signup successful");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Signup Failed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    // Checks if the user is present
    public boolean checkUser(UserData user) {
        Connection conn1 = mysql.openConnection();
        String sql = "SELECT* FROM users where email = ? or username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());
            ResultSet result = pstmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn1);
        }
        return false;
    }

    // Log In
    public UserData login(String email, String password) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                UserData user = new UserData();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setPhone(result.getString("phone"));
                user.setStatus(result.getString("account_status"));
                user.setRole(result.getString("role"));
                user.setRegistrationDate(result.getTimestamp("registration_date"));
                user.setImagePath(result.getString("profile_image"));
                return user;
            }else{
                System.out.println("nothing");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }

        return null; // Login failed
    }

    // Forgot Password
    // check if email is in the table
    public boolean checkEmail(UserData user) {
        Connection conn3 = mysql.openConnection();
        String sql = "SELECT id FROM users where email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            ResultSet result = pstmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn3);
        }
        return false;
    }

    public boolean resetPassword(String username, String newPassword) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE users SET pass = ? WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    // Profile
    public UserData getProfileById(int id) {
        String sql = "SELECT username, email, phone, account_status, profile_image, registration_date FROM users WHERE id = ?";

        try (Connection conn = mysql.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                UserData user = new UserData();
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));
                user.setStatus(result.getString("account_status"));
                user.setImagePath(result.getString("profile_image"));
                user.setRole(result.getString("role"));
                user.setRegistrationDate(result.getTimestamp("registration_date"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "Error retrieving user profile by ID", ex);
        }

        return null;
    }

    // Update user profile
    public boolean updateProfileById(UserData user) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE users SET username = ?, phone = ?, profile_image = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getImagePath());
            pstmt.setInt(4, user.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // View All Users
    public List<UserData> getAllUsers() {
        List<UserData> users = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM users";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserData user = new UserData();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setImagePath(rs.getString("profile_image"));
                user.setStatus(rs.getString("account_status"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn); // close your connection safely
        }

        return users;
    }

    public List<UserData> getUsersByStatus(String status) {
        List<UserData> users = new ArrayList<>();
        Connection conn = mysql.openConnection();

        String sql = "SELECT * FROM users WHERE account_status = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);  // "active", "banned", or "deactivated"
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserData user = new UserData();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setImagePath(rs.getString("profile_image"));
                user.setStatus(rs.getString("account_status"));
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }

        return users;
    }

    //Ban/Unban Users
    public static boolean updateUserStatus(int userId, String newStatus) {
        String sql = "UPDATE users SET account_status = ? WHERE id = ?";
        try (Connection conn = new MySqlConnection().openConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
//check prashanna prashanna prashanna
//hello 
//hello
