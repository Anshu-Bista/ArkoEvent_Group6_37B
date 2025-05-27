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
import javax.swing.JOptionPane;

 
/**
*
* @author hp
*/
public class UserDao {
    MySqlConnection mysql = new MySqlConnection();
    
 
    public void signUp(UserData user){
        Connection conn = mysql.openConnection();
        String sql = "INSERT into users(username, email, contact, pass) values(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getContact());
            pstmt.setString(4, user.getPassword());  
            int ra = pstmt.executeUpdate();
            if(ra>0){
                JOptionPane.showMessageDialog(null, "Registered Successfully!");
            }else{
                JOptionPane.showMessageDialog(null, "Failed to Register !");
            }
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE,null, ex);
        }
        finally{
            mysql.closeConnection(conn);          
        }
    }
    public boolean checkUser(UserData user){
        Connection connn = mysql.openConnection();
        String sql ="SELECT* FROM users where email = ? or username = ?"; 
        try (PreparedStatement pstmt = connn.prepareStatement(sql)){
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());
            ResultSet result = pstmt.executeQuery();
            return result.next();
        }
        catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            mysql.closeConnection(connn);
        }
        return false;
    }
    
}   

