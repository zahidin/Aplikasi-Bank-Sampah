/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksampah.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author zahid
 */
public class connection {
    
    private static Connection dbConnect;
    
    public static Connection connectDb() throws SQLException{
        if(dbConnect == null){
            try{
                String host = "jdbc:mysql://localhost:3306/bank_sampah";
                String username = "root";
                String password = "root123";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                dbConnect = DriverManager.getConnection(host, username, password);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke Database", "Peringatan", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        return dbConnect;
    }
}
