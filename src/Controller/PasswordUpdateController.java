/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.sql.*;

public class PasswordUpdateController {
    private Connection conn;

    public PasswordUpdateController(Connection conn) {
        this.conn = conn;
    }

    public void updatePassword(String userEmail) {
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        Object[] message = {
            "New Password:", newPasswordField,
            "Confirm Password:", confirmPasswordField
        };

        int option = JOptionPane.showConfirmDialog(
            null, message, "Update Password", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password fields cannot be empty.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.");
                return;
            }

            try {
                String sql = "UPDATE users SET password = ? WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newPassword); // In real apps, hash the password!
                stmt.setString(2, userEmail);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Password updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating password: " + e.getMessage());
            }
        }
    }
}