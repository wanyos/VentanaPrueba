
package com.wanyos.componentes;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author wanyos
 */
public class TxtCalendario extends JTextField implements Configuraciones {
    
    public TxtCalendario(int ancho, Color c){
        this.setColumns(ancho);
        this.setFont(fuente_letra);
        this.setForeground(color_letra_blanco);
        this.setBackground(c);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setEditable(false);
        this.setBorder(null);
    }
    
}
