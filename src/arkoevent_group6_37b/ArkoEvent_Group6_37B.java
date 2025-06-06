package arkoevent_group6_37b;

import controller.CreateEventController;
import view.CreateEvent;

public class ArkoEvent_Group6_37B {
    public static void main(String[] args) {
        CreateEvent view = new CreateEvent();
        CreateEventController controller = new CreateEventController(view);
        controller.open();
    }
}
