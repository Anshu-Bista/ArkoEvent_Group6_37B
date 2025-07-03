/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author thismac
 */

public class Booker extends UserData {
    private int ticketCount;

    public Booker() {
        super();
    }

    public Booker(int id, String imagePath, String username, String email, String phone, String status, int ticketCount) {
        super(); // calling no-arg constructor
        this.setId(id);
        this.setImagePath(imagePath);
        this.setUsername(username);
        this.setEmail(email);
        this.setPhone(phone);
        this.setStatus(status);
        this.ticketCount = ticketCount;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
