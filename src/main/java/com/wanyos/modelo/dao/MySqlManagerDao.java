
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
    private final Conexion conexion;
    
    public MySqlManagerDao(){
        conexion = new Conexion();
    }
    
    @Override
    public MySqlServicioDao getServicioDao() {
        if(mysql_servicio == null){
           this.cx = conexion.getConexion("emt"); //nombre base datos
           return new MySqlServicioDao(cx); 
        }
        return mysql_servicio;
    }

    
    
    
}
