package Controller;

import model.UserData;
import View.*;
import dao.UserDao;
import util.SessionUtil;
import util.NavigationUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private final Login loginView;
    private final UserDao dao = new UserDao();

    public LoginController(Login loginView) {
        this.loginView = loginView;
        this.loginView.loginListener(new LoginUser());
        this.loginView.signupListener(new GoToSignUp());
    }

    public void open() {
        this.loginView.setVisible(true);
    }

    public void close() {
        this.loginView.setVisible(false);
    }

    private class LoginUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.username.getText();
            String password = loginView.password.getText();

            UserData user = dao.login(email, password);

            if (user == null) {
                JOptionPane.showMessageDialog(loginView, "Invalid Username or Password");
            } else {
                SessionUtil.setCurrentUser(user); // Store user in session globally
                loginView.dispose(); // Close login window
                NavigationUtil.goToDashboard(); // Navigate based on role
            }
        }
    }

    private class GoToSignUp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (SessionUtil.getCurrentUser() != null) {
                JOptionPane.showMessageDialog(loginView, "You are already logged in!");
                return;
            }
            loginView.dispose();
            NavigationUtil.goToSignUp();
        }
    }
}
