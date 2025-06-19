package arkoevent_group6_37b;

//import controller.CreateEventController;
//import controller.ViewAllUsersController;
import controller.ProfileController;
import dao.UserDao;
import view.AdminProfile;
//import view.CreateEvent;
//import view.ViewUsers;

public class ArkoEvent_Group6_37B {
    /*public static void main(String[] args) {
        ViewUsers view =  new ViewUsers();
        ViewAllUsersController controller = new ViewAllUsersController(view);
        controller.open();
    }*/
    /*public static void main(String[] args) {
        CreateEvent view = new CreateEvent();
        CreateEventController controller = new CreateEventController(view);
        controller.open();
    }*/
    
    public static void main(String[] args) {
        AdminProfile view = new AdminProfile();
        ProfileController controller = new ProfileController(view);
        UserDao userDao = new UserDao();
        int userId = 1; // Replace with actual logged-in user ID

        controller.setUserDao(userDao);
        controller.setUserId(userId);
        controller.open();
    }

}
