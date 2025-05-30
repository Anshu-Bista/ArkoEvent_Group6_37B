package controller;

//import dao.BookingDao;
import dao.EventDao;
import model.EventData;
import view.EventDetail;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventDetailsController {
    private final EventDao eventDao = new EventDao();
    //private final BookingDao bookingDao = new BookingDao();
    private final EventDetail eventDetailView;
    private final int userId;
    private final int eventId;

    public EventDetailsController(EventDetail eventDetailView, int eventId, int userId) {
        this.eventDetailView = eventDetailView;
        this.eventId = eventId;
        this.userId = userId;

        // Show details immediately
        loadEventDetails();

        // Add listener for booking
        //eventDetailView.addBookEventListener(new BookEventListener());
    }

    public void open() {
        this.eventDetailView.setVisible(true);
    }

    public void close() {
        this.eventDetailView.dispose();
    }

    private void loadEventDetails() {
        try {
            EventData event = eventDao.getEventById(eventId);// db
            if (event == null) {
                JOptionPane.showMessageDialog(null, "Event not found.");
            } else {
                eventDetailView.displayEventDetails(event);// view
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading event details: " + ex.getMessage());
        }
    }

    class LoadEventDetailsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            loadEventDetails();
        }
    }
    /*
    class BookEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (bookingDao.userBooked(eventId, userId)) {
                    JOptionPane.showMessageDialog(null, "You have already booked this event.");
                    return;
                }

                boolean booked = bookingDao.bookEvent(eventId, userId);// db
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
         */
}
