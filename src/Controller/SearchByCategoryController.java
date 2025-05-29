package controller;

import model.EventDao;
import model.EventData;
import view.SearchCategoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchByCategoryController {
    private final SearchCategoryView view;
    private final EventDao eventDao;

    public SearchByCategoryController(SearchCategoryView view, EventDao eventDao) {
        this.view = view;
        this.eventDao = eventDao;

        // Attach action listener to search button
        this.view.addSearchButtonListener(new SearchListener());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String category = view.getSelectedCategory();

                if (category == null || category.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please select or enter a category.");
                    return;
                }

                List<EventData> events = eventDao.getEventsByCategory(category);    //db

                if (events.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No events found for category: " + category);
                }

                view.displayEvents(events);  //view 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error searching events: " + ex.getMessage());
            }
        }
    }
}
