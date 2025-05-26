/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Ekta Thapa
 */
public class ResetPasswordController {
    private final UserDao userDao = new UserDao();
    private final ResetPassword resetView;

    public ResetPasswordController(ResetPassword resetView) {
        this.resetView = resetView;
        resetView.addResetListener(new ResetPasswordListener());
    }

    public void open() {
        this.resetView.setVisible(true);
    }

    public void close() {
        this.resetView.dispose();
    }

    class ResetPasswordListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String email = resetView.getEmailField().getText();
                String newPassword = new String(resetView.getNewPasswordField().getPassword());

                if (email.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(resetView, "Email and Password cannot be empty");
                    return;
                }

                boolean success = userDao.resetPassword(email, newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(resetView, "Password reset successful!");
                } else {
                    JOptionPane.showMessageDialog(resetView, "Email not found. Please try again.");
                }
            } catch (Exception ex) {
                System.out.println("Error resetting password: " + ex.getMessage());
            }
        }
    }
}
