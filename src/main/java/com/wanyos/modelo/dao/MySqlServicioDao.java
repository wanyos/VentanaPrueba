
package com.wanyos.modelo.dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author wanyos
 */
public class MySqlServicioDao extends MySqlAbstract {

    private final Connection cx;
    private final String sql_insert_servicio = "insert into servicio values(?,?,?,?,?,?)";
    private final String sql_insert_puesto = "insert into puesto values(?,?,?,?,?,?,?,?)";
    private final String sql_insert_otro_servicio = "insert into otro_servicio values(?,?)";
    private final String sql_get_turno = "select * from turno \n" +
                                    "inner join cuadro on turno.id_cuadro = cuadro.num and turno.tipo = cuadro.tipo\n" +
                                    "inner join tipo_dia on cuadro.num = tipo_dia.num_cuadro and cuadro.tipo = tipo_dia.tipo \n" +
                                    "where tipo_dia.tipo like ? and cuadro.num_linea=? and turno.num like ? and ? between tipo_dia.fecha_init and tipo_dia.fecha_fin;";
    private final String sql_get_servicio = "select turno,linea,puesto,descripcion,nota from servicio where fecha=?";
    private final String sql_get_otro_servicio = "select * from puesto inner join otro_servicio on puesto.id_puesto = otro_servicio.puesto where fecha=? and puesto=?";
    private final String sql_delete_servicio = "delete from servicio where fecha=?";
    private final String sql_set_servicio = "update servicio set turno=?, linea=?, puesto=?, descripcion=?, nota=? where fecha=?";
    private final String sql_set_otro_servicio = "update otro_servicio set puesto=? where fecha=?";
    private final String sql_get_puesto = "select * from puesto where id_puesto=?";
    private String mensaje;
    
    
    public MySqlServicioDao(Connection cx){
        this.cx = cx;
    }
    
    public void setMensaje(String m){
        mensaje = m;
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    
    
    public int insertServicio(LocalDate fecha, String turno, String linea, String puesto, String descripcion, String [] datos, String nota){
        PreparedStatement ps = null;
        int v = 0;
        try {
            
            if(puesto != null && descripcion != null){
              insertPuesto(puesto, descripcion, datos); //si el puesto ya existe no lo guarda
            }
            
            ps = cx.prepareStatement(sql_insert_servicio);
            ps.setDate(1, Date.valueOf(fecha));
            ps.setString(2, turno);
            ps.setString(3, linea);
            ps.setString(4, puesto);
            ps.setString(5, descripcion);
            ps.setString(6, nota);
            v = ps.executeUpdate();
            
            if(puesto != null && descripcion != null){
              insertOtroServicio(fecha, puesto);  //foreign key campo fecha, si no existe el servicio no se guarda en la tabla otro_servicio
            }
            
        } catch(SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    

    
    public int setTurno(LocalDate fecha, String turno, String linea, String nota){
        int v = 0;
        PreparedStatement ps = null;    
        try {
            
            ps = cx.prepareStatement(sql_set_servicio);
            ps.setString(1, turno);
            ps.setString(2, linea);
            ps.setString(3, null);
            ps.setString(4, null);
            ps.setString(5, nota);
            ps.setDate(6, Date.valueOf(fecha));
            v = ps.executeUpdate();
            
        } catch(SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos (null, ps);
        }
        return v;
    }
    

    
    private int insertOtroServicio(LocalDate fecha, String puesto){
        PreparedStatement ps = null;
        int v = 0;
        try {
            ps = cx.prepareStatement(sql_insert_otro_servicio);
            ps.setDate(1, Date.valueOf(fecha));
            ps.setString(2, puesto);
            v = ps.executeUpdate();
        } catch (SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    
    private void insertPuesto(String puesto, String descripcion, String [] datos) {
        PreparedStatement ps = null;
         
        try {
            ps = cx.prepareStatement(sql_insert_puesto);
            ps.setString(1, puesto);
            ps.setString(2, descripcion);
    
            LocalTime tm1 = LocalTime.parse(datos[0]).plus(1, ChronoUnit.HOURS);
            Time t1 = Time.valueOf(tm1);
           
            ps.setTime(3, t1);
            ps.setString(4, datos[1]);
            ps.setString(5, datos[2]);

            LocalTime tm2 = LocalTime.parse(datos[3]).plus(1, ChronoUnit.HOURS);
            Time t2 = Time.valueOf(tm2);
            
            ps.setTime(6, t2);
            ps.setString(7, datos[4]);
            ps.setString(8, datos[5]);
            ps.executeUpdate();
        } catch (SQLException e) {
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
    }
    
    public int setGuardarEditarOtro(LocalDate fecha, String puesto, String descripcion, String [] datos, String nota){
        int v = 0;
        PreparedStatement ps = null;
        try {
            
            insertPuesto(puesto, descripcion, datos);  //si el puesto no existe se crea como nuevo
            setOtroServicio(fecha, puesto);  //foreign key campo fecha, si no existe el servicio no se guarda en la tabla otro_servicio
            
            ps = cx.prepareStatement(sql_set_servicio);
            ps.setString(1, null);
            ps.setString(2, null);
            ps.setString(3, puesto);
            ps.setString(4, descripcion);
            ps.setString(5, nota);
            ps.setDate(6, Date.valueOf(fecha));
            v = ps.executeUpdate();
            
        } catch(SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
        return v;
    }
    
    
    private void setOtroServicio(LocalDate fecha, String puesto){
        PreparedStatement ps = null;
        
        try {
            ps = cx.prepareStatement(sql_set_otro_servicio);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setString(1, puesto);
            ps.executeUpdate();
        } catch(SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
    }
    

    /**
     * Busca el turno y la linea en las tablas usadas para cuadros horario
     * La fecha es necesaria pues un mismo turno cambiara dependiendo de la temporada
     * @param fecha
     * @param turno
     * @param linea
     * @return 
     */
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
           
        } catch (SQLException e) {
           this.setMensaje(e.getMessage());
        } finally {
           super.closeObjetos(rs, ps);
        }
        return datos;
    }
    
    public String [] getDatosOtroServicio(LocalDate fecha, String puesto){
        String [] datos = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
          ps = cx.prepareStatement(sql_get_otro_servicio);
          ps.setDate(1, Date.valueOf(fecha));
          ps.setString(2, puesto);
          rs = ps.executeQuery();
          
          while(rs.next()){
              datos = new String[6];
              for(int i = 0; i < datos.length; i++){
                  datos[i] = rs.getString(i+3);
              }
          }
        } catch (SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(rs, ps);
        }
        return datos;
    }
    
    public String [] getPuesto(String puesto, String descripcion){
        String [] datos_puesto = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = cx.prepareStatement(sql_get_puesto);
            ps.setString(1, puesto);
            rs = ps.executeQuery();
            
            while(rs.next()){
                datos_puesto = new String[6];
                for(int a = 0; a<datos_puesto.length; a++){
                    datos_puesto[a] = rs.getString(a+3);
                }
            }
            
        } catch (SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(rs, ps);
        }
        
        return datos_puesto;
    }
    
    
    public String [] getDatosServicio(LocalDate fecha){
        String [] datos = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = cx.prepareStatement(sql_get_servicio);
            ps.setDate(1, Date.valueOf(fecha));
            rs = ps.executeQuery();
     
            while(rs.next()){
                datos = new String[5];
                for(int a = 0; a < 5; a++){
                    datos[a] = rs.getString(a+1);
                }
            }
        } catch(SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(rs, ps);
        }
        return datos;
    }
    
    public int deleteServicio(LocalDate fecha){
        int c = 0;
        PreparedStatement ps = null;
        
        try {
            ps = cx.prepareStatement(sql_delete_servicio);
            ps.setDate(1, Date.valueOf(fecha));
            c = ps.executeUpdate();
        } catch (SQLException e){
            this.setMensaje(e.getMessage());
        } finally {
            closeObjetos(null, ps);
        }
        return c;
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

    /**
     * Método implementado en superclase
     * @param fecha
     * @return true si la fecha es un dia ya trabajado
     */
    public boolean getDiaServicio(LocalDate fecha){
        return super.getDiaServicio(cx, fecha);
    }
    
   
    
}
