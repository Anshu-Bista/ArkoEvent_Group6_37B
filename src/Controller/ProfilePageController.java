package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.UserDao;
import model.UserData;
import view.AdminProfile;

public class ProfilePageController {
    private final AdminProfile profileView;
    private final UserDao userDao;
    private final String userEmail;

    public ProfilePageController(AdminProfile profileView, UserDao userDao, String userEmail) {
        this.profileView = profileView;
        this.userDao = userDao;
        this.userEmail = userEmail;

        loadProfile();
        this.profileView.addUpdateProfileListener(new UpdateProfileListener());
    }
    public void open() {
        profileView.setVisible(true);
    }

    public void close() {
        profileView.dispose();
    }

    private void loadProfile() {
        try {
            UserData user = userDao.getProfile(userEmail);
            if (user != null) {
                profileView.displayUserProfile(user);
                profileView.setFieldsEditable(false);
            } else {
                JOptionPane.showMessageDialog(profileView, "User not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(profileView, "Error loading profile: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProfile() {
        try {
            UserData updatedUser = profileView.getUpdatedProfile();
            updatedUser.setEmail(userEmail);

            // Simple validation
            if (updatedUser.getUsername().trim().isEmpty()) {
                JOptionPane.showMessageDialog(profileView, "Name cannot be empty.");
                return;
            }

            boolean success = userDao.updateProfile(updatedUser);
            if (success) {
                JOptionPane.showMessageDialog(profileView, "Profile updated successfully!");
                loadProfile();
            } else {
                JOptionPane.showMessageDialog(profileView, "Failed to update profile.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(profileView, "Error updating profile: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class UpdateProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!profileView.isEditable()) {
                profileView.setFieldsEditable(true);
            } else {
                int option = JOptionPane.showConfirmDialog(profileView, "Do you want to save changes?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    updateProfile();
                    profileView.setFieldsEditable(false);
                }
            }

        }
    }

    
}
