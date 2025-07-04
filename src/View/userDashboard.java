/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.Color;
import java.awt.event.ActionListener;

/**
 *
 * @author thismac
 */
public class userDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(userDashboard.class.getName());

    /**
     * Creates new form userDashboard
     */
    public userDashboard() {
        initComponents();
        eventsList.setLayout(new javax.swing.BoxLayout(eventsList, javax.swing.BoxLayout.Y_AXIS));
        eventsList.setPreferredSize(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        myBookings = new javax.swing.JButton();
        homebtn = new javax.swing.JButton();
        profilebtn = new javax.swing.JButton();
        logout_btn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventsList = new javax.swing.JPanel();
        ticketType = new javax.swing.JComboBox<>();
        search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(208, 202, 232));
        sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        myBookings.setBackground(new java.awt.Color(208, 202, 232));
        myBookings.setText("My Bookings");
        sidebar.add(myBookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 200, 40));

        homebtn.setBackground(new java.awt.Color(153, 153, 255));
        homebtn.setText("Home");
        sidebar.add(homebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 200, 40));

        profilebtn.setBackground(new java.awt.Color(208, 202, 232));
        profilebtn.setText("Profile");
        sidebar.add(profilebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 200, 40));

        logout_btn.setBackground(new java.awt.Color(208, 202, 232));
        logout_btn.setText("Log Out");
        sidebar.add(logout_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 220, 40));

        bg.add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -2, 256, 630));

        jPanel1.setBackground(new java.awt.Color(229, 179, 208));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Home");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(524, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 0, 700, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        eventsList.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout eventsListLayout = new javax.swing.GroupLayout(eventsList);
        eventsList.setLayout(eventsListLayout);
        eventsListLayout.setHorizontalGroup(
            eventsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        eventsListLayout.setVerticalGroup(
            eventsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(eventsList);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 84, -1, 550));

        ticketType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Paid", "Free" }));
        bg.add(ticketType, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 80, -1));

        search.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        search.setForeground(java.awt.Color.gray);
        search.setText("search");
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFocusLost(evt);
            }
        });
        bg.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 221, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
        if(search.getText().equals("search")){
            search.setText("");
            search.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_searchFocusGained

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        if(search.getText().isEmpty()){
            search.setText("search");
            search.setForeground(Color.gray);
        }
    }//GEN-LAST:event_searchFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new userDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    public javax.swing.JPanel eventsList;
    private javax.swing.JButton homebtn;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logout_btn;
    private javax.swing.JButton myBookings;
    private javax.swing.JButton profilebtn;
    public javax.swing.JTextField search;
    private javax.swing.JPanel sidebar;
    public javax.swing.JComboBox<String> ticketType;
    // End of variables declaration//GEN-END:variables
    public void filterListener(ActionListener listener){
        ticketType.addActionListener(listener);
    }
    
    public javax.swing.JTextField getSearchField() {
        return  search;
    }
    
    public void addMyEventsListener(ActionListener listener) {
        myBookings.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logout_btn.addActionListener(listener);
    }

    public void addProfileListener(ActionListener listener) {
        profilebtn.addActionListener(listener);
    }

    public void addHomeListener(ActionListener listener) {
        homebtn.addActionListener(listener);
    }
}
