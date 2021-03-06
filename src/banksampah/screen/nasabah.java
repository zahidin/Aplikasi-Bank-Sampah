/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksampah.screen;

import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class nasabah extends javax.swing.JInternalFrame {
    DefaultTableModel tabel = new DefaultTableModel();
    /**
     * Creates new form nasabah
     */
    public nasabah() {
        initComponents();
        this.loadData();
        this.init();
    }
    
    private void init(){
        inputName.setText("");
        inputUsername.setText("");
        inputPassword.setText("");
        inputAddress.setText("");
        inputTelp.setText("");
        inputUsername.setEnabled((true));
        btnSubmit.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void loadData(){
        tabel.getDataVector().removeAllElements();
        tabel.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT no_account as 'No Account', name as 'Nama', address as 'Alamat', telp as 'Telp' FROM users WHERE role = 'NASABAH';    ";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableUser.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        inputName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        inputUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputAddress = new javax.swing.JTextArea();
        inputTelp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        inputPassword = new javax.swing.JPasswordField();
        btnSubmit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        btnReport = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Management Nasabah");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Nasabah"));

        jLabel1.setText("Nama ");

        jLabel2.setText("Username");

        inputUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputUsernameActionPerformed(evt);
            }
        });

        jLabel3.setText("Password");

        jLabel4.setText("Alamat");

        inputAddress.setColumns(20);
        inputAddress.setRows(5);
        jScrollPane1.setViewportView(inputAddress);

        jLabel5.setText("Telp");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear2.setText("Clear");
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClear2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addComponent(inputPassword)
                    .addComponent(inputUsername)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(inputTelp)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {inputName, inputPassword, inputTelp, inputUsername, jScrollPane1});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClear2, btnDelete, btnSubmit, btnUpdate});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClear2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear2, btnDelete, btnSubmit, btnUpdate});

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Table Nasabah"));

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No.", "Nama", "Alamat", "Telp"
            }
        ));
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableUser);
        if (tableUser.getColumnModel().getColumnCount() > 0) {
            tableUser.getColumnModel().getColumn(0).setHeaderValue("No.");
            tableUser.getColumnModel().getColumn(1).setHeaderValue("Nama");
            tableUser.getColumnModel().getColumn(2).setHeaderValue("Alamat");
            tableUser.getColumnModel().getColumn(3).setHeaderValue("Telp");
        }

        btnReport.setText("Cetak Laporan");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        this.insertNasabah();
        this.loadData();
        this.init();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void inputUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputUsernameActionPerformed
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(inputName.getText().equals("")|| inputAddress.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong", "Pemberitahuan", JOptionPane.WARNING_MESSAGE);
        }else{
            this.updateNasabah();
        }
        this.loadData();
        this.init();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked

    this.click();        // TODO add your handling code here:
    }//GEN-LAST:event_tableUserMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        try {
            this.deleteNasabah();
            this.loadData();
            this.init();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang dipilih");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
        this.init();
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        // TODO add your handling code here:
        this.report();
    }//GEN-LAST:event_btnReportActionPerformed
     
    private void report() {
        try {
                    String namafile = "src/banksampah/report/nasabah.jasper";
                    File file = new File(namafile);
                    JasperReport jasper = (JasperReport) JRLoader.loadObject(file.getPath());
                    JasperPrint print = JasperFillManager.fillReport(jasper, null, banksampah.connection.connection.connectDb());
                    JasperViewer.viewReport(print,false);
                }catch (JRException ex) {
                    System.out.println("Error Laporan : " + ex);
                } catch (SQLException ex) {
            Logger.getLogger(nasabah.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void click(){
        try{
            String noAccount = tableUser.getValueAt(tableUser.getSelectedRow(), 0).toString();

            java.sql.Connection conn = (Connection) banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet user = stm.executeQuery("SELECT * FROM users WHERE no_account = " + noAccount + "");
               
            if (user.next()){
                inputName.setText(user.getString("name"));
                inputUsername.setText(user.getString("username"));
                inputAddress.setText(user.getString("address"));
                inputPassword.setText(user.getString("password"));
                inputTelp.setText(user.getString("telp"));
                
                inputUsername.setEnabled(false);
                btnSubmit.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
      
    }
    
    private void insertNasabah(){
        
        String name = inputName.getText();
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String telp = inputTelp.getText();
        String address = inputAddress.getText();
        String passwordHash = banksampah.utils.encrypt.hashMd5(password);

        Random rnd = new Random();
        int noAccount = rnd.nextInt(999999);
        
        try{
            Connection conn = (Connection) banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet checkUsername = stm.executeQuery(String.format("SELECT * FROM users WHERE username = '%s'",username));
            
            if(checkUsername.next()){          
                JOptionPane.showMessageDialog(null, "Username telah digunakan");
            }else {
                String query = String.format("INSERT INTO users (name, username, password, telp, address, role, no_account) VALUES ('%s','%s','%s','%s','%s','NASABAH','%010d');", name,username,passwordHash,telp,address,noAccount);
                stm.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Berhasil Menambahkan Nasabah", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
         JOptionPane.showMessageDialog(this, "Gagal Memasukkan Data !", "Peringatan", JOptionPane.ERROR_MESSAGE);
         System.out.println(e.getMessage());
         
        } 
    }
    
    private void updateNasabah(){
        try{
            String name = inputName.getText();
            String username = inputUsername.getText();
            String password = inputPassword.getText();
            String address = inputAddress.getText();
            String telp = inputTelp.getText();
            
            String passwordHash = banksampah.utils.encrypt.hashMd5(password);
            
            Connection conn = (Connection) banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String query = String.format("UPDATE users SET name='%s', password='%s', address='%s', telp='%s' WHERE username = '%s'",name,passwordHash,address,telp,username);
            com.mysql.jdbc.PreparedStatement prepare = (PreparedStatement)conn.prepareStatement(query);
            prepare.execute();
            JOptionPane.showMessageDialog(this, "Berhasil mengubah data nasabah", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            btnSubmit.setEnabled(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Gagal Mengubah Data !", "Peringatan", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        
    }
    
    private void deleteNasabah(){
        int s_row = tableUser.getSelectedRow();
        if(s_row==-1){
            return;
        }
        String noAccount = (String) tableUser.getValueAt(s_row,0);
        try{
            Connection conn = (Connection)banksampah.connection.connection.connectDb();
            String querry = "DELETE FROM users WHERE no_account=?";
            java.sql.PreparedStatement stm = (PreparedStatement)conn.prepareStatement(querry);
            stm.setString(1,noAccount);
            stm.executeUpdate();
            stm.close();
            JOptionPane.showMessageDialog(this, "Berhasil menghapus data nasabah", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            btnSubmit.setEnabled(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Tidak bisa menghapus data", "Hapus Data", JOptionPane.WARNING_MESSAGE);
        } finally {
            this.loadData();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear2;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextArea inputAddress;
    private javax.swing.JTextField inputName;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JTextField inputTelp;
    private javax.swing.JTextField inputUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableUser;
    // End of variables declaration//GEN-END:variables
}
