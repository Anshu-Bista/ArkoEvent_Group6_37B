package dao;

import java.awt.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    // Get events by category
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

    // Get event by ID
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

    // Get all events
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

    // Helper method to map ResultSet to Event object
    private Event mapEvent(ResultSet rs) throws SQLException {
        return new Event(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getDate("date").toLocalDate(),
            rs.getString("location")
        );
    }
}

