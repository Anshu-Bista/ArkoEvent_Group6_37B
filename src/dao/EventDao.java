package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.EventData;
import Database.MySqlConnection;
import java.util.ArrayList;
import util.SessionUtil;

public class EventDao {

    private MySqlConnection mysql = new MySqlConnection();

    public EventDao() {
    }

    private Connection openConnection() {
        return mysql.openConnection();
    }

    // Create Event
    public boolean createEvent(EventData event) {
        String sql = "INSERT INTO events (title, location, description, category, type, ticket_type, "
                + "event_status, event_date, start_time, end_time, rsvp_deadline, price, tickets_available, tickets_sold, banner,user_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getLocation());
            stmt.setString(3, event.getDescription());
            stmt.setString(4, event.getCategory());
            stmt.setString(5, event.getType());
            stmt.setString(6, event.getTicketType());
            stmt.setString(7, event.getEventStatus());
            stmt.setDate(8, java.sql.Date.valueOf(event.getEventDate()));
            stmt.setTime(9, java.sql.Time.valueOf(event.getStartTime()));
            stmt.setTime(10, java.sql.Time.valueOf(event.getEndTime()));
            stmt.setDate(11, java.sql.Date.valueOf(event.getRsvpDeadline()));
            stmt.setDouble(12, event.getPrice());
            stmt.setInt(13, event.getTicketsAvailable());
            stmt.setInt(14, event.getTicketsSold());
            stmt.setString(15, event.getBanner());
            stmt.setInt(16, SessionUtil.getCurrentUser().getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<EventData> getEventsByUserId(int userId) {
        ArrayList<EventData> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE user_id = ?";

        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date rsvpDeadlineDate = rs.getDate("rsvp_deadline");

                    EventData event = new EventData(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("type"),
                            rs.getString("ticket_type"),
                            rs.getString("event_status"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rsvpDeadlineDate != null ? rsvpDeadlineDate.toLocalDate() : null,
                            rs.getDouble("price"),
                            rs.getInt("tickets_available"),
                            rs.getInt("tickets_sold"),
                            rs.getString("banner")
                    );
                    events.add(event);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return events;
    }
    
    public ArrayList<EventData> getEventsByTicketTypeAndSearch(String type, String searchQuery) {
        ArrayList<EventData> events = new ArrayList<>();
        String sql = "{CALL getEventsByTicketTypeAndSearch(?, ?)}";

        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setString(2, searchQuery.equals("search")? "":searchQuery);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date rsvpDeadlineDate = rs.getDate("rsvp_deadline");

                    EventData event = new EventData(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("type"),
                            rs.getString("ticket_type"),
                            rs.getString("event_status"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rsvpDeadlineDate != null ? rsvpDeadlineDate.toLocalDate() : null,
                            rs.getDouble("price"),
                            rs.getInt("tickets_available"),
                            rs.getInt("tickets_sold"),
                            rs.getString("banner")
                    );
                    events.add(event);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return events;
    }



    // Get event by ID
    public EventData getEventById(int eventID) {
        String sql = "SELECT * FROM events WHERE id = ?";

        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Handle nullable rsvp_deadline with null check
                    java.sql.Date rsvpDeadlineDate = rs.getDate("rsvp_deadline");

                    EventData event = new EventData(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("type"),
                            rs.getString("ticket_type"),
                            rs.getString("event_status"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rsvpDeadlineDate != null ? rsvpDeadlineDate.toLocalDate() : null,
                            rs.getDouble("price"),
                            rs.getInt("tickets_available"),
                            rs.getInt("tickets_sold"),
                            rs.getString("banner")
                    );
                    event.setUserId(rs.getInt("user_id"));
                    return event;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    // Total Events for Admin Dashboard
    public int getEventCount() {
        String query = "SELECT COUNT(*) FROM events";
        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteEvent(EventData event) {
        String sql = "DELETE FROM events WHERE id = ?";

        try (Connection conn = openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, event.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
