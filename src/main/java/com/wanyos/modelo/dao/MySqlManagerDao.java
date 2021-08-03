
package com.wanyos.modelo.dao;

import com.wanyos.vista.InitApp;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingWorker;

/**
 *
 * @author wanyos
 */
public class MySqlManagerDao {
    
    
    private Connection cx;
    private MySqlServicioDao mysql_servicio; 
    private MySqlCalendarioDao mysql_calendario;
    private MySqlLibreGeneradoDao mysql_libre;
    private MySqlCambiosDao mysql_cambios;
    private MySqlPedidoDao mysql_pedidos;
    private Conexion conexion;
    private static MySqlManagerDao manager_dao;
    private Thread t;
    private WorkerManagerDao k;
    
    
      private MySqlManagerDao() {
        k = new WorkerManagerDao();
        k.execute();
    }
      
        
      public static void setManagerDao() {
        if (manager_dao == null) {
            manager_dao = new MySqlManagerDao();
        }
    }
      
     public static MySqlManagerDao getManagerDao() {
        return manager_dao;
    }
     
    
   
    private void getCx() {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        conexion = new Conexion();
                    } catch (SQLException ex) {
                        InitApp.setMensajeLbl("No hay conxión con BD... " + ex.getMessage());
                    }
                }
            });
            t.start();
    }
    
   
    private class WorkerManagerDao extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            String b = "";
            getCx();
            while (t.isAlive()) {
                b = b.concat("-");
                if (b.length() > 10) {
                    b = "";
                }
                Thread.sleep(40);
                InitApp.setMensajeLbl(b);
            }
            return null;
        }

        @Override
        public void done() {
            if (conexion != null) {
                cx = conexion.getConexion();
                InitApp.setEnabledPn(true);
                InitApp.setMensajeLbl("Conexión BD con éxito... ");
            } else {
                InitApp.setMensajeLbl("No hay conxión con BD... ");
            }
        }
    }
   
    
    public Connection getConexion(){
        return cx;
    }
    
    public MySqlServicioDao getServicioDao() {
        if(conexion != null){
            cx = conexion.getConexion();
            mysql_servicio = new MySqlServicioDao(cx);
        }
        return mysql_servicio;
    }

    public MySqlCalendarioDao getCalendarioDao(){
        if(conexion != null){
            cx = conexion.getConexion();
            mysql_calendario = new MySqlCalendarioDao(cx);
        }
        return mysql_calendario;
    }
    
    public MySqlLibreGeneradoDao getLibreDao(){
        if(conexion != null){
            cx = conexion.getConexion();
            mysql_libre = new MySqlLibreGeneradoDao(cx);
        }
        return mysql_libre;
    }
    
    public MySqlCambiosDao getCambiosDao(){
        if(conexion != null){
            cx = conexion.getConexion();
            mysql_cambios = new MySqlCambiosDao(cx);
        }
        return mysql_cambios;
    }
    
    public MySqlPedidoDao getPedidosDao(){
        if(conexion != null){
            cx = conexion.getConexion();
            mysql_pedidos = new MySqlPedidoDao(cx);
        }
        return mysql_pedidos;
    }
    
}
