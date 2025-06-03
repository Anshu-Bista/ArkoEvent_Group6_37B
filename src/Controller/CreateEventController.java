package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import model.EventData;
import model.EventDao;

public class CreateEventController {
    private final CreateEvent createEventView;
    private final EventDao eventDao;

    public CreateEventController(CreateEvent createEventView, EventDao eventDao) {
        this.createEventView = createEventView;
        this.eventDao = eventDao;

        this.createEventView.addAddEventListener(new AddEventListener());
    }

    public void open() {
        this.createEventView.setVisible(true);
    }

    public void close() {
        this.createEventView.dispose();
    }

    // Create Event method handling all fields and validations
    private void createEvent() {
        try {
            String title = createEventView.getEventTitle().trim();
            String location = createEventView.getEventLocation().trim();
            String description = createEventView.getEventDescription().trim();
            String category = createEventView.getEventCategory().trim();
            String type = createEventView.getEventType(); // "Private" or "Public"
            String ticketType = createEventView.getTicketType(); // "Paid" or "Free"
            String eventStatus = createEventView.getEventStatus(); // "Draft" or "Published"

            LocalDate eventDate;
            LocalTime startTime;
            LocalTime endTime;
            LocalDate rsvpDeadline;

            // Parse dates/times with error handling
            try {
                eventDate = createEventView.getEventDate();
                startTime = createEventView.getStartTime();
                endTime = createEventView.getEndTime();
                rsvpDeadline = createEventView.getRsvpDeadline();
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(createEventView, "Invalid date or time format: " + dtpe.getMessage());
                return;
            }

            double price = createEventView.getEventPrice();
            int ticketsAvailable = createEventView.getTicketsAvailable();

            // Basic validations
            if (title.isEmpty() || location.isEmpty() || category.isEmpty() ||
                    eventDate == null || startTime == null || endTime == null ||
                    rsvpDeadline == null || type == null || ticketType == null || eventStatus == null) {
                JOptionPane.showMessageDialog(createEventView, "Please fill in all required fields.");
                return;
            }

            if (endTime.isBefore(startTime)) {
                JOptionPane.showMessageDialog(createEventView, "End time cannot be before start time.");
                return;
            }

            if (rsvpDeadline.isAfter(eventDate)) {
                JOptionPane.showMessageDialog(createEventView, "RSVP deadline cannot be after the event date.");
                return;
            }

            if (ticketType.equalsIgnoreCase("Free")) {
                price = 0; // Enforce price zero if free ticket
            } else if (price < 0) {
                JOptionPane.showMessageDialog(createEventView, "Price cannot be negative.");
                return;
            }

            EventData event = new EventData(
                    0, // ID auto-generated
                    title,
                    location,
                    description,
                    category,
                    type,
                    ticketType,
                    eventStatus,
                    eventDate,
                    startTime,
                    endTime,
                    rsvpDeadline,
                    price,
                    ticketsAvailable,
                    0 // ticketsSold start at 0
            );

            boolean success = eventDao.createEvent(event);

            if (success) {
                JOptionPane.showMessageDialog(createEventView, "Event created successfully!");
                createEventView.clearFields();
            } else {
                JOptionPane.showMessageDialog(createEventView, "Failed to create the event.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(createEventView, "Error creating event: " + ex.getMessage());
        }
    }

    class AddEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            createEvent();
        }
    }
}
