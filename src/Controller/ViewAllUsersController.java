package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.UserData;
import view.UserCard;
import view.ViewUsers;

import javax.swing.*;
import java.util.List;

import util.NavigationUtil;
import util.SessionUtil;

public class ViewAllUsersController {
    private final UserController userController;
    private final ViewUsers view;

    public ViewAllUsersController(ViewUsers view) {
        this.view = view;
        this.userController = new UserController();

        this.view.addLogoutListener(e -> SessionUtil.logout(view));

        this.view.addProfileListener(e -> {
            view.dispose();
            NavigationUtil.goToProfile();
        });

        this.view.addCreateEventListener(e -> {
            view.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.view.addHomeListener(e -> {
            view.dispose();
            NavigationUtil.goToDashboard(); // Treat dashboard as home
        });

        this.view.addMyEventsListener(e -> {
            JOptionPane.showMessageDialog(view, "My Events page is under development.");
        });

        this.view.addDiscoverListener(e -> {
            JOptionPane.showMessageDialog(view, "Discover page is under development.");
        });

        this.view.getStatusComboBox().addActionListener(new StatusFilterListener());

        loadUsersByStatus("active"); // Load user cards initially
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    private void loadUsersByStatus(String status) {
        List<UserData> users = userController.getUsersByStatus(status);
        JPanel userPanel = view.getUserPanel();

        userPanel.removeAll();

        for (UserData user : users) {
            UserCard card = new UserCard();
            card.setUser(user);
            JButton actionButton = card.getActionButton();

            // Remove old listeners to prevent duplicates on refresh
            for (ActionListener al : actionButton.getActionListeners()) {
                actionButton.removeActionListener(al);
            }

            String userStatus = user.getStatus();
            if (userStatus != null) {
                userStatus = userStatus.trim().toLowerCase();

                if (userStatus.equals("banned")) {
                    actionButton.setText("Unban");
                    actionButton.setEnabled(true);
                } else if (userStatus.equals("active")) {
                    actionButton.setText("Ban");
                    actionButton.setEnabled(true);
                } else {
                    actionButton.setText("N/A");
                    actionButton.setEnabled(false);
                }
            } else {
                actionButton.setText("N/A");
                actionButton.setEnabled(false);
            }

            // Add action listener for ban/unban
            actionButton.addActionListener(new BanUnbanListener(user, actionButton));

            userPanel.add(card);
        }

        userPanel.revalidate();
        userPanel.repaint();

        System.out.println("Users found: " + users.size());
    }

    private class StatusFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedStatus = view.getStatusComboBox().getSelectedItem().toString().toLowerCase();
            loadUsersByStatus(selectedStatus);
        }
    }

    private class BanUnbanListener implements ActionListener {
        private final UserData user;
        private final JButton actionButton;

        public BanUnbanListener(UserData user, JButton actionButton) {
            this.user = user;
            this.actionButton = actionButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = actionButton.getText();
            boolean success = false;

            if ("Ban".equals(action)) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to ban this user?",
                        "Confirm Ban", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    success = userController.banUser(user.getId());
                }
            } else if ("Unban".equals(action)) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Unban this user?",
                        "Confirm Unban", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    success = userController.unbanUser(user.getId());
                }
            }

            if (success) {
                JOptionPane.showMessageDialog(null, "User status updated.");
                String currentFilter = view.getStatusComboBox().getSelectedItem().toString().toLowerCase();
                loadUsersByStatus(currentFilter); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user status.");
            }
        }
    }
}
