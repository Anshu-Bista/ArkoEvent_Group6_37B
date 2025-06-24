package dao;

import model.FeedbackData;
import Database.MySqlConnection;

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


    public List<FeedbackData> getAllFeedbacks() {
        List<FeedbackData> feedbackList = new ArrayList<>();
        String sql = "{CALL GetAllFeedbacks()}";

        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FeedbackData data = new FeedbackData();
                data.setId(rs.getInt("id"));
                data.setRating(rs.getInt("rating"));
                data.setSubmittedAt(rs.getString("submitted_at"));
                data.setUsername(rs.getString("user_name"));
                data.setTitle(rs.getString("event_name"));
                feedbackList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
}
