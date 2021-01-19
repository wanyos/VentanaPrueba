
package com.wanyos.vista;

import com.wanyos.componentes.Configuraciones;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

/**
 *
 * @author wanyos
 */
public class PnAbstract extends JPanel implements Configuraciones {

    
    protected final Color color_panel_central = new Color(51,153,255);
    protected final Color color_panel_right = new Color(95,106,116);
    
    /**
     * Creates new form NewJPanel
     */
    public PnAbstract() {
        initComponents();
        this.btn.setVisible(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_right = new javax.swing.JPanel();
        btn = new javax.swing.JButton();
        pn_center = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        pn_right.setBackground(new java.awt.Color(95, 106, 116));
        pn_right.setLayout(new java.awt.GridLayout(8, 1, 0, 15));

        btn.setText("jButton1");
        pn_right.add(btn);

        add(pn_right, java.awt.BorderLayout.LINE_END);

        pn_center.setBackground(new java.awt.Color(51, 153, 255));
        pn_center.setLayout(new javax.swing.BoxLayout(pn_center, javax.swing.BoxLayout.Y_AXIS));
        add(pn_center, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn;
    protected javax.swing.JPanel pn_center;
    protected javax.swing.JPanel pn_right;
    // End of variables declaration//GEN-END:variables
}
