/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProfileSelect.java
 *
 * Created on 3 Oct, 2010, 4:18:07 PM
 */

package com.pratikabu.expart.boundary;

import com.gmail.pratikabu.pls.controller.DatabaseController;
import com.pratikabu.expart.components.ProfileInfo;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Pratik
 */
public class ProfileSelection extends javax.swing.JDialog {
    private ArrayList<Integer> ids = new ArrayList<Integer>();
    private ArrayList<String> arrNames = new ArrayList<String>();

    /** Creates new form ProfileSelect */
    public ProfileSelection(java.awt.Frame parent) {
        super(parent, false);
        initComponents();

        fetchAndSaveAllPaymentModes();
        setLocationRelativeTo(null);

        this.getRootPane().setDefaultButton(jButton2);

        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Profile");
        setResizable(false);

        jButton1.setText("Add New Profile");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Select Profile:");

        jLabel2.setText("Select a profile to continue the ExPart or create a new one.");

        jButton2.setText("Next >");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 244, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jComboBox1.getSelectedIndex();
        if(row != -1){
            ProfileInfo profile = new ProfileInfo();
            profile.setPid(ids.get(row));
            profile.setpName(arrNames.get(row));

            this.dispose();
            new MainWindow(profile);
        }else
            JOptionPane.showMessageDialog(this, "Kindly select any profile.\n"
                    + "You can click on Add New Profile button and create a new profile and profile.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        ProfileEntry.showVoucher(null, -1);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception e){}
                new ProfileSelection(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    private void fetchAndSaveAllPaymentModes() {
        String sql="SELECT * " +
                "FROM Profiles";

        DatabaseController dbc=new DatabaseController();
        ResultSet rset=dbc.getQueryResultSet(sql, new Object[]{});

        try{
            while(rset.next()){
                ids.add(rset.getInt(1));
                arrNames.add(rset.getString(2));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error fetching Payment Types from Database.", "Eh..", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally{
            dbc.closeConnection();
        }

        //add it to the combo box
        this.jComboBox1.removeAllItems();//remove all first
        //now add new ones
        for(String pmName : arrNames)
            this.jComboBox1.addItem(pmName);
    }
}
