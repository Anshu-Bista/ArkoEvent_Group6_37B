/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Booking;
import View.myBookingCard;
import View.myBookings;
import dao.BookingDao;
import java.util.ArrayList;
import util.NavigationUtil;
import util.SessionUtil;

/**
 *
 * @author thismac
 */
public class myBookingsController {
    private final myBookings myBooking;
    private final BookingDao dao = new BookingDao();
    
    
    public myBookingsController(myBookings myBooking){
        this.myBooking = myBooking;
        getSetBookings();
        
        this.myBooking.addHomeListener(e -> {
            myBooking.dispose();
            NavigationUtil.goToDashboard();
        });

        this.myBooking.addProfileListener(e -> {
            myBooking.dispose();
            NavigationUtil.goToProfile();
        });

        
    }
    
    public void open(){
        this.myBooking.setVisible(true);
    }
    
    public void close(){
        this.myBooking.setVisible(false);
    }
    
    public void getSetBookings(){
        ArrayList<Booking> bookings =dao.getBookingsByUser(SessionUtil.getCurrentUser().getId());
        
        myBooking.eventsList.removeAll(); // Clear previous cards
         int i = 1;
        for (Booking booking : bookings) {
            System.out.println(booking.getEventTitle());
            myBookingCard card = new myBookingCard(Integer.toString(i)+". "+booking.getEventTitle(), booking.getTicketCount());
            card.setVisible(true);
            myBooking.eventsList.add(card);
            i++;
        }

        myBooking.eventsList.revalidate();
        myBooking.eventsList.repaint();
    }
    
}
