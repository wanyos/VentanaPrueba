
package com.wanyos.componentes.comunes;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import com.wanyos.componentes.Configuraciones;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author wanyos
 */
public class CalendarChooser extends JDateChooser implements Configuraciones {
    
    private JTextFieldDateEditor editor;
    
    public CalendarChooser(){
        this.setDateFormatString("dd/MM/yyyy");
        this.setPreferredSize(new Dimension(140,25));
        editor = ((JTextFieldDateEditor)this.getDateEditor());
        editor.setHorizontalAlignment(0);
        editor.setForeground(COLOR_LETRA_BLANCO);
        editor.setFont(FUENTE_LETRA);
        editor.setBackground(COLOR_PANEL_CENTRAL);
    }
    
    public JTextFieldDateEditor getEditor(){
        return editor;
    }
    
    public LocalDate getFechaChooser() {
        LocalDate fecha = null;
        if (this.getDate() != null) {
            Date d = this.getDate();
            fecha = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return fecha;
    }
        
    
}
