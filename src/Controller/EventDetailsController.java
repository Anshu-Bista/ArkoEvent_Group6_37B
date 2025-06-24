package Controller;

import View.*;

import dao.EventDao;
import dao.BookingDao;
import Model.EventData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.NavigationUtil;
import util.SessionUtil;

public class EventDetailsController {
    private final EventDao eventDao = new EventDao();
    private final BookingDao bDao = new BookingDao();
    private final eventDetails eventDetailView;
    private final int userId = SessionUtil.getCurrentUser().getId();
    private final JDialog dialog;
    
    private EventData event;

    public EventDetailsController(eventDetails eventDetailView, EventData event,JDialog dialog) {
        this.eventDetailView = eventDetailView;
        this.event = event;
        this.dialog = dialog;
        

        // Show details immediately
        loadEventDetails();

        // Add listener for booking
        this.eventDetailView.bookEventsListener(new BookEventListener());
    }

    public void open() {
        this.eventDetailView.setVisible(true);
    }

    public void close() {
        this.eventDetailView.setVisible(false);
    }

    private void loadEventDetails() { 
           
            if (event == null) {
                JOptionPane.showMessageDialog(null, "Event not found.");
            } else {
                eventDetailView.displayEventDetails(event);// view
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
            int avaiable = event.getTicketsAvailable()-event.getTicketsSold();
            if(ticketCount > avaiable){
                JOptionPane.showMessageDialog(eventDetailView, "Not enough available");
            } else {
                try {

                    boolean booked = bDao.bookEvent(event.getId(), ticketCount);
                    if (booked) {
                        JOptionPane.showMessageDialog(eventDetailView, "Event booked successfully!");
                        dialog.dispose();
                        NavigationUtil.goToDashboard();
                        
                    } else {
                        JOptionPane.showMessageDialog(eventDetailView, "Booking failed. Please try again.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(eventDetailView, "Error during booking: " + ex.getMessage());
                }
            }
            
        }
    }
}