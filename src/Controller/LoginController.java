/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.Userdao;
import Model.UserData;
import View.Login;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class LoginController {
    private final Userdao userDao = new Userdao();
    private final Login loginView;
    
    public LoginController(Login loginView){
        this.loginView = loginView;
        loginView.AddUserListener(new AddUserListener());
    }
    
    public void open(){
        this.loginView.setVisible(true);
    }
    public void close(){
        this.loginView.setVisible(false);
        
    }

    private  class AddUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("dsfksdk");
            try{
                String userName = loginView.username();
                String password = loginView.getpassword();
                
                if(userName.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username is required");
                    return;
                }
               
                if(password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Password required");
                    return;
                }
                if(password.equals("mismatch")){
                    JOptionPane.showMessageDialog(null, "Passwords Mismatch");
                }
                
              //aaba try reegeestering ani check if data go
                UserData user = new UserData(userName,password);
                if(userDao.checkUser(user)){
                    JOptionPane.showMessageDialog(null, "Duplicate User");
                    return;
                }
                userDao.Login(user);
                
                
            }catch (HeadlessException ex){
                JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
            }
        
        }
    }

}