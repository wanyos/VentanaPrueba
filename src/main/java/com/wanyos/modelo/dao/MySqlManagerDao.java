
package com.wanyos.modelo.dao;

import com.wanyos.dao.ManagerDAO;
import java.sql.Connection;

/**
 *
 * @author wanyos
 */
public class MySqlManagerDao implements ManagerDAO {
    
    
    private Connection cx;
    private MySqlServicioDao mysql_servicio; 
    private MySqlCalendarioDao mysql_calendario;
    private final Conexion conexion;
    
    public MySqlManagerDao(){
        conexion = new Conexion();
    }
    
    @Override
    public MySqlServicioDao getServicioDao() {
        if (mysql_servicio == null) {
            this.cx = conexion.getConexion("emt"); //nombre base datos
            if (cx != null) {
                return new MySqlServicioDao(cx);
            }
        }
        return mysql_servicio;
    }

    public MySqlCalendarioDao getCalendarioDao(){
        if (mysql_calendario == null) {
            this.cx = conexion.getConexion("emt"); //nombre base datos
            if (cx != null) {
                return new MySqlCalendarioDao(cx);
            }
        }
        return mysql_calendario;
    }
    
    
}
