package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String sql = "INSERT INTO events (title, location, description, category, event_type, ticket_type, "
                   + "status, event_date, start_time, end_time, rsvp_deadline, price, total_tickets, tickets_sold) "
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
            stmt.setInt(14, event.getTicketsSold()); // Add this line
    
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    

}
