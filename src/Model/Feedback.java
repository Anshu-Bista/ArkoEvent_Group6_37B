/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author thismac
 */
public class Feedback {
    private int userId;
    private int eventId;
    private String username;
    private String eventName;
    private int rating;
    private String feedbackText;

    public Feedback(int userId, int eventId, String username, String eventName, int rating, String feedbackText) {
        this.userId = userId;
        this.eventId = eventId;
        this.username = username;
        this.eventName = eventName;
        this.rating = rating;
        this.feedbackText = feedbackText;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getUsername() {
        return username;
    }

    public String getEventName() {
        return eventName;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
    
}
