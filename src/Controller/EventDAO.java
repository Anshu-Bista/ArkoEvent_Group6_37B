/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
/**
 *
 * @author Ekta Thapa
 */

import java.awt.Event;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EventDAO {
    private Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Get events by category
    public List<Event> getEventsByCategory(String category) throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE category = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(mapEvent(rs));
        }
        return list;
    }

    // 2. Get event by ID
    public Event getEventById(int id) throws SQLException {
        String sql = "SELECT * FROM events WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return mapEvent(rs);
        }
        return null;
    }

    // 3. Get all events
    public List<Event> getAllEvents() throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            list.add(mapEvent(rs));
        }
        return list;
    }

    // Helper function to map ResultSet to Event object
     private Event mapEvent(ResultSet rs) throws SQLException {
    java.sql.Date sqlDate = rs.getDate("date");
    LocalDate localDate = sqlDate != null ? sqlDate.toLocalDate() : null;

    return new Event(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("category"),
        localDate,
        rs.getString("location")
    );
}
}

