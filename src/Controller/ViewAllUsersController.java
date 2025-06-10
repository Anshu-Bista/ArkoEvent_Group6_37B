package controller;

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

        loadAllUsers();  // Load user cards into scroll panel
    }
    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    private void loadAllUsers() {
        List<UserData> users = userController.getAllUsers();  // Fetch from DAO through controller
        JPanel userPanel = view.getUserPanel();  // get panel inside scrollpane

        userPanel.removeAll();

        for (UserData user : users) {
            UserCard card = new UserCard();  
            card.setUser(user);              // Pass user info (name, image path)
            userPanel.add(card);
        }

        userPanel.revalidate();
        userPanel.repaint();
    }
}
