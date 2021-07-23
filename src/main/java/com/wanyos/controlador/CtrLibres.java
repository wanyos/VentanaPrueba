
package com.wanyos.controlador;

import com.wanyos.modelo.LibreGenerado;
import com.wanyos.modelo.TratarPdfDiasGenerados;
import com.wanyos.modelo.dao.MySqlLibreGeneradoDao;
import com.wanyos.modelo.dao.MySqlManagerDao;
import com.wanyos.modelo.dao.MySqlPedidoDao;
import com.wanyos.vista.InitApp;
import com.wanyos.vista.PnAbstract;
import com.wanyos.vista.PnLibreGenerado;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author wanyos
 */
public class CtrLibres {
    
    private PnLibreGenerado pn_libres;
    private MySqlManagerDao manager_dao;
    private MySqlLibreGeneradoDao mysql_libre_generado;
    private MySqlPedidoDao mysql_dia_pedido;
    private JProgressBar barra;
    private List<LibreGenerado> lista_nuevos, lista_cambios;
    
    
//    public CtrLibres(){}
    
    public CtrLibres() {
        this.barra = InitApp.getBarraPs();
        manager_dao = new MySqlManagerDao();
        mysql_libre_generado = manager_dao.getLibreDao();
        mysql_dia_pedido = manager_dao.getPedidosDao();
        pn_libres = new PnLibreGenerado(this);
        if (mysql_libre_generado == null) {
            pn_libres.setMensajeLbl("Error no existe conexión con la BD...");
        }
    }
    
     public PnAbstract getPnLibres(){
        if(mysql_libre_generado != null){
            return this.pn_libres;
        }
        return null;
    }
     
     
     public List<LibreGenerado> getListadoLibres(String tipo, boolean todos) {
        List<LibreGenerado> datos = mysql_libre_generado.getListadoLibres(tipo, todos);
        return datos;
    }
     
    
    public String [] getTipoLibres(){
        String [] tipo_libres = mysql_libre_generado.getTipoLibres();
        if(tipo_libres == null){
            tipo_libres = new String[] {""};
        }
        return tipo_libres;
    }
    
    public boolean setEditarLibre(String id_libre, String fecha_c, String fecha_d) {
        LocalDate fecha_cobro = LocalDate.of(0, 1, 1), fecha_disfrute = LocalDate.of(0, 1, 1);
        try {
            int id = Integer.parseInt(id_libre);
            if (!fecha_c.equalsIgnoreCase("--")) {
                fecha_cobro = LocalDate.parse(fecha_c, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (!fecha_d.equalsIgnoreCase("--")) {
                fecha_disfrute = LocalDate.parse(fecha_d, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            int v = mysql_libre_generado.setEditarLibre(id, fecha_cobro, fecha_disfrute);
            if (v != 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public boolean setEliminarLibre(String id_libre){
        try {
            int id = Integer.parseInt(id_libre);
            int v = mysql_libre_generado.EliminarLibre(id);
            if(v != 0){
                return true;
            }
        } catch(Exception e){
            return false;
        }
        return false;
    }
    
   
    /**
     * Obtener el archivo pdf libres
     * Comprobar fecha de la ultima actualizacion bd y fecha del nuevo archivo
     * Crear copia de seguridad tabla generado
     * Renombrar copia de seguridad. Existen dos copias, copia0 y copia1
     * Se elimina copia0 y se renombra copia1 como copia0, la nueva copia se llamara copia1
     * Obtener la lista LibreGenerado para actualziar la bd
     * Actualizar la bd
     */
    public void actualizarDiasGenerados() {
        File archivo_libres = getFileLibres();               //obtener el archivo más reciente con los libres 

        if (archivo_libres == null) {
            pn_libres.setMensajeLbl("No existe archivo libres nuevo...");
        } else {
            if (!isPosterior(archivo_libres)) {
                pn_libres.setMensajeLbl("El archivo de libres nuevo es anterior a la última actualización...");
            } else {
                Worker w = new Worker(archivo_libres);
                w.addPropertyChangeListener(new OyentePropiedadBarraPs(barra, 100));
                w.execute();
            }
        }
    }
    
    
    
    
    
     /**
     * Obtiene el archivo más actual de la carpeta "ruta_dias_generados"
     * @return archivo pdf de libres 
     */
    private File getFileLibres() {
        String pat = ".*\\.pdf";
        File archivos = new File(getDirectorioArchivos());
        File[] d = archivos.listFiles();
        LocalDate fecha_last = null;
        File archivo_libres = null;

        for (File aux : d) {
            if (!aux.isDirectory() && Pattern.matches(pat, aux.getName())) { //no es un directorio y es un .pdf
                long milisegundos = aux.lastModified();
                LocalDate fecha_modificacion = Instant.ofEpochMilli(milisegundos).atZone(ZoneId.systemDefault()).toLocalDate();

                if (fecha_last == null || fecha_modificacion.isAfter(fecha_last)) {
                    fecha_last = fecha_modificacion;
                    archivo_libres = aux;
                }
            }
        }
        return archivo_libres;
    }

    
    private String getDirectorioArchivos() {
        String ruta_archivos = "C:\\Users\\wanyos\\Desktop\\pdf_emt\\libres";
        File file = new File(ruta_archivos);
        while(!file.exists() || !file.isDirectory()){
            ruta_archivos = JOptionPane.showInputDialog(pn_libres, "Ruta archivos pdf...", "Error en ruta archivos", JOptionPane.ERROR_MESSAGE);
            file = new File(ruta_archivos);
        }
        return ruta_archivos;
    }
    
        

    /**
     * Fecha de archivo es posterior a la ultima actualizacion de base de datos
     * @param archivo_libres
     * @return 
     */
    private boolean isPosterior(File archivo_libres) {
        LocalDateTime fecha_ultima_actualizacion = mysql_libre_generado.getFechaCrearTabla("copia1_generado");
        long milisegundos = archivo_libres.lastModified();
        LocalDateTime fecha_archivo_libres = Instant.ofEpochMilli(milisegundos).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (fecha_ultima_actualizacion != null && fecha_archivo_libres.isAfter(fecha_ultima_actualizacion)) {
            return true;
        }
        return true;  //este método no funcionara se eliminara en el futuro
    }
    
    
    
    
    
    /**
     * SwingWorker<T,V> T valor que va ha devolver la tarea pesada, V valor necesario para actualizar
     * V es el tipo de datos que admiten los métodos publish y process
     * en caso de barra progreso sera Inteeger, en caso de un label un String
     * El método que hace la tarea pesada en doInBackground, el done() muestra el resultado en pantalla 
     * si necesitamos el resultado llamamos a get() pero esto bloquea el hilo y la aplicación
     * el método publish() cambia de hilo automaticamente para llamar a process() que va indicando como va el proceso
     * para una barra de progreso solo se pasa un entero al metodo process que es el valor que se necesita para incremetnar la barra
     */
    private class Worker extends SwingWorker<Integer, Integer> {

        private int v = 0;
        private File archivo_libres;
        
        
        public Worker(File archivo_libres) {
           this.archivo_libres = archivo_libres;
        }
        
        
        /**
         * Lee el archivo pdf del parametro y lo convierte a una lista de dias generados
         * @param archivo_libres
         */
        private List<String> getListaLibres(File archivo_libres) {
            List<String> lista_dias_limpia = null;
            pn_libres.setMensajeLbl("Leyendo archivo pdf...");
            
            try {
                String ruta = archivo_libres.getPath();
                TratarPdfDiasGenerados libres_pdf = new TratarPdfDiasGenerados(ruta);
                Thread h1 = new Thread(libres_pdf);
                h1.start();
                while (libres_pdf.getDiasLeidosPdf() == null) {
                    this.setProgress(getValorBarra());
                }
                h1.join();
                List<String> lista_dias = libres_pdf.getDiasLeidosPdf();

                this.setProgress(getValorBarra());

                if (lista_dias == null || lista_dias.isEmpty()) {
                    pn_libres.setMensajeLbl("No existen dias generados en la lista...");
                } else {
                    lista_dias_limpia = getLimpiarLista(lista_dias);
                }

            } catch (Exception ex) {
                pn_libres.setMensajeLbl("No se ha podido leer el archivo libres...");
            }
            pn_libres.setMensajeLbl(" --- ");
            return lista_dias_limpia;
        }
        
        /**
         * Elimina los datos que no necesita un libre generado obtiene de cada
         * linea los datos que acepta la tabla de la base datos
         * @param lista_dias
         * @return
         */
        private List<String> getLimpiarLista(List<String> lista_dias) {
            List<String> lista = new ArrayList<>();
            String n = "";

            for (String aux : lista_dias) {
                this.setProgress(getValorBarra());
                String[] par = aux.split(";");
                n = n.concat(par[0] + ";").trim();
                n = n.concat(par[2] + ";").trim();
                String f_c = getFormatoFecha(par[3]);
                String f_d = getFormatoFecha(par[4]);
                n = n.concat(f_c + ";");
                n = n.concat(f_d + ";");
                lista.add(n);
                n = "";
            }
            return lista;
        }

        private String getFormatoFecha(String f) {
            String f_format = f;
            if (f_format.equalsIgnoreCase("--")) {
                f_format = "0001-01-01";
            }
            return f_format;
        }
        
         /**
         * Recorre los dias generados y crea listas por tipo 
         * Recoge de la BD los dias que tenga del mismo tipo 
         * Llama al metodo que comparara ambas listas
         * @param lista_nueva_generados
         */
        private void setListaDiasTipo(List<LibreGenerado> lista_nueva_generados) {
            lista_nuevos = new ArrayList<>();
            lista_cambios = new ArrayList<>(); //se crean en este método para no sobreescribir los datos usado en setListaNuevosCambios
            List<LibreGenerado> lista_aux = new ArrayList<>();
            int contador = 0;
            for (LibreGenerado aux : lista_nueva_generados) {
                this.setProgress(getValorBarra());
                if (!lista_aux.isEmpty() && !aux.getTipo().equalsIgnoreCase(lista_aux.get(lista_aux.size() - 1).getTipo())) {
                    lista_aux.clear();
                }
                lista_aux.add(aux);
                contador++;
                if (contador >= lista_nueva_generados.size()) {
                    setBaseDatos(lista_aux, getListaTipoBD(aux.getTipo()));
                } else if (!aux.getTipo().equalsIgnoreCase(lista_nueva_generados.get(contador).getTipo())) {
                    setBaseDatos(lista_aux, getListaTipoBD(aux.getTipo()));
                }
            }
        }

        private List<LibreGenerado> getListaTipoBD(String tipo) {
            List<LibreGenerado> lista_tipo_bd = new ArrayList<>();
            lista_tipo_bd = mysql_libre_generado.getListaTipo(tipo);
            return lista_tipo_bd;
        }
        
        
        private void setBaseDatos(List<LibreGenerado> lista_tipo_nueva, List<LibreGenerado> lista_tipo_bd) {
            if (lista_tipo_bd == null || lista_tipo_bd.isEmpty()) {
                //guardar toda la lista en la BD
                this.setListaDiasGeneradosBD(lista_tipo_nueva);
            } else {
                setListaNuevosCambios(lista_tipo_nueva, lista_tipo_bd);
            }
        }
    
        /**
         * Edita los dias men la base de datos que tienen algún cambio, guarda estos cambios en la list cambios
         * Si existen dias nuevos los guarda en la base de datos y en la lista nuevos
         * Una vez terminado se llama a la función actualizar pedidos que eliminara de la tabla dia_pedido aquellos dias 
         * que han sido concedidos y se han confirmado
         * @param lista_nueva
         * @param lista_bd 
         */
        private void setListaNuevosCambios(List<LibreGenerado> lista_nueva, List<LibreGenerado> lista_bd) {
            int contador;
            Iterator<LibreGenerado> it;

            for (LibreGenerado aux_ln : lista_nueva) {
                this.setProgress(getValorBarra());
                contador = 0;
                it = lista_bd.iterator();
                while (it.hasNext()) {
                    contador++;
                    LibreGenerado aux_bd = it.next();
                    if (aux_ln.getFechaGenerado().isEqual(aux_bd.getFechaGenerado())) {
                        if (!(aux_ln.getFechaCobro().isEqual(aux_bd.getFechaCobro())) || !(aux_ln.getFechaDisfrute().isEqual(aux_bd.getFechaDisfrute()))) {
                            //modificar el aux_ln en bd
                            mysql_libre_generado.setEditarLibre(aux_bd.getId(), aux_ln.getFechaCobro(), aux_ln.getFechaDisfrute());
                            lista_cambios.add(aux_bd);
                        }
                        //son iguales o ya se ha modificado
                        it.remove();
                        contador = lista_bd.size() - 1;
                        break;
                    }
                }
                if (contador >= lista_bd.size()) {
                    lista_nuevos.add(aux_ln);
                }
            }
            if (!lista_nuevos.isEmpty()) {
                this.setListaDiasGeneradosBD(lista_nuevos);
            }
            actualizarPedidos(); //actualiza la tabla dia_pedido
        }

    
    private void setListaDiasGeneradosBD(List<LibreGenerado> lista){
        for(LibreGenerado aux: lista){
            this.setProgress(getValorBarra());
            mysql_libre_generado.setDiaGenerado(aux);
        }
    }
    
    /**
     * Si en la lista de cambios existe un dia que estaba pendiente de confirmación porque se ha pedido
     * se elimina de la tabla dia_pedido
     */
        private void actualizarPedidos() {
            if (!lista_cambios.isEmpty()) {
                for (LibreGenerado aux : lista_cambios) {
                    if (mysql_dia_pedido.existePedido(aux.getId())) {
                        mysql_dia_pedido.removePedido(aux.getId());
                    }
                }
            }
        }
        
        
        /**
         * Convierte la lista en una nueva lista con formato LibreGenerado para la base de datos
         * @param lista_dias
         * @return
         */
        private List<LibreGenerado> getDiasGenerados(List<String> lista_dias) {
            List<LibreGenerado> lista_generado = new ArrayList<>();

            for (int a = 0; a < lista_dias.size(); a++) {
                this.setProgress(getValorBarra());
                String linea = lista_dias.get(a);
                String[] datos = linea.split(";");
                int id = a + 1;
                String tipo = datos[0];
                LocalDate fecha_g = getFecha(datos[1]);
                LocalDate fecha_c = getFecha(datos[2]);
                LocalDate fecha_d = getFecha(datos[3]);
                LibreGenerado libre = new LibreGenerado(id, tipo, fecha_g, fecha_c, fecha_d);
                lista_generado.add(libre);
            }
            return lista_generado;
        }

        
        private LocalDate getFecha(String f) {
            String pat = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            LocalDate local_date = LocalDate.of(0001, 01, 01);
            if (Pattern.matches(pat, f)) {
                return local_date;
            }
            local_date = LocalDate.parse(f, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return local_date;
        }
        
        private int getValorBarra(){
            if(v >= 100){
                v = 0;
            }
            return ++v;
        }
        
        
        private boolean copiasTablaGenerado() {
            boolean correcto = false;
            pn_libres.setMensajeLbl("Creando copia tabla...");
            try {
                MySqlLibreGeneradoDao hilo_mysql = manager_dao.getLibreDao();
                Thread ta1 = new Thread(hilo_mysql);
                ta1.start();
                while (hilo_mysql.getMensaje().equalsIgnoreCase("")) {
                    this.setProgress(getValorBarra());
                }
                ta1.join();
                String mensaje = hilo_mysql.getMensaje();

                if (mensaje.equalsIgnoreCase("correcto")) {
                    correcto = true;
                }

            } catch (InterruptedException ex) {
                pn_libres.setMensajeLbl("Error hilo copias tabla generado...");
            }
            pn_libres.setMensajeLbl(" --- ");
            return correcto;
        }
        
        
        @Override
        protected Integer doInBackground() throws Exception {
            this.setProgress(getValorBarra());
            boolean correcto = copiasTablaGenerado();

            if (correcto) {
                List<String> lista_libres = getListaLibres(archivo_libres);
                if (lista_libres != null) {
                    setListaDiasTipo(getDiasGenerados(lista_libres));
                    pn_libres.setPnNuevosCambios(lista_nuevos, lista_cambios);
                }
            } else {
                pn_libres.setMensajeLbl("No es posible continuar, no se han actualizado las copias de tabla generado...");
            }
            return v;
        }
        
       
        @Override
        protected void done() {
            barra.setVisible(false);
        }

    }
    
    


       
    
}
    
    

    
    
