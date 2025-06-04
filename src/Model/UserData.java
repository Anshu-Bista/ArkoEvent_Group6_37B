/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hp
 */
 
   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Timestamp;

/**
 *
 * @author hp
 */
public class UserData {
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String confirmPassword;
    private String status;
    private byte[] profileImage;
    private Timestamp registrationDate;

    
    public UserData(String username, String email, String password, String confirmPassword, String phone, byte[] profileImage, String status, Timestamp registrationDate){
        this.username = username;
        this.email = email;
        this.password = password;   
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.profileImage = profileImage;
        this.status = (status != null)? status : "active";
        this.registrationDate = (registrationDate != null)? registrationDate : new Timestamp(System.currentTimeMillis());
    }
    
   
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getConfirmPassword(){
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword){
        this.confirmPassword = confirmPassword;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    
    public Timestamp getRegistrationDate(){
        return registrationDate;
    }
    public void setRegistrationDate(Timestamp registrationDate){
        this.registrationDate = registrationDate;
    }

    
    public byte[] getProfileImage(){
        return profileImage;
    }
    public void setProfileImage(byte[] profileImage){
        this.profileImage = profileImage;
    }
}