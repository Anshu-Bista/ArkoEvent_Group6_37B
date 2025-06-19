package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import dao.EventDao;
import database.MySqlConnection;
import model.EventData;
import util.SessionUtil;
import view.CreateEvent;

public class CreateEventController {
    private final CreateEvent createEventView;
    private EventDao eventDao; 

    public CreateEventController(CreateEvent view) {
        this.createEventView = view;
        this.createEventView.addLogoutListener(e -> SessionUtil.logout(createEventView));

        try {
            Connection conn = new MySqlConnection().openConnection();
            this.eventDao = new EventDao(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }

        this.createEventView.addAddEventListener(new AddEventListener());
    }

    public void open() {
        createEventView.setVisible(true);
    }

    public void close() {
        createEventView.dispose();
    }

    private void handleCreateEvent() {
        if (eventDao == null) {
            JOptionPane.showMessageDialog(createEventView, "Error: Event DAO not available.");
            return;
        }

        try {
            String eventTitle = createEventView.getEventTitle().trim();
            String eventLocation = createEventView.getEventLocation().trim();
            String eventDescription = createEventView.getEventDescription().trim();
            String eventCategory = createEventView.getEventCategory().trim();
            String eventType = createEventView.getEventType();
            String ticketType = createEventView.getTicketType();
            String eventStatus;

            int confirm = JOptionPane.showConfirmDialog(
                createEventView, "Are you sure you want to publish this event?",
                "Confirm Publish", JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                eventStatus = "Published";
            } else {
                eventStatus = "Draft";
                JOptionPane.showMessageDialog(createEventView, "Event saved as draft.");
                createEventView.clearFields();
                return;
            }

            LocalDate eventDate = createEventView.getEventDate();
            LocalTime startTime = createEventView.getStartTime();
            LocalTime endTime = createEventView.getEndTime();
            LocalDate rsvpDeadline = createEventView.getRsvpDeadline();

            double ticketPrice = createEventView.getEventPrice();
            int totalTickets = createEventView.getTicketsAvailable();

            if (eventTitle.isEmpty() || eventLocation.isEmpty() || eventCategory.isEmpty()
                    || eventDate == null || startTime == null || endTime == null
                    || rsvpDeadline == null || eventType == null || ticketType == null) {
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
                ticketPrice = 0;
            } else if (ticketPrice < 0) {
                JOptionPane.showMessageDialog(createEventView, "Ticket price cannot be negative.");
                return;
            }

            EventData event = new EventData(
                0, eventTitle, eventLocation, eventDescription,
                eventCategory, eventType, ticketType, eventStatus,
                eventDate, startTime, endTime, rsvpDeadline,
                ticketPrice, totalTickets, 0
            );

            boolean success = eventDao.createEvent(event);

            if (success) {
                JOptionPane.showMessageDialog(createEventView, "Event created successfully!");
                createEventView.clearFields();
            } else {
                JOptionPane.showMessageDialog(createEventView, "Failed to create the event.");
            }

        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(createEventView, "Invalid date/time: " + dtpe.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(createEventView, "Error creating event: " + ex.getMessage());
        }
    }

    class AddEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            handleCreateEvent();
        }
    }
}
