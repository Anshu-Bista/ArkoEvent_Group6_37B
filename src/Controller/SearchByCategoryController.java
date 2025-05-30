package controller;

import model.EventDao;
import model.EventData;
import view.EventList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchByCategoryController {
    private final EventList eventListView; // Renamed from 'view' to 'eventListView'
    private final EventDao eventDao;

    public SearchByCategoryController(EventList eventListView, EventDao eventDao) {
        this.eventListView = eventListView;
        this.eventDao = eventDao;

        // Attach action listener to the search button in EventList
        this.eventListView.addSearchButtonListener(new SearchListener());
    }

    public void open() {
        this.eventListView.setVisible(true);
    }

    public void close() {
        this.eventListView.dispose();
    }

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String category = eventListView.getSelectedCategory();

                if (category == null || category.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(eventListView, "Please select or enter a category.");
                    return;
                }

                List<EventData> events = eventDao.getEventsByCategory(category); // DB layer

                if (events.isEmpty()) {
                    JOptionPane.showMessageDialog(eventListView, "No events found for category: " + category);
                }

                eventListView.displayEvents(events); // Show results in EventList page
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(eventListView, "Error searching events: " + ex.getMessage());
            }
        }
    }
}
