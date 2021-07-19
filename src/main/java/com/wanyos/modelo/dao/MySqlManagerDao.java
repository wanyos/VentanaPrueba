
package com.wanyos.modelo.dao;

import java.sql.Connection;

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
    private final Conexion conexion;
    private final String NOMBRE_BD = "emt";
    
    
    public MySqlManagerDao(){
        conexion = new Conexion();
    }
    
    public Connection getConexion(){
        return cx;
    }
    
    public MySqlServicioDao getServicioDao() {
        if (mysql_servicio == null) {
            this.cx = conexion.getConexion(NOMBRE_BD); 
            if (cx != null) {
                return new MySqlServicioDao(cx);
            }
        }
        return mysql_servicio;
    }

    public MySqlCalendarioDao getCalendarioDao(){
        if (mysql_calendario == null) {
            this.cx = conexion.getConexion(NOMBRE_BD); 
            if (cx != null) {
                return new MySqlCalendarioDao(cx);
            }
        }
        return mysql_calendario;
    }
    
    public MySqlLibreGeneradoDao getLibreDao(){
        if (mysql_libre == null) {
            this.cx = conexion.getConexion(NOMBRE_BD); 
            if (cx != null) {
                return new MySqlLibreGeneradoDao(cx);
            }
        }
        return mysql_libre;
    }
    
    public MySqlCambiosDao getCambiosDao(){
        if (mysql_cambios == null) {
            this.cx = conexion.getConexion(NOMBRE_BD);
            if (cx != null) {
                return new MySqlCambiosDao(cx);
            }
        }
        return mysql_cambios;
    }
    
    public MySqlPedidoDao getPedidosDao(){
        if (mysql_pedidos == null) {
            this.cx = conexion.getConexion(NOMBRE_BD); 
            if (cx != null) {
                return new MySqlPedidoDao(cx);
            }
        }
        return mysql_pedidos;
    }
    
}
