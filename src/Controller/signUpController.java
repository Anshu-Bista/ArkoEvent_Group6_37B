/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import model.UserData;
import view.Registration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class SignUpController {
    private final UserDao userDao = new UserDao();
    private final Registration registerView;
    
    public SignUpController(Registration registerView){
        this.registerView = registerView;
        registerView.AddUserListener(new AddUserListener());
    }
    
    public void open(){
        this.registerView.setVisible(true);
    }
    public void close(){
        this.registerView.setVisible(false);
        
    }

    private  class AddUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("dsfksdk");
            try{
                String userName = registerView.getuName();
                String email = registerView.getEmail();
                String contact = registerView.getContact();
                String password = registerView.getPassword();
                
                if(userName.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username is required");
                    return;
                }
                if(email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Emaill is required");
                    return;
                }
                if(contact.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Contact is required");
                    return;
                }
                if(password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Password required");
                    return;
                }
                if(password.equals("mismatch")){
                    JOptionPane.showMessageDialog(null, "Passwords Mismatch");
                }
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }
                
              //aaba try reegeestering ani check if data go
              UserData user = new UserData(userName, email, password, contact);
                if(userDao.checkUser(user)){
                    JOptionPane.showMessageDialog(null, "Duplicate User");
                    return;
                }
                userDao.signUp(user);
                
                
            }catch (HeadlessException ex){
                JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
            }
        
        }
    }

}
