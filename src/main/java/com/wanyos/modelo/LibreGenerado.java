
package com.wanyos.modelo;

import java.time.LocalDate;

/**
 *
 * @author wanyos
 */
public class LibreGenerado {
    
    private int id;
    private String tipo;
    private LocalDate fecha_generado, fecha_cobro, fecha_disfrute;

    public LibreGenerado(int id, String tipo, LocalDate fecha_generado, LocalDate fecha_cobro, LocalDate fecha_disfrute) {
        this.id = id;
        this.tipo = tipo;
        this.fecha_generado = fecha_generado;
        this.fecha_cobro = fecha_cobro;
        this.fecha_disfrute = fecha_disfrute;
    }
    
    public int getId(){
        return this.id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaGenerado() {
        return fecha_generado;
    }
    
    public LocalDate getFechaCobro() {
        return fecha_cobro;
    }

    public void setFechaCobro(LocalDate fecha_cobro) {
        this.fecha_cobro = fecha_cobro;
    }

    public LocalDate getFechaDisfrute() {
        return fecha_disfrute;
    }

    public void setFechaDisfrute(LocalDate fecha_disfrute) {
        this.fecha_disfrute = fecha_disfrute;
    }

   
    
    
}
