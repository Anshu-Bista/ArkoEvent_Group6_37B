package dao;

import model.FeedbackData;
import database.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    private final MySqlConnection mysql = new MySqlConnection();

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
