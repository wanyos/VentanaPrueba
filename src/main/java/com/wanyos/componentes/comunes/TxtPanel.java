
package com.wanyos.componentes.comunes;

import com.wanyos.componentes.Configuraciones;
import javax.swing.JTextField;

/**
 *
 * @author wanyos
 */
public class TxtPanel extends JTextField implements Configuraciones {
    
    public TxtPanel(int ancho){
        this.setColumns(ancho);
        this.setFont(FUENTE_LETRA);
        this.setForeground(COLOR_LETRA_BLANCO);
        this.setBackground(COLOR_PANEL_CENTRAL);
        this.setHorizontalAlignment(JTextField.CENTER);
    }
    
}
