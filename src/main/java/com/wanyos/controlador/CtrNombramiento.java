
package com.wanyos.controlador;

import com.wanyos.modelo.dao.MySqlManagerDao;
import com.wanyos.modelo.dao.MySqlServicioDao;
import com.wanyos.vista.PnAbstract;
import com.wanyos.vista.PnNombramiento;
import java.time.LocalDate;
import javax.swing.JLabel;

/**
 *
 * @author wanyos
 */
public class CtrNombramiento {
    
    private PnAbstract pn_nombramiento;
    private MySqlManagerDao manager_dao;
    private MySqlServicioDao mysql_servicio;
    
    
    public CtrNombramiento(JLabel lbl_mensaje){
        pn_nombramiento = new PnNombramiento(lbl_mensaje, this);
        manager_dao = new MySqlManagerDao();
        mysql_servicio = manager_dao.getServicioDao();
        if(mysql_servicio == null){
            pn_nombramiento.setMensajeLbl("Error no existe conexión con la BD...");
        }
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
     * @param fecha
     * @param turno
     * @param linea
     * @return 
     */
    public String[] getDatosTurno(LocalDate fecha, String turno, String linea) {
        String [] datos_turno = mysql_servicio.getTurno(fecha, turno, linea); 
        return datos_turno;    
    }
    
    
    public String[] getDatosOtroServicio(LocalDate fecha, String puesto){
        String [] datos_otro_ser = mysql_servicio.getDatosOtroServicio(fecha, puesto);
        return datos_otro_ser;
    }
    
    public String [] getPuesto (String puesto, String descripcion){
        String [] datos_puesto = mysql_servicio.getPuesto(puesto, descripcion);
        return datos_puesto;
    }
    
    
    public boolean setGuardarDatosTurno(LocalDate fecha, String turno, String linea, String nota){
        int v = mysql_servicio.insertServicio(fecha, turno, linea, null, null, null, nota);
        if(v == 1){
            return true;
        }
        return false;
    }
    
    public boolean setGuardarDatosOtro(LocalDate fecha, String puesto, String descripcion, String [] datos, String nota) {
        int v = mysql_servicio.insertServicio(fecha, null, null, puesto, descripcion, datos, nota);
        if(v == 1){
            return true;
        }
        return false;
    }
    
    public String [] getDatosServicio(LocalDate fecha){
        String [] datos = null;
        datos = mysql_servicio.getDatosServicio(fecha);
        return datos;
    }
    
    public boolean setGuardarEditarTurno(LocalDate fecha, String turno, String linea, String nota) {
        int v = mysql_servicio.setTurno(fecha, turno, linea, nota);
        if (v == 1) {
            return true;
        }
        return false;
    }
    
    public boolean setGuardarEditarOtro(LocalDate fecha, String puesto, String descripcion, String [] datos, String nota){
        int v = mysql_servicio.setGuardarEditarOtro(fecha, puesto, descripcion, datos, nota);
        if(v == 1){
            return true;
        }
        return false;
    }
    
    public boolean setEliminarDatos(LocalDate fecha){
        int v = mysql_servicio.deleteServicio(fecha);
        if(v == 1){
            return true;
        }
        return false;
    }
    
  
    
}
