package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.MySqlConnection;

public class BookingDao {
    MySqlConnection mysql = new MySqlConnection();

    public  boolean bookEvent(int eventId, int userId, int ticketCount, double ticketPrice) {
        Connection conn = mysql.openConnection();

        try {
            // First check if booking exists
            String checkSql = "SELECT ticket_count, total_amount FROM bookings WHERE event_id = ? AND user_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, eventId);
                checkStmt.setInt(2, userId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Booking exists: update ticket count and total amount
                    int currentCount = rs.getInt("ticket_count");
                    double currentAmount = rs.getDouble("total_amount");

                    String updateSql = "UPDATE bookings SET ticket_count = ?, total_amount = ? WHERE event_id = ? AND user_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, currentCount + ticketCount);
                        updateStmt.setDouble(2, currentAmount + (ticketCount * ticketPrice));
                        updateStmt.setInt(3, eventId);
                        updateStmt.setInt(4, userId);
                        int updated = updateStmt.executeUpdate();
                        return updated > 0;
                    }
                } else {
                    // No booking exists: insert new
                    String insertSql = "INSERT INTO bookings (event_id, user_id, ticket_count, total_amount, booking_date, status, payment_status) VALUES (?, ?, ?, ?, NOW(), 'Confirmed', 'Unpaid')";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, eventId);
                        insertStmt.setInt(2, userId);
                        insertStmt.setInt(3, ticketCount);
                        insertStmt.setDouble(4, ticketCount * ticketPrice);
                        int inserted = insertStmt.executeUpdate();
                        return inserted > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }

        return false;
    }

}
