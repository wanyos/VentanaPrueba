
package com.wanyos.componentes;

import javax.swing.JLabel;

/**
 *
 * @author wanyos
 */
public class LblPanel extends JLabel implements Configuraciones {
    
    
    public LblPanel(String texto){
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setForeground(color_letra_blanco);
        this.setFont(fuente_letra);
        this.setText(texto);
    }
}