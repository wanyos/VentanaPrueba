
package com.wanyos.componentes.comunes;

import static com.wanyos.componentes.Configuraciones.color_letra_blanco;
import static com.wanyos.componentes.Configuraciones.color_panel_central;
import static com.wanyos.componentes.Configuraciones.fuente_letra;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 * @author wanyos
 */
public class Lista extends JList {
    
    
    public Lista(){
        this.setBackground(color_panel_central);
        this.setForeground(color_letra_blanco);
        this.setFont(fuente_letra);
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
