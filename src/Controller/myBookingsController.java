/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
import View.eventCard;
import View.myBookingCard;
import View.myBookings;
import dao.BookingDao;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Box;
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
        this.myBooking.addLogoutListener(e -> SessionUtil.logout(myBooking));
        try {
            getSetBookings();
        } catch (SQLException ex) {
            System.getLogger(myBookingsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
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
    
    public void getSetBookings() throws SQLException{
        ArrayList<EventData> bookedEvents =dao.getUserBookings();
        
        myBooking.eventsList.removeAll(); // Clear previous cards
        
        for (EventData event : bookedEvents) {
            eventCard eventPanel = new eventCard();
            new eventCardController(eventPanel, event,true);
             myBooking.eventsList.add(eventPanel);
             myBooking.eventsList.add(Box.createVerticalStrut(10));
        }

        myBooking.eventsList.revalidate();
        myBooking.eventsList.repaint();
    }
    
}
