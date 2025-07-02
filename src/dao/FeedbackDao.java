package dao;

import Database.MySqlConnection;
import Model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    private final MySqlConnection mysql = new MySqlConnection();

    public void updateBookingFeedback(int userId, int eventId, int rating, String feedback) throws SQLException {
        String sql = "UPDATE bookings SET rating = ?, feedback = ? WHERE user_id = ? AND event_id = ?";
        Connection conn = mysql.openConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rating);
            if (feedback != null) {
                stmt.setString(2, feedback);
            } else {
                stmt.setNull(2, java.sql.Types.VARCHAR);
            }
            stmt.setInt(3, userId);
            stmt.setInt(4, eventId);

            stmt.executeUpdate();
        }
    }

    public ArrayList<Feedback> getFeedbacksByEventId(int eventId) throws SQLException {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String sql = "{CALL getEventFeedbacks(?)}";
        Connection conn = mysql.openConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback(
                            rs.getInt("user_id"),
                            rs.getInt("event_id"),
                            rs.getString("username"),
                            rs.getString("event_name"),
                            rs.getInt("rating"),
                            rs.getString("feedback")
                    );
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }

}
