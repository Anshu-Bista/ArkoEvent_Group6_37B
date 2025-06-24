package Controller;

import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.UserDao;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.UserData;
import util.NavigationUtil;
import util.SessionUtil;


public class AdminProfileController {
    private final AdminProfile profileView;
    private final UserDao userDao;
    private final int userId;
    private UserData currentUser;

    public AdminProfileController(AdminProfile profileView) {
        this.profileView = profileView;

        this.userDao = new UserDao();   // Directly initialize from session
        currentUser = SessionUtil.getCurrentUser();
        

        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        this.userId = currentUser.getId();

        this.profileView.addUpdateProfileListener(new UpdateProfileListener());
        this.profileView.addDeactivateListener(new DeactivateAccountListener());
        this.profileView.uploadPicListener(new updatePic());
        this.profileView.addLogoutListener(e -> SessionUtil.logout(profileView));
        

        this.profileView.addProfileListener(e -> {
            this.profileView.dispose();
            NavigationUtil.goToProfile();
        });

        this.profileView.addCreateEventListener(e -> {
             this.profileView.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.profileView.addHomeListener(e -> {
             this.profileView.dispose();
            NavigationUtil.goToDashboard();
        });

        this.profileView.addMyEventsListener(e -> {
             this.profileView.dispose();
            NavigationUtil.goToMyEvents();
        });

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

    private class updatePic implements ActionListener {

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
                    JOptionPane.showMessageDialog(profileView, "Couldnot update pofile picture");
                }

            }
        }

       
    }

    private class UpdateProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!profileView.isEditable()) {
                profileView.setFieldsEditable(true);
                profileView.update_btn.setText("Save");
            } else {
                int option = JOptionPane.showConfirmDialog(profileView, "Do you want to save changes?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    updateProfile();
                    profileView.setFieldsEditable(false);
                    profileView.update_btn.setText("Update");
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
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(profileView, "Failed to deactivate account.");
                }
            }
        }
    }
}
