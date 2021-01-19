
package com.wanyos.componentes;

import javax.swing.JTextField;

/**
 *
 * @author wanyos
 */
public class TxtPanel extends JTextField implements Configuraciones {
    
    public TxtPanel(int ancho){
        this.setColumns(ancho);
        this.setFont(fuente_letra);
        this.setForeground(color_letra_blanco);
        this.setBackground(color_panel_central);
        this.setHorizontalAlignment(JTextField.CENTER);
    }
    
}
