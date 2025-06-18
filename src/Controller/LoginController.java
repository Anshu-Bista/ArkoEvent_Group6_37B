package Controller;

import Dao.UserDao;
import Model.UserData;
import View.Login;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {
    private final UserDao userDao = new UserDao();
    private final Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
        this.loginView.AddUserListener(new AddUserListener());
    }

    public void open() {
        this.loginView.setVisible(true);
    }

    public void close() {
        this.loginView.setVisible(false);
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String userName = loginView.username().trim();
                String password = loginView.getpassword().trim();

                // Check for empty or placeholder values
                if (userName.isEmpty() || userName.equals("Enter your username")) {
                    JOptionPane.showMessageDialog(null, "Username is required");
                    return;
                }

                if (password.isEmpty() || password.equals("jPasswordField1")) {
                    JOptionPane.showMessageDialog(null, "Password is required");
                    return;
                }

                System.out.println("Attempting login with username: " + userName + " and password: " + password);

                UserData user = new UserData(userName, password);
                boolean isLoggedIn = userDao.login(user);

                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                   
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}

