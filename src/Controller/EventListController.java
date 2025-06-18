package Controller;
import View.EventCard;
import View.EventList;
import java.util.List;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.EventDao;
import java.awt.Event;
import model.EventData;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class EventListController {
    private final EventDao eventDao = new EventDao();
    private final EventList eventListView;

    public EventListController(EventList eventListView) {
        this.eventListView = eventListView;

        eventListView.addLoadEventsListener(new LoadEventsListener());
    }

    public void open() {
        this.eventListView.setVisible(true);
    }

    public void close() {
        this.eventListView.dispose();
    }

    private void loadEvents() {
        try {
            List<Event> events = eventDao.getAllEvents();
            JPanel panel = eventListView.getEventPanel();
            panel.removeAll();

            for (Event event : events) {
                EventCard card = new EventCard();
                card.setEvent(event);
                panel.add(card);
            }
            panel.revalidate(); // refresh panel layout
            panel.repaint(); // repaint to visualize changes

            if (events.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No events available");
            } else {
                eventListView.displayEvents(events); // view
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading events" + ex.getMessage());
        }
    }

    class LoadEventsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadEvents(); // Refresh events
        }
    }
    
}
