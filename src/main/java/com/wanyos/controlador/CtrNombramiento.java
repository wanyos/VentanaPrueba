
package com.wanyos.controlador;

import com.wanyos.modelo.Turno;
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
    }
    
    
    public PnAbstract getPnNombramiento(){
        return this.pn_nombramiento;
    }
    
    public String getDiaDisponible(LocalDate fecha){
        String mensaje = "disponible";
        String mensaje_libre = getDiaLibreDisponible(fecha);
        String mensaje_vacacion = getDiaVacacionDisponible(fecha);
        String mensaje_baja = getDiaBajaDisponible(fecha);
        String mensaje_cambio = getDiaCambioDisponible(fecha);
        String mensaje_pedido = getDiaPedidoDisponible(fecha);
        
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
    
    
    public boolean setGuardarDatos(LocalDate fecha, String [] turno_linea, String [] horario, String nota){
       Turno t = new Turno(fecha, turno_linea[0], turno_linea[1],
                            horario[0],horario[1],horario[2],horario[3],horario[4],horario[5],horario[6],
                            horario[7],horario[8],horario[9],horario[10],horario[11],nota);
        mysql_servicio.insert(t);
        return true;
    }
    
    public boolean setEditarDatos(String [] turno_linea, String [] horario, String nota){
        
        return true;
    }
    
    public boolean setEliminarDatos(String [] turno_linea, String [] horario, String nota){
        
        
        return true;
    }
    
  
    
}
