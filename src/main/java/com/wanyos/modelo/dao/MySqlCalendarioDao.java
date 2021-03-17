
package com.wanyos.modelo.dao;

import com.wanyos.dao.ServicioDAO;
import com.wanyos.modelo.Turno;
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
public class MySqlCalendarioDao extends MySqlAbstract implements ServicioDAO {
    
    private final Connection cx;
    private final String mysql_lista_vacacion = "select * from vacacion where year(fecha_init)=?";
    private final String mysql_festivos = "select * from festivo where year(fecha)=?";
    private final String mysql_libres = "select * from libre_grupo where year(fecha)=? and tipo=?";
    private final String mysql_insert_libres = "insert into libre_grupo values(?,?)";
    
    public MySqlCalendarioDao(Connection cx){
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
    
    public List<LocalDate> getFestivos(int year){
        List<LocalDate> lista_festivos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
           ps = cx.prepareStatement(mysql_festivos);
           ps.setInt(1, year);
           rs = ps.executeQuery();
           
           while(rs.next()){
               LocalDate fecha = LocalDate.parse(rs.getString("fecha"), DateTimeFormatter.ISO_DATE);
               lista_festivos.add(fecha);
           }
            
        } catch(SQLException e){
            
        } finally {
            closeObjetos(rs, ps);
        }
        
        return lista_festivos;
    }
    
}
