package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import model.EventData;
import view.CreateEvent;
import dao.EventDao;

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

    // Handles creation of the event with form validations
    private void handleCreateEvent() {
        try {
            String eventTitle = createEventView.getEventTitle().trim();
            String eventLocation = createEventView.getEventLocation().trim();
            String eventDescription = createEventView.getEventDescription().trim();
            String eventCategory = createEventView.getEventCategory().trim();
            String eventType = createEventView.getEventType(); // "Private" or "Public"
            String ticketType = createEventView.getTicketType(); // "Paid" or "Free"
            String eventStatus;

            // Confirm event publishing
            int confirm = JOptionPane.showConfirmDialog(
                    createEventView,
                    "Are you sure you want to publish this event?",
                    "Confirm Publish",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                eventStatus = "Published";
            } else {
                eventStatus = "Draft";
                JOptionPane.showMessageDialog(createEventView, "Event saved as draft.");
                createEventView.clearFields();
                return;
            }

            // Parse dates/times with error handling
            LocalDate eventDate;
            LocalTime startTime;
            LocalTime endTime;
            LocalDate rsvpDeadline;

            try {
                eventDate = createEventView.getEventDate();
                startTime = createEventView.getStartTime();
                endTime = createEventView.getEndTime();
                rsvpDeadline = createEventView.getRsvpDeadline();
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(createEventView, "Invalid date or time: " + dtpe.getMessage());
                return;
            }

            double ticketPrice = createEventView.getEventPrice();
            int totalTickets = createEventView.getTicketsAvailable();

            // Validations
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
                    0, // Auto-generated ID
                    eventTitle,
                    eventLocation,
                    eventDescription,
                    eventCategory,
                    eventType,
                    ticketType,
                    eventStatus,
                    eventDate,
                    startTime,
                    endTime,
                    rsvpDeadline,
                    ticketPrice,
                    totalTickets,
                    0 // Tickets sold initially 0
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
            System.out.println("clicked");
            handleCreateEvent();
        }
    }
}
