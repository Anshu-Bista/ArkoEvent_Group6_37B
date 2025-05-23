/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.MySqlConnection;
import Model.UserData;

/**
 *
 * @author hp
 */
public class UserDao {
    MySqlConnection mysql = new MySqlConnection();
    Connection conn = mysql.openConnection();

    public void signUp(UserData user){
        String sql = "INSERT into users(username, email, password) values(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(1, user.getEmail());
            pstmt.setString(1, user.getPassword());  
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE,null, ex);
        }
        finally{
            mysql.closeConnection(conn);          
        }
    }
    
    public boolean checkUser(UserData user){
        Connection conn = mysql.openConnection();
        String sql ="SELECT* FROM users where email = ? or username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());
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


    
}
