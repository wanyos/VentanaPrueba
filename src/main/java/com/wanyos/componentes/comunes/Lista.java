
package com.wanyos.componentes.comunes;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import static com.wanyos.componentes.Configuraciones.COLOR_PANEL_CENTRAL;
import static com.wanyos.componentes.Configuraciones.COLOR_LETRA_BLANCO;
import static com.wanyos.componentes.Configuraciones.FUENTE_LETRA;

/**
 * @author wanyos
 */
public class Lista extends JList {
    
    
    public Lista(){
        this.setBackground(COLOR_PANEL_CENTRAL);
        this.setForeground(COLOR_LETRA_BLANCO);
        this.setFont(FUENTE_LETRA);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setVisibleRowCount(7);
        DefaultListCellRenderer.UIResource posicion = new DefaultListCellRenderer.UIResource();
        posicion.setHorizontalAlignment(SwingConstants.CENTER);
        this.setCellRenderer(posicion);
    }
    
    public void setDatos(String [] datos){
        this.setListData(datos);
    }
    
    /**
     *  a = 1 --> izquierda
     *  a = 2 --> centro
     *  a = 3 --> derecha
     * @param a 
     */
    public void setAlinear(int a) {
        DefaultListCellRenderer cellRenderer = (DefaultListCellRenderer) this.getCellRenderer();
        switch (a) {
            case 1:
                cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case 2:
                cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case 3:
                cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                break;
        }
        
        
        
    }
    
    
    
    

    
}
