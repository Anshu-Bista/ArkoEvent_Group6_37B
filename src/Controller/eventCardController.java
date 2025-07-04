/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
import View.*;
import View.eventCard;
import dao.BookingDao;
import dao.EventDao;
import dao.FeedbackDao;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import util.SessionUtil;
import javax.swing.SwingUtilities;

/**
 *
 * @author thismac
 */
public class eventCardController {
    private final eventCard card;
    private final EventData event;
    private final String role;
    private  boolean booked = false;
    
    public eventCardController(eventCard card,EventData event){
        System.out.println("2 params");
        this.card =  card;
        this.event = event;
        this.card.bookDeleteListener(new bookDelete());
        
        role = SessionUtil.getCurrentUser().getRole();
        setValues();
    }
    
    public eventCardController(eventCard card,EventData event,boolean booked){
        System.out.println("3 params");
        this.card =  card;
        this.event = event;
        this.booked = booked;
        this.card.bookDeleteListener(new bookDelete());
        
        role = SessionUtil.getCurrentUser().getRole();
        setValues();
    }
    
    public void open(){
        card.setVisible(true);
    }
    
    public void close(){
        card.setVisible(false);
    }
    
    public void setValues(){
        String path = event.getBanner();
        if (path != null && !path.isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(path);
                Image originalImage = originalIcon.getImage();

                // Resize image to 140x140
                Image scaledImage = originalImage.getScaledInstance(190, 172, Image.SCALE_SMOOTH);

                // Set the scaled image as icon
                card.banner.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
        
        card.title.setText(event.getTitle());
        card.Location.setText(event.getLocation());
        card.date.setText(event.getEventDate().toString());
        card.time.setText(event.getStartTime().toString()+"-"+event.getEndTime());
        card.Description.setText(event.getDescription());
        card.price.setText("NPR "+Double.toString(event.getPrice()));
        if(booked){
            card.left.setText(Integer.toString(event.getTicketsSold())+" Booked");
        }else{
            card.left.setText(Integer.toString(event.getTicketsAvailable()-event.getTicketsSold())+ " Left");
        }
        
        System.out.println(role);
        System.out.println(booked);
        
        if(role.equals("admin")){
            
            if(booked){
                card.bookDelete.setText("View Bookings");
            }else{
            card.bookDelete.setText("Delete");
            }
        }else{
            
            if (booked) {
                if (LocalDate.now().isAfter(event.getEventDate())) {
                    card.bookDelete.setText("Give Feedback");
                } else {
                    card.bookDelete.setText("Cancel");
                }
            }
            else{
                card.bookDelete.setText("Book Now");
            }
            
        }
    }

    private  class bookDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(role.equals("admin")){
                if(booked){
                    bookersList bookersView = new bookersList(event.getId());
                    JDialog dialog = new JDialog((Frame) null, "Event Details", true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                        dialog.getContentPane().add(bookersView);

                        dialog.pack();
                        dialog.setLocationRelativeTo(card);
                        dialog.setVisible(true);
                    
                }else{
                    int choice = JOptionPane.showConfirmDialog(
                            card,
                            "Are you sure you want to delete this event?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        EventDao dao = new EventDao();
                        boolean success = dao.deleteEvent(event);

                        if (success) {
                            JOptionPane.showMessageDialog(null, "Event deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            close();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete event.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                
            } else if (role.equals("user")) {
                if (booked) {
                    if (card.bookDelete.getText().equals("Cancel")) {

                        int choice = JOptionPane.showConfirmDialog(
                                card,
                                "Are you sure you want to cancel this booking?",
                                "Cancel Booking",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            try {
                                BookingDao dao = new BookingDao();
                                boolean success = dao.deleteBookingAndUpdateTickets(SessionUtil.getCurrentUser().getId(), event.getId());

                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Booking Cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    close();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to cancel booking.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException ex) {
                                System.getLogger(eventCardController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                            }
                        }

                    } else if (card.bookDelete.getText().equals("Give Feedback")) {
                        FeedbackForm feedbackCard = new FeedbackForm();
                            feedbackCard.Title.setText(event.getTitle());

                        int result = JOptionPane.showConfirmDialog(
                                null,
                                feedbackCard,
                                "Submit Feedback",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                int selectedRating = Integer.parseInt((String) feedbackCard.rating.getSelectedItem());
                                String feedbackText = feedbackCard.feedBack.getText().trim();

                                // Optional: ignore placeholder text
                                if (feedbackText.equals("Your Feedback here")) {
                                    feedbackText = null;
                                }

                                // Now update the booking table
                                FeedbackDao dao = new FeedbackDao();
                                dao.updateBookingFeedback(SessionUtil.getCurrentUser().getId(), event.getId(), selectedRating, feedbackText);

                                JOptionPane.showMessageDialog(null, "Feedback submitted successfully.");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                
            } else {
                    if(SessionUtil.getCurrentUser().getStatus().equals("banned")){
                        JOptionPane.showMessageDialog(card, "You have been blocked and Cannot Book !");
                    }else{
                        eventDetails eventPanel = new eventDetails();

                        JDialog dialog = new JDialog((Frame) null, "Event Details", true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                        dialog.getContentPane().add(eventPanel);

                        EventDetailsController controller = new EventDetailsController(eventPanel, event, dialog);

                        dialog.pack();
                        dialog.setLocationRelativeTo(card);
                        dialog.setVisible(true);
                    }
                }
        }

    }
    
    }
    
}
