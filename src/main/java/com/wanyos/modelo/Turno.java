
package com.wanyos.modelo;

import java.time.LocalDate;

/**
 *
 * @author wanyos
 */
public class Turno {
    
    private LocalDate fecha;
    private String turno, linea, h_init, l_init, dir_init, h_fin, l_fin, dir_fin,
                                 h_init_p, l_init_p, dir_init_p, h_fin_p, l_fin_p, dir_fin_p, nota;

//    public Turno(LocalDate fecha, String turno, String linea, String h_init, String l_init, String dir_init, String h_fin, String l_fin, String dir_fin) {
//        this.fecha = fecha;
//        this.turno = turno;
//        this.linea = linea;
//        this.h_init = h_init;
//        this.l_init = l_init;
//        this.dir_init = dir_init;
//        this.h_fin = h_fin;
//        this.l_fin = l_fin;
//        this.dir_fin = dir_fin;
//    }
//
//    
//    
//    public Turno(LocalDate fecha, String turno, String linea, String h_init, String l_init, String dir_init, 
//                                                              String h_fin, String l_fin, String dir_fin, 
//                                                              String h_init_p, String l_init_p, String dir_init_p, 
//                                                              String h_fin_p, String l_fin_p, String dir_fin_p, String nota) {
//        this.fecha = fecha;
//        this.turno = turno;
//        this.linea = linea;
//        this.h_init = h_init;
//        this.l_init = l_init;
//        this.dir_init = dir_init;
//        this.h_fin = h_fin;
//        this.l_fin = l_fin;
//        this.dir_fin = dir_fin;
//        this.h_init_p = h_init_p;
//        this.l_init_p = l_init_p;
//        this.dir_init_p = dir_init_p;
//        this.h_fin_p = h_fin_p;
//        this.l_fin_p = l_fin_p;
//        this.dir_fin_p = dir_fin_p;
//        this.nota = nota;
//    }
    
    

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getH_init() {
        return h_init;
    }

    public void setH_init(String h_init) {
        this.h_init = h_init;
    }

    public String getL_init() {
        return l_init;
    }

    public void setL_init(String l_init) {
        this.l_init = l_init;
    }

    public String getDir_init() {
        if(dir_init == null){
            return "";
        }
        return dir_init;
    }

    public void setDir_init(String dir_init) {
        this.dir_init = dir_init;
    }

    public String getH_fin() {
        return h_fin;
    }

    public void setH_fin(String h_fin) {
        this.h_fin = h_fin;
    }

    public String getL_fin() {
        return l_fin;
    }

    public void setL_fin(String l_fin) {
        this.l_fin = l_fin;
    }

    public String getDir_fin() {
        if(dir_fin == null){
            return "";
        }
        return dir_fin;
    }

    public void setDir_fin(String dir_fin) {
        this.dir_fin = dir_fin;
    }

    public String getH_init_p() {
        if(h_init_p == null){
            return "";
        }
        return h_init_p;
    }

    public void setH_init_p(String h_init_p) {
        this.h_init_p = h_init_p;
    }

    public String getL_init_p() {
        if(l_init_p == null){
            return "";
        }
        return l_init_p;
    }

    public void setL_init_p(String l_init_p) {
        this.l_init_p = l_init_p;
    }

    public String getDir_init_p() {
        if(dir_init_p == null){
            return "";
        }
        return dir_init_p;
    }

    public void setDir_init_p(String dir_init_p) {
        this.dir_init_p = dir_init_p;
    }

    public String getH_fin_p() {
        if(h_fin_p == null){
            return "";
        }
        return h_fin_p;
    }

    public void setH_fin_p(String h_fin_p) {
        this.h_fin_p = h_fin_p;
    }

    public String getL_fin_p() {
        if(l_fin_p == null){
            return "";
        }
        return l_fin_p;
    }

    public void setL_fin_p(String l_fin_p) {
        this.l_fin_p = l_fin_p;
    }

    public String getDir_fin_p() {
        if(dir_fin_p == null){
            return "";
        }
        return dir_fin_p;
    }

    public void setDir_fin_p(String dir_fin_p) {
        this.dir_fin_p = dir_fin_p;
    }

    public String getNota() {
        if(nota == null){
            return "";
        }
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "" +this.getFecha() + ";" + this.getTurno() + ";" + this.getLinea() + ";" 
                + this.getH_init() + ";" + this.getL_init() + ";" + this.getDir_init() + ";" 
                + this.getH_fin() + ";" + this.getL_fin() + ";" + this.getDir_fin() + ";" 
                + this.getH_init_p() + ";" + this.getL_init_p() + ";" + this.getDir_init_p() + ";" 
                + this.getH_fin_p() + ";" + this.getL_fin_p() + ";" + this.getDir_fin_p() + ";" 
                + this.getNota() + ' ';
    }
    
    
    
}
