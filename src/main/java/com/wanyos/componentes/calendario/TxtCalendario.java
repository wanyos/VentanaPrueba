
package com.wanyos.componentes.calendario;

import com.wanyos.componentes.Configuraciones;
import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author wanyos
 */
public class TxtCalendario extends JTextField implements Configuraciones {
    
    public TxtCalendario(int ancho, Color c){
        this.setColumns(ancho);
        this.setFont(FUENTE_LETRA);
        this.setForeground(COLOR_LETRA_BLANCO);
        this.setBackground(c);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setEditable(false);
        this.setBorder(null);
    }
    
}
