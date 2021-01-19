
package com.wanyos.componentes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author wanyos
 */
public class BtnMenu extends JButton {
    
    public BtnMenu(String nombre){
        this.setBackground(new Color(51,51,51));
        this.setForeground(new Color(255,255,255));
        Font f = new Font("Dialog", 2, 12);
        this.setFont(f);
        this.setText(nombre);
    }
    
}
