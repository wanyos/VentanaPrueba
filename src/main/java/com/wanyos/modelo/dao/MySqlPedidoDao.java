
package com.wanyos.modelo.dao;

import com.wanyos.modelo.DiaPedido;
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
public class MySqlPedidoDao extends MySqlAbstract {
    
    
    private Connection cx;
    private String mysql_insert = "insert into dia_pedido values(?,?,?,?,?);"; 
    private String mysql_get_pedidos = "select * from dia_pedido";
    private String mysql_existe_pedido = "select * from dia_pedido where id=?";
    private String mysql_remove_pedido = "delete from dia_pedido where id=?";
    
    public MySqlPedidoDao(Connection cx){
        this.cx = cx;
    }
    
    public int setDiaPedido(LocalDate fecha, LocalDate fecha_p, LocalDate fecha_o, String tipo, int id){
        PreparedStatement ps = null;
        int v = 0;
        
        try {
            ps = cx.prepareStatement(mysql_insert);
            ps.setDate(1, Date.valueOf(fecha));
            ps.setDate(2, Date.valueOf(fecha_p));
            ps.setDate(3, Date.valueOf(fecha_o));
            ps.setString(4, tipo);
            ps.setInt(5, id);
            v = ps.executeUpdate();
        } catch (SQLException ex) {
            
        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    
    public List<DiaPedido> getPedidos() {
        List<DiaPedido> dias_pedidos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cx.prepareStatement(mysql_get_pedidos);
            rs = ps.executeQuery();

            while (rs.next()) {
                LocalDate fecha_peticion = LocalDate.parse(rs.getString("fecha_peticion"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_pedido = LocalDate.parse(rs.getString("fecha_pedido"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_ofrecido = LocalDate.parse(rs.getString("fecha_ofrecido"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tipo = rs.getString("tipo");
                int id = rs.getInt("id");
                DiaPedido dp = new DiaPedido(fecha_peticion, fecha_pedido, fecha_ofrecido, tipo, id);
                dias_pedidos.add(dp);
            }
        } catch (SQLException ex) {

        } finally {
            closeObjetos(rs, ps);
        }
        return dias_pedidos;
    }
    
    public boolean existePedido(int id) {
        PreparedStatement ps = null;
        int v = 0;

        try {
            ps = cx.prepareStatement(mysql_existe_pedido);
            ps.setInt(1, id);
            v = ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            closeObjetos(null, ps);
        }
        if (v != 0) {
            return true;
        }
        return false;
    }
    
    
    public void removePedido(int id) {
        PreparedStatement ps = null;

        try {
            ps = cx.prepareStatement(mysql_remove_pedido);
            ps.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            closeObjetos(null, ps);
        }
    }
    
}
