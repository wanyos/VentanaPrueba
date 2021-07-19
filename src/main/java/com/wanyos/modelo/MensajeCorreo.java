
package com.wanyos.modelo;

import java.time.LocalDate;

/**
 *
 * @author wanyos
 */
public class MensajeCorreo {
    
   private String titulo, contenido;
   private LocalDate fecha_correo;
   private int numero;

   
    public MensajeCorreo(String titulo, LocalDate fecha_correo, int numero, String contenido) {
        this.titulo = titulo;
        this.fecha_correo = fecha_correo;
        this.numero = numero;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaCorreo() {
        return fecha_correo;
    }

    public void setFechaCorreo(LocalDate fecha_correo) {
        this.fecha_correo = fecha_correo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    
                    
   
    
    
}
