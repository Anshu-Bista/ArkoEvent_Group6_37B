/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arkoevent_group6_37b;

import java.sql.Connection;
import java.sql.SQLException;

import controller.CreateEventController;
import dao.EventDao;
import database.MySqlConnection;
import view.CreateEvent;

/**
 *
 * @author hp
 */
public class ArkoEvent_Group6_37B {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MySqlConnection mySqlConn = new MySqlConnection();
        try (Connection conn = mySqlConn.openConnection()) {
            EventDao eventDao = new EventDao(conn);
            CreateEvent createEventView = new CreateEvent();
            CreateEventController controller = new CreateEventController(createEventView, eventDao);
            controller.open();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    }
}
