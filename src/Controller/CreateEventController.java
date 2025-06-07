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
import view.CreateEvent;

public class CreateEventController {
    private final CreateEvent createEventView;
    private EventDao eventDao; 

    private boolean isEditMode = false;
    private EventData editingEvent = null;

    public CreateEventController(CreateEvent view) {
        this.createEventView = view;

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

    // Call this when editing an existing event
    public void setEditEvent(EventData event) {
        this.isEditMode = true;
        this.editingEvent = event;
        createEventView.setFormData(event); // assumes your CreateEvent view has this method
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
                createEventView, isEditMode ? "Update this event?" : "Publish this event?",
                isEditMode ? "Confirm Update" : "Confirm Publish", JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                eventStatus = "Published";
            } else {
                eventStatus = "Draft";
                JOptionPane.showMessageDialog(createEventView, "Event saved as draft.");
                createEventView.resetForm();
                resetFormState();
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

            // Set ID = existing ID if editing, otherwise 0
            int eventId = isEditMode && editingEvent != null ? editingEvent.getId() : 0;

            EventData event = new EventData(
                eventId, eventTitle, eventLocation, eventDescription,
                eventCategory, eventType, ticketType, eventStatus,
                eventDate, startTime, endTime, rsvpDeadline,
                ticketPrice, totalTickets, 0
            );

            boolean success;
            if (isEditMode && editingEvent != null) {
                success = eventDao.updateEvent(event); // You need to define this in EventDao
            } else {
                success = eventDao.createEvent(event);
            }

            if (success) {
                JOptionPane.showMessageDialog(createEventView,
                    isEditMode ? "Event updated successfully!" : "Event created successfully!");
                createEventView.resetForm();;
                resetFormState();
            } else {
                JOptionPane.showMessageDialog(createEventView,
                    isEditMode ? "Failed to update event." : "Failed to create the event.");
            }

        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(createEventView, "Invalid date/time: " + dtpe.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(createEventView, "Error creating/updating event: " + ex.getMessage());
        }
    }

    private void resetFormState() {
        isEditMode = false;
        editingEvent = null;
    }

    class AddEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleCreateEvent();
        }
    }
}
