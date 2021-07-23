
package com.wanyos.componentes.comunes;

import com.wanyos.componentes.Configuraciones;
import javax.swing.JLabel;

/**
 *
 * @author wanyos
 */
public class LblPanel extends JLabel implements Configuraciones {
    
    
    public LblPanel(String texto){
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setForeground(COLOR_LETRA_BLANCO);
        this.setFont(FUENTE_LETRA);
        this.setText(texto);
    }
    
   
}
