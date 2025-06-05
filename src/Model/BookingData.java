package model;

import java.sql.Timestamp;

public class BookingData {
    private int bookingId;
    private int eventId;
    private int userId;
    private String userName;
    private Timestamp bookingDate;
    private String status; // confirmed, cancelled, pending
    private String paymentStatus; // refunded, paid, unpaid
    private int ticketCount;
    private double totalAmount;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingData(int bookingId, int eventId, int userId, String userName, Timestamp bookingDate, String status,
            String paymentStatus, int ticketCount, double totalAmount) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.userId = userId;
        this.userName = userName;
        this.bookingDate = new Timestamp(System.currentTimeMillis());
        this.status = "Confirmed";
        this.paymentStatus = paymentStatus;
        this.ticketCount = ticketCount;
        this.totalAmount = totalAmount;
    }
}
