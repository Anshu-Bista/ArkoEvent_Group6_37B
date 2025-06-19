/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author Ekta Thapa
 */
   
public class Reset extends JFrame {

    private JPasswordField Password;
    private JPasswordField ConfirmPassword;
    private JButton submitButton;

    public Reset() {
        setTitle("Reset Password");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // UI elements
        JLabel titleLabel = new JLabel("Reset Your Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel passwordLabel = new JLabel("New Password:");
        Password = new JPasswordField(20);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        ConfirmPassword = new JPasswordField(20);

        submitButton = new JButton("Update Password");

        // Layout setup
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(Password, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        add(ConfirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(submitButton, gbc);
    }

    // --- Getter and Setter methods ---

    public String getPassword() {
        return new String(Password.getPassword());
    }

    public void setPassword(String password) {
        this.Password.setText(password);
    }

    public String getConfirmPassword() {
        return new String(ConfirmPassword.getPassword());
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword.setText(confirmPassword);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(JButton submitButton) {
        this.submitButton = submitButton;
    }
}

