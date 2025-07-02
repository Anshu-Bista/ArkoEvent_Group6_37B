/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
import View.Bookings;
import View.eventCard;
import View.myEvents;
import dao.EventDao;
import java.util.ArrayList;
import javax.swing.Box;
import util.NavigationUtil;
import util.SessionUtil;

/**
 *
 * @author thismac
 */
public class BookingsController {
    private final Bookings bookingsPage;
    public javax.swing.JPanel eventsContainer;
    
    public BookingsController(Bookings bookings){
        System.out.println("inBookingsControllee");
        this.bookingsPage = bookings;
        
        this.bookingsPage.addLogoutListener(e -> SessionUtil.logout(bookingsPage));
      
        this.bookingsPage.addProfileListener(e -> {
            bookingsPage.dispose();
            NavigationUtil.goToProfile();
        });

        this.bookingsPage.addCreateEventListener(e -> {
            bookingsPage.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.bookingsPage.addHomeListener(e -> {
            bookingsPage.dispose();
            NavigationUtil.goToDashboard(); // Treat dashboard as home
        });

        this.bookingsPage.addMyEventsListener(e -> {
            bookingsPage.dispose();
            NavigationUtil.goToMyEvents();
        });
        
        this.bookingsPage.addBookingListener(e -> {
            bookingsPage.dispose();
            NavigationUtil.goToMyEvents();
        });
        
        setCards();
    }
    
    public void open(){
        this.bookingsPage.setVisible(true);
    }
    
    public void close(){
        this.bookingsPage.setVisible(false);
    }
    
    public void setCards(){
        EventDao dao = new EventDao();
        
        ArrayList<EventData> bookedEvents = dao.getBookedEventsByUserId(SessionUtil.getCurrentUser().getId());
        
        bookingsPage.eventsList.removeAll(); // Clear previous cards
        
        for (EventData event : bookedEvents) {
            eventCard eventPanel = new eventCard();
            new eventCardController(eventPanel, event,true);
             bookingsPage.eventsList.add(eventPanel);
             bookingsPage.eventsList.add(Box.createVerticalStrut(10));
        }

        bookingsPage.eventsList.revalidate();
        bookingsPage.eventsList.repaint();
        
    
    }
    
    
}
