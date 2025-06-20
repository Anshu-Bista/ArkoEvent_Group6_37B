package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.UserDao;
import model.UserData;
import util.NavigationUtil;
import util.SessionUtil;
import view.UserProfile;

public class UserProfileController {
    private final UserProfile profView;
    private UserDao userDao;
    private int userId;

    public UserProfileController(UserProfile profileView) {
    this.profView = profileView;

    this.profView.addUpdateProfileListener(new UpdateProfileListener());
    this.profView.addDeactivateListener(new DeactivateAccountListener());
    this.profView.addLogoutListener(e -> SessionUtil.logout(profileView));

    // Add navigation listeners here using lambdas:
    this.profView.addHomeListener(e -> {
        profView.dispose();
        NavigationUtil.goToDashboard();
    });

    this.profView.addProfileListener(e -> {
        profView.dispose();
        NavigationUtil.goToProfile();
    });

    this.profView.addMyEventsListener(e -> {
        profView.dispose();
        JOptionPane.showMessageDialog(profView, "My Events page is under development.");
    });

}


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void open() {
        loadProfile();
        profView.setVisible(true);
    }

    public void close() {
        profView.dispose();
    }

    private void loadProfile() {
        try {
            if (userDao == null) {
                throw new IllegalStateException("UserDao is not initialized.");
            }
            UserData user = userDao.getProfileById(userId);
            if (user != null) {
                profView.displayUserProfile(user);
                profView.setFieldsEditable(false);
            } else {
                JOptionPane.showMessageDialog(profView, "User not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(profView, "Error loading profile: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProfile() {
        try {
            UserData updatedUser = profView.getUpdatedProfile();
            updatedUser.setId(userId);

            // Simple validation
            if (updatedUser.getUsername().trim().isEmpty()) {
                JOptionPane.showMessageDialog(profView, "Name cannot be empty.");
                return;
            }

            boolean success = userDao.updateProfileById(updatedUser);
            if (success) {
                JOptionPane.showMessageDialog(profView, "Profile updated successfully!");
                loadProfile();
            } else {
                JOptionPane.showMessageDialog(profView, "Failed to update profile.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(profView, "Error updating profile: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class UpdateProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!profView.isEditable()) {
                profView.setFieldsEditable(true);
            } else {
                int option = JOptionPane.showConfirmDialog(profView, "Do you want to save changes?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    updateProfile();
                    profView.setFieldsEditable(false);
                }
            }

        }
    }

    private class DeactivateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(profView,
                    "Are you sure you want to deactivate your account?",
                    "Confirm Deactivation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = userDao.updateUserStatus(userId, "deactivated");
                if (success) {
                    JOptionPane.showMessageDialog(profView, "Account deactivated. Application will exit.");
                    System.exit(0); // or redirect to login
                } else {
                    JOptionPane.showMessageDialog(profView, "Failed to deactivate account.");
                }
            }
        }
    }

}
