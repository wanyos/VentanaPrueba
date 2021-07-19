
package com.wanyos.modelo.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wanyos
 */
public class MySqlCalendarioDao extends MySqlAbstract {
    
    private final Connection cx;
    private final String mysql_lista_vacacion = "select * from vacacion where year(fecha_init)=?";
    private final String mysql_festivos = "select * from festivo where year(fecha)=?";
    private final String mysql_libres = "select * from libre_grupo where year(fecha)=? and tipo=?";
    private final String mysql_pedidos = "select * from dia_pedido where year(fecha_pedido)=?";
    private final String mysql_concedidos = "select * from generado where year(fecha_disfrute)=?";
    private final String mysql_insert_libres = "insert into libre_grupo values(?,?)";
    
    public MySqlCalendarioDao(Connection cx){
        this.cx = cx;
    }

    /**
     * Se guardan todos los libres del año del usr
     * En esta tabla se haran modificaciones cuando se cambien dias
     * @param lista_libres
     * @param tipo 
     */
    public void insertLibres(List<LocalDate> lista_libres, String tipo) {
        PreparedStatement ps = null;

        try {
            ps = cx.prepareStatement(mysql_insert_libres);
            for(LocalDate fecha: lista_libres){
                ps.setDate(1, Date.valueOf(fecha));
                ps.setString(2, tipo);
                ps.executeUpdate();
            }
        } catch (SQLException e) {

        } finally {
            closeObjetos(null, ps);
        }
    }
    
    
    
    public List<LocalDate> getLibreUsr(int year, String tipo){
        List<LocalDate> lista_subgrupo = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs;
        
        try {
            ps = cx.prepareStatement(mysql_libres);
            ps.setInt(1, year);
            ps.setString(2, tipo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String fecha = rs.getString("fecha");
                LocalDate f = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);
                lista_subgrupo.add(f);
            }
            
        } catch(SQLException e){
            
        } finally {
            closeObjetos(null, ps);
        }
        
        return lista_subgrupo;
    }
    
    public List<LocalDate> getListaVacacionUsr(int year){
        List<LocalDate> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        try {
            ps = cx.prepareStatement(mysql_lista_vacacion);
            ps.setInt(1, year);
            rs = ps.executeQuery();
            
            while(rs.next()){
                LocalDate fecha_init = LocalDate.parse(rs.getString("fecha_init"), DateTimeFormatter.ISO_DATE);
                LocalDate fecha_fin = LocalDate.parse(rs.getString("fecha_fin"), DateTimeFormatter.ISO_DATE);
                lista.add(fecha_init);
                while(fecha_init.isBefore(fecha_fin)){
                    fecha_init = fecha_init.plusDays(1);
                    lista.add(fecha_init);
                }
            }
            
        } catch (SQLException e){
            
        } finally {
            closeObjetos(rs, ps);
        }
        
        return lista;
    }
    
    
    private List<LocalDate> getLista(int year, String mysql, String nombre_campo){
        List<LocalDate> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
           ps = cx.prepareStatement(mysql);
           ps.setInt(1, year);
           rs = ps.executeQuery();
           
           while(rs.next()){
               LocalDate fecha = LocalDate.parse(rs.getString(nombre_campo), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
               lista.add(fecha);
           }
            
        } catch(SQLException e){
            System.out.println();
        } finally {
            closeObjetos(rs, ps);
        }
        
        return lista;
    }
    
    public List<LocalDate> getFestivos(int year){
        List<LocalDate> lista_festivos = this.getLista(year, mysql_festivos, "fecha");
        return lista_festivos;
    }
    
    public List<LocalDate> getDiasPedidos(int year) {
        List<LocalDate> lista_pedidos = this.getLista(year, mysql_pedidos, "fecha_pedido");
        return lista_pedidos;
    }
    
     public List<LocalDate> getDiasConcedidos(int year){
        List<LocalDate> lista_concedidos = this.getLista(year, mysql_concedidos, "fecha_disfrute");        
        return lista_concedidos;
    }
    
    
}
