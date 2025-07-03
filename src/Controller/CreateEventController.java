package Controller;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import dao.EventDao;
import Model.EventData;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.NavigationUtil;
import util.SessionUtil;


public class CreateEventController {
    private final CreateEvent createEventView;
    private EventDao eventDao; 
    private String banner;

    public CreateEventController(CreateEvent view) {
        this.createEventView = view;
        this.createEventView.uploadBannerListener(new setBanner());
          
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
            createEventView.dispose();
            NavigationUtil.goToMyEvents();
        });
        
        this.createEventView.addBookingListener(e -> {
            createEventView.dispose();
            NavigationUtil.goToMyEvents();
        });



        try {
            this.eventDao = new EventDao();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }

        // Attach action listener to 'Add Event' button
        this.createEventView.addAddEventListener(new AddEventListener());
        this.createEventView.addEventStatusListener(new EventStatusListener());
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
            // Retrieve user input
            String eventTitle = createEventView.getEventTitle().trim();
            String eventLocation = createEventView.getEventLocation().trim();
            String eventDescription = createEventView.getEventDescription().trim();
            String eventCategory = createEventView.getEventCategory().trim();
            String eventType = createEventView.getEventType();
            String ticketType = createEventView.getTicketType();
            String eventStatus = createEventView.getEventStatus(); // Selected status from UI
    
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
            
            
            double ticketPrice = ticketType.equals("Paid")? createEventView.getEventPrice():0;
            int totalTickets = createEventView.getTicketsAvailable();
    
            // Validate date/time fields
            if (eventDate == null || startTime == null || endTime == null || rsvpDeadline == null) {
                JOptionPane.showMessageDialog(createEventView, "Date and time fields cannot be empty.");
                return;
            }
    
            // Validate time logic
            if (endTime.isBefore(startTime)) {
                JOptionPane.showMessageDialog(createEventView, "End time cannot be before start time.");
                return;
            }
    
            if (rsvpDeadline.isAfter(eventDate)) {
                JOptionPane.showMessageDialog(createEventView, "RSVP deadline cannot be after the event date.");
                return;
            }
    
            // Ticket price validation
            if (ticketType.equalsIgnoreCase("Free")) {
                ticketPrice = 0;
            } else if (ticketPrice < 0) {
                JOptionPane.showMessageDialog(createEventView, "Ticket price cannot be negative.");
                return;
            }
    
            // Handle event status confirmation based on selected status
            if ("Draft".equalsIgnoreCase(eventStatus)) {
                int confirm = JOptionPane.showConfirmDialog(
                    createEventView, "Do you want to save this event as a draft?",
                    "Confirm Save as Draft", JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(createEventView, "Draft save cancelled.");
                    return;
                }
            } else if ("Published".equalsIgnoreCase(eventStatus)) {
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
    
            // Create EventData object
            EventData event = new EventData(
                0, eventTitle, eventLocation, eventDescription,
                eventCategory, eventType, ticketType, eventStatus,
                eventDate, startTime, endTime, rsvpDeadline,
                ticketPrice, totalTickets, 0,banner
            );
    
            // Save event to DB
            boolean success = eventDao.createEvent(event);
    
            if (success) {
                JOptionPane.showMessageDialog(createEventView, "Event " + eventStatus.toLowerCase() + " successfully!");
                
            } else {
                JOptionPane.showMessageDialog(createEventView, "Failed to save the event.");
            }
    
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(createEventView, "Invalid date/time: " + dtpe.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(createEventView, "Error creating event: " + ex.getMessage());
        }
    }

    private class setBanner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Profile Image");

            // Optional: set filter for image files only
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(imageFilter);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();

                // Set image path to user object
               banner = imagePath;

                // Load and scale the image
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image scaledImage = originalIcon.getImage().getScaledInstance(220, 120, Image.SCALE_SMOOTH);
                createEventView.bannerSelector.setIcon(new ImageIcon(scaledImage));
            }
        }

        
    }

    // ActionListener for the 'Add Event' button
    class AddEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleCreateEvent();
        }
    }
    
    // ActionListener for event status change - just logs for now
    class EventStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedStatus = createEventView.getEventStatus();
            System.out.println("Status selected: " + selectedStatus);
        }
    }
}
