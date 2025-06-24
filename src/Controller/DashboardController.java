package Controller;

import dao.EventDao;
import dao.FeedbackDao;
import dao.UserDao;
import model.FeedbackData;
import View.*;


import javax.swing.*;
import java.util.List;

import util.NavigationUtil;
import util.SessionUtil;

public class DashboardController {
    private final AdminDashboard dashboardView;
    private final UserDao userDao;
    private final EventDao eventDao;
    private final FeedbackDao feedbackDao;

    public DashboardController(AdminDashboard dashboardView) {
        this.dashboardView = dashboardView;
        this.userDao = new UserDao();
        this.eventDao = new EventDao();
        this.feedbackDao = new FeedbackDao();
        
        this.dashboardView.addUserListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToUsers();
        });

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
            NavigationUtil.goToDashboard(); // Treat dashboard as home
        });

        this.dashboardView.addMyEventsListener(e -> {
            this.dashboardView.dispose();
            NavigationUtil.goToMyEvents();
        });



        /*
        this.dashboardView.addEventListener(e -> {
            dashboardView.dispose();
            NavigationUtil.goToEvents();
        });

        this.dashboardView.addBookingListener(e -> {
            dashboardView.dispose();
            NavigationUtil.goToBookings();
        });

        */
        
        this.dashboardView.addLogoutListener(e->SessionUtil.logout(dashboardView));

        loadDashboardCounts();

        loadFeedbackCards();

    }

    private void loadDashboardCounts() {
        int totalUsers = userDao.getUserCount();
        int totalEvents = eventDao.getEventCount();

        dashboardView.updateDashboardCounts(totalUsers, totalEvents);
    }

    private void loadFeedbackCards() {
        List<FeedbackData> feedbackList = feedbackDao.getAllFeedbacks();
        JPanel feedbackPanel = dashboardView.getFeedbackPanel();

        feedbackPanel.removeAll(); // Clear existing cards

        for (FeedbackData feedback : feedbackList) {
            FeedbackCard card = new FeedbackCard();
            card.setFeedback(feedback);
            feedbackPanel.add(card);
        }

        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    public void open() {
        dashboardView.setVisible(true);
    }

    public void close() {
        dashboardView.dispose();
    }
}
