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
public class garbageOut extends javax.swing.JInternalFrame {
    DefaultTableModel tabel = new DefaultTableModel();
    DefaultTableModel tabel1 = new DefaultTableModel();
    DefaultTableModel tabel2 = new DefaultTableModel();

    int price, qty, idCategory, idMerchant, totalPrice = 0;
    String noTransaction, totalAmount = "";

    /**
     * Creates new form garbageOut
     */
    public garbageOut() {
        initComponents();
        this.loadData();
        this.init();
    }
    
    private void init(){
        inputCategory.setText("");
        inputMerchant.setText("");
        inputQty.setText("");
        labelPrice.setText("Rp. 0");
       
        inputCategory.setEnabled(false);
        inputMerchant.setEnabled(false);
        btnSubmit.setEnabled(false);
    }

    
     private void loadData(){
        tabel.getDataVector().removeAllElements();
        tabel.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT transaction_trash_sales.no_transaction as 'No Transaksi', category_trash.name as 'Jenis Sampah', transaction_trash_sales.qty as 'Kg', transaction_trash_sales.amount as 'Harga', DATE_FORMAT(transaction_trash_sales.created_at, '%d-%m-%Y') as 'Tanggal Transaksi' from category_trash INNER JOIN transaction_trash_sales on category_trash.id_category = transaction_trash_sales.id_category;";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableGarbageOut.setModel(DbUtils.resultSetToTableModel(rs));
//            tableGarbageOut.getColumn("Nama").setMinWidth(0);
//            tableGarbageOut.getColumn("Nama").setMaxWidth(0);
//            tableGarbageOut.getColumn("Nama").setWidth(0);
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
        }
    }
     
    private void loadDataCategory(){
        tabel1.getDataVector().removeAllElements();
        tabel1.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT category_trash.id_category as 'id',category_trash.code as \"Kode\", category_trash.name  as \"Nama\", stock_trash.qty as 'Stock (kg)', category_trash.price_sell as \"Harga\" from category_trash INNER JOIN stock_trash ON category_trash.id_category = stock_trash.id_category;";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableCategory.setModel(DbUtils.resultSetToTableModel(rs));
            tableCategory.getColumn("id").setMinWidth(0);
            tableCategory.getColumn("id").setMaxWidth(0);
            tableCategory.getColumn("id").setWidth(0);
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
        }
    }
    
    private void loadDataMerchant(){
        tabel2.getDataVector().removeAllElements();
        tabel2.fireTableDataChanged();
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = "SELECT id_user as 'id', no_account as 'No Account', name as 'Nama', address as 'Alamat', telp as 'Telp' FROM users WHERE role = 'MERCHANT';";
            java.sql.ResultSet rs = stm.executeQuery(querry);
            tableMerchant.setModel(DbUtils.resultSetToTableModel(rs));
            tableMerchant.getColumn("id").setMinWidth(0);
            tableMerchant.getColumn("id").setMaxWidth(0);
            tableMerchant.getColumn("id").setWidth(0);
        }catch(Exception e){
            System.out.println("Error Load Data : " + e);
        }
    }
    
    private void insertGarbageOut(){
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(999999);
        String qty = inputQty.getText();
        
        String noTransaction = String.format("TRX-%s", randomNumber);

        try{
            Connection conn = (Connection) banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String query = String.format("INSERT INTO transaction_trash_sales (no_transaction, id_category, qty, amount, id_user) VALUES ('%s','%s','%s','%s','%s');", noTransaction, this.idCategory, qty, this.totalPrice, this.idMerchant);
            stm.executeUpdate(query);
            this.saveStock(this.idCategory, Integer.parseInt(qty));
            stm.close();

            JOptionPane.showMessageDialog(this, "Berhasil Menambahkan Transaksi Keluar", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
         JOptionPane.showMessageDialog(this, "Gagal Memasukkan Data !", "Peringatan", JOptionPane.ERROR_MESSAGE);
         System.out.println(e.getMessage());
         
        } 
    }
     
    private void saveStock(int idCategory, int qty){
        try{
            java.sql.Connection conn = (Connection)banksampah.connection.connection.connectDb();
            java.sql.Statement stm = conn.createStatement();
            String querry = String.format("SELECT * FROM stock_trash WHERE id_category = %s ;", idCategory);
            java.sql.ResultSet rs = stm.executeQuery(querry);
            if(rs.next()){
                int totalQty = rs.getInt("qty") - qty;
                stm.executeUpdate(String.format("UPDATE stock_trash SET qty=%s WHERE id_category = %s ;", totalQty, idCategory));
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
                String namafile = "src/banksampah/report/garbageOut.jasper";
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
        String terbilang = banksampah.utils.terbilang.createWords(new BigDecimal(this.totalAmount)) + "Rupiah";
        
        if(this.noTransaction == ""){
            JOptionPane.showMessageDialog(this, "Pilih data ditable terlebih dahulu", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else{
            try {  
                    String namafile = "src/banksampah/report/receiptOut.jasper";
                    HashMap hash = new HashMap();
                    hash.put("noTransaction", this.noTransaction);
                    hash.put("terbilang", terbilang);
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

        dialogCategory = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCategory = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        dialogDateChoose = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        startDate = new com.toedter.calendar.JDateChooser();
        btnCetak = new javax.swing.JButton();
        dialogMerchant = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMerchant = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inputCategory = new javax.swing.JTextField();
        btnSearchCategory = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        inputQty = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        labelPrice = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        inputMerchant = new javax.swing.JTextField();
        btnSearchCategory1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGarbageOut = new javax.swing.JTable();
        btnReport = new javax.swing.JButton();
        btnReport1 = new javax.swing.JButton();

        dialogCategory.setSize(new java.awt.Dimension(400, 350));

        tableCategory.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableCategory);

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogCategoryLayout = new javax.swing.GroupLayout(dialogCategory.getContentPane());
        dialogCategory.getContentPane().setLayout(dialogCategoryLayout);
        dialogCategoryLayout.setHorizontalGroup(
            dialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogCategoryLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        dialogCategoryLayout.setVerticalGroup(
            dialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
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

        dialogMerchant.setSize(new java.awt.Dimension(400, 350));

        tableMerchant.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableMerchant);

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

        javax.swing.GroupLayout dialogMerchantLayout = new javax.swing.GroupLayout(dialogMerchant.getContentPane());
        dialogMerchant.getContentPane().setLayout(dialogMerchantLayout);
        dialogMerchantLayout.setHorizontalGroup(
            dialogMerchantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogMerchantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogMerchantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogMerchantLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        dialogMerchantLayout.setVerticalGroup(
            dialogMerchantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogMerchantLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogMerchantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sampah Keluar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Sampah Keluar"));

        jLabel2.setText("Pilih Jenis Sampah");

        inputCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCategoryActionPerformed(evt);
            }
        });

        btnSearchCategory.setText("Cari");
        btnSearchCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCategoryActionPerformed(evt);
            }
        });

        jLabel3.setText("Berat (kg)");

        inputQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputQtyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputQtyFocusLost(evt);
            }
        });
        inputQty.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                inputQtyInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        inputQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputQtyActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel4.setText("Harga");

        labelPrice.setFont(new java.awt.Font("Lucida Grande", 2, 14)); // NOI18N
        labelPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPrice.setText("Rp. 5.000.000");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel8.setText("Pilih Pengepul");

        inputMerchant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputMerchantActionPerformed(evt);
            }
        });

        btnSearchCategory1.setText("Cari");
        btnSearchCategory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCategory1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(inputCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearchCategory))
                    .addComponent(jLabel2)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(inputQty, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(labelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(inputMerchant, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearchCategory1))
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputMerchant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchCategory1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPrice))
                .addGap(18, 18, 18)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnSubmit});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Table Sampah Keluar"));

        tableGarbageOut.setModel(new javax.swing.table.DefaultTableModel(
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
        tableGarbageOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGarbageOutMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableGarbageOut);

        btnReport.setText("Cetak Struk Pembayaran");
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReport1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputCategoryActionPerformed

    private void btnSearchCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCategoryActionPerformed
        // TODO add your handling code here:
        dialogCategory.setLocationRelativeTo(null);
        this.loadDataCategory();
        dialogCategory.setVisible(true);
    }//GEN-LAST:event_btnSearchCategoryActionPerformed

    private void inputQtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputQtyFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_inputQtyFocusGained

    private void inputQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputQtyFocusLost
//         TODO add your handling code here:
        String qty = inputQty.getText();
        
        if(Integer.parseInt(qty) > this.qty){
            JOptionPane.showMessageDialog(this, "Stock melebihi batas!", "Peringatan", JOptionPane.ERROR_MESSAGE);
        } else {
            int total = Integer.parseInt(qty) * this.price;
            this.totalPrice = total;
            DecimalFormat idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            idr.setDecimalFormatSymbols(formatRp);

            labelPrice.setText(String.format("%s %n", idr.format(total)));
            btnSubmit.setEnabled(true);
        }
    }//GEN-LAST:event_inputQtyFocusLost

    private void inputQtyInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_inputQtyInputMethodTextChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_inputQtyInputMethodTextChanged

    private void inputQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputQtyActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        this.insertGarbageOut();
        this.loadData();
        this.init();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.init();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        // TODO add your handling code here:
       this.receipt();
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReport1ActionPerformed
        // TODO add your handling code here:
        dialogDateChoose.setLocationRelativeTo(null);
        dialogDateChoose.setVisible(true);
    }//GEN-LAST:event_btnReport1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String id = tableCategory.getValueAt(tableCategory.getSelectedRow(), 0).toString();
        String code = tableCategory.getValueAt(tableCategory.getSelectedRow(), 1).toString();
        String price = tableCategory.getValueAt(tableCategory.getSelectedRow(), 4).toString();
        String qty = tableCategory.getValueAt(tableCategory.getSelectedRow(), 3).toString();
        
        this.price = Integer.parseInt(price);
        this.idCategory = Integer.parseInt(id); 
        this.qty = Integer.parseInt(qty);

        inputCategory.setText(code);
        dialogCategory.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dialogCategory.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:

        this.report();
        dialogDateChoose.setVisible(false);
    }//GEN-LAST:event_btnCetakActionPerformed

    private void inputMerchantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputMerchantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputMerchantActionPerformed

    private void btnSearchCategory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCategory1ActionPerformed
        // TODO add your handling code here:
        
        dialogMerchant.setLocationRelativeTo(null);
        this.loadDataMerchant();
        dialogMerchant.setVisible(true);
    }//GEN-LAST:event_btnSearchCategory1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String id = tableMerchant.getValueAt(tableMerchant.getSelectedRow(), 0).toString();
        String name = tableMerchant.getValueAt(tableMerchant.getSelectedRow(), 2).toString();
        
        this.idMerchant = Integer.parseInt(id); 

        inputMerchant.setText(name);
        dialogMerchant.setVisible(false);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dialogMerchant.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tableGarbageOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGarbageOutMouseClicked
        // TODO add your handling code here:
        this.noTransaction = tableGarbageOut.getValueAt(tableGarbageOut.getSelectedRow(), 0).toString();   
        this.totalAmount = tableGarbageOut.getValueAt(tableGarbageOut.getSelectedRow(), 3).toString();

    }//GEN-LAST:event_tableGarbageOutMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnReport1;
    private javax.swing.JButton btnSearchCategory;
    private javax.swing.JButton btnSearchCategory1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JDialog dialogCategory;
    private javax.swing.JDialog dialogDateChoose;
    private javax.swing.JDialog dialogMerchant;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JTextField inputCategory;
    private javax.swing.JTextField inputMerchant;
    private javax.swing.JTextField inputQty;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelPrice;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTable tableCategory;
    private javax.swing.JTable tableGarbageOut;
    private javax.swing.JTable tableMerchant;
    // End of variables declaration//GEN-END:variables
}
