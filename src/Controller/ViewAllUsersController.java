package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.UserData;
import view.UserCard;
import view.ViewUsers;

import javax.swing.*;
import java.util.List;

public class ViewAllUsersController {
    private final UserController userController;
    private final ViewUsers view;

    public ViewAllUsersController(ViewUsers view) {
        this.view = view;
        this.userController = new UserController();

        this.view.getStatusComboBox().addActionListener(new StatusFilterListener());

        loadUsersByStatus("active");    // Load user cards into scroll panel

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
    
}
