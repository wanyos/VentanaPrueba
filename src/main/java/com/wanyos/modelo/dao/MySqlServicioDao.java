
package com.wanyos.modelo.dao;

import com.wanyos.modelo.Turno;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import com.wanyos.dao.ServicioDAO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wanyos
 */
public class MySqlServicioDao extends MySqlAbstract implements ServicioDAO {

    private Connection cx;
    private String insert_turno = "insert into servicio values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String select_fecha = "select fecha from servicio where fecha=?";
    private String sql_get_turno = "select * from turno \n" +
                                    "inner join modelo_cuadro on turno.id_cuadro = modelo_cuadro.id_cuadro\n" +
                                    "inner join cuadro on modelo_cuadro.num_cuadro = cuadro.num and modelo_cuadro.tipo = cuadro.tipo\n" +
                                    "inner join tipo_dia on cuadro.num = tipo_dia.num_cuadro and cuadro.tipo = tipo_dia.tipo \n" +
                                    "where tipo_dia.tipo like ? and cuadro.num_linea=? and turno.num like ? and ? between tipo_dia.fecha_init and tipo_dia.fecha_fin;";
    
    public MySqlServicioDao(Connection cx){
        this.cx = cx;
    }
    
    @Override
    public void insert(Turno s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Turno s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set(Turno s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Turno> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Turno get(LocalDate s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public String[] getTurno(LocalDate fecha, String turno, String linea) {
        String dia = "%"+super.getTipoDia(cx, fecha);
        PreparedStatement ps = null;
        ResultSet rs = null;
        String [] datos = {"","","","","","","","","","","",""};
        
        try {
            ps = cx.prepareStatement(sql_get_turno);
            ps.setString(1, dia);
            ps.setString(2, linea);
            ps.setString(3, "%"+turno);
            ps.setDate(4, Date.valueOf(fecha));
            rs = ps.executeQuery();
             int contador = 0;
            while(rs.next() && contador < 12){
                datos[contador++] = rs.getString("h_init").substring(0, 5);
                datos[contador++] = rs.getString("l_init");
                datos[contador++] = rs.getString("dir_init");
                datos[contador++] = rs.getString("h_fin").substring(0, 5);
                datos[contador++] = rs.getString("l_fin");
                datos[contador++] = rs.getString("dir_fin");
            }
           
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        } finally {
           super.closeObjetos(rs, ps);
        }
        return datos;
    }
    
    
    /**
     * Método que se implementa en la superclase
     * Sera utilizado por más clases que heredan de ella
     * @param fecha
     * @return true si la fecha es un día libre de grupo o subgrupo
     */
    public boolean getDiaLibreDisponible(LocalDate fecha){
        return super.getDiaLibreDisponible(cx, fecha);
    }
    
    /**
     * Método implementado en la superclase
     * @param fecha
     * @return true si la fecha es un día de vacación
     */
    public boolean getDiaVacacionDisponible(LocalDate fecha){
        return super.getDiaVacacion(cx, fecha);
    }
    
    /**
     * Método implementado en la superclase
     * @param fecha
     * @return true si la fecha es un día de baja 
     */
    public boolean getDiaBajaDisponible(LocalDate fecha){
        return super.getDiaBaja(cx, fecha);
    }
    
    /**
     * Método implementado en la superclase
     * @param fecha
     * @return true si la fecha es un día cambiado
     */
    public boolean getDiaCambioDisponible(LocalDate fecha){
        return super.getDiaCambio(cx, fecha);
    }
    
    /**
     * Método implementado en la superclase
     * @param fecha
     * @return true si la fecha es un día pedido
     */
    public boolean getDiaPedidoDisponible(LocalDate fecha){
        return super.getDiaPedido(cx, fecha);
    }

    
   
    
}
