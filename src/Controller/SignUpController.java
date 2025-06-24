package Controller;

import View.*;
import dao.UserDao;
import model.UserData;


import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class SignUpController {
    private final UserDao userDao = new UserDao();
    private final Registration registerView;

    public SignUpController(Registration registerView) {
        this.registerView = registerView;
        registerView.AddUserListener(new AddUserListener());
    }

    public void open() {
        this.registerView.setVisible(true);
    }

    public void close() {
        this.registerView.setVisible(false);
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String userName = registerView.getuName();
                String email = registerView.getEmail();
                String contact = registerView.getContact();
                String password = registerView.getPassword();

                // Basic field validation
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(registerView, "Username is required");
                    return;
                }
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(registerView, "Email is required");
                    return;
                }
                if (contact.isEmpty()) {
                    JOptionPane.showMessageDialog(registerView, "Contact is required");
                    return;
                }
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(registerView, "Password is required");
                    return;
                }

                // Validate email format
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(registerView, "Invalid email format.");
                    return;
                }

                // Create UserData object
                UserData user = new UserData(userName, email, password, contact);

                // Check duplicate user
                if (userDao.checkUser(user)) {
                    JOptionPane.showMessageDialog(registerView, "Duplicate User");
                    return;
                }

                // Try to sign up user
                boolean success = userDao.signUp(user);
                if (success) {
                    // On success, open Login view and close register vie
                    Login view = new Login();
                    LoginController c = new LoginController(view);
                    c.open();
                    close();
                } else {
                    JOptionPane.showMessageDialog(registerView, "Unable to Register");
                }

            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}
