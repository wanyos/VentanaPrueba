
package com.wanyos.modelo.dao;

import com.wanyos.modelo.LibreGenerado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wanyos
 */
public class MySqlLibreGeneradoDao extends MySqlAbstract implements Runnable {
    
    private final Connection cx;
    private final String mysql_libres_tipo = "select * from generado where tipo=? order by fecha desc";
    private final String mysql_libres_tipo_disponibles = "select * from generado where tipo=? and fecha_cobro='0001-01-01' and fecha_disfrute='0001-01-01'";
    private final String mysql_todos_libres = "select * from generado";
    private final String mysql_editar_libre= "update generado set fecha_cobro=?, fecha_disfrute=? where id=?";
    private final String mysql_eliminar_libre = "delete from generado where id=?";
    private final String mysql_insert_libres = "insert into generado values(?,?,?,?,?)";
    private final String mysql_nombre_tipo_libre = "select distinct tipo from generado";
    private final String mysql_fecha_creacion_tabla = "select create_time from information_schema.tables where table_name=?";
    private String terminado = "";
    
    
    public MySqlLibreGeneradoDao(Connection cx){
        this.cx = cx;
    }

    
    public String getMensaje(){
        return this.terminado;
    }
    
//    public String[][] getListadoLibres(String tipo, boolean todos){
//        String [][] listado = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        
//        try {
//            
//            if (tipo.equalsIgnoreCase("todos")) {
//                ps = cx.prepareStatement(mysql_todos_libres, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            } else {
//                if (!todos) {
//                    ps = cx.prepareStatement(mysql_libres_tipo, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                } else {
//                    ps = cx.prepareStatement(mysql_libres_tipo_disponibles, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                }
//                 ps.setString(1, tipo);
//            }
//            rs = ps.executeQuery();
//
//            rs.last();
//            int l = rs.getRow();
//            rs.beforeFirst();
//            listado = new String[l][5];
//            int f = 0;
//            while(rs.next()){
//                int c = 0;
//                listado[f][c++] = rs.getString("id");
//                listado[f][c++] = rs.getString("tipo");
//                listado[f][c++] = rs.getString("fecha");
//                listado[f][c++] = getFormatFecha(rs.getString("fecha_cobro"));
//                listado[f][c++] = getFormatFecha(rs.getString("fecha_disfrute"));
//                f++;
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            closeObjetos(rs, ps);
//        }
//        return listado;
//    }
    
     public List<LibreGenerado> getListadoLibres(String tipo, boolean todos){
        List<LibreGenerado> listado = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            if (tipo.equalsIgnoreCase("todos")) {
                ps = cx.prepareStatement(mysql_todos_libres, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } else {
                if (!todos) {
                    ps = cx.prepareStatement(mysql_libres_tipo, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                } else {
                    ps = cx.prepareStatement(mysql_libres_tipo_disponibles, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
                 ps.setString(1, tipo);
            }
            rs = ps.executeQuery();
            
            while(rs.next()){
                int id = Integer.parseInt(rs.getString("id"));
                String t = rs.getString("tipo");
                LocalDate fecha_g = LocalDate.parse(rs.getString("fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_c = LocalDate.parse(rs.getString("fecha_cobro"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_d = LocalDate.parse(rs.getString("fecha_disfrute"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LibreGenerado libre = new LibreGenerado(id,t,fecha_g,fecha_c,fecha_d);
                listado.add(libre);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeObjetos(rs, ps);
        }
        return listado;
    }
    
    
    
//    private String getFormatFecha(String f){
//        String fecha = f;
//        if(fecha.equalsIgnoreCase("0000-00-00") || fecha.equalsIgnoreCase("0001-01-01")){
//            return "--";
//        }
//        return fecha;
//    }
    
    
    public int setEditarLibre(int id, LocalDate fecha_c, LocalDate fecha_d) {
        PreparedStatement ps = null;
        int v = 0;
        
        try {
            ps = cx.prepareStatement(mysql_editar_libre);
            ps.setDate(1, Date.valueOf(fecha_c));
            ps.setDate(2, Date.valueOf(fecha_d));
            ps.setInt(3, id);
            v = ps.executeUpdate();

        } catch (SQLException e) {

        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    
    
    public int EliminarLibre(int id){
        PreparedStatement ps = null;
        int v = 0;
        
        try {
            ps = cx.prepareStatement(mysql_eliminar_libre);
            ps.setInt(1, id);
            v = ps.executeUpdate();
        } catch(SQLException e){
            
        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    
    public List<LibreGenerado> getListaTipo(String tipo_dia) {
        List<LibreGenerado> lista_tipo = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cx.prepareStatement(mysql_libres_tipo);
            ps.setString(1, tipo_dia);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String tipo = rs.getString("tipo");
                LocalDate fecha_g = LocalDate.parse(rs.getString("fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_c = LocalDate.parse(rs.getString("fecha_cobro"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fecha_d = LocalDate.parse(rs.getString("fecha_disfrute"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LibreGenerado libre = new LibreGenerado(id, tipo, fecha_g, fecha_c, fecha_d);
                lista_tipo.add(libre);
            }

        } catch (SQLException e) {

        } finally {
            closeObjetos(rs, ps);
        }

        return lista_tipo;
    }
    
    
    
    public int setDiaGenerado (LibreGenerado libre){
        PreparedStatement ps = null;
        int v = 0;

        try {
            ps = cx.prepareStatement(mysql_insert_libres);
                ps.setInt(1, libre.getId());
                ps.setString(2, libre.getTipo());
                ps.setDate(3, Date.valueOf(libre.getFechaGenerado()));
                ps.setDate(4, Date.valueOf(libre.getFechaCobro()));
                ps.setDate(5, Date.valueOf(libre.getFechaDisfrute()));
                v = ps.executeUpdate();
            
        } catch (SQLException e) {

        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }

    /**
     * Lista con los tipos que existen en la tabla generado
     * Se usa para rellenar el comboBox del panel libre_generado
     * @return 
     */
    public String[] getTipoLibres() {
        String[] tipo_libre = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cx.prepareStatement(mysql_nombre_tipo_libre, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
            rs.last();
            int longitud = rs.getRow();
            rs.beforeFirst();
            tipo_libre = new String[longitud];
            int contador = 0;

            while (rs.next()) {
                tipo_libre[contador++] = rs.getString("tipo");
            }

        } catch (SQLException e) {
            System.out.println();
        } finally {
            closeObjetos(rs, ps);
        }

        return tipo_libre;
    }
    

//    public int getTotalRegistrosLibreGenerado(){
//        Statement st = null;
//        ResultSet rs = null;
//        int t = 0;
//        
//        try {
//            st = cx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            rs = st.executeQuery ("select * from generado");
//            
//            rs.last();
//            t = rs.getRow();
//            
//        } catch (SQLException ex) {
//            System.out.println();
//        } finally {
//            try {
//                rs.close();
//                st.close();
//            } catch (SQLException ex) {
//                
//            }
//        }
//        return t;
//    }
    
    
    
//    public void eliminarTodoLibreGenerado(){
//         PreparedStatement ps = null;
//        try {
//            ps = cx.prepareStatement("truncate table generado");
//            ps.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println();
//        } finally {
//            closeObjetos(null, ps);
//        }
//       
//    }
    
    /**
     * Devuelve la fecha que se creo la tabla
     * Se usa para saber cual fue la fecha de actualizacion de la tabla generado
     * @param nombre_tabla
     * @return 
     */
    public LocalDateTime getFechaCrearTabla(String nombre_tabla) {
        LocalDateTime fecha_creacion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = cx.prepareStatement(mysql_fecha_creacion_tabla);
            ps.setString(1, nombre_tabla);
            rs = ps.executeQuery();

            while (rs.next()) {
                String fecha = rs.getString(1);
                fecha_creacion = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println();
        } finally {
            closeObjetos(null, ps);
        }

        return fecha_creacion;
    }
    
    
    public void actualizarCopiaTablaGenerado() {
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion("emt");
        Statement st = null;
        ResultSet rs = null;

        PreparedStatement ps = null;
        try {
            c.setAutoCommit(false);
            st = c.createStatement();
            rs = st.executeQuery("show tables like 'copia0_generado'");
            if (rs.next()) {
                //si existe copia0 existe copia1
                st.execute("drop table copia0_generado");
                st.execute("alter table copia1_generado rename copia0_generado");
                st.execute("create table copia1_generado select * from generado");
            } else {
                //no existe copia0 probar si existe copia1
                rs = st.executeQuery("show tables like 'copia1_generado'");
                if (rs.next()) {
                    st.execute("alter table copia1_generado rename copia0_generado");
                    st.execute("create table copia1_generado select * from generado");
                } else {
                    //no existe copia1 ni copia0, crear solo copia1
                    st.execute("create table copia1_generado select * from generado");
                }
            }
            c.commit();
            terminado = "correcto";
        } catch (SQLException | NullPointerException e) {
            terminado = "incorrecto";
            System.out.println(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
    }

    @Override
    public void run() {
        actualizarCopiaTablaGenerado();
    }
    
       
   
}
   
    

