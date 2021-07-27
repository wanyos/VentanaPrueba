
package com.wanyos.modelo;

import com.wanyos.vista.InitApp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wanyos
 */
public class TratarPdfDiasGenerados implements Runnable {
    
    private List<String> cut_lines;
    private String line;
    private List<Integer> errores_escritura;
    private List<String> dias_pdf_leidos;
    private LeerPdf lpdf;
    private List<String> datos;
    private String ruta, nombre_archivo;
    
    
    public TratarPdfDiasGenerados(String ruta, String nombre_archivo) throws Exception{
        this.ruta = ruta;
        this.nombre_archivo = nombre_archivo;
    }
    
    public TratarPdfDiasGenerados(String ruta) throws Exception{
        this.ruta = ruta;
    }
    
    /**
     * Lista de dias leidos
     * @return 
     */
    public List<String> getDiasLeidosPdf(){
        return this.dias_pdf_leidos;
    }
    
    /**
     * Si existe un error en una línea se guarda por su número
     * @return 
     */
    public List<Integer> getErroreEscritura(){
        return this.errores_escritura;
    }
    
    
    /**
     * Devuelve una cadena con los números de las líneas que tienen error
     * @return 
     */
    public String getListaErroresLineas(){
        String errores = "";
        if(errores_escritura.isEmpty() || errores_escritura == null){
            errores = " No existen errores...";
        } else {
            errores = "Existen errores en las lineas: ";
            for(Integer l: errores_escritura){
                String n = String.valueOf(l);
                errores = errores.concat(n+" ");
            }
        }
        return errores;
    }
    
    
    private void setDatosPdf(List<String> datos_pdf){
        if(datos_pdf != null){
           List<String> extract_lines = extractLines(datos_pdf);
           dias_pdf_leidos = cutLines(extract_lines); 
        }
    }
    
    
    private void setLine(String line){
        this.line = line;
    }
    
    
    private String getLine(){
        return this.line;
    }
    
    
    /**
     * Cada pagina es un String, corta el string en lineas por el salto de cada linea
     * Si la linea contiene un día generado lo añade al array 
     * Se usa expresión regular para determinar que línea es un día generado
     * @param paginas 
     */
    private List<String> extractLines(List<String> paginas) {
        List<String> lines = new ArrayList<>();
        Pattern p = Pattern.compile("[L..\\s]*+[/../....]");  //patron que debe cumplir la linea de día generado

        if (!paginas.isEmpty()) {
            for (String aux : paginas) {
                String[] lineas = aux.split("\n");
                for (int a = 0; a < lineas.length; a++) {
                    String l = lineas[a];
                    Matcher m = p.matcher(l);
                    if (m.find() && (l.contains("A disposición") || l.contains("Disfrute"))) {
                        lines.add(lineas[a]);
                    }
                }
            }
        }
        return lines;
    }
    

    /**
     * Cada linea se separa por los campos: tipo, condicion, fecha_generado, fecha_cobro, fecha_disfrute
     * Cada línea se va pasando por cada uno de los métodos que extrae la información que necesita
     * Una vez que un método a extraido su información se crea un nuevo String sin esos datos
     * @param lines 
     */
    private List<String> cutLines(List<String> lines){
        this.errores_escritura = new ArrayList<>();
        cut_lines = new ArrayList<>();
        String tipo = "", condicion = "", fecha_generado = "", fecha_cobro = "", fecha_disfrute = "";
        
        if (!lines.isEmpty()) {
            for (String aux : lines) {
                tipo = this.getTipo(aux);
                condicion = this.getCondicion(this.getLine());
                fecha_generado = this.getFechaGenerado(this.getLine());
                fecha_cobro = this.getFechaCobroDisfrute(this.getLine());
                fecha_disfrute = this.getFechaCobroDisfrute(this.getLine());
              if(tipo.isEmpty() || tipo == null || fecha_generado.isEmpty() || fecha_generado == null){
                  cut_lines.add("Error: el tipo no existe o la fecha generado no existe");
                  //se usa el largo del array para saber en que línea está el error
                  this.errores_escritura.add(cut_lines.size());
              } else {
                  String final_line = tipo.concat(";").concat(condicion).concat(";").concat(fecha_generado).concat(";").
                                      concat(fecha_cobro).concat(";").concat(fecha_disfrute);
                cut_lines.add(final_line);
              }
            }
        }
        return cut_lines;
    }
    
    
    /**
     * Devuelve el tipo de libre
     * La cadena ha de comenzar con 'L'
     * Le quita a la linea los caracteres del tipo 
     * @param line
     * @return 
     */
    private String getTipo(String line) {
        int contador = 0;
        String tipo = null;
        String new_line;
        if (line.charAt(0) == 'L') {
            while (Character.isAlphabetic(line.charAt(contador)) || Character.isDigit(line.charAt(contador))) {
                contador++;
            }
            tipo = line.substring(0, contador).trim();
            new_line = line.substring(contador, line.length()).trim();
            this.setLine(new_line);
        }
        return tipo;
    }
    
    
    /**
     * Cadena que indica la condición del día
     * @param line
     * @return 
     */
    private String getCondicion(String line) {
        String condicion = "";
        String new_line;
        int a = 0;
        
        for (a = 0; a < line.length(); a++) {
            if (Character.isDigit(line.charAt(a))) {
                if (this.isFecha(line, a)) {
                    condicion = condicion.concat(line.substring(0, a));
                    break;
                }
            }
        }
        new_line = line.substring(a, line.length()).trim();
        this.setLine(new_line);
        return condicion;
    }
    
    
    /**
     * Si existe fecha generado la extrae de la cadena
     * por el formato de cada linea esta fecha a de ser la primera que encuentra
     * @param line
     * @return 
     */
    private String getFechaGenerado(String line) {
        String fecha_generado = null;
        String new_line;
        int contador = 0;
        boolean is_fecha = false;
        while (!is_fecha) {
            if (Character.isDigit(line.charAt(contador))) {
                if (this.isFecha(line, contador)) {
                    fecha_generado = line.substring(contador, contador + 10).trim();
                    is_fecha = true;
                }
            } else {
                contador++;
            }
        }
        new_line = line.substring(contador + 10, line.length()).trim();
        this.setLine(new_line);
        return fecha_generado;
    }
    
    
    /**
     * Vale para las dos opciones
     * O bien es una fecha o bien no tiene fecha y se devuelve '--'
     * @param line
     * @return 
     */
    private String getFechaCobroDisfrute(String line) {
        String fecha = null;
        String new_line;
        int contador = 0;
        int init = 0;
        while (!Character.isDigit(line.charAt(init)) && line.charAt(init) != '-') {
            init++;
        }
        contador = init;
        if (line.charAt(contador) == '-') {
            while (line.charAt(contador) == '-') {
                contador++;
            }
        } else {
            if (this.isFecha(line, init)) {
                contador = init + 10;
            }
        }
        fecha = line.substring(init, contador).trim();
        new_line = line.substring(contador, line.length()).trim();
        this.setLine(new_line);
        return fecha;
    }
    
    
    /**
     * Comprueba que una cadena tiene formato fecha
     * La cadena se extrae de la linea empezando en el caracter indicado y contado 
     * los caracteres que debe tener una fecha de la forma; dd/mm/yyyy
     * @param cadena
     * @param init_sub_string
     * @return 
     */
    private boolean isFecha(String linea, int init_sub_string) {
        String exp = "\\d{2}/\\d{2}/\\d{4}";
        String sub_cadena = "";
        if (linea.length() >= 10) {
            sub_cadena = linea.substring(init_sub_string, init_sub_string + 10);
        }
        return Pattern.matches(exp, sub_cadena);
    }

    @Override
    public void run() {
        try {
            if(nombre_archivo != null){
               lpdf = new LeerPdf(ruta, nombre_archivo);   
            } else {
                lpdf = new LeerPdf(ruta);
            }
            datos = lpdf.getDatosPdf();
            setDatosPdf(datos);
        } catch (Exception ex) {
            InitApp.setMensajeLbl("Erro run() TratarPdfDiasGenerados... "+ex.getMessage());
        }
    }

    
    
    
}
