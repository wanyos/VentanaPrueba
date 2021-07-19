
package com.wanyos.componentes.calendario;

import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author wanyos
 */
public class PnYear extends JPanel {

    private final PnMonth [] pn_meses;
    
    public PnYear(int year, Color c_pn_year, Color c_pn_months) {
        initComponents();
        this.setBackground(c_pn_year);
        pn_meses = new PnMonth[13];
        crearMeses(year, c_pn_months);
    }

    
    private void crearMeses(int year, Color c_pn_months){
        for(int a = 1; a <= 12; a++) {
            PnMonth pn = new PnMonth(250, 150 , a, year);
            pn.setColorFondo(c_pn_months);
            pn_meses[a] = pn;
            this.add(pn);
        }
        this.updateUI();
    }
    
    public void setColorDias(List<String> dias, Color c, int mes, boolean foreground){
        if(pn_meses != null){
            PnMonth pn = pn_meses[mes];
            pn.setColorDias(foreground, dias, c);
        }
    }
    
    public void setColorVacacion(List<String> dias, int mes, Color libre, Color vacacion, Color libre_vacacion){
        if(pn_meses != null){
            PnMonth pn = pn_meses[mes];
            pn.setColorVacacion(dias, libre, vacacion, libre_vacacion);
        }
    }
    
    public void resetColor(Color c){
        for(int a = 1; a <= 12; a++){
            PnMonth pn = pn_meses[a];
            pn.setColorFondo(c);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMinimumSize(new java.awt.Dimension(500, 650));
        setName(""); // NOI18N
        setLayout(new java.awt.GridLayout(4, 3, 10, 10));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
