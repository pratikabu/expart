/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BalanceView.java
 *
 * Created on Jun 5, 2010, 11:53:18 PM
 */

package com.pratikabu.expart.reports;

import com.gmail.pratikabu.pls.components.Transaction;
import com.gmail.pratikabu.pls.controller.DatabaseController;
import com.gmail.pratikabu.pls.controller.TransactionHandler;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Blue
 */
public class UsersView extends javax.swing.JDialog {
    private ArrayList<Integer> ids = new ArrayList<Integer>();

    /** Creates new form BalanceView */
    public UsersView(java.awt.Frame parent) {
        super(parent, false);
        initComponents();

        renderBalance();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Users View");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jButton3.setText("View");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jList1.getSelectedIndex();
        if (row != -1) {
            String sql = "SELECT COUNT(*) "
                    + "FROM Payee "
                    + "WHERE puid=?";
            String sqlRec = "SELECT COUNT(*) "
                    + "FROM Receiver "
                    + "WHERE ruid=?";

            DatabaseController dbc=new DatabaseController();
            ResultSet rset=dbc.getQueryResultSet(sql, new Object[]{ids.get(row)});

            DatabaseController dbcRec=new DatabaseController();
            ResultSet rsetRec=dbcRec.getQueryResultSet(sqlRec, new Object[]{ids.get(row)});

            try{
                if(rset.next()){
                    rsetRec.next();

                    int count = rset.getInt(1) + rsetRec.getInt(1);

                    if(count > 0)
                        JOptionPane.showMessageDialog(this, "You cannot delete this user as there are\n"
                                + "transactions saved for this user.\n"
                                + "Remove them first.", "Cannot Remove", JOptionPane.ERROR_MESSAGE);
                    else{
                        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this User?", "Remvoing", JOptionPane.YES_NO_OPTION);

                        if(choice == JOptionPane.YES_OPTION){
                            //do remove profile
                            TransactionHandler th = new TransactionHandler();
                            th.addQuery(new Transaction("DELETE FROM User "
                                    + "WHERE uid=?", new Object[]{ids.get(row)}));

                            if(th.executeQuery()){
                                this.dispose();
                                new UsersView(null);
                            }
                        }
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error Removing.", "Eh..", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }finally{
                dbc.closeConnection();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = jList1.getSelectedIndex();
        if (row != -1) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void renderBalance() {
        String sql="SELECT * "
                + "FROM User "
                + "ORDER BY uName";

        DatabaseController dbc=new DatabaseController();
        ResultSet rset=dbc.getQueryResultSet(sql, new Object[]{});

        try{
            Vector<String> data = new Vector<String>();
            while(rset.next()){
                ids.add(rset.getInt(1));
                //display all records
                data.add(rset.getString(2));
            }

            jList1.setListData(data);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error fetching data.", "Eh..", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally{
            dbc.closeConnection();
        }
    }

}
