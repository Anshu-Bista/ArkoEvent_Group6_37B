package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.MySqlConnection;
import Model.Booking;
import java.util.ArrayList;
import util.SessionUtil;

public class BookingDao {

    MySqlConnection mysql = new MySqlConnection();

    public boolean bookEvent(int eventId, int ticketCount) {
        int userId = SessionUtil.getCurrentUser().getId();
        Connection conn = mysql.openConnection();
        boolean success = false;

        try {
            conn.setAutoCommit(false);

            // Check existing booking
            String checkSql = "SELECT ticket_count FROM bookings WHERE event_id = ? AND user_id = ?";
            int currentCount = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, eventId);
                checkStmt.setInt(2, userId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    currentCount = rs.getInt("ticket_count");
                }
            }

            if (currentCount > 0) {
                // Update booking
                String updateSql = "UPDATE bookings SET ticket_count = ? WHERE event_id = ? AND user_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, currentCount + ticketCount);
                    updateStmt.setInt(2, eventId);
                    updateStmt.setInt(3, userId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert booking
                String insertSql = "INSERT INTO bookings (event_id, user_id, ticket_count) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, eventId);
                    insertStmt.setInt(2, userId);
                    insertStmt.setInt(3, ticketCount);
                    insertStmt.executeUpdate();
                }
            }

            // Update tickets_sold in events
            String updateTicketsSql = "UPDATE events SET tickets_sold = tickets_sold + ? WHERE id = ?";
            try (PreparedStatement updateTicketsStmt = conn.prepareStatement(updateTicketsSql)) {
                updateTicketsStmt.setInt(1, ticketCount);
                updateTicketsStmt.setInt(2, eventId);
                updateTicketsStmt.executeUpdate();
            }

            conn.commit();
            success = true;

        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }

        return success;
    }
    
    public ArrayList<Booking> getBookingsByUser(int userId) {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.id AS booking_id, b.user_id, b.event_id, b.ticket_count, e.title, u.username "
                + "FROM bookings b "
                + "JOIN events e ON b.event_id = e.id "
                + "JOIN users u ON b.user_id = u.id "
                + "WHERE b.user_id = ?";

        try (Connection conn = mysql.openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int eventId = rs.getInt("event_id");
                int ticketCount = rs.getInt("ticket_count");
                String eventTitle = rs.getString("title");
                String username = rs.getString("username");

                bookings.add(new Booking(bookingId, userId, eventId, ticketCount, eventTitle, username));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookings;
    }


}
