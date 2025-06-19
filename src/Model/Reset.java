package Model;

import javax.swing.*;

public class Reset extends JFrame {

    public class ResetPasswordModel {
    private String password;
    private String confirmPassword;

    public ResetPasswordModel() {
    }

    public ResetPasswordModel(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Validation method to check if passwords match and are valid
    public boolean isValid() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
        return password.equals(confirmPassword);
    }
    }
}