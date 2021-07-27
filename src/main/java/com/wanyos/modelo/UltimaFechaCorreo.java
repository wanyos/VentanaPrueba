
package com.wanyos.modelo;

import com.wanyos.vista.InitApp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Guarda la última fecha de correo leido 
 * Se crea un archivo con la fecha serializada
 * @author wanyos
 */
public class UltimaFechaCorreo implements Serializable {
    
    private File archivo_fecha;
    private final String RUTA_ARCHIVO = "ultima_fecha_correo";
    
    
    public UltimaFechaCorreo(){
        archivo_fecha = new File(RUTA_ARCHIVO);
    }
    
  
    public void setGuardarFecha (LocalDate fecha) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(archivo_fecha));
            out.writeObject(fecha);
            out.close();
        } catch (IOException ex) {
            InitApp.setMensajeLbl("!!!Error escritura archivo setGuardarFecha() UltimaFechaCorreo... "+ex.getMessage());
        }
    }
     
    
    public LocalDate getUltimaFecha () {
        LocalDate fecha = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(archivo_fecha));
            fecha = (LocalDate) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            InitApp.setMensajeLbl("!!!Error lectura archivo fecha correo getUltimaFecha() UltimaFechaCorreo... "+e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                InitApp.setMensajeLbl("Error cerrar archivo UltimaFechaCorreo... "+ex.getMessage());
            }
        }
        return fecha;
    }
    
   
    
    
//    public static void main(String[] args) {
//        UltimaFechaCorreo f = new UltimaFechaCorreo();
//        LocalDate fecha = LocalDate.of(2021, 06, 28);
//        f.escribirObjeto(fecha);
//        LocalDate fecha_leida = f.leerObjetoArchivo();
//        System.out.println("");
//    }
    
}
