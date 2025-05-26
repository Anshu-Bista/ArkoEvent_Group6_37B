/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author Ekta Thapa
 */

import Database.MySqlConnection;
import Model.LoginRequest;
import Model.UserData;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDao {
    MySqlConnection mysql = new MySqlConnection();
    Connection conn = mysql.openConnection();
   
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
    public boolean updatePassword(String email, String newPassword, String confirmPassword){
        if (!newPassword.equals(confirmPassword)){
            return false;
        }
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
