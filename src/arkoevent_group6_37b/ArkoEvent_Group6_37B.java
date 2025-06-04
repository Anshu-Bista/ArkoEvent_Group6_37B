package arkoevent_group6_37b;

import View.Reset;
import controller.PasswordUpdateController;

public class ArkoEvent_Group6_37B {

    public static void main(String[] args) {
        // Example username - this would typically come from a login screen
        String username = "testUser";

        // Create the Reset view (UI)
        Reset resetView = new Reset(username);

        // Connect the controller (optional - depends on your structure)
        PasswordUpdateController controller = new PasswordUpdateController(resetView);

        // Open the UI window
        controller.open();
    }
}
