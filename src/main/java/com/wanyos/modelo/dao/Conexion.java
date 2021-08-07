
package com.wanyos.modelo.dao;

import com.wanyos.vista.InitApp;
import java.awt.Frame;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author wanyos
 */
public class Conexion {
    
    private static Connection cx;
    private static Conexion conexion;
    private final String NOMBRE_BD = "emt";
    private static boolean init_mysql;
    
    
    private Conexion() throws SQLException{
       cx = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + NOMBRE_BD + "?serverTimezone=UTC", "wanyos", "1712@fll");
    }
    
    public static Connection getConexion() throws SQLException{
        if(conexion == null){
            new Conexion();
        }
        return cx;
    }
    
//    public boolean getInitMySql80(){
//        return init_mysql;
//    }
    
    
    public static void iniciarServicioMySql() {
        InitApp.setMensajeLbl("");
        int op = JOptionPane.showConfirmDialog(null, "No hay conexión con BD...\n Iniciar servicio MySQL80?",
                "Init servicio mysql...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (op == 0) {
            initMySql();
        } else {
            System.exit(0);
        }
    }

    /**
     * Inicia el servicio MySQL80 desde el acceso directo init_mysql_lnk
     * este archivo esta ubicado en la raiz del programa
     * ejecuta: "net start MySQL80" en la terminal 
     */
    private static void initMySql() {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("cmd /c start \"\" \"sc.lnk\"");
            //empezar a preguntar si el servicio esta activo, limitar la cantidad de veces que se comprueba
            int vuelta = 0;
            while (!init_mysql && vuelta < 100) {
                setInitMySql80();
                vuelta++;
            }
            if (init_mysql) {
                JOptionPane.showMessageDialog(null, "La conexión se ha realizado con éxito hay que cerrar\n "
                        + "el programa y volver a iniciarlo...", "Cerrar programa...", JOptionPane.INFORMATION_MESSAGE);
                
            } else if(init_mysql && vuelta > 100){
                JOptionPane.showMessageDialog(null, "La conexión se ha realizado con éxito\n "
                        + "se supero el tiempo de espera de inicio\n"
                        + "se va a cerrar el programa...", "Error de espera...", JOptionPane.INFORMATION_MESSAGE);
            } else if(!init_mysql && vuelta > 100){
                JOptionPane.showMessageDialog(null, "La conexión no se ha realizado\n "
                        + "se supero el tiempo de espera de inicio\n"
                        + "se va a cerrar el programa...", "Error de inicio...", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } catch (IOException ex) {
            InitApp.setMensajeLbl("!!Error no ha sido posible iniciar mysql..." + ex.getMessage());
        }
    }
    

    
    /**
     * Comprueba que el servicio MySQL80 esta corriendo
     * @return 
     */
    private static void setInitMySql80() {
        Runtime rt = Runtime.getRuntime();
        InputStream in = null;
        int v;
        try {
            Process exec = rt.exec("cmd.exe /c " + "net start");
            in = exec.getInputStream();
            int length = -1;
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();

            while ((length = in.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, length, "GBK"));
                v = sb.indexOf("MySQL80");
                if (v != -1) {
                    init_mysql = true;
                    break;
                }
            }
        } catch (IOException ex) {
            init_mysql = false;
        }
    }
    
    public static void initServerXampp(){
        try {
            Runtime.getRuntime().exec("C:\\xampp\\mysql_start.bat", new String[]{"argumento"});
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void stopServerXampp() {
        try {
            Process p = new ProcessBuilder("C:\\xampp\\mysql_stop.bat", "argumento").start();
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
//     private void consola(){
//        try {
//            Runtime.getRuntime().exec("cmd.exe /c dir");
//            //Runtime.getRuntime().exec("runas /profile /user:wanyos cmd.exe /c start");
//        } catch (IOException ex) {
//            Logger.getLogger(InitApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("");
//    }
    
//     private void comando1() {
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            InputStream in = null;
//
//            //String cmd = "C:\WINDOWS\System32\cmd.exe";
////            String[] cmd = {"C:\\WINDOWS\\System32\\net.exe","start","MySQL80"};
//            //String[] cmd = {"C:\\WINDOWS\\system32\\cmd.exe","/c","start"};
////            String mysql = "runas /profile /user:Administrador /savecred /c start C:\\Users\\wanyos\\Documents\\NetBeansProjects\\VentanaPrueba\\init_mysql.lnk";
////            String sql = "runas /profile /user:Administrador C:\\Users\\wanyos\\Documents\\NetBeansProjects\\VentanaPrueba\\init_mysql.lnk";
//            String my = "runas /profile /user:wanyos \"net.exe /c C:\\Users\\wanyos\\Documents\\NetBeansProjects\\VentanaPrueba\\init_mysql.lnk\"";
////            String f = "runas /profile user:Administrador C:\\Windows\\System32\\net.exe start MySQL80";
////            String m = "cmd.exe /c start MySQL80";
////             String my = "C:\\Users\\wanyos\\Documents\\NetBeansProjects\\VentanaPrueba\\init_mysql.lnk";
//
//            //Process exec = runtime.exec("cmd.exe /c " + "dir"); // ejecutar el comando tree /?
////            Process exec = runtime.exec("cmd.exe /c dir"); 
////             runtime.exec(cmd);
//            Process exec = runtime.exec(my);
//            in = exec.getInputStream();
//
//            int length = -1;
//            byte[] buffer = new byte[1024];
//            StringBuilder sb = new StringBuilder();
//
//            while ((length = in.read(buffer)) != -1) {
//                sb.append(new String(buffer, 0, length, "GBK"));
//            }
//            System.out.println(sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    
    
    
    
}
