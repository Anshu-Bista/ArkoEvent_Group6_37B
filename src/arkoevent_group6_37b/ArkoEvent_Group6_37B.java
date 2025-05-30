/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arkoevent_group6_37b;

import Database.MySqlConnection;
import Database.*;

/**
 *
 * @author hp
 */
public class ArkoEvent_Group6_37B {
    public static void main(String[]args){
        Database db = new MySqlConnection();
        System.out.println(db);
        try {
            db.openConnection();
            System.out.println(db.openConnection());
//            if (db.openConnection() != null){
//                System.out.println("Database  connected successfully!");
//               }else{
//                System.out.println("Failed to connect to database.");
//            }
        } catch(Exception e){
           System.out.println("Failed to connect to database.");
           System.out.println(e);
       }
    }
    
    /**
     * @param args the command line arguments
     */
  
        // TODO code application logic here
    }
    

