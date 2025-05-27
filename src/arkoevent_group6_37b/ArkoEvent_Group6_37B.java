/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arkoevent_group6_37b;

import Controller.signUpController;
import View.Registration;

/**
 *
 * @author hp
 */
public class ArkoEvent_Group6_37B {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Registration registerView = new Registration();
       signUpController c = new signUpController(registerView);
       c.open();
    }
    
}
