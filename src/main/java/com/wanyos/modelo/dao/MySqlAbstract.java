
package com.wanyos.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author wanyos
 */
public class MySqlAbstract {
    
    
    private final String sql_libre_disponible = "select fecha from libre_grupo where fecha=?";
    private final String sql_dia_vacacion = "select * from vacacion where ? between fecha_init and fecha_fin;";
    private final String sql_dia_baja = "select * from baja where ? between fecha_init and fecha_fin";
    private final String sql_dia_cambio = "select fecha from cambio where dia_pedido=?";
    private final String sql_dia_pedido = "select fecha from generado where fecha_disfrute=?";
    private final String sql_dia_festivo = "select fecha from festivo where fecha=?";
    private final String sql_dia_servicio = "select fecha from servicio where fecha=?";
    
    protected void closeObjetos(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {

            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {

            }
        }
    }
    
    private boolean existeFecha(Connection cx, String sql, LocalDate fecha){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(fecha));
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            
        } finally {
            closeObjetos(rs, ps);
        }
        return false;
    }
    
    
    
    /**
     * Comprueba si la fecha es un libre de grupo o subgrupo
     * @param cx
     * @param fecha
     * @return true si es libre de grupo o subgrupo
     */
    protected boolean getDiaLibreDisponible(Connection cx, LocalDate fecha){
       return this.existeFecha(cx, sql_libre_disponible, fecha);
    }
    
    /**
     * Busca la fecha en la tabla vacacion
     * @param cx
     * @param fecha
     * @return true si existe esa fecha como vacacion
     */
    protected boolean getDiaVacacion(Connection cx, LocalDate fecha){
        return this.existeFecha(cx, sql_dia_vacacion, fecha);
    }
    
    /**
     * Busca la fecha en la tabla baja. Si no hay fecha_fin baja coge la fecha del sistema
     * en el momento de la consulta
     * @param cx
     * @param fecha
     * @return true si existe esa fecha como baja
     */
    protected boolean getDiaBaja(Connection cx, LocalDate fecha){
       return this.existeFecha(cx, sql_dia_baja, fecha);
    }
    
    /**
     * Busca la fecha en la tabla cambio por si ese dia lo hemos cambiado
     * @param cx
     * @param fecha
     * @return true si el dia lo hemos pedido por cambio de libre con compañero
     */
    protected boolean getDiaCambio(Connection cx, LocalDate fecha){
        return this.existeFecha(cx, sql_dia_cambio, fecha);
    }
    
    /**
     * Busca en la tabla generado
     * @param cx
     * @param fecha
     * @return true si el día lo hemos pedido a disfrutar por un libre generado
     */
    protected boolean getDiaPedido(Connection cx, LocalDate fecha){
        return this.existeFecha(cx, sql_dia_pedido, fecha);
    }
    
    protected boolean getDiaServicio(Connection cx, LocalDate fecha){
        return this.existeFecha(cx, sql_dia_servicio, fecha);
    }
    
    
    /**
     * Comprueba si la fecha es un dia festivo, o el dia de la semana
     * @param cx
     * @param fecha
     * @return tipo de dia: L=laboral S=sabado F=festivo
     */
    protected String getTipoDia(Connection cx, LocalDate fecha) {
        String tipo_dia = "";
        if (this.existeFecha(cx, sql_dia_festivo, fecha)) {
            return "F";
        } else {

            int v = fecha.getDayOfWeek().getValue();
            if (v >= 1 && v <= 5) {
                tipo_dia = "L";
            } else if (v == 6) {
                tipo_dia = "S";
            } else if (v == 7) {
                tipo_dia = "F";
            }

        }
        return tipo_dia;
    }
    
    
    
}
