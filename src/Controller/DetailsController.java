package controller;

import javax.swing.JOptionPane;

import model.EventData;

public class EventDetailsController {
    private final EventDao eventDao = new EventDao();
    private final BookingDao bookingDao = new BookingDao;
    private final EventDetails eventDetailsView;
    private final userId;
    private final eventId;

    public EventDetailsController(EventDetailsView eventDetailsView) {
        this.eventDetailsView = eventDetailsView;

        eventDetailsView.addViewEventDetailsListener(new ShowEventDetailsListener());
        eventDetailsView.addBookEventListener(new BookEventListener());
    }

    public void open() {
        this.eventDetailsView.setVisible(true);
    }

    public void close(){
        this.eventDetailsView.dispose();
    }

    class ShowEventDetailsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                EventData event = eventDao.getEventById(eventId);
                if (event == null){
                    JOptionPane.showMessageDialog(null, "Event not found");
                }
                else{
                    eventDetailsView.displayEventDetails(event);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error loading events " + ex.getMessage());
            }
        }
    }

    class BookEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                if(bookingDao.userBooked(eventId, userId)){
                    JOptionPane.showMessageDialog(null, "You have already booked this event");
                    return;
                }
                boolean booked = bookingDao.bookEvent(eventId, userId);
                if (booked){
                    JOptionPane.showMessageDialog(null, "Event booked successfully!");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Booking failed. Please try again. ");
                }
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error during booking "+ ex.getMessage());
            }
        }
    }
}