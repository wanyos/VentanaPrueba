
package com.wanyos.componentes.calendario;

import com.wanyos.componentes.comunes.LblPanel;
import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author wanyos
 */
public class PnMonth extends JPanel {

    private JLabel lbl_mes;
    private JTextField [][] matriz_dias;
    private Color color_fondo;
    
    
    public PnMonth(int width, int height, int mes, int year) {
        initComponents();
        this.setSize(width, height);
        color_fondo = new Color(70,68,63);
        setLblMes(mes);
        crearMatriz();
        setDiasMes(mes, year);
        setColorFondo(color_fondo);
    }
    
    
    private void setLblMes(int mes){
        String nombre_mes = this.getNombreMes(mes);
        lbl_mes = new LblPanel(nombre_mes);
        this.PnTitulo.add(lbl_mes);
        this.PnTitulo.updateUI();
    }
   
    
    private void crearMatriz() {
        int contador = 0;
        String[] dias_semana = {"L", "M", "X", "J", "V", "S", "D"};
        matriz_dias = new TxtCalendario[7][7];
        for (int f = 0; f < 7; f++) {
            for (int c = 0; c < 7; c++) {
                if (f == 0) {
                    TxtCalendario txt = new TxtCalendario(5, color_fondo);
                    txt.setText(dias_semana[contador++]);
                    matriz_dias[f][c] = txt;
                    PnDias.add(txt);
                } else {
                    TxtCalendario txt = new TxtCalendario(5, color_fondo);
                    txt.setText("");
                    matriz_dias[f][c] = txt;
                    PnDias.add(txt);
                }
            }
        }
    }
    
    private void setDiasMes(int mes, int year) {
        if (mes > 0 && mes < 13) {
            LocalDate primer_dia_mes = LocalDate.of(year, mes, 1);
            int total_dias = primer_dia_mes.lengthOfMonth();
            DayOfWeek dia_semana = primer_dia_mes.getDayOfWeek();
            int v_dia = dia_semana.getValue();
            
            int fila = 1;
            int colum = v_dia;
            int dia = 1;
            while(dia <= total_dias){
                matriz_dias[fila][colum-1].setText(String.valueOf(dia++));
                if(colum > 6){
                    colum = 1;
                    fila++;
                } else {
                    colum++;
                }
            }
        }
    }
    
    private String getNombreMes(int num_mes){
        String nombre;
        switch (num_mes) {
            case 1:
                nombre = "Enero";
                break;
            case 2:
                nombre = "Febrero";
                break;
            case 3:
                nombre = "Marzo";
                break;
            case 4:
                nombre = "Abril";
                break;
            case 5:
                nombre = "Mayo";
                break;
            case 6:
                nombre = "Junio";
                break;
            case 7:
                nombre = "Julio";
                break;
            case 8:
                nombre = "Agosto";
                break;
            case 9:
                nombre = "Septiembre";
                break;
            case 10:
                nombre = "Octubre";
                break;
            case 11:
                nombre = "Noviembre";
                break;
            case 12:
                nombre = "Diciembre";
                break;
            default:
                nombre = "Desconocido";
        }
        return nombre;
    }
    
    /**
     * Cambia el color de fondo del panel 
     * @param color
     * @param c 
     */
    public void setColorFondo(Color color){
        this.setBackground(color);
        this.PnTitulo.setBackground(color);
        this.PnDias.setBackground(color);
        for(int f = 0; f < 7; f++){
            for(int c = 0; c < 7; c++){
               matriz_dias[f][c].setBackground(color);
            }
        }
        this.PnTitulo.updateUI();
        this.PnDias.updateUI();
        this.updateUI();
    }
    
    /**
     * Colorea texto del txt dia de la matriz
     * @param foreground
     * @param dias
     * @param color 
     */
    public void setColorDias(boolean foreground, List<String> dias, Color color) {
        while (dias != null && !dias.isEmpty()) {
            String dia = dias.get(0);
            dias.remove(0);
            for (int f = 1; f < 7; f++) {
                for (int c = 0; c < 7; c++) {
                    if (dia.equalsIgnoreCase(matriz_dias[f][c].getText())) {
                        if (foreground) {
                            matriz_dias[f][c].setForeground(color);
                        } else {
                            matriz_dias[f][c].setBackground(color);
                        }
                    }
                }
            }
        }
    }
    
    public void setColorVacacion(List<String> lista, Color libre, Color vacacion, Color vacacion_libre) {
        if (lista != null && !lista.isEmpty()) {
            for (String dia : lista) {
                for (int f = 1; f < 7; f++) {
                    for (int c = 0; c < 7; c++) {
                        if (dia.equalsIgnoreCase(matriz_dias[f][c].getText())) {
                            if (matriz_dias[f][c].getBackground() == libre) {
                                matriz_dias[f][c].setBackground(vacacion_libre);
                            } else {
                                matriz_dias[f][c].setBackground(vacacion);
                            }
                        }
                    }
                }
            }
        }
    }

    
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnTitulo = new javax.swing.JPanel();
        PnDias = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        add(PnTitulo);

        PnDias.setLayout(new java.awt.GridLayout(7, 7));
        add(PnDias);
    }// </editor-fold>//GEN-END:initComponents


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnDias;
    private javax.swing.JPanel PnTitulo;
    // End of variables declaration//GEN-END:variables
}
