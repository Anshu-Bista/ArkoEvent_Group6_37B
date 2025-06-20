/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.UserData;
import View.*;
import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 *
 * @author Prashanna
 */
public class LoginController {
    
    private final Login loginView;
    private final UserDao dao = new UserDao();
    
    public LoginController(Login loginView){
        this.loginView = loginView;
        this.loginView.loginListener(new loginUser());
        this.loginView.signupListener(new gotoSignUp());
    }
    
    public void open(){
        this.loginView.setVisible(true);
    }
    
    public void close(){
        this.loginView.setVisible(false);
    }

    private class loginUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.username.getText();
            String password = loginView.password.getText();
            UserData user = dao.login(email, password);
            System.out.println(user.getId());
            if(user == null){
                JOptionPane.showMessageDialog(loginView, "Invalid UserName or Password");
            }
            else if (user.getRole().equals("user")){
                JOptionPane.showMessageDialog(loginView, "User Dashboard");
            }
            else  if(user.getRole().equals("admin")){
                JOptionPane.showMessageDialog(loginView, "Admin Dashboard");
            }
            
        }

    }

    private class gotoSignUp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Registration view = new Registration();
            SignupController c = new SignupController(view);
            c.open();
            close();
        }

    }
    
}
