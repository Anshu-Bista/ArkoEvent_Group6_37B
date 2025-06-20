package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EventData;
import database.MySqlConnection; // Import your connection helper

public class EventDao {
    private MySqlConnection mysql = new MySqlConnection();

    public EventDao() {
    }

    private Connection openConnection() {
        return mysql.openConnection();
    }

    //Create Events
    public boolean createEvent(EventData event) {
        String sql = "INSERT INTO events (title, location, description, category, type, ticket_type, "
                + "event_status, event_date, start_time, end_time, rsvp_deadline, price, tickets_available, tickets_sold) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
    }

        
    // Total Users for Admin Dashboard
    public int getEventCount() {
        String query = "SELECT COUNT(*) FROM events";
        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return 0;
    }
}
