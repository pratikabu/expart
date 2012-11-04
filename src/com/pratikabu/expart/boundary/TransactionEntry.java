/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Transaction.java
 *
 * Created on May 29, 2010, 2:40:10 PM
 */

package com.pratikabu.expart.boundary;

import com.gmail.pratikabu.pls.components.ContentInfo;
import com.gmail.pratikabu.pls.components.TableInfo;
import com.gmail.pratikabu.pls.components.Transaction;
import com.gmail.pratikabu.pls.controller.ContentRegistryServer;
import com.gmail.pratikabu.pls.controller.DatabaseController;
import com.gmail.pratikabu.pls.controller.TransactionHandler;
import com.gmail.pratikabu.pls.exceptions.FieldNameNotFoundException;
import com.pratikabu.expart.components.AmountInfo;
import com.pratikabu.expart.components.CurrencyTextField;
import com.pratikabu.expart.components.TotalAmountInfo;
import com.pratikabu.expart.components.UserAmountDetail;
import com.pratikabu.expart.components.UsefulMethods;
import com.pratikabu.expart.searchBox.UserData;
import com.pratikabu.expart.searchBox.UserSearchDialog;
import java.awt.Color;
import java.awt.Point;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import newComponents.DateSelector;
import newComponents.searchBox.SearchableTextBox;

/**
 *
 * @author Blue
 */
public class TransactionEntry extends javax.swing.JDialog {
    private ArrayList<TotalAmountInfo> totals = new ArrayList<TotalAmountInfo>();
    
    private int tid = -1;
    private Date transDate = new Date();

    private ArrayList<UserAmountDetail> vectPayee = new ArrayList<UserAmountDetail>();
    private ArrayList<UserAmountDetail> vectReceiver = new ArrayList<UserAmountDetail>();

    private ContentInfo ci, ciRec;
    private ContentInfo ciPayee;

    /** Creates new form Transaction */
    private TransactionEntry(java.awt.Frame parent, int tid) {
        super(parent, false);
        initComponents();

        jTextField7.setText(UsefulMethods.toString(new Date(), UsefulMethods.SHORT));

        renderTransaction(tid);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addPayee(UserData ud){
        if(ud!=null){
            AmountInfo ai=PaymentBox.showPaymentBox(this);
            if(ai!=null){//ensure that amount is entered
                jTextField1.setText("");
                
                addPayee(ud, ai);
            }
        }
    }

    private void addPayee(UserData ud, AmountInfo ai){
        //check whether it exists previously
        boolean exists = false;
        for (UserAmountDetail pd : vectPayee) {
            if ((pd.getUserData().getUid() == ud.getUid()) && (pd.getAI().getPaymentMode() == ai.getPaymentMode())) {//if exists
                exists = true;
                //update amount
                pd.getAI().addAmount(ai.getAmount());
                break;
            }
        }

        if (!exists) //add new row it in table
            vectPayee.add(new UserAmountDetail(ud, ai));

        //rerender list
        renderPayeeList();

        calculateTotals();//render totals
    }

    private void renderPayeeList(){
        String[] data=new String[3];

        for(int i=jTable1.getRowCount()-1; i>=0; i--)
            ((DefaultTableModel)jTable1.getModel()).removeRow(i);

        for(UserAmountDetail pd : vectPayee){
            data[0] = pd.getUserData().getUName();
            data[1] = CurrencyTextField.formatCurrency(pd.getAI().getAmount());
            data[2] = UsefulMethods.getPaymentModeString(pd.getAI().getPaymentMode());

            ((DefaultTableModel) jTable1.getModel()).addRow(data);
        }
    }

    private void addReceiver(UserData ud){
        if(ud!=null){
            AmountInfo ai = null;

            if(jCheckBox1.isSelected())
                ai = PaymentBoxEqually.showPaymentBoxEqually(this, getRemainingAI());
            else
                ai = PaymentBox.showPaymentBox(this, getRemainingAI());

            if(ai != null){//ensure that amount is entered
                jTextField2.setText("");

                addReceiver(ud, ai);
            }
        }
    }

    private void addReceiver(UserData ud, AmountInfo ai) {
        //check whether it exists previously
        boolean exists = false;
        for (UserAmountDetail pd : vectReceiver) {
            if ((pd.getUserData().getUid() == ud.getUid()) && (pd.getAI().getPaymentMode() == ai.getPaymentMode())) { //if user exists
                exists = true;

                if(jCheckBox1.isSelected()) {
                    //do not add any amount, because there should not be any effect if the user is already added on the distribute equally
                } else {
                    //update amount
                    pd.getAI().addAmount(ai.getAmount());
                }
                break;
            }
        }

        if (!exists) { //add new row it in table
            vectReceiver.add(new UserAmountDetail(ud, ai));
            
            if(jCheckBox1.isSelected())//distribute equally if it is selected
                distributeEqually();
        }

        //show it in table
        renderReceiverList();

        calculateTotals();//render totals
    }

    private void renderReceiverList(){
        String[] data=new String[3];

        for(int i=jTable2.getRowCount()-1; i>=0; i--)
            ((DefaultTableModel)jTable2.getModel()).removeRow(i);

        for(UserAmountDetail pd : vectReceiver){
            data[0] = pd.getUserData().getUName();
            data[1] = CurrencyTextField.formatCurrency(pd.getAI().getAmount());
            data[2] = UsefulMethods.getPaymentModeString(pd.getAI().getPaymentMode());

            ((DefaultTableModel) jTable2.getModel()).addRow(data);
        }
    }

    public static int showVoucher(java.awt.Frame parent, int tid){
        return new TransactionEntry(parent, tid).tid;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTID = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lbDifference = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField2 = new SearchableTextBox(new UserSearchDialog(), 200){
            public void enterPressed(Object data){
                addReceiver((UserData)data);
            }
        };
        jButton9 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new SearchableTextBox(new UserSearchDialog(), 200){
            public void enterPressed(Object data){
                addPayee((UserData)data);
            }
        };
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lbTotalPayed = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbTotalRecieved = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaction");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        lbTID.setText("New");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("TID:");

        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField7FocusLost(evt);
            }
        });

        jLabel3.setText("Date:");

        lbDifference.setFont(new java.awt.Font("Tahoma", 1, 12));
        lbDifference.setForeground(new java.awt.Color(0, 0, 255));
        lbDifference.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDifference.setText("0.00");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("Gross Difference:");

        jButton1.setText("...");
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setMnemonic('C');
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButton3.setMnemonic('S');
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(350);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Receiver:"));

        jTextField2.setToolTipText("Type Reciever's Name");

        jButton9.setText("Edit");
        jButton9.setEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Receiver", "Amount", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jButton8.setText("Remove");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Distribute Equally");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Payee:"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payee", "Amount", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButton6.setText("Edit");
        jButton6.setEnabled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Remove");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextField1.setToolTipText("Type Payee's Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setDividerLocation(74);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel4.setText("Description:");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTextArea1.setRows(1);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel3);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment Mode", "Total Payed", "Total Received", "Difference"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel4);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel11.setText("Total Payed:");

        lbTotalPayed.setFont(new java.awt.Font("Tahoma", 1, 12));
        lbTotalPayed.setForeground(new java.awt.Color(0, 0, 255));
        lbTotalPayed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotalPayed.setText("0.00");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel12.setText("Total Received:");

        lbTotalRecieved.setFont(new java.awt.Font("Tahoma", 1, 12));
        lbTotalRecieved.setForeground(new java.awt.Color(0, 0, 255));
        lbTotalRecieved.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotalRecieved.setText("0.00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 487, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotalPayed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotalRecieved)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbDifference)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTID)
                    .addComponent(jLabel2)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lbTotalPayed)
                        .addComponent(jLabel12)
                        .addComponent(lbTotalRecieved)
                        .addComponent(jLabel8)
                        .addComponent(lbDifference)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        transDate=UsefulMethods.parseDate(jTextField7.getText());
        jTextField7.setText(UsefulMethods.toString(transDate, UsefulMethods.SHORT));
    }//GEN-LAST:event_jTextField7FocusLost

    private void jTextField7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusGained
        jTextField7.selectAll();
    }//GEN-LAST:event_jTextField7FocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(ensureCorrect())
            if(save())
                showVoucher(null, tid);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(checkPayerSelected()){
            //do the edit
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(checkPayerSelected()){
            int row = jTable1.getSelectedRow();
            vectPayee.remove(row);
            
            //rerender list
            renderPayeeList();

            calculateTotals();//render totals
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(checkReceiverSelected()){
            //edit receiver
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(checkReceiverSelected()){
            int row = jTable2.getSelectedRow();
            vectReceiver.remove(row);

            //rerender list
            renderReceiverList();

            calculateTotals();//render totals
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int x, y;
        Point r=this.jButton1.getLocationOnScreen();
        x=r.x;
        y=r.y+jButton1.getHeight();
        Date date=DateSelector.showDateSelector(null, transDate, x, y);
        if(date!=null)
            updateDate(date);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        jSplitPane1.setDividerLocation(0.5);
    }//GEN-LAST:event_formComponentResized

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        boolean selected = jCheckBox1.isSelected();
        if(selected){
            distributeEqually();

            renderReceiverList();
            calculateTotals();
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel lbDifference;
    private javax.swing.JLabel lbTID;
    private javax.swing.JLabel lbTotalPayed;
    private javax.swing.JLabel lbTotalRecieved;
    // End of variables declaration//GEN-END:variables

    private boolean checkPayerSelected(){
        boolean selected=jTable1.getSelectedRow()!=-1;
        if(!selected)
            JOptionPane.showMessageDialog(jPanel1, "Select any Payee.");
        return selected;
    }

    private boolean checkReceiverSelected(){
        boolean selected=jTable2.getSelectedRow()!=-1;
        if(!selected)
            JOptionPane.showMessageDialog(jPanel2, "Select any Receiver.");
        return selected;
    }

    private void renderTransaction(int tid) {
        this.tid=tid;
        
        ciRec=ContentRegistryServer.getContentInfo(new TableInfo("Receiver", null), null);
        ciPayee=ContentRegistryServer.getContentInfo(new TableInfo("Payee", null), null);

        if (tid == -1) {
            ci=ContentRegistryServer.getContentInfo(new TableInfo("Transaction", null), null);
        } else {
            try {
                lbTID.setText(tid + "");
                ci=ContentRegistryServer.getContentInfo(new TableInfo("Transaction", null), tid);
                
                updateDate((Date)ci.getFieldValue("tDate"));

                jTextArea1.setText(ci.getFieldValue("tDesc").toString());

                renderTransactionDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateDate(Date date){
        transDate = date;
        jTextField7.setText(UsefulMethods.toString(date, UsefulMethods.SHORT));
    }
    
    private boolean ensureCorrect(){
        boolean correct = true;
        StringBuilder msgs = new StringBuilder("");

        //date
        correct = transDate != null;

        if (!correct) {
            msgs.append("\n -Date is null.");
        }

        if (vectPayee.isEmpty() || vectReceiver.isEmpty()) {
            correct = false;
            msgs.append("\n -Either payee or receiver count is zero.");
        }

        //do a check for all of the summissions
        ArrayList<AmountInfo> remainingAI = getRemainingAI();
        if (remainingAI.isEmpty()) {
            correct = false;
            msgs.append("\n -There is no transaction done on this page.");
        } else {
            for (AmountInfo ai : remainingAI) {
                if (ai.getAmount() != 0.0) {
                    correct = false;
                    msgs.append("\n -The difference between Total Payee Amount and Total Received Amount is not 0(zero).");
                    break;
                }
            }
        }

        if (!correct) {

            JOptionPane.showMessageDialog(this, "These errors were found while saving:\n" + msgs.toString()
                    , "Eh", JOptionPane.ERROR_MESSAGE);
        }else{//confirm here
            int choice=JOptionPane.showConfirmDialog(this, "Now Saving. Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
            correct=choice==JOptionPane.YES_OPTION;
        }

        return correct;
    }

    private boolean save() {
        boolean saved=false;

        //create a transaction, add all transactions to this object and finally execute it.. :)
        TransactionHandler th=new TransactionHandler();

        if(ci.isIsNew()){
            tid=UsefulMethods.getNextID("tid", "Transaction");
            ci.setPrimaryKeyValue(tid);
        }else{//if old
            //remove all previous records of payee and receiver
            String sqlPayeeDelete = "DELETE FROM Payee WHERE ptid=?";
            String sqlReceiverDelete = "DELETE FROM Receiver WHERE rtid=?";

            th.addQuery(new Transaction(sqlPayeeDelete, new Object[]{tid}));
            th.addQuery(new Transaction(sqlReceiverDelete, new Object[]{tid}));
        }

        try{
            //transaction data
            ci.setFieldValue("tDate", transDate);
            ci.setFieldValue("tDesc", jTextArea1.getText());
            ci.setFieldValue("tProfile", UsefulMethods.profile.getPid());
            th.addQuery(ci.getTransaction());//add trans

            for (UserAmountDetail pd : vectPayee) {
                ciPayee.setFieldValue("ptid", tid);
                ciPayee.setFieldValue("puid", pd.getUserData().getUid());
                ciPayee.setFieldValue("pAmt", pd.getAI().getAmount());
                ciPayee.setFieldValue("pMode", pd.getAI().getPaymentMode());
                th.addQuery(ciPayee.getTransaction());
            }

            for (UserAmountDetail pd : vectReceiver) {
                ciRec.setFieldValue("rtid", tid);
                ciRec.setFieldValue("ruid", pd.getUserData().getUid());
                ciRec.setFieldValue("rAmt", pd.getAI().getAmount());
                ciRec.setFieldValue("rMode", pd.getAI().getPaymentMode());
                th.addQuery(ciRec.getTransaction());
            }

            saved=th.executeQuery();//execute transactions at once
            if(saved){
                this.tid = -1;//make it new
                this.dispose();
            }
        }catch(FieldNameNotFoundException e){
            e.printStackTrace();
        }

        if(!saved)
            JOptionPane.showMessageDialog(this, "Error Saving Data.\n\n", "Error", JOptionPane.ERROR_MESSAGE);

        return saved;
    }

    private ArrayList<AmountInfo> getRemainingAI() {
        ArrayList<AmountInfo> remainingAI = new ArrayList<AmountInfo>();

        for(TotalAmountInfo tai : totals){
            //fetch and save partition amount
            AmountInfo ai = new AmountInfo();
            ai.setAmount(tai.difference());
            ai.setPaymentMode(tai.getPaymentMode());
            remainingAI.add(ai);
        }
        
        return remainingAI;
    }

    private void calculateTotals() {
        totals = new ArrayList<TotalAmountInfo>();

        //add all payees data
        for (UserAmountDetail uad : vectPayee) {
            boolean pmExists = false; //variable for checking whether payment mode already exists in the TotalAmountAI
            for (TotalAmountInfo tai : totals) {
                if (tai.getPaymentMode() == uad.getAI().getPaymentMode()) {
                    pmExists = true;
                    tai.addPayee(uad.getAI().getAmount());

                    break;
                }
            }

            if (!pmExists) {
                TotalAmountInfo newTAI = new TotalAmountInfo();
                newTAI.setPaymentMode(uad.getAI().getPaymentMode());
                newTAI.setTotalPayed(uad.getAI().getAmount());

                totals.add(newTAI);
            }
        }

        //add all receiver data
        for (UserAmountDetail uad : vectReceiver) {
            boolean pmExists = false; //variable for checking whether payment mode already exists in the TotalAmountAI
            for (TotalAmountInfo tai : totals) {
                if (tai.getPaymentMode() == uad.getAI().getPaymentMode()) {
                    pmExists = true;
                    tai.addReceiver(uad.getAI().getAmount());
                    break;
                }
            }

            if (!pmExists) {
                TotalAmountInfo newTAI = new TotalAmountInfo();
                newTAI.setPaymentMode(uad.getAI().getPaymentMode());
                newTAI.setTotalReceived(uad.getAI().getAmount());

                totals.add(newTAI);
            }
        }

        renderTotals();
    }

    private void renderTotals() {
        String[] data = new String[4];
        double totalPayed = 0.00, totalReceived = 0.00, grossDifference = 0.0;

        for(int i=jTable3.getRowCount()-1; i>=0; i--)
            ((DefaultTableModel)jTable3.getModel()).removeRow(i);

        for(TotalAmountInfo tai : totals){
            data[0] = UsefulMethods.getPaymentModeString(tai.getPaymentMode());
            data[1] = CurrencyTextField.formatCurrency(tai.getTotalPayed());
            data[2] = CurrencyTextField.formatCurrency(tai.getTotalReceived());
            double diff = tai.difference();
            data[3] = CurrencyTextField.formatCurrency(diff);

            ((DefaultTableModel) jTable3.getModel()).addRow(data);

            //totals
            totalPayed += tai.getTotalPayed();
            totalReceived += tai.getTotalReceived();
            //add gross info
            grossDifference += diff;
        }

        lbTotalPayed.setText(CurrencyTextField.formatCurrency(totalPayed));
        lbTotalRecieved.setText(CurrencyTextField.formatCurrency(totalReceived));
        if(grossDifference == 0.00)
            lbDifference.setForeground(Color.BLUE);
        else
            lbDifference.setForeground(Color.RED);
        lbDifference.setText(CurrencyTextField.formatCurrency(grossDifference));
    }

    private void renderTransactionDetails() {
            String sqlPayee = "SELECT * "
                    + "FROM PayeeDetails "
                    + "WHERE ptid=?";
            String sqlRec = "SELECT * "
                    + "FROM ReceiverDetails "
                    + "WHERE rtid=?";

            DatabaseController dbc = new DatabaseController();
            ResultSet rset = dbc.getQueryResultSet(sqlPayee, new Object[]{tid});

            DatabaseController dbcRec = new DatabaseController();
            ResultSet rsetRec = dbcRec.getQueryResultSet(sqlRec, new Object[]{tid});

            try {
                while (rset.next()) {
                    AmountInfo ai = new AmountInfo();
                    ai.setPaymentMode(rset.getInt(3));
                    ai.setAmount(rset.getDouble(5));

                    addPayee(new UserData(rset.getInt(1), rset.getString(2)), ai);
                }

                while (rsetRec.next()) {
                    AmountInfo ai = new AmountInfo();
                    ai.setPaymentMode(rsetRec.getInt(3));
                    ai.setAmount(rsetRec.getDouble(5));

                    addReceiver(new UserData(rsetRec.getInt(1), rsetRec.getString(2)), ai);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error fetching data for Transaction Details.", "Eh..", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                dbc.closeConnection();
                dbcRec.closeConnection();
            }
    }

    private void distributeEqually() {
        for(TotalAmountInfo tai : totals){
            updateUserAmountInfoEqually(tai, getUserCount(tai.getPaymentMode()));
        }
    }

    private int getUserCount(int paymentMode) {
        int userCount = 0;
        for(UserAmountDetail uad : vectReceiver){
            if(uad.getAI().getPaymentMode() == paymentMode)
                userCount++;
        }

        return userCount;
    }

    private void updateUserAmountInfoEqually(TotalAmountInfo tai, int userCount) {
        if(userCount != 0)
            for(UserAmountDetail uad : vectReceiver) {
                if(uad.getAI().getPaymentMode() == tai.getPaymentMode())
                    uad.getAI().setAmount(tai.getTotalPayed() / userCount);
            }
    }
}
