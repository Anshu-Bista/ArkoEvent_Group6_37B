/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.User.Dao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Ekta Thapa
 */

public class ResetPasswordController {
    ResetPassword view;

    public ResetPasswordController(ResetPassword view) {
        this.view = view;
        this.view.resetPassword(new ResetPasswordHandler());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    class ResetPasswordHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText().trim();
            String newPassword = String.valueOf(view.getNewPasswordField().getPassword());
            String confirmPassword = String.valueOf(view.getConfirmPasswordField().getPassword());

            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Fill in all the fields");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(view, "Invalid email format");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match");
                return;
            }

            // Optionally, hash the password here before saving
            // String hashedPassword = hashPassword(newPassword);

            UserDao userDao = new UserDao();
            boolean isUpdated = userDao.updatePassword(email, newPassword);

            if (isUpdated) {
                JOptionPane.showMessageDialog(view, "Password reset successfully");
                // Optionally clear fields or close the view
            } else {
                JOptionPane.showMessageDialog(view, "Failed to reset password. Email not found.");
            }
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email.matches(emailRegex);
        }

        // Optional password hashing method if you want to hash passwords
        /*
        private String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hashedBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        */
    }
}
