package movierentals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import movierentals.MovieRentalsDatabase;
import movierentals.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marko
 */
public class ConnectionGUI extends javax.swing.JFrame {
    
    private static User user;
    private static ConnectionGUI connGUI;

    /**
     * Creates new form ConnectionGUI
     */
    public ConnectionGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        wb_name = new javax.swing.JLabel();
        wb_pass = new javax.swing.JLabel();
        server_name = new javax.swing.JLabel();
        server_port = new javax.swing.JLabel();
        db_name = new javax.swing.JLabel();
        wb_pass_txt = new javax.swing.JTextField();
        wb_name_txt = new javax.swing.JTextField();
        serv_name_txt = new javax.swing.JTextField();
        port_num_txt = new javax.swing.JTextField();
        db_conn_title = new javax.swing.JLabel();
        db_name_txt = new javax.swing.JTextField();
        conn_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        wb_name.setText("Server name:");

        wb_pass.setText("Server password:");
        wb_pass.setToolTipText("");

        server_name.setText("Connection name:");

        server_port.setText("Port number:");

        db_name.setText("Database name:");

        db_conn_title.setText("Connecting to the database");

        db_name_txt.setText("movie_rentals");

        conn_btn.setText("Connect");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wb_name)
                            .addComponent(wb_pass)
                            .addComponent(server_name)
                            .addComponent(server_port)
                            .addComponent(db_name))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(wb_name_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(wb_pass_txt)
                            .addComponent(serv_name_txt)
                            .addComponent(port_num_txt)
                            .addComponent(db_name_txt)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(db_conn_title))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(conn_btn)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(db_conn_title)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wb_name)
                    .addComponent(wb_name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wb_pass)
                    .addComponent(wb_pass_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(server_name)
                    .addComponent(serv_name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(server_port)
                    .addComponent(port_num_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(db_name)
                    .addComponent(db_name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(conn_btn)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton conn_btn;
    private javax.swing.JLabel db_conn_title;
    private javax.swing.JLabel db_name;
    public static javax.swing.JTextField db_name_txt;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JTextField port_num_txt;
    public static javax.swing.JTextField serv_name_txt;
    private javax.swing.JLabel server_name;
    private javax.swing.JLabel server_port;
    private javax.swing.JLabel wb_name;
    public static javax.swing.JTextField wb_name_txt;
    private javax.swing.JLabel wb_pass;
    public static javax.swing.JTextField wb_pass_txt;
    // End of variables declaration//GEN-END:variables
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        connGUI = new ConnectionGUI();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                connGUI.setVisible(true);
                connGUI.setLocationRelativeTo(null);
            }
        });
        
        conn_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MovieRentalsDatabase myDatabase = new MovieRentalsDatabase(ConnectionGUI.wb_name_txt.getText(), wb_pass_txt.getText(), serv_name_txt.getText(), port_num_txt.getText(), db_name_txt.getText());
                if (myDatabase.connect()) {
                    user = new User(myDatabase);
                    LoginGUI.main(myDatabase);
                    connGUI.setVisible(false);
                    connGUI.dispose();
                }else{
                    JFrame jfwrong = new JFrame();
                    JPanel jpwrong = new JPanel();
                    JLabel jlwrong = new JLabel("Wrong info entered");
                    jpwrong.add(jlwrong);
                    jfwrong.add(jpwrong);
                    jfwrong.setVisible(true);
                    jfwrong.setLocationRelativeTo(null);
                    jfwrong.pack();
                }
                
            }
        });
        
    }
    
    public static User getUser() {
        return user;
    }
    
    public static ConnectionGUI getFrame(){
        return connGUI;
    }
    
}
