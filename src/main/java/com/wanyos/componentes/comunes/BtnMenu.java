
package com.wanyos.componentes.comunes;

import com.wanyos.componentes.Configuraciones;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Cursor.HAND_CURSOR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author wanyos
 */
public class BtnMenu extends JButton implements Configuraciones {
    
    
        //btn_guardar.setOpaque(true);
        //btn_guardar.setBorder(null);
        //btn_guardar.setBorderPainted(false);
        //btn_guardar.setContentAreaFilled(false);
    
    public BtnMenu(String nombre){
        this.setBackground(color_boton_menu);
        this.setForeground(color_letra_blanco);
        this.setFont(fuente_btn_menu);
        this.setText(nombre);
        this.setFocusPainted(false);
        this.setCursor(new Cursor(HAND_CURSOR));
    }
    
    public void setIcono(ImageIcon img){
         this.setIcon(img);
    }
    
    public void setIconoFoco(ImageIcon img){
        this.setRolloverIcon(img);
    }
    
    public void setColorFoco(Color inicial, Color foco) {
        this.addMouseListener(new OyenteRaton(this, inicial, foco));
    }
    
    public void setColorFondo(Color c){
        this.setBackground(c);
    }
    
    public void isEnabled(boolean enabled, Color inicial, Color foco){
        if(!enabled){
            this.setColorFondo(foco);
            this.setColorFoco(foco, foco);
        } else {
            this.setColorFondo(inicial);
            this.setColorFoco(inicial, foco);
        }
    }
    
    
    private class OyenteRaton extends MouseAdapter {

        private BtnMenu btn;
        private Color inicial, foco;
        
        public OyenteRaton(BtnMenu btn, Color inicial, Color foco){
            this.btn = btn;
            this.inicial = inicial;
            this.foco = foco;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(foco);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btn.setBackground(inicial);
        }
        
        
        
    }
    
}
