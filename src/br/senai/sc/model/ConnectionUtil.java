package br.senai.sc.model;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;


/**
 *
 * @author jayson.melo
 */
public class ConnectionUtil {

    private static java.sql.Connection con;
    
    public static java.sql.Connection getConnection(){
        try { 
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/masterplayer";            
            con = DriverManager.getConnection(url,"root","server");
            con.setAutoCommit(false);
            return con;
        } catch ( ClassNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Problema de conexao com o banco");
        } catch (CommunicationsException e) {
        	e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problema de conexao com o banco");
        } catch ( SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Problema de conexao com o banco");
        
		}
        return null;
    }
    
    public static void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
