
package com.wanyos.controlador;

import com.wanyos.modelo.CalculoLibres;
import com.wanyos.modelo.dao.MySqlCalendarioDao;
import com.wanyos.modelo.dao.MySqlManagerDao;
import com.wanyos.vista.PnAbstract;
import com.wanyos.vista.PnCalendario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;

/**
 *
 * @author wanyos
 */
public class CtrCalendario {
    
    private final PnCalendario pn_calendario;
    private final MySqlManagerDao manager_dao;
    private final MySqlCalendarioDao mysql_calendario;
    private CalculoLibres cal_libres;
    private List<LocalDate> lista_libres, lista_subgrupo, lista_sub_comunes;
    
    
    public CtrCalendario(JLabel lbl_mensaje){
        manager_dao = new MySqlManagerDao(); 
        mysql_calendario = manager_dao.getCalendarioDao();
        pn_calendario = new PnCalendario(lbl_mensaje, this);
        if(mysql_calendario == null){
            pn_calendario.setMensajeLbl("Error no existe conexión con la BD...");
        }
    }
    
     public PnAbstract getPnCalendario(){
        if(mysql_calendario != null){
            return this.pn_calendario;
        }
        return null;
    }
     
     
    /**
     * Convierte la lista de fechas en un mapa de meses con valor entero
     * y los dias del mes que necesita cada lista del mapa
     * @return 
     */ 
    private Map<Integer, List<String>> getMapa(List<LocalDate> lista_fechas) {
        Map<Integer, List<String>> mapa = new HashMap<>();
        for (int a = 1; a <= 12; a++) {
            mapa.put(a, new ArrayList<>());
        }
        for (LocalDate aux : lista_fechas) {
            int mes = aux.getMonthValue();
            String d = String.valueOf(aux.getDayOfMonth());
            List<String> lista_map = mapa.get(mes);
            lista_map.add(d);
        }
        return mapa;
    }
    
    
    public Map<Integer, List<String>> getListaLibres(){
        Map<Integer, List<String>> map_libres = new HashMap<>();
        if(lista_libres != null && !lista_libres.isEmpty()){
            return map_libres = getMapa(lista_libres);
        }
        return map_libres;
    }
    
    public Map<Integer, List<String>> getListaSubgrupo(){
        Map<Integer, List<String>> map_subgrupo = new HashMap<>();
        if(lista_subgrupo != null && !lista_subgrupo.isEmpty()){
            return map_subgrupo = getMapa(lista_subgrupo);
        }
        return map_subgrupo;
    }
    
    public Map<Integer, List<String>> getListaSubComunes(){
        Map<Integer, List<String>> map_comunes = new HashMap<>();
        if(lista_sub_comunes != null && !lista_sub_comunes.isEmpty()){
           return map_comunes = getMapa(lista_sub_comunes);
        }
        return map_comunes;
    }
    
    public Map<Integer, List<String>> getListaFestivos(int year){
        Map<Integer, List<String>> map_festivos = new HashMap<>();
        List<LocalDate> lista = mysql_calendario.getFestivos(year);
        if(lista != null && !lista.isEmpty()){
            return map_festivos = getMapa(lista);
        }
        return map_festivos;
    }
    
    
    /**
     * Se piden los libres por el tipo, grupo-subgrupo-sub_comunes
     * @param year
     * @param tipo
     * @return 
     */
    public Map<Integer, List<String>> getLibresUsr(int year, String tipo){
        Map<Integer, List<String>> map_usr = new HashMap<>();
        List<LocalDate> libres = mysql_calendario.getLibreUsr(year, tipo);
        if(libres != null && !libres.isEmpty()){
            return map_usr = getMapa(libres);
        }
        return map_usr;
    }
    
    public Map<Integer, List<String>> getListaVacacionUsr(int year){
         Map<Integer, List<String>> map_vacacion = new HashMap<>();
         List<LocalDate> lista = mysql_calendario.getListaVacacionUsr(year);
         if(lista != null && !lista.isEmpty()){
             return map_vacacion = getMapa(lista);
         }
         return map_vacacion;
    }
    
    
    public Map<Integer, List<String>> getListaPedidosUsr(int year){
         Map<Integer, List<String>> map_pedidos = new HashMap<>();
         List<LocalDate> lista_pedidos = mysql_calendario.getDiasPedidos(year);
         if(lista_pedidos != null && !lista_pedidos.isEmpty()){
             return map_pedidos = getMapa(lista_pedidos);
         }
         return map_pedidos;
    }
   
    /**
     * Todos los dias concedidos a librar que se han pedido en el año del parametro
     * @param year
     * @return 
     */
    public Map<Integer, List<String>> getListaConcedidosUsr(int year){
         Map<Integer, List<String>> map_concedidos = new HashMap<>();
         List<LocalDate> lista_concedidos = mysql_calendario.getDiasConcedidos(year);
         if(lista_concedidos != null && !lista_concedidos.isEmpty()){
             return map_concedidos = getMapa(lista_concedidos);
         }
         return map_concedidos;
    }
    
     
    /**
     * Crea el objeto CalculoLibres y le pide calcular los libres del parametro
     * @param year
     * @param grupo
     * @param subgrupo 
     */
    public void setDiasLibres(int year, String grupo, String subgrupo) {
        cal_libres = new CalculoLibres();
        if (grupo.equalsIgnoreCase("usr")) {

        } else {
            int g = Integer.parseInt(grupo);
            cal_libres.setListaLibresYear(year, g);
            lista_libres = cal_libres.getListaLibres(year);
            if (year >= 2020) {
                lista_subgrupo = cal_libres.lista_dias_nuevo_subgrupo(g, subgrupo, year);
                lista_sub_comunes = cal_libres.getSubgrupoComunes(g, subgrupo, year);
            } else if (year < 2020) {
                lista_subgrupo = cal_libres.lista_dias_subgrupo(g, subgrupo, year);
            }
        }
    }
    
}
