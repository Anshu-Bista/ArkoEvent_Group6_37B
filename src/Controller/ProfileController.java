package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.UserDao;
import model.UserData;
import view.AdminProfile;

public class ProfileController {
    private final AdminProfile profileView;
    private UserDao userDao;
    private int userId; 

    public ProfileController(AdminProfile profileView) {
        this.profileView = profileView;
        this.profileView.addUpdateProfileListener(new UpdateProfileListener());
        this.profileView.addDeactivateListener(new DeactivateAccountListener());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void open() {
        loadProfile(); 
        profileView.setVisible(true);
    }

    public void close() {
        profileView.dispose();
    }

    private void loadProfile() {
        try {
            if (userDao == null) {
                throw new IllegalStateException("UserDao is not initialized.");
            }
            UserData user = userDao.getProfileById(userId);  
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
            updatedUser.setId(userId); 
    
            // Simple validation
            if (updatedUser.getUsername().trim().isEmpty()) {
                JOptionPane.showMessageDialog(profileView, "Name cannot be empty.");
                return;
            }
    
            boolean success = userDao.updateProfileById(updatedUser);  
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

    private class DeactivateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(profileView,
                    "Are you sure you want to deactivate your account?",
                    "Confirm Deactivation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = userDao.updateUserStatus(userId, "deactivated");
                if (success) {
                    JOptionPane.showMessageDialog(profileView, "Account deactivated. Application will exit.");
                    System.exit(0); // or redirect to login
                } else {
                    JOptionPane.showMessageDialog(profileView, "Failed to deactivate account.");
                }
            }
        }
    }

}
