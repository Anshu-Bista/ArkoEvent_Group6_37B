package Dao;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Event> getEventsByCategory(String category) throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE category = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEvent(rs));
                }
            }
        }

        return list;
    }

    public Event getEventById(int id) throws SQLException {
        String sql = "SELECT * FROM events WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEvent(rs);
                }
            }
        }

        return null;
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapEvent(rs));
            }
        }

        return list;
    }

    private Event mapEvent(ResultSet rs) throws SQLException {
        return new Event(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("category"),
            rs.getString("date"),
            rs.getString("location")
        );
    }
}


