package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.UserDao;
import model.UserData;
import util.NavigationUtil;
import util.SessionUtil;
import View.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UserProfileController {
    private final UserProfile profView;
    private final UserDao userDao;
    private final int userId;
    private UserData currentUser;
   

    public UserProfileController(UserProfile profileView) {
        this.profView = profileView;

        this.userDao = new UserDao(); // Initialize DAO and User ID from session
        currentUser = SessionUtil.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        this.userId = currentUser.getId();

        this.profView.addUpdateProfileListener(new UpdateProfileListener());
        this.profView.addDeactivateListener(new DeactivateAccountListener());
        this.profView.addLogoutListener(e -> SessionUtil.logout(profileView));
        this.profView.addDpListener(new addDP());

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

    public void open() {
        loadProfile();
        profView.setVisible(true);
    }

    public void close() {
        profView.dispose();
    }

    private void loadProfile() {
        try {
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

    private  class addDP implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Profile Image");

            // Optional: set filter for image files only
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(imageFilter);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();

                // Set image path to user object
                
                currentUser.setImagePath(imagePath);
                boolean success = userDao.updateProfileById(currentUser);
                if(success){
                    loadProfile();
                } else{
                    JOptionPane.showMessageDialog(profView, "Couldnot update pofile picture");
                }

        }

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
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(profView, "Failed to deactivate account.");
                }
            }
        }
    }
}

