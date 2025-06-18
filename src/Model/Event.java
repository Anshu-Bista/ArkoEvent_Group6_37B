/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Ekta Thapa
 */
public class Event {
    private final int id;
    private final String title;       // changed from 'name' to 'title' for clarity
    private final String category;
    private final String date;        // use String, or change to LocalDate/LocalDateTime if needed
    private final String location;

    public Event(int id, String title, String category, String date, String location) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.date = date;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return title + " (" + category + ") on " + date + " at " + location;
    }
}
