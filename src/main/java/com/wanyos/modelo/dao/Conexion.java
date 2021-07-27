
package com.wanyos.modelo.dao;

import java.awt.Frame;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author wanyos
 */
public class Conexion {
    
    private Connection cx;
    private final String NOMBRE_BD = "emt";
    
    public Conexion() throws SQLException {
        cx = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + NOMBRE_BD + "?serverTimezone=UTC", "wanyos", "1712@fll");
    }
    
    
    public Connection getConexion(){
        return cx;
    }
    
    
    public static void initServer(){
        try {
            Runtime.getRuntime().exec("C:\\xampp\\mysql_start.bat", new String[]{ "argumento" });
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void stopServer() {
        try {
            Process p = new ProcessBuilder("C:\\xampp\\mysql_stop.bat", "argumento").start();
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
}
