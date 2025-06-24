package model;

public class FeedbackData {
    private int id;
    private int eventId;
    private int userId;
    private int rating;
    private String submittedAt;

    // Display-related fields from joined tables
    private String username;
    private String title;

    public FeedbackData(int id, int eventId, int userId, int rating, String submittedAt, String username,
            String title) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.submittedAt = submittedAt;
        this.username = username;
        this.title = title;
    }

    public FeedbackData(int rating, String submittedAt, String username, String title) {
        this.rating = rating;
        this.submittedAt = submittedAt;
        this.username = username;
        this.title = title;
    }

    public FeedbackData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}