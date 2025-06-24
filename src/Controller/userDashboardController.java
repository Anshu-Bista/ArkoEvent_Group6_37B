/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
import View.*;
import dao.EventDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import util.SessionUtil;

/**
 *
 * @author thismac
 */
public class userDashboardController {
    private final userDashboard dash;
    public javax.swing.JPanel eventsContainer;
    public final EventDao dao = new EventDao();
    
    public userDashboardController(userDashboard dash){
        this.dash = dash;
        this.dash.filterListener(new showFiltered());
        setCards();
        
    }
    public void open(){
        this.dash.setVisible(true);
    }
    
    public void close(){
        this.dash.setVisible(false);
    }
    
    public void setCards(){
        EventDao dao = new EventDao();
        ArrayList<EventData> events = dao.getEventsByTicketType(dash.ticketType.getSelectedItem().toString());
        
        eventsContainer = dash.eventsList;
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

    private  class showFiltered implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setCards();
        }

        
    }
    
}
