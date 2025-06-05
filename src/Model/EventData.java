package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventData {
    private int id;
    private String title;
    private String location;
    private String description;
    private String category;
    private String type;          // Private or Public
    private String ticketType;    // Paid or Free
    private String eventStatus;   // Draft or Published
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate rsvpDeadline;
    private double price;
    private int ticketsAvailable;
    private int ticketsSold;

    public EventData(int id, String title, String location, String description,
                     String category, String type, String ticketType, String eventStatus,
                     LocalDate eventDate, LocalTime startTime, LocalTime endTime,
                     LocalDate rsvpDeadline, double price, int ticketsAvailable, int ticketsSold) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.category = category;
        this.type = type;
        this.ticketType = ticketType;
        this.eventStatus = eventStatus;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rsvpDeadline = rsvpDeadline;
        this.price = price;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTicketType() {
        return ticketType;
    }
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    public String getEventStatus() {
        return eventStatus;
    }
    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
    public LocalDate getEventDate() {
        return eventDate;
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public LocalDate getRsvpDeadline() {
        return rsvpDeadline;
    }
    public void setRsvpDeadline(LocalDate rsvpDeadline) {
        this.rsvpDeadline = rsvpDeadline;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getTicketsAvailable() {
        return ticketsAvailable;
    }
    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }
    public int getTicketsSold() {
        return ticketsSold;
    }
    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
