package controller;

import Dao.*;
import View.*;
import dao.BookingDao;

import model.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventDetailsController {
    private final EventDao eventDao = new EventDao();
    private final BookingDao bDao = new BookingDao();
    // private final BookingDao bookingDao = new BookingDao();
    private final EventDetail eventDetailView;
    private final int userId;
    private final int eventId;
    private EventData event;

    public EventDetailsController(EventDetail eventDetailView, int eventId, int userId) {
        this.eventDetailView = eventDetailView;
        this.eventId = eventId;
        this.userId = userId;

        // Show details immediately
        loadEventDetails();

        // Add listener for booking
        eventDetailView.bookEventsListener(new BookEventListener());
    }

    public void open() {
        this.eventDetailView.setVisible(true);
    }

    public void close() {
        this.eventDetailView.dispose();
    }

    private void loadEventDetails() {
        try {
            event = eventDao.getEventById(eventId);// db
            if (event == null) {
                JOptionPane.showMessageDialog(null, "Event not found.");
            } else {
                eventDetailView.displayEventDetails(event);// view
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading event details: " + ex.getMessage());
        }
    }

    class LoadEventDetailsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadEventDetails();
        }
    }

    public class BookEventListener implements ActionListener {
      

        public void actionPerformed(ActionEvent e) {
         
         int ticketCount = Integer.parseInt(eventDetailView.ticketCount.getValue().toString());
            try {
              
                boolean booked = bDao.bookEvent(event.getId(), userId, ticketCount, event.getPrice());
                if (booked) {
                    JOptionPane.showMessageDialog(null, "Event booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Booking failed. Please try again.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error during booking: " + ex.getMessage());
            }
        }
    }
}
