
package com.wanyos.controlador;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

/**
 *
 * @author wanyos
 */
public class OyentePropiedadBarraPs implements PropertyChangeListener {
    
    
     private JProgressBar barra;

        public OyentePropiedadBarraPs(){}
        
        public OyentePropiedadBarraPs(JProgressBar barra, int max_value) {
            this.barra = barra;
            barra.setMaximum(max_value);
            barra.setVisible(true);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // Checamos si la propiedad modificada es progress
            if (evt.getPropertyName().equalsIgnoreCase("progress")) {
                 int m = (int) evt.getNewValue();    
                 barra.setValue(m);
            }
        }
    }

