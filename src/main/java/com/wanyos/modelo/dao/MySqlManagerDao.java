
package com.wanyos.modelo.dao;

import com.wanyos.vista.InitApp;
import java.sql.Connection;
import java.sql.SQLException;

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
    private static Conexion conexion;
    private static MySqlManagerDao manager_dao;
    
    private MySqlManagerDao(){
        
    }
    
    public static MySqlManagerDao getManagerDao(){
        if(manager_dao == null){
            try {
                conexion = new Conexion();
                if(conexion != null){
                    manager_dao = new MySqlManagerDao();
                }
            } catch (SQLException ex) {
                InitApp.setMensajeLbl("Error conexión BD... "+ex.getMessage());
            }
        }
        return manager_dao;
    }
    
    public Connection getConexion(){
        return cx = conexion.getConexion();
    }
    
    public MySqlServicioDao getServicioDao() {
        if (mysql_servicio == null) {
            this.cx = conexion.getConexion(); 
            if (cx != null) {
                return new MySqlServicioDao(cx);
            }
        }
        return mysql_servicio;
    }

    public MySqlCalendarioDao getCalendarioDao(){
        if (mysql_calendario == null) {
            this.cx = conexion.getConexion(); 
            if (cx != null) {
                return new MySqlCalendarioDao(cx);
            }
        }
        return mysql_calendario;
    }
    
    public MySqlLibreGeneradoDao getLibreDao(){
        if (mysql_libre == null) {
            this.cx = conexion.getConexion(); 
            if (cx != null) {
                return new MySqlLibreGeneradoDao(cx);
            }
        }
        return mysql_libre;
    }
    
    public MySqlCambiosDao getCambiosDao(){
        if (mysql_cambios == null) {
            this.cx = conexion.getConexion();
            if (cx != null) {
                return new MySqlCambiosDao(cx);
            }
        }
        return mysql_cambios;
    }
    
    public MySqlPedidoDao getPedidosDao(){
        if (mysql_pedidos == null) {
            this.cx = conexion.getConexion(); 
            if (cx != null) {
                return new MySqlPedidoDao(cx);
            }
        }
        return mysql_pedidos;
    }
    
}
