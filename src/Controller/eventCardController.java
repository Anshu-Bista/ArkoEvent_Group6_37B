/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EventData;
import View.eventCard;
import dao.EventDao;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import util.SessionUtil;

/**
 *
 * @author thismac
 */
public class eventCardController {
    private final eventCard card;
    private final EventData event;
    private final String role;
    
    public eventCardController(eventCard card,EventData event){
        this.card =  card;
        this.event = event;
        this.card.bookDeleteListener(new bookDelete());
        
        role = SessionUtil.getCurrentUser().getRole();
        setValues();
    }
    
    public void open(){
        card.setVisible(true);
    }
    
    public void close(){
        card.setVisible(false);
    }
    
    public void setValues(){
        String path = event.getBanner();
        if (path != null && !path.isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(path);
                Image originalImage = originalIcon.getImage();

                // Resize image to 140x140
                Image scaledImage = originalImage.getScaledInstance(190, 172, Image.SCALE_SMOOTH);

                // Set the scaled image as icon
                card.banner.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
        
        card.title.setText(event.getTitle());
        card.Location.setText(event.getLocation());
        card.date.setText(event.getEventDate().toString());
        card.time.setText(event.getStartTime().toString()+"-"+event.getEndTime());
        card.Description.setText(event.getDescription());
        card.price.setText("NPR "+Double.toString(event.getPrice()));
        card.left.setText(Integer.toString(event.getTicketsAvailable()-event.getTicketsSold())+ " Left");
        if(role.equals("admin")){
            card.bookDelete.setText("Delete");
        }else{
            card.bookDelete.setText("Book Now");
        }
    }

    private  class bookDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(role.equals("admin")){
                int choice = JOptionPane.showConfirmDialog(
                        card,
                        "Are you sure you want to delete this event?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    EventDao dao = new EventDao();
                    boolean success = dao.deleteEvent(event);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Event deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete event.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        }

    }
    
    
    
}
