/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.*;
import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author thismac
 */
public class UserDashboardController {
    private final UserDashboard dashboard;
    private final UserData user;
    
    public UserDashboardController(UserDashboard  dashboard, UserData  user){
        this.dashboard = dashboard;
        this.user = user;
        this.dashboard.LogOutListener(new logoutUser());
        this.dashboard.Name.setText(user.getUsername());
    }
    
    public void open(){
        this.dashboard.setVisible(true);
    }
    
    public void close(){
        this.dashboard.setVisible(false);
    }

    private  class logoutUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           Login logIn = new Login();
           LoginController c = new LoginController(logIn);
           c.open();
           close();
        }

       
    }
    
}
