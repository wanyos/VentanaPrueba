
package com.wanyos.componentes;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Dimension;

/**
 *
 * @author wanyos
 */
public class CalendarChooser extends JDateChooser {
    
    private JTextFieldDateEditor editor;
    
    public CalendarChooser(){
        this.setDateFormatString("dd/MM/yyyy");
        this.setPreferredSize(new Dimension(140,25));
        editor = ((JTextFieldDateEditor)this.getDateEditor());
    }
    
    public JTextFieldDateEditor getEditor(){
        return editor;
    }
    
}
