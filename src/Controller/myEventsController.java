/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
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
public class myEventsController {
    private final myEvents MyEvents;
    public javax.swing.JPanel eventsContainer;
    
    public myEventsController(myEvents MyEvents){
        this.MyEvents = MyEvents;
        
        this.MyEvents.addLogoutListener(e -> SessionUtil.logout(MyEvents));
      
        this.MyEvents.addProfileListener(e -> {
            MyEvents.dispose();
            NavigationUtil.goToProfile();
        });

        this.MyEvents.addCreateEventListener(e -> {
            MyEvents.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.MyEvents.addHomeListener(e -> {
            MyEvents.dispose();
            NavigationUtil.goToDashboard(); // Treat dashboard as home
        });

        this.MyEvents.addMyEventsListener(e -> {
            MyEvents.dispose();
            NavigationUtil.goToMyEvents();
        });
        
        this.MyEvents.addBookingListener(e -> {
            MyEvents.dispose();
            NavigationUtil.gotoBookings();
        });
        
        
        setCards();
       
    }
    
    public void open(){
        this.MyEvents.setVisible(true);
    }
    
    public void close(){
        this.MyEvents.setVisible(false);
    }
    
    public void setCards(){
        EventDao dao = new EventDao();
        ArrayList<EventData> events = dao.getEventsByUserId(SessionUtil.getCurrentUser().getId());
        eventsContainer = MyEvents.eventsList;
        eventsContainer.removeAll();

        for (EventData event : events) {
            eventCard eventPanel = new eventCard();
            new eventCardController(eventPanel, event);
            eventsContainer.add(eventPanel);
            eventsContainer.add(Box.createVerticalStrut(10));
        }

        eventsContainer.revalidate();
        eventsContainer.repaint();
        
    
    }
    
    
}
