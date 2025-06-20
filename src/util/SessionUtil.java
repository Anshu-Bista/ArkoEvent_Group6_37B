package util;

import javax.swing.*;

import model.UserData;

public class SessionUtil {
    private static UserData currentUser;

    public static void setCurrentUser(UserData user) {
        currentUser = user;
    }

    public static UserData getCurrentUser() {
        return currentUser;
    }

    public static void logout(JFrame currentFrame) {
        int confirm = JOptionPane.showConfirmDialog(currentFrame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            currentFrame.dispose();  // Close current window
            //new LoginView().setVisible(true);  // Redirect to login
        }
    }
}
