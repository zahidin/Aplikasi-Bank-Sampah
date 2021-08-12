/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksampah.utils;

import java.security.MessageDigest;
import javax.swing.JOptionPane;
import java.math.BigInteger;

/**
 *
 * @author zahid
 */
public class encrypt {
    
    /**
     *
     * @param param
     * @return
     */
    public static String hashMd5(String param){
       try{
           MessageDigest md = MessageDigest.getInstance("MD5");
           byte[] messageDigest = md.digest(param.getBytes());
           BigInteger no = new BigInteger(1, messageDigest);
           String hashtext = no.toString(16);
           while (hashtext.length() < 32){
               hashtext = "0" + hashtext;
           }
           return hashtext; 
       } catch(Exception e){
           throw new RuntimeException(e);
       }
   };
    
}
