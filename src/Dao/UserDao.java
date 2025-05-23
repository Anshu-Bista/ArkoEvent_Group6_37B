/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.ZoneId;


import Database.MySqlConnection;
import Model.UserData;

/**
 *
 * @author hp
 */
public class UserDao {
    MySqlConnection mysql = new MySqlConnection();
    Connection conn = mysql.openConnection();

    //Sign Up
    public void signUp(UserData user){
        String sql = "INSERT into users(username, email, password, phone) values(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());  
            pstmt.setString(4, user.getPhone());  
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE,null, ex);
        }
        finally{
            mysql.closeConnection(conn);          
        }
    }


    //Checks if the user is present
    public boolean checkUser(UserData user){
        Connection conn = mysql.openConnection();
        String sql ="SELECT* FROM users where email = ? or username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());  
            pstmt.setString(3, user.getPhone());  
            ResultSet result = pstmt.executeQuery();
            return result.next();
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

    //Log In
    public boolean login(String email, String password){
        Connection conn = mysql.openConnection();
        String sql ="SELECT* FROM users where email = ? or password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();
            return result.next(); 
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

    //Forgot Password
    //check if email is in the table
    public boolean checkEmail(UserData user){
        Connection conn = mysql.openConnection();
        String sql ="SELECT id FROM users where email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            ResultSet result = pstmt.executeQuery();
            return result.next();
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

    //save reset code and current timestamp 
    public void saveResetCode(String email, String resetCode){
        Connection conn = mysql.openConnection();
        String sql ="UPDATE users SET reset_code = ?, reset_code_timestamp = NOW() WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, resetCode);
            pstmt.setString(2, email);          
            pstmt.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
    }

    //verify the code and time 
    public boolean verifyResetCode(String email, String resetCode){
        Connection conn = mysql.openConnection();
        String sql ="SELECT reset_code_timestamp FROM users WHERE email= ? AND reset_code = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, email);
            pstmt.setString(2, resetCode);          
            ResultSet result = pstmt.executeQuery();

            if(result.next()){
                LocalDateTime generatedAt = result.getTimestamp("reset_code_timestamp").toLocalDateTime();
                LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
                //valid only if within 3 minutes
                if (generatedAt.plusMinutes(3).isAfter(now)){
                    return true;
                }
            }
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

    //update password and clear reset code nad timestamp
    public boolean updatePassword(String email, String newPassword){
        Connection conn = mysql.openConnection();
        String sql ="UPDATE users SET password = ?, reset_code = null, reset_code_timestamp = null WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);          
            int rowsUpdated  = pstmt.executeUpdate();
            return rowsUpdated > 0; // success if atleast one row was updated

        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

    //Profile
    public UserData getProfile(String email){
        Connection conn = mysql.openConnection();
        String sql ="SELECT username, email, phone, account_status, profile_image, registration_date FROM users where email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, email);
            ResultSet result = pstmt.executeQuery();
            if (result.next()){
                UserData user = new UserData();
                user.setUsername(result.getString("username"));               
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));
                user.setStatus(result.getString("account_status"));        
                user.setProfileImage(result.getBytes("profile_image"));
                user.setRegistrationDate(result.getString("registration_date"));
            } 
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return null;
    }

    public boolean updateProfile(UserData user){
        Connection conn = mysql.openConnection();
        String sql ="UPDATE users SET username = ?, phone = ?, account_status = ?, profile_image = ? WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPhone());  
            pstmt.setString(3, user.getStatus());            
            pstmt.setBytes(4, user.getProfileImage());  
            pstmt.setString(5, user.getEmail());  

            int rows = pstmt.executeUpdate();
            return rows > 0; // success if atleast one row was updated

        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(conn);
        }
        return false;
    }

}
