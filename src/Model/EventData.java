package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventData {
    private int id;
    private String title;
    private String location;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private int ticketsAvailable;
    private int ticketsSold;

    public EventData(int id, String title, String location, String description, LocalDate date, LocalTime startTime,
            LocalTime endTime, double price, int ticketsAvailable, int ticketsSold) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
    }
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

