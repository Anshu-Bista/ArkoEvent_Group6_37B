package util;

import javax.swing.*;

public class SessionUtil {
    public static void logout(JFrame currentFrame) {
        int confirm = JOptionPane.showConfirmDialog(currentFrame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            currentFrame.dispose();  // Close current window
            //new LoginView().setVisible(true);  // Redirect to login
        }
    }
}
