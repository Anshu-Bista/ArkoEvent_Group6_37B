package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.MySqlConnection;
import Model.Booker;
import Model.EventData;
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

    public ArrayList<EventData> getUserBookings() throws SQLException {
        ArrayList<EventData> bookings = new ArrayList<>();
        int userId = SessionUtil.getCurrentUser().getId();
        Connection conn = mysql.openConnection();

        String sql = """
            SELECT 
                e.id,
                e.user_id,
                e.title,
                e.location,
                e.description,
                e.category,
                e.type,
                e.ticket_type,
                e.event_status,
                e.event_date,
                e.start_time,
                e.end_time,
                e.rsvp_deadline,
                e.price,
                b.ticket_count AS tickets_available,
                e.tickets_sold,
                e.banner
            FROM bookings b
            JOIN events e ON b.event_id = e.id
            WHERE b.user_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
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
                            rs.getInt("tickets_available"), // ticket_count from bookings
                            rs.getInt("tickets_sold"),
                            rs.getString("banner")
                    );
                    event.setUserId(rs.getInt("user_id"));

                    bookings.add(event);
                }
            }
        }

        return bookings;
    }

    public boolean deleteBookingAndUpdateTickets(int userId, int eventId) throws SQLException {
        String selectSql = "SELECT ticket_count FROM bookings WHERE user_id = ? AND event_id = ?";
        String deleteSql = "DELETE FROM bookings WHERE user_id = ? AND event_id = ?";
        String updateSql = "UPDATE events SET tickets_sold = tickets_sold - ? WHERE id = ?";
        Connection conn = mysql.openConnection();

        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql); PreparedStatement deleteStmt = conn.prepareStatement(deleteSql); PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            conn.setAutoCommit(false); // start transaction

            // Step 1: Get ticket_count
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, eventId);

            int ticketCount = 0;
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    ticketCount = rs.getInt("ticket_count");
                } else {
                    conn.rollback();
                    return false; // No booking found
                }
            }

            // Step 2: Delete booking
            deleteStmt.setInt(1, userId);
            deleteStmt.setInt(2, eventId);
            int deleted = deleteStmt.executeUpdate();
            if (deleted == 0) {
                conn.rollback();
                return false; // Deletion failed
            }

            // Step 3: Update events.tickets_sold
            updateStmt.setInt(1, ticketCount);
            updateStmt.setInt(2, eventId);
            updateStmt.executeUpdate();

            conn.commit(); // commit transaction
            return true;
        } catch (SQLException ex) {
            conn.rollback(); // rollback on error
            throw ex;
        } finally {
            conn.setAutoCommit(true); // restore default
        }
    }

    public ArrayList<Booker> getBookersByEventId(int eventId) {
        ArrayList<Booker> bookers = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.email, u.phone, u.account_status, u.profile_image, b.ticket_count "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.id "
                + "WHERE b.event_id = ?";
        Connection conn = mysql.openConnection();
        
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booker booker = new Booker(
                            rs.getInt("id"),
                            rs.getString("profile_image"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("account_status"),
                            rs.getInt("ticket_count")
                    );
                    bookers.add(booker);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookers;
    }


}
