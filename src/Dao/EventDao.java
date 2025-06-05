package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EventData;

public class EventDao {
    private final Connection connection;

    public EventDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new event into the events table.
     * 
     * @param event EventData object containing event details.
     * @return true if insert was successful, false otherwise.
     */
    public boolean createEvent(EventData event) {
        String sql = "INSERT INTO events (title, location, description, category, type, ticket_type, "
                   + "event_status, date, start_time, end_time, rsvp_deadline, price, tickets_available, tickets_sold) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
