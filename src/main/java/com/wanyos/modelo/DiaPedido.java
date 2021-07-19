
package com.wanyos.modelo;

import java.time.LocalDate;

/**
 *
 * @author wanyos
 */
public class DiaPedido {
    
    private LocalDate fecha_peticion, fecha_pedido, fecha_ofrecido;
    private String tipo;
    private int id;

    public DiaPedido(LocalDate fecha_peticion, LocalDate fecha_pedido, LocalDate fecha_ofrecido, String tipo, int id) {
        this.fecha_peticion = fecha_peticion;
        this.fecha_pedido = fecha_pedido;
        this.fecha_ofrecido = fecha_ofrecido;
        this.tipo = tipo;
        this.id = id;
    }

    public LocalDate getFechaPeticion() {
        return fecha_peticion;
    }

    public LocalDate getFechaPedido() {
        return fecha_pedido;
    }

    public LocalDate getFechaOfrecido() {
        return fecha_ofrecido;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }
    
    
    
    
    
    
}
