/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


/**
 *
 * @author Asus
 */
public class Loginpagedata {
   
    private String username;
    private String password;
    
    // Constructor
    public Loginpagedata(String username, String password) {
       
        this.username = username;
        this.password = password;
     
    }

    
 
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static void main(String[] args) {
        Loginpagedata data = new Loginpagedata("testUser", "testPass");
        System.out.println("Username: " + data.getUsername());
        System.out.println("Password: " + data.getPassword());
    }
 
}
    

     







  



  