package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import dao.EventDao;
import model.EventData;
import util.NavigationUtil;
import util.SessionUtil;
import view.CreateEvent;

public class CreateEventController {
    private final CreateEvent createEventView;
    private EventDao eventDao; 

    public CreateEventController(CreateEvent view) {
        this.createEventView = view;
        this.createEventView.addLogoutListener(e -> SessionUtil.logout(createEventView));
        
        this.createEventView.addProfileListener(e -> {
            createEventView.dispose();
            NavigationUtil.goToProfile();
        });

        this.createEventView.addCreateEventListener(e -> {
            createEventView.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.createEventView.addHomeListener(e -> {
           createEventView.dispose();
            NavigationUtil.goToDashboard(); // Treat dashboard as home
        });

        this.createEventView.addMyEventsListener(e -> {
            JOptionPane.showMessageDialog(createEventView, "My Events page is under development.");
        });

        this.createEventView.addDiscoverListener(e -> {
            JOptionPane.showMessageDialog(createEventView, "Discover page is under development.");
        });

        try {
            this.eventDao = new EventDao();
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
            String eventStatus = createEventView.getEventStatus(); // Assuming you have a getter for status combobox
    
            // Validate required fields
            if (eventTitle.isEmpty() || eventLocation.isEmpty() || eventCategory.isEmpty()
                    || eventType == null || ticketType == null) {
                JOptionPane.showMessageDialog(createEventView, "Please fill in all required fields.");
                return;
            }
    
            LocalDate eventDate = createEventView.getEventDate();
            LocalTime startTime = createEventView.getStartTime();
            LocalTime endTime = createEventView.getEndTime();
            LocalDate rsvpDeadline = createEventView.getRsvpDeadline();
    
            if (eventDate == null || startTime == null || endTime == null || rsvpDeadline == null) {
                JOptionPane.showMessageDialog(createEventView, "Please fill in all date/time fields.");
                return;
            }
    
            // Validate times and dates
            if (endTime.isBefore(startTime)) {
                JOptionPane.showMessageDialog(createEventView, "End time cannot be before start time.");
                return;
            }
    
            if (rsvpDeadline.isAfter(eventDate)) {
                JOptionPane.showMessageDialog(createEventView, "RSVP deadline cannot be after the event date.");
                return;
            }
    
            double ticketPrice = createEventView.getEventPrice();
            int totalTickets = createEventView.getTicketsAvailable();
    
            if (ticketType.equalsIgnoreCase("Free")) {
                ticketPrice = 0;
            } else if (ticketPrice < 0) {
                JOptionPane.showMessageDialog(createEventView, "Ticket price cannot be negative.");
                return;
            }
    
            // New logic: handle status confirmation based on selected status
            if ("Draft".equalsIgnoreCase(eventStatus)) {
                // Confirm saving as draft
                int confirm = JOptionPane.showConfirmDialog(
                    createEventView, "Do you want to save this event as a draft?",
                    "Confirm Save as Draft", JOptionPane.YES_NO_OPTION
                );
    
                if (confirm != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(createEventView, "Draft save cancelled.");
                    return;
                }
            } else if ("Published".equalsIgnoreCase(eventStatus)) {
                // Confirm publishing event
                int confirm = JOptionPane.showConfirmDialog(
                    createEventView, "Are you sure you want to publish this event?",
                    "Confirm Publish", JOptionPane.YES_NO_OPTION
                );
    
                if (confirm != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(createEventView, "Event saved as draft instead.");
                    eventStatus = "Draft";
                }
            } else {
                // For any other statuses, fallback to draft
                eventStatus = "Draft";
            }
    
            EventData event = new EventData(
                0, eventTitle, eventLocation, eventDescription,
                eventCategory, eventType, ticketType, eventStatus,
                eventDate, startTime, endTime, rsvpDeadline,
                ticketPrice, totalTickets, 0
            );
    
            boolean success = eventDao.createEvent(event);
    
            if (success) {
                JOptionPane.showMessageDialog(createEventView, "Event " + eventStatus.toLowerCase() + " successfully!");
                createEventView.clearFields();
            } else {
                JOptionPane.showMessageDialog(createEventView, "Failed to save the event.");
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
