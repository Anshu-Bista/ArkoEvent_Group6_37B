package controller;

public class EventListController {
    private final EventDao eventDao = new EventDao();
    private final EventList eventListView;
    
    public EventListController(EventList eventListView){
        this.eventListView = eventListView;

        eventListView.addLoadEventsListener(new LoadEventsListener());
    }

    public void open(){
        this.eventListView.setVisible(true);
    }
    
    public void close(){
        this.eventListView.dispose;
    }

    class LoadEventsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            List<EventData> events = eventDao.getAllEvents();
            eventListView.displayEvents(events);
        }
    }
}
