package dao;

import database.MySqlConnection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EventData;

public class EventDao {
    MySqlConnection mysql = new MySqlConnection();



    /**
     * Inserts a new event into the events table.
     * 
     * @param event EventData object containing event details.
     * @return true if insert was successful, false otherwise.
     */
    public boolean createEvent(EventData event) {
        Connection conn = mysql.openConnection();

        String sql = "INSERT INTO events (title, location, description, category, event_type, ticket_type, "
                + "status, event_date, start_time, end_time, rsvp_deadline, price, total_tickets, tickets_sold) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
            stmt.setInt(14, event.getTicketsSold()); // Add this line

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public EventData getEventById(int eventID) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM events WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventID);
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
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
                        rs.getDate("rsvp_deadline") != null ? rs.getDate("rsvp_deadline").toLocalDate() : null,
                        rs.getDouble("price"),
                        rs.getInt("tickets_available"),
                        rs.getInt("tickets_sold")
                    );
                    return event;
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
}