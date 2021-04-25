
package com.wanyos.modelo;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee el pdf de la ruta y lo devuelve en forma de texto
 * @author wanyos
 */
public class LeerPdf {
    
     private List<String> datos_pdf;

     
    public LeerPdf(String ruta, String nombre_archivo) throws Exception {
        comprobarArchivo(ruta, nombre_archivo);
    }
    
    public LeerPdf(String ruta) throws Exception {
        File archivo = new File(ruta);
        if (archivo != null && archivo.exists()) {
            this.lecturaPdf(archivo.getAbsolutePath());
        } else {
            throw new IOException();
        }
    }
    
    /**
     * Comprueba la existencia del archivo
     * @param ruta
     */
    private void comprobarArchivo(String ruta, String nombre_archivo) throws Exception {
        File archivo = new File(ruta + "\\" + nombre_archivo);
        //leer pdf
        if(!archivo.exists()){
           throw new IOException(); 
        } 
        this.lecturaPdf(archivo.getAbsolutePath());
    }

    
    /**
     * Lee un String por página del pdf
     */
    private void lecturaPdf(String ruta) throws Exception {
        datos_pdf = new ArrayList<>();
        String contenido;
        PdfReader lector = null;

        lector = new PdfReader(ruta);
        int totalPaginas = lector.getNumberOfPages();
        // por cada pagina, leeremos su contenido
        for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
            contenido = PdfTextExtractor.getTextFromPage(lector, iPagina).trim();
            datos_pdf.add(contenido);
        }

        if (lector != null) {
            lector.close();
        }
    }
    
    
    public List<String> getDatosPdf(){
        return this.datos_pdf;
    }
    
    
    
}
