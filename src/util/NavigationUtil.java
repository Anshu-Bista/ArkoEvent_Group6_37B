package util;

import Controller.*;
import View.*;
import Controller.*;


import model.UserData;

public class NavigationUtil {

    private static String getUserRole() {
        UserData currentUser = SessionUtil.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getRole();
        }
        return null;
    }

    public static void goToLogin() {
        Login loginView = new Login();
        LoginController loginController = new LoginController(loginView);
        loginController.open();
    }

    public static void goToSignUp() {
        Registration registrationView = new Registration();
        SignUpController signUpController = new SignUpController(registrationView);
        signUpController.open();
    }

    public static void goToDashboard() {
        String role = getUserRole();
        if ("admin".equalsIgnoreCase(role)) {
            AdminDashboard dashboardView = new AdminDashboard();
            DashboardController dashboardController = new DashboardController(dashboardView);
            dashboardController.open();
        } else if ("user".equalsIgnoreCase(role)) {
            
              userDashboard userDashboardView = new userDashboard();
             userDashboardController c = new
             userDashboardController(userDashboardView);
             c.open();
             
        } else {
            showAccessDenied();
        }
    }

    public static void goToProfile() {
        String role = getUserRole();
        if ("admin".equalsIgnoreCase(role)) {
            AdminProfile profileView = new AdminProfile();
            AdminProfileController profileController = new AdminProfileController(profileView);
            profileController.open();
        } else if ("user".equalsIgnoreCase(role)) {
            UserProfile userProfileView = new UserProfile();
            UserProfileController userProfileController = new UserProfileController(userProfileView);
            userProfileController.open();
        } else {
            showAccessDenied();
        }
    }

    public static void goToUsers() {
        String role = getUserRole();
        if ("admin".equalsIgnoreCase(role)) {
            ViewUsers userView = new ViewUsers();
            ViewAllUsersController userController = new ViewAllUsersController(userView);
            userController.open();
        } else {
            showAccessDenied();
        }
    }

    public static void goToCreateEvent() {
        String role = getUserRole();
        if ("admin".equalsIgnoreCase(role)) {
            CreateEvent createView = new CreateEvent();
            CreateEventController createController = new CreateEventController(createView);
            createController.open();
        } else {
            showAccessDenied();
        }
    }
    
    public static void goToMyEvents() {
        String role = getUserRole();
        if ("admin".equalsIgnoreCase(role)) {
            myEvents view = new myEvents();
            myEventsController myEvents = new myEventsController(view);
            myEvents.open();
        } else {
            showAccessDenied();
        }
    }
    
    public static void gotoMyBookings() {
        String role = getUserRole();
        if ("user".equalsIgnoreCase(role)) {
            myBookings view = new myBookings();
            myBookingsController c = new myBookingsController(view);
            c.open();
        } else {
            showAccessDenied();
        }
    }

    private static void showAccessDenied() {
        javax.swing.JOptionPane.showMessageDialog(null,
                "Access Denied: You do not have permission to access this page.");
    }
}