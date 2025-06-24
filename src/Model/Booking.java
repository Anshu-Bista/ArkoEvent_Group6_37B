package Model;

public class Booking {
    private int bookingId;
    private int userId;
    private int eventId;
    private int ticketCount;
    private String eventTitle;
    private String username;

    public Booking(int bookingId, int userId, int eventId, int ticketCount, String eventTitle, String username) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.ticketCount = ticketCount;
        this.eventTitle = eventTitle;
        this.username = username;
    }

    // Getters
    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getEventId() { return eventId; }
    public int getTicketCount() { return ticketCount; }
    public String getEventTitle() { return eventTitle; }
    public String getUsername() { return username; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", eventId=" + eventId +
                ", ticketCount=" + ticketCount +
                ", eventTitle='" + eventTitle + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
