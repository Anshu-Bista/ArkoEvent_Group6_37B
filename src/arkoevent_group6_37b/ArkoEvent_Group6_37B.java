package arkoevent_group6_37b;

//import controller.CreateEventController;

//import controller.DashboardController;
//import view.AdminDashboard;

//import controller.ViewAllUsersController;
//import controller.AdminProfileController;
//import dao.UserDao;
//import model.UserData;
//import util.SessionUtil;
//import view.AdminProfile;
//import view.CreateEvent;
//import view.ViewUsers;
import view.Login;
import controller.LoginController;

public class ArkoEvent_Group6_37B {/*
    public static void main(String[] args) {
        AdminDashboard dashboardView = new AdminDashboard();
        DashboardController c= new DashboardController(dashboardView);
        c.open();
        
    }*/

    
    /*
     * public static void main(String[] args) {
     * ViewUsers view = new ViewUsers();
     * ViewAllUsersController controller = new ViewAllUsersController(view);
     * controller.open();
     * }
     */
    /*
     * public static void main(String[] args) {
     * CreateEvent view = new CreateEvent();
     * CreateEventController controller = new CreateEventController(view);
     * controller.open();
     * }
     */
    /*
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        int userId = 12; // Replace with actual logged-in user ID

        // Fetch user from DB
        UserData currentUser = userDao.getProfileById(userId);
        if (currentUser == null) {
            System.out.println("User not found");
            return;
        }

        // Set current user in session
        SessionUtil.setCurrentUser(currentUser);

        // Create view and controller, pass user info
        AdminProfile view = new AdminProfile();
        AdminProfileController controller = new AdminProfileController(view);

        controller.setUserDao(userDao);
        controller.setUserId(userId);
        controller.open();
    }*/


    public static void main(String[] args) {
       
        Login form = new Login();
        LoginController c = new LoginController(form);
        c.open();
    }

}