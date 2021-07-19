
package com.wanyos.componentes.comunes;

import com.wanyos.componentes.Configuraciones;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author wanyos
 */
public class TxtAreaPanel extends JTextArea implements Configuraciones {
    
    private JScrollPane scrol;
    
    public TxtAreaPanel(int f, int c) {
        this.setRows(f);
        this.setColumns(c);
        this.setBackground(color_panel_central);
        this.setForeground(color_letra_blanco);
        this.setFont(fuente_letra);
        this.setLineWrap(true);
    }

    public JScrollPane getScrolTxtArea() {
        scrol = new JScrollPane();
        scrol.setViewportView(this);
        return scrol;
    }
    
}
