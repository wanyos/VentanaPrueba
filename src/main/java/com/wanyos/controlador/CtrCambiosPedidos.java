
package com.wanyos.controlador;

import com.wanyos.modelo.DiaPedido;
import com.wanyos.modelo.LibreGenerado;
import com.wanyos.modelo.dao.MySqlCambiosDao;
import com.wanyos.modelo.dao.MySqlLibreGeneradoDao;
import com.wanyos.modelo.dao.MySqlManagerDao;
import com.wanyos.modelo.dao.MySqlPedidoDao;
import com.wanyos.vista.PnAbstract;
import com.wanyos.vista.PnCambiosPedidos;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author wanyos
 */
public class CtrCambiosPedidos {
    
    private PnAbstract pn_cambios_pedidos;
    private MySqlManagerDao manager_dao;
    private MySqlCambiosDao mysql_cambios;
    private MySqlPedidoDao mysql_pedidos;
    private MySqlLibreGeneradoDao mysql_libre_generado;
    
    
    public CtrCambiosPedidos(MySqlManagerDao manager_dao){
        this.manager_dao = manager_dao;
        pn_cambios_pedidos = new PnCambiosPedidos(this);
        mysql_cambios = manager_dao.getCambiosDao();
        mysql_pedidos = manager_dao.getPedidosDao();
        mysql_libre_generado = manager_dao.getLibreDao();
    }
    
    public PnAbstract getPnCambiosPedidos() {
        if (mysql_cambios != null && mysql_pedidos != null && mysql_libre_generado != null) {
            return pn_cambios_pedidos;
        }
        return null;
    }

    public String[] getListadoTipoDias(){
        String [] lista = mysql_libre_generado.getTipoLibres();
        return lista;
    }
    
    /**
     * Elimina de la lista de libres los dias que ya han sido pedidos pero que estan a falta de confirmacion
     * Estos dias seran eliminados de la tabla dia_pedido una vez esten concedidos, para ello hay que actualizar 
     * el pdf de dias generados
     * @param tipo
     * @return 
     */
    public List<LibreGenerado> getListadoLibres(String tipo){
        List<LibreGenerado> lista_libres = mysql_libre_generado.getListadoLibres(tipo, true);
        List<DiaPedido> lista_dias_pedidos = mysql_pedidos.getPedidos();
        
        Iterator<LibreGenerado> it = lista_libres.iterator();
        while (it.hasNext()){
            LibreGenerado li = it.next();
            for(DiaPedido aux: lista_dias_pedidos){
                if(aux.getId() == li.getId()){
                    it.remove();
                    break;
                }
            }
        }
        return  lista_libres;
    }
    
    
    /**
     * Comprueba que el dia que vamos a pedir no es un libre, subgrupo, dia vacacion, dia baja, dia pasado, día cambiado, día ya pedido
     * @param fecha_pedido
     * @return 
     */
    public boolean comprobarDiaPedido(LocalDate fecha_pedido){
        LocalDate fecha_ahora = LocalDate.now();
        long dias = DAYS.between(fecha_ahora, fecha_pedido);
        if(dias <= 5){
            pn_cambios_pedidos.setMensajeLbl("El día pedido debe ser inferior a 5 dias siguientes...");
            return false;
        } else {
            
            if(mysql_libre_generado.getDiaLibreDisponible(manager_dao.getConexion(), fecha_pedido)){              //revisar este acceso al manager
                pn_cambios_pedidos.setMensajeLbl("El día pedido es un día libre o subgrupo...");
                return false;
            } else if(mysql_libre_generado.getDiaVacacion(manager_dao.getConexion(), fecha_pedido)){
                pn_cambios_pedidos.setMensajeLbl("El día pedido es un día de vacación...");
                return false;
            } else if(mysql_libre_generado.getDiaBaja(manager_dao.getConexion(), fecha_pedido)){
                pn_cambios_pedidos.setMensajeLbl("El día pedido es un día de baja...");
                return false;
            } else if(mysql_libre_generado.getDiaCambio(manager_dao.getConexion(), fecha_pedido)){
                pn_cambios_pedidos.setMensajeLbl("El día pedido es un día ya cambiado...");
                return false;
            } else if(mysql_libre_generado.getDiaPedido(manager_dao.getConexion(), fecha_pedido)){
                pn_cambios_pedidos.setMensajeLbl("El día pedido es un día que ya se ha pedido...");
                return false;
            }
        }
        return true;
    }
    
    
    
    public boolean setPedirDia(LocalDate fecha, LocalDate dia_pedido, LibreGenerado dia_seleccionado) {
        //guardar el dia pedido en la tabla dia_pedido, este se pintara de naranja en el calendario
        //una vez que se actualice el archivo de libres y este concedido se pintara de verde
        LocalDate fecha_peticion = LocalDate.now();;
        if(fecha != null){
           fecha_peticion = fecha; 
        }
        LocalDate fecha_o = dia_seleccionado.getFechaGenerado();
        String tipo = dia_seleccionado.getTipo();
        int id = dia_seleccionado.getId();
        int v = mysql_pedidos.setDiaPedido(fecha_peticion, dia_pedido, fecha_o, tipo, id);
        if (v != 0) {
            return true;
        }
        return false;
    }
    
    
}
