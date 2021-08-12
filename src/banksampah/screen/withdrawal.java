/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksampah.screen;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author zahid
 */
public class withdrawal extends javax.swing.JInternalFrame {
    DefaultTableModel tabel = new DefaultTableModel();
    DefaultTableModel tabel2 = new DefaultTableModel();
    int price, idUser, saldo = 0;
    String noTransaction, totalAmount = "";
    /**
     * Creates new form withdrawal
     */
    public withdrawal() {
        initComponents();
        this.loadData();
        this.init();
    }
    
    private void init(){
        inputAmount.setText("");
        inputNoAccount.setText("");
        labelAmount.setText("Rp. 0");
       
        inputNoAccount.setEnabled(false);
        btnSubmit.setEnabled(false);
    }
    
     private void loadData(){
        tabel.getDataVector().removeAllElements();
        tabel.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT withdrawal.no_transaction as \"No Transaksi\", users.name  as \"Nama\", users.no_account as 'No Account', withdrawal.amount as \"Penarikan\", users.saldo  as \"Saldo Awal\" from withdrawal INNER JOIN users ON withdrawal.id_user = users.id_user ;";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableWithdrawal.setModel(DbUtils.resultSetToTableModel(rs));
            tableWithdrawal.getColumn("Saldo Awal").setMinWidth(0);
            tableWithdrawal.getColumn("Saldo Awal").setMaxWidth(0);
            tableWithdrawal.getColumn("Saldo Awal").setWidth(0);
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
        }
    }
     
    private void loadDataUser(){
        tabel2.getDataVector().removeAllElements();
        tabel2.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT id_user as 'id', no_account as 'No Account', name as 'Nama', address as 'Alamat', telp as 'Telp', saldo FROM users WHERE role = 'NASABAH';";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableUser.setModel(DbUtils.resultSetToTableModel(rs));
            tableUser.getColumn("id").setMinWidth(0);
            tableUser.getColumn("id").setMaxWidth(0);
            tableUser.getColumn("id").setWidth(0);
            
            tableUser.getColumn("saldo").setMinWidth(0);
            tableUser.getColumn("saldo").setMaxWidth(0);
            tableUser.getColumn("saldo").setWidth(0);
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
        }
    }
    
    private void insertWithdrawal(){
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(999999);
        String amount = inputAmount.getText();
        
        String noTransaction = String.format("TRX-%s", randomNumber);

        try{
            Connection conn = (Connection) banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String query = String.format("INSERT INTO withdrawal (no_transaction, id_user, amount) VALUES ('%s','%s','%s');", noTransaction, this.idUser, Integer.parseInt(amount));
            stm.executeUpdate(query);
            this.saveSaldo(this.idUser);
            stm.close();

            JOptionPane.showMessageDialog(this, "Berhasil Melakukkan Penarikkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
         JOptionPane.showMessageDialog(this, "Gagal Memasukkan Data !", "Peringatan", JOptionPane.ERROR_MESSAGE);
         System.out.println(e.getMessage());
         
        } 
    }
    
    private void saveSaldo(int idUser){
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = String.format("SELECT * FROM users WHERE id_user = %s ;", idUser);
            java.sql.ResultSet rs = stm.executeQuery(querry);
            if(rs.next()){
                int totalSaldo = rs.getInt("saldo") - Integer.parseInt(inputAmount.getText()) ;
                stm.executeUpdate(String.format("UPDATE users SET saldo=%s WHERE id_user= %s ;", totalSaldo, idUser));
            }else{
                JOptionPane.showMessageDialog(this, "Data User Tidak Ditemukkan !", "Peringatan", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(this, "Gagal Memasukkan Data !", "Peringatan", JOptionPane.ERROR_MESSAGE);
             System.out.println(e.getMessage());
        }
    }
    
    private void report() {
        try {
                Date start = startDate.getDate();
                Date end = endDate.getDate();
                String namafile = "src/banksampah/report/withdrawal.jasper";
                HashMap hash = new HashMap();
                hash.put("startDate", start);
                hash.put("endDate", end);
                File file = new File(namafile);
                JasperReport jasper = (JasperReport) JRLoader.loadObject(file.getPath());
                JasperPrint print = JasperFillManager.fillReport(jasper, hash, banksampah.connection.connection.connectDb());
                JasperViewer.viewReport(print,false);
            }catch (JRException ex) {
                System.out.println("Error Laporan : " + ex);
            } catch (SQLException ex) {
            Logger.getLogger(nasabah.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void receipt() {
        String terbilangWords = banksampah.utils.terbilang.createWords(new BigDecimal(Integer.parseInt(this.totalAmount))) + "Rupiah";

        if(this.noTransaction == ""){
            JOptionPane.showMessageDialog(this, "Pilih data ditable terlebih dahulu", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else{
            try {  
                    String namafile = "src/banksampah/report/receiptWithdrawal.jasper";
                    HashMap hash = new HashMap();
                    hash.put("noTransaction", this.noTransaction);
                    hash.put("terbilang", terbilangWords);
                    File file = new File(namafile);
                    JasperReport jasper = (JasperReport) JRLoader.loadObject(file.getPath());
                    JasperPrint print = JasperFillManager.fillReport(jasper, hash, banksampah.connection.connection.connectDb());
                    JasperViewer.viewReport(print,false);
                }catch (JRException ex) {
                    System.out.println("Error Laporan : " + ex);
                } catch (SQLException ex) {
                Logger.getLogger(nasabah.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogUser = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        dialogDateChoose = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        startDate = new com.toedter.calendar.JDateChooser();
        btnCetak = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        inputAmount = new javax.swing.JTextField();
        btnSearchUser = new javax.swing.JButton();
        inputNoAccount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        labelAmount = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWithdrawal = new javax.swing.JTable();
        btnReport = new javax.swing.JButton();
        btnReport1 = new javax.swing.JButton();

        dialogUser.setSize(new java.awt.Dimension(450, 350));

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableUser);

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogUserLayout = new javax.swing.GroupLayout(dialogUser.getContentPane());
        dialogUser.getContentPane().setLayout(dialogUserLayout);
        dialogUserLayout.setHorizontalGroup(
            dialogUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogUserLayout.createSequentialGroup()
                .addGroup(dialogUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogUserLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        dialogUserLayout.setVerticalGroup(
            dialogUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogDateChoose.setSize(new java.awt.Dimension(300, 320));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("PILIH TANGGAL AWAL DAN AKHIR");

        jLabel6.setText("Tanggal Awal");

        jLabel7.setText("Tanggal Akhir");

        btnCetak.setText("Cetak Laporan");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogDateChooseLayout = new javax.swing.GroupLayout(dialogDateChoose.getContentPane());
        dialogDateChoose.getContentPane().setLayout(dialogDateChooseLayout);
        dialogDateChooseLayout.setHorizontalGroup(
            dialogDateChooseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogDateChooseLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(dialogDateChooseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogDateChooseLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dialogDateChooseLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogDateChooseLayout.createSequentialGroup()
                        .addGroup(dialogDateChooseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(endDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42))))
        );
        dialogDateChooseLayout.setVerticalGroup(
            dialogDateChooseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogDateChooseLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Penarikan Dana");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Withdrawal"));

        jLabel1.setText("No Account");

        inputAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputAmountFocusLost(evt);
            }
        });
        inputAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputAmountActionPerformed(evt);
            }
        });

        btnSearchUser.setText("Cari");
        btnSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchUserActionPerformed(evt);
            }
        });

        inputNoAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNoAccountActionPerformed(evt);
            }
        });

        jLabel2.setText("Penarikan");

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel4.setText("Sisa Saldo");

        labelAmount.setFont(new java.awt.Font("Lucida Grande", 2, 14)); // NOI18N
        labelAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAmount.setText("Rp. 5.000.000");
        labelAmount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(inputAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(inputNoAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSearchUser)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchUser)
                    .addComponent(inputNoAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Table Withdrawal"));

        tableWithdrawal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableWithdrawal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableWithdrawalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableWithdrawal);

        btnReport.setText("Cetak Pembayaran");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnReport1.setText("Cetak Laporan");
        btnReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReport1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReport1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputAmountActionPerformed

    private void btnSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchUserActionPerformed
        // TODO add your handling code here:
        dialogUser.setLocationRelativeTo(null);
        this.loadDataUser();
        dialogUser.setVisible(true);
    }//GEN-LAST:event_btnSearchUserActionPerformed

    private void inputNoAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNoAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNoAccountActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
                this.init();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        this.insertWithdrawal();
        this.loadData();
        this.init();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        // TODO add your handling code here:
        this.receipt();
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReport1ActionPerformed
        // TODO add your handling code here:
        dialogDateChoose.setLocationRelativeTo(null);
        dialogDateChoose.setVisible(true);
    }//GEN-LAST:event_btnReport1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String id = tableUser.getValueAt(tableUser.getSelectedRow(), 0).toString();
        String noAccount = tableUser.getValueAt(tableUser.getSelectedRow(), 1).toString();
        String saldo = tableUser.getValueAt(tableUser.getSelectedRow(), 5).toString();
        this.idUser = Integer.parseInt(id);
        this.saldo = Integer.parseInt(saldo);

        inputNoAccount.setText(noAccount);
        
        dialogUser.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dialogUser.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void inputAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputAmountFocusLost
        // TODO add your handling code here:
        String amount = inputAmount.getText();
        int total = this.saldo - Integer.parseInt(amount);
        
        if(this.saldo < total){
            inputAmount.setText("");
            JOptionPane.showMessageDialog(this, "Saldo Tidak Mencukupi Untuk Penarikan", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else{
            DecimalFormat idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
         
            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            idr.setDecimalFormatSymbols(formatRp);

            labelAmount.setText(String.format("%s %n", idr.format(total)));
            btnSubmit.setEnabled(true);
        }
    }//GEN-LAST:event_inputAmountFocusLost

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        this.report();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void tableWithdrawalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableWithdrawalMouseClicked
        this.noTransaction = tableWithdrawal.getValueAt(tableWithdrawal.getSelectedRow(), 0).toString();
        String withdraw = tableWithdrawal.getValueAt(tableWithdrawal.getSelectedRow(), 3).toString();
        String saldo = tableWithdrawal.getValueAt(tableWithdrawal.getSelectedRow(), 4).toString();
        int total = (Integer.parseInt(saldo) + Integer.parseInt(withdraw)) - Integer.parseInt(withdraw);
        this.totalAmount = Integer.toString(total);
    }//GEN-LAST:event_tableWithdrawalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnReport1;
    private javax.swing.JButton btnSearchUser;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JDialog dialogDateChoose;
    private javax.swing.JDialog dialogUser;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JTextField inputAmount;
    private javax.swing.JTextField inputNoAccount;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAmount;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTable tableUser;
    private javax.swing.JTable tableWithdrawal;
    // End of variables declaration//GEN-END:variables
}
