
package com.wanyos.vista;

import com.wanyos.componentes.Configuraciones;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author wanyos
 */
public class PnAbstract extends JPanel implements Configuraciones {

    
    protected final Color color_panel_frame = new Color(95,106,116);
    protected final Color color_panel_central = new Color(51,153,255);
    protected final Color color_panel_right = new Color(95,106,116);
    protected final Color color_txt_libre = new Color(50,219,16);
    protected final Color color_txt_subgrupo = new Color(44,55,42);
    protected final Color color_txt_sub1 = new Color(212,216,82);
    protected final Color color_txt_sub2 = new Color(232,132,37);
    protected final Color color_txt_vacacion = new Color(68,88,200);
    protected final Color color_txt_pedido = new Color(118,23,154);
    protected final Color color_txt_libre_vacacion = new Color(23,154,128);
    protected final Color color_txt_festivos = new Color(232, 51, 19);
    protected JLabel lbl_mensaje;
    
    /**
     * Creates new form NewJPanel
     */
    public PnAbstract(JLabel lbl_mensaje) {
        initComponents();
        this.lbl_mensaje = lbl_mensaje;
        this.btn.setVisible(false);
    }
    
    public void setMensajeLbl(String mensaje){
        //se sobreescribe en las subclases
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
