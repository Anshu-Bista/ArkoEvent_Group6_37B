package Controller;

import Model.EventData;
import Model.Feedback;
import Model.UserData;
import dao.EventDao;
import dao.FeedbackDao;
import dao.UserDao;

import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import java.util.List;

import util.NavigationUtil;
import util.SessionUtil;

public class DashboardController {

    private final AdminDashboard dashboardView;
    private final UserDao userDao;
    private final EventDao eventDao;
    private final FeedbackDao feedbackDao;
    ArrayList<EventData> events = null;

    public DashboardController(AdminDashboard dashboardView) {
        this.dashboardView = dashboardView;
        this.userDao = new UserDao();
        this.eventDao = new EventDao();
        this.feedbackDao = new FeedbackDao();
        events = eventDao.getEventsByUserId(SessionUtil.getCurrentUser().getId());

        this.dashboardView.addLogoutListener(e -> SessionUtil.logout(dashboardView));

        this.dashboardView.addProfileListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToProfile();
        });

        this.dashboardView.addCreateEventListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToCreateEvent();
        });

        this.dashboardView.addHomeListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToDashboard();
        });

        this.dashboardView.addMyEventsListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToMyEvents();
        });

        this.dashboardView.addBookingListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.gotoBookings();
        });

        this.dashboardView.addUserListener(new showUsers());
        this.dashboardView.addFeedBackListener(new showFeedbacks());
        this.dashboardView.eventSelectorListener(new eventSelected());

        if (events.isEmpty()) {
            JOptionPane.showMessageDialog(dashboardView, "No Events");
            loadUserCards();
        } else {
            dashboardView.eventSelector.removeAllItems();
            for (EventData event : events) {
                dashboardView.eventSelector.addItem(event.getTitle());
            }
            this.dashboardView.eventSelector.setSelectedIndex(0);

        }

        loadDashboardCounts();
        loadUserCards();

    }

    private void loadDashboardCounts() {
        int totalUsers = userDao.getUserCount();

        dashboardView.user_btn.setText("Users: " + Integer.toString(totalUsers));
    }

    private void loadFeedbackCards() {
        int selectedIndex = dashboardView.eventSelector.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= events.size()) {
            return; // Or show a message/log, but avoid accessing invalid index
        }

        int eId = events.get(selectedIndex).getId();
        try {
            ArrayList<Feedback> Feedbacks = feedbackDao.getFeedbacksByEventId(eId);
            dashboardView.listPanel.removeAll();

            for (Feedback feedback : Feedbacks) {
                FeedBackCard card = new FeedBackCard(feedback);
                dashboardView.listPanel.add(card);
                dashboardView.listPanel.add(Box.createVerticalStrut(10));
            }

            dashboardView.listPanel.revalidate();
            dashboardView.listPanel.repaint();
        } catch (SQLException ex) {
            System.getLogger(DashboardController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void loadUserCards() {

        dashboardView.eventSelector.setVisible(false);
        ArrayList<UserData> users = userDao.getUsersOnly();
        dashboardView.listPanel.removeAll();

        for (UserData user : users) {
            UserCard card = new UserCard(user);

            dashboardView.listPanel.add(card);
            dashboardView.listPanel.add(Box.createVerticalStrut(10));
        }

        dashboardView.listPanel.revalidate();
        dashboardView.listPanel.repaint();

    }

    public void open() {
        dashboardView.setVisible(true);
    }

    public void close() {
        dashboardView.dispose();
    }

    private class eventSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loadFeedbackCards();
        }

    }

    private class showFeedbacks implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.eventSelector.setVisible(true);
            loadFeedbackCards();
        }

    }

    private class showUsers implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loadUserCards();
        }
    }
}
