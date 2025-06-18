/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Dao;

import Model.UserData;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Asus
 */
public class UserDaoTest {
  
    
    public UserDaoTest() {
    }
    
    @Test

    public void test(){
        assertEquals(2,2);
    }
    
    
   
    public static void setUpClass() {
    }
    
    
    public static void tearDownClass() {
    }
    
    
    public void setUp() {
    }
    
    
    public void tearDown() {
    }

    /**
     * Test of login method, of class UserDao.
     */
    
    public void testLogin() {
        System.out.println("login");
        UserData user = new UserData("batch37b","batch31","password");
        UserDao instance = new UserDao();
        instance.login(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
