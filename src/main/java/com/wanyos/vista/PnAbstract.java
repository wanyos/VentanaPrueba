
package com.wanyos.vista;

import com.wanyos.componentes.Configuraciones;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author wanyos
 */
public class PnAbstract extends JPanel implements Configuraciones {
    
    protected ImageIcon img_azul_cambios = new ImageIcon(getClass().getResource(R_IMG_AZUL_CAMBIOS));
    protected ImageIcon img_gris_cambios = new ImageIcon(getClass().getResource(R_IMG_GRIS_CAMBIOS));
    protected ImageIcon img_azul_buscar = new ImageIcon(getClass().getResource(R_IMG_AZUL_BUSCAR));
    protected ImageIcon img_gris_buscar = new ImageIcon(getClass().getResource(R_IMG_GRIS_BUSCAR));
    protected ImageIcon img_azul_aceptar = new ImageIcon(getClass().getResource(R_IMG_AZUL_ACEPTAR));
    protected ImageIcon img_gris_aceptar = new ImageIcon(getClass().getResource(R_IMG_GRIS_ACEPTAR));
    
    /**
     * Creates new form NewJPanel
     */
    public PnAbstract() {
        initComponents();
        this.btn.setVisible(false);
    }
    
    public void setMensajeLbl(String mensaje){
        InitApp.setMensajeLbl(mensaje);
    }
    
    public static void setEnabledPnRight(boolean b){
        Component [] c = pn_right.getComponents();
        for(Component aux: c){
            if(aux instanceof JButton){
                aux.setEnabled(b);
            }
//            String n = aux.getName();
//            if(n != null && b && (aux.getName().equals("btn_eliminar") || aux.getName().equals("btn_editar"))){
//                aux.setEnabled(!b);
//            }
        }
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
    protected static javax.swing.JPanel pn_right;
    // End of variables declaration//GEN-END:variables
}
