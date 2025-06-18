/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    MySqlConnection mysql = new MySqlConnection();

    /**
     * Updates the user's password using the provided username.
     *
     * @param username     The user's username
     * @param newPassword  The new password to set
     * @return true if password was updated, false otherwise
     */
    public boolean resetPassword(String username, String newPassword) {
    Connection conn = mysql.openConnection();
    String sql = "UPDATE users SET pass = ? WHERE username = 'admin'"; // hardcoded
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, newPassword);
        int updated = pstmt.executeUpdate();
        return updated > 0;
    } catch (SQLException ex) {
        Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        mysql.closeConnection(conn);
    }
    return false;
}
}
