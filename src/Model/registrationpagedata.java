/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Aayusha
 */
public class registrationpagedata {
    private String Username;
    private String email;
    private String contact;
    private String password;
    private String confirmPassword;
 
    // Constructor
    public registrationpagedata(String email, String Username, String password, String confirmPassword, String contact) {
        this.email = email;
        this.contact = contact;
        this.Username = Username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
 
//Getters and setters
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }
 
    public String getUsername() {
        return Username;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
 
    public String getpassword() {
        return password;
    }
    public void setpassword(String password) {
        this.password = password;
    }
 
    public String getconfirmPassword() {
        return confirmPassword;
    }
    public void setconfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getcontact()
    {
        return contact;
    }
    public void setcontact(String contact)
    {
        this.contact = contact;
    }
}