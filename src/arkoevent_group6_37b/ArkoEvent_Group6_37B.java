/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arkoevent_group6_37b;
import Database.*;
import Database.Mysql;

/**
 *
 * @author hp
 */
public class ArkoEvent_Group6_37B {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database db = new Mysql();
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
    
