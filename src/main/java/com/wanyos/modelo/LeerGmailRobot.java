package com.wanyos.modelo;

import com.wanyos.controlador.OyentePropiedadBarraPs;
import com.wanyos.vista.Hilo;
import com.wanyos.vista.InitApp;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * Clase que lee correo de gmail. Necesarias las librerias activation, javamail
 * @author wanyos
 */
public class LeerGmailRobot extends SwingWorker<Map, Integer> {

    private final String CUENTA = "juanjor99@gmail.com";
    private final String PASSWORD = "8980Fky13923";
    private final String CARPETA_LEER = "robot";  //carpeta de gmail que se lee
    private Map<LocalDate, String[]> map;
    private LocalDate fecha_limit;
    private boolean todos;
    private int v, total_correos;
    private JProgressBar barra;
    private ModeloLista ml;
    private Hilo hilo;
    
    
    
    public LeerGmailRobot(boolean todos, LocalDate fecha, ModeloLista ml, Hilo h) {
        map = new HashMap<>();
        this.todos = todos;
        this.fecha_limit = fecha;
        v = 0;
        this.ml = ml;
        this.hilo = h;
        barra = InitApp.getBarraPs();
    }
    

    
    @Override
    protected Map<LocalDate, String[]> doInBackground() throws Exception {
        List<MensajeCorreo> lista_mensaje = new ArrayList<>();
        try {
            Message m[] = getFolder().getMessages();
            if (m != null && m.length > 0) {
                
                total_correos = m.length;
                new OyentePropiedadBarraPs(barra, total_correos);
                
                //recibe paneles y calendar, mientras este la barra visible los bloquea
                hilo.setMsm("Leyendo...");
                hilo.start();
                
                for (Message message : m) {
                    publish(new Integer(v++));
                    String titulo = message.getSubject();
                    LocalDate fecha_correo = message.getReceivedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int numero = message.getMessageNumber();
                    String contenido = message.getContent().toString().trim();
                    String cadena_final = tratarCadena(contenido).trim();

                    lista_mensaje.add(new MensajeCorreo(titulo, fecha_correo, numero, cadena_final));
                }
            }
        } catch (MessagingException | IOException ex) {
            InitApp.setMensajeLbl("!!!Error no existen mensakes... "+ex.getMessage());
        }

        leerCorreos(lista_mensaje);
        return map;
    }
    
    
    @Override
    protected void process(java.util.List<Integer> lista) {
        if (!isCancelled()) {
            Integer parteCompletada = lista.get(lista.size() - 1);
            barra.setValue(parteCompletada.intValue());
        }
    }
    
    
    @Override
    public void done(){
        try {
            map = get();
            ml.setMap(map);
            barra.setVisible(false);
        } catch (InterruptedException ex) {
            InitApp.setMensajeLbl("Error metodo done() LeerGmail... "+ex.getMessage());
        } catch (ExecutionException ex) {
            InitApp.setMensajeLbl("Error metodo done() LeerGmail... "+ex.getMessage());
        }
    }
        
    
    /**
     * Configura todo para leer correos de gmail
     * @return carpeta de correos obtenida
     */
    private Folder getFolder() {
        Properties props = new Properties();
        Folder inbox = null;
        try {
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", CUENTA, PASSWORD);
            inbox = store.getFolder(CARPETA_LEER);
            inbox.open(Folder.READ_ONLY);
        } catch (NoSuchProviderException ex) {
            InitApp.setMensajeLbl("!!!Error lectura gmail getFolder() LeerGmail..." + ex.getMessage());
        } catch (MessagingException ex) {
            InitApp.setMensajeLbl("!!!Error lectura gmail getFolder() LeerGmail..." + ex.getMessage());
        }
        return inbox;
    }
    
     /**
     * Limpia la cadena etiquetas, tildes y la pone en minusculas
     * @param contenido
     * @return
     */
    private String tratarCadena(String contenido) {
        //cortar el contenido por la tabla de los datos
        int inicio = contenido.indexOf("<table ");
        int fin = contenido.indexOf("</table>");
        String corte = contenido.substring(inicio, fin).trim();

        //eliminar etiquetas html
        corte = corte.replaceAll("\\<.*?\\>", "").trim();

        //quitar acentos
        String cadena_normalize = Normalizer.normalize(corte, Normalizer.Form.NFD).trim();
        String cadena_sin_acentos = cadena_normalize.replaceAll("[^\\p{ASCII}]", "").trim();

        //pasar a minusculas
        String cadena_final = cadena_sin_acentos.toLowerCase().trim();
        return cadena_final;
    }

    
    private void leerCorreos(List<MensajeCorreo> lista) {
        if (!lista.isEmpty()) {
            if (!todos && fecha_limit == null) {
                this.setUltimaFecha();
            } 
                
            for (MensajeCorreo aux : lista) {
                if (fecha_limit != null && aux.getFechaCorreo().isAfter(fecha_limit)) {
                    tratarMensajes(aux.getTitulo(), aux.getFechaCorreo(), aux.getNumero(), aux.getContenido());
                } else {
                    tratarMensajes(aux.getTitulo(), aux.getFechaCorreo(), aux.getNumero(), aux.getContenido());
                }
                 hilo.setMsm("Tratar mensajes");
            }
        }
    }

    
    private void setUltimaFecha() {
        UltimaFechaCorreo uf = new UltimaFechaCorreo();
        LocalDate fecha = uf.getUltimaFecha();
        if (fecha != null) {
            fecha_limit = fecha;
        }
    }
    
    /**
     * Escoge el titulo del correo y lo manda a su metodo para el proceso
     * @param titulo
     * @param fecha
     * @param numero
     * @param contenido
     */
    private void tratarMensajes(String titulo, LocalDate fecha_correo, int numero, String contenido) {
        String corte_titulo = getCorteTitulo(titulo);
        List<String> lista = this.getListaTitulos();

        if (corte_titulo.equalsIgnoreCase(lista.get(0))) {
            //tratarLibres(fecha_correo, contenido);

        } else if (corte_titulo.equalsIgnoreCase(lista.get(1))) {
            tratarNombramiento(fecha_correo, contenido);
            

        } else if (corte_titulo.equalsIgnoreCase(lista.get(2))) {
            //tratarHistorico(fecha_correo, contenido);

        } else if (corte_titulo.equalsIgnoreCase(lista.get(3))) {
            //minutos

        } else if (corte_titulo.equalsIgnoreCase(lista.get(4))) {
            //ajuste nomina
        }
    }
    
    private List<String> getListaTitulos() {
        List<String> lista = new ArrayList<>();
        lista.add("tu consulta de libres");
        lista.add("tu nombramiento de servicio");
        lista.add("tu historico de nombramiento de servicio");
        lista.add("minutos acumulados");
        lista.add("tus ajustes de nómina");
        return lista;
    }

    
    /**
     * Corta el titulo del correo para escoger solo la segunda parte
     * @param titulo
     * @return
     */
    private String getCorteTitulo(String titulo) {
        String[] part = titulo.split("-");
        String corte = part[1].trim();
        return corte;
    }
    
    
    
    /**
     * Quita todos los saltos
     * @param fecha
     * @param contenido
     */
    private void tratarNombramiento(LocalDate fecha, String contenido) {
         hilo.setMsm("Tratar nombramiento");
        List<String> datos_limpios = new ArrayList<>();
        datos_limpios = new ArrayList<>();
        String l = contenido.replace("\n", "").trim();
        String[] partes = l.split(": ");
        for (String aux : partes) {
            v++;
            String[] partes1 = aux.split("\r");
            for (String aux1 : partes1) {
                if (!aux1.isEmpty()) {
                    datos_limpios.add(aux1);
                }
            }
        }
        crearDatosTurno(datos_limpios);
    }
    
     private void crearDatosTurno(List<String> datos) {
         hilo.setMsm("crear datos turno");
        String[] turno = new String[14]; //turno,linea /fi,hi,li,ff,hf,lf /fip,hip,lip,ffp,hfp,lfp
        String fecha = null;
        int cont = 0;
        for (String aux : datos) {
            v++;
            if (aux.equalsIgnoreCase("linea")) {
                cont++;
                break;
            }
            cont++;
        }

        for (int a = 0; a < datos.size() && cont < datos.size(); a++) {
            if (isFecha(datos.get(cont))) {
                String[] div = datos.get(cont).split(" ");
                turno[a++] = div[0];
                if (fecha == null) {
                    fecha = div[0];
                }
                turno[a] = div[1];
                cont += 2;
            } else {
                turno[a] = datos.get(cont);
                cont += 2;
            }
        }
        if (fecha != null) {
            setTurnos(fecha, turno);
            fecha = null;
        }
    }
     
        private boolean isFecha(String c) {
            String pattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}.*";
            return Pattern.matches(pattern, c);
        }

        /**
         * Guarda los datos del turno junto con su fecha Solo admite un turno por dia
         * @param f
         * @param t
         */
        private void setTurnos(String f, String[] t) {
             hilo.setMsm("Set turno");
            LocalDate fecha = LocalDate.parse(f, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            map.put(fecha, t);
        }
        
          

    

    }






    
    
//    public static void main(String[] args) {
//        LeerGmailRobot lm = new LeerGmailRobot();
//        lm.init();
//        LocalDate fecha_last = LocalDate.of(2021, 1, 1);
//        lm.leerCorreosFecha(fecha_last);
//        HashMap map = (HashMap) lm.getMapTurnos();
//
//        for (Object clave : map.keySet()) {
//            String[] valor = (String[]) map.get(clave);
//            System.out.println("Fecha: " + clave);
//            for (String aux : valor) {
//                if (aux != null) {
//                    System.out.println(aux);
//                }
//            }
//            System.out.println("---------------------------------------------------------");
//        }
//    }

   

   


