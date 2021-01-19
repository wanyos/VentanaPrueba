
package com.wanyos.componentes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author wanyos
 */
public class ComboBox extends JComboBox {
    
    
    private final DefaultComboBoxModel model;
    
    
    public ComboBox(String [] items){
      model = new DefaultComboBoxModel(items);
      this.setModel(model);
    }
    
    public void setColorTexto (Color c){
        ((JTextField) this.getEditor().getEditorComponent()).setForeground(c);
    }
    
    public void setColorFondo (Color c){
        ((JTextField) this.getEditor().getEditorComponent()).setBackground(c);
    }
    
    public void setFuente (Font f){
        this.getEditor().getEditorComponent().setFont(f);
    }
    
     
    
    
    
    
    
}
