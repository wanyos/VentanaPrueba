
package com.wanyos.controlador;

import com.wanyos.modelo.LeerGmailRobot;
import com.wanyos.modelo.ModeloLista;
import com.wanyos.modelo.dao.MySqlManagerDao;
import com.wanyos.modelo.dao.MySqlServicioDao;
import com.wanyos.vista.Hilo;
import com.wanyos.vista.PnAbstract;
import com.wanyos.vista.PnNombramiento;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author wanyos
 */
public class CtrNombramiento {
    
    private PnAbstract pn_nombramiento;
    private MySqlServicioDao mysql_servicio;
    private LeerGmailRobot lgr;
    
    
    public CtrNombramiento(MySqlManagerDao manager_dao){
        pn_nombramiento = new PnNombramiento(this);
        mysql_servicio = manager_dao.getServicioDao();
    }
    
    
    public PnAbstract getPnNombramiento(){
        if(mysql_servicio != null){
            return this.pn_nombramiento;
        }
        return null;
    }
    
    
    
    public String getDiaDisponible(LocalDate fecha){
         String mensaje = "disponible";
        String mensaje_libre = getDiaLibreDisponible(fecha);
        String mensaje_vacacion = getDiaVacacionDisponible(fecha);
        String mensaje_baja = getDiaBajaDisponible(fecha);
        String mensaje_cambio = getDiaCambioDisponible(fecha);
        String mensaje_pedido = getDiaPedidoDisponible(fecha);
        String mensaje_servicio = getDiaServicio(fecha);
        
        if(!mensaje_baja.equalsIgnoreCase(mensaje)){
            return mensaje_baja;
        } else if(!mensaje_libre.equalsIgnoreCase(mensaje) && !mensaje_vacacion.equalsIgnoreCase(mensaje)){
            return mensaje_libre+" y "+mensaje_vacacion;
        } else if(!mensaje_libre.equalsIgnoreCase(mensaje)){
            return mensaje_libre;
        } else if(!mensaje_vacacion.equalsIgnoreCase(mensaje)){
            return mensaje_vacacion;
        } else if(!mensaje_cambio.equalsIgnoreCase(mensaje)){
            return mensaje_cambio;
        } else if(!mensaje_pedido.equalsIgnoreCase(mensaje)){
            return mensaje_pedido;
        } else if(!mensaje_servicio.equalsIgnoreCase(mensaje)){
            return mensaje_servicio;
        }
          return mensaje;
    }
    
    /**
     * Compueba que el día para el servicio no es un libre
     * @param fecha
     * @return mensaje con el valor del dia
     */
    private String getDiaLibreDisponible(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaLibreDisponible(fecha);
        if(existe){
           return "libre grupo o subgrupo";
        }
        return mensaje;
    }
    
    /**
     * Comprueba que el dia para grabar un servicio no es un día de vacación
     * @param fecha
     * @return 
     */
    private String getDiaVacacionDisponible(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaVacacionDisponible(fecha);
        if(existe){
           return "vacación";
        }
        return mensaje;
    }
    
    /**
     * Comprueba que la fecha no es un día de baja
     * @param fecha
     * @return 
     */
    private String getDiaBajaDisponible(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaBajaDisponible(fecha);
        if(existe){
           return "baja";
        }
        return mensaje;
    }
    
    /**
     * Fecha no es un día pedido a un compañero por cambio de libre
     * @param fecha
     * @return 
     */
    private String getDiaCambioDisponible(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaCambioDisponible(fecha);
        if(existe){
            return "cambiado";
        }
        return mensaje;
    }
    
    /**
     * Fecha no es un día pedido por un libre generado
     * @param fecha
     * @return 
     */
    private String getDiaPedidoDisponible(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaPedidoDisponible(fecha);
        if(existe){
            return "pedido";
        }
        return mensaje;
    }
    
    /**
     * Fecha no es un dia ya trabajado
     * @param fecha
     * @return 
     */
    private String getDiaServicio(LocalDate fecha){
        String mensaje = "disponible";
        boolean existe = mysql_servicio.getDiaServicio(fecha);
        if(existe){
            return "trabajado";
        }
        return mensaje;
    }
   
    /**
     * Si el turno existe como turno de un cuadro existente
     * @param fecha necesaria para obtener el cuadro correspondiente con su temporada
     * @param turno
     * @param linea
     * @return 
     */
    public String[] getDatosTurno(LocalDate fecha, String turno, String linea) {
        String [] datos_turno = mysql_servicio.getTurno(fecha, turno, linea); 
        comprobarMensajeMysql();
        return datos_turno;    
    }
    
    
    public String[] getDatosOtroServicio(LocalDate fecha, String puesto){
        String [] datos_otro_ser = mysql_servicio.getDatosOtroServicio(fecha, puesto);
         comprobarMensajeMysql();
        return datos_otro_ser;
    }
    
    public String [] getPuesto (String puesto, String descripcion){
        String [] datos_puesto = mysql_servicio.getPuesto(puesto, descripcion);
         comprobarMensajeMysql();
        return datos_puesto;
    }
    
    
    public void getCorreosLeidos(boolean todos, LocalDate desde_fecha, ModeloLista ml, Hilo h) {
         lgr = new LeerGmailRobot(todos, desde_fecha, ml, h);
            lgr.execute();   
    }
    
    
    public boolean setGuardarDatosTurno(LocalDate fecha, String turno, String linea, String nota){
        int v = mysql_servicio.insertServicio(fecha, turno, linea, null, null, null, nota);
        if(v == 1){
            return true;
        }
        comprobarMensajeMysql();
        return false;
    }
    
    public boolean setTurnosLeidosCorreos(){
        Map<LocalDate, String[]> aux = lgr.getMap();
        if(!aux.isEmpty()){
           for(Map.Entry<LocalDate, String[]> m : aux.entrySet()){
               LocalDate fecha = m.getKey();
               String [] datos = m.getValue();
               String turno = datos[0];
               String linea = datos[1];
               this.setGuardarDatosTurno(fecha, turno, linea, "");
           }
           return true;
        }
        return false;
    }
    
    public boolean setGuardarDatosOtro(LocalDate fecha, String puesto, String descripcion, String [] datos, String nota) {
        int v = mysql_servicio.insertServicio(fecha, null, null, puesto, descripcion, datos, nota);
        if(v == 1){
            return true;
        }
         comprobarMensajeMysql();
        return false;
    }
    
    public String [] getDatosServicio(LocalDate fecha){
        String [] datos = null;
        datos = mysql_servicio.getDatosServicio(fecha);
        comprobarMensajeMysql();
        return datos;
    }
    
    public boolean setGuardarEditarTurno(LocalDate fecha, String turno, String linea, String nota) {
        int v = mysql_servicio.setTurno(fecha, turno, linea, nota);
        if (v == 1) {
            return true;
        }
        comprobarMensajeMysql();
        return false;
    }
    
    public boolean setGuardarEditarOtro(LocalDate fecha, String puesto, String descripcion, String [] datos, String nota){
        int v = mysql_servicio.setGuardarEditarOtro(fecha, puesto, descripcion, datos, nota);
        if(v == 1){
            return true;
        }
         comprobarMensajeMysql();
        return false;
    }
    
    public boolean setEliminarDatos(LocalDate fecha){
        int v = mysql_servicio.deleteServicio(fecha);
        if(v == 1){
            return true;
        }
         comprobarMensajeMysql();
        return false;
    }
    
    private void comprobarMensajeMysql() {
        if (mysql_servicio.getMensaje() != null) {
            pn_nombramiento.setMensajeLbl(mysql_servicio.getMensaje());
        }
    }
    
  
    
}
