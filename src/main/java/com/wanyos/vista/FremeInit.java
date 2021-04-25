
package com.wanyos.vista;

import com.wanyos.controlador.CtrCalendario;
import com.wanyos.controlador.CtrLibres;
import com.wanyos.controlador.CtrNombramiento;
import javax.swing.*;

/**
 *
 * @author wanyos
 */
public class FremeInit extends JFrame {

    
    public FremeInit() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.lbl_titulo.setText("Ventana principal...");
        this.barra_ps.setVisible(false);
        this.barra_ps.setStringPainted(true);
        this.setVisible(true);
    }
    
    
    public void setPanel(JPanel p){
        this.pn_ctr.removeAll();
        this.pn_ctr.add(p);
        this.pn_ctr.updateUI();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_frame = new javax.swing.JPanel();
        pn_inf = new javax.swing.JPanel();
        lbl_mensaje = new javax.swing.JLabel();
        barra_ps = new javax.swing.JProgressBar();
        pn_sup = new javax.swing.JPanel();
        pn_sup_left = new javax.swing.JPanel();
        lbl_titulo = new javax.swing.JLabel();
        pn_sup_right = new javax.swing.JPanel();
        btn_cerrar = new javax.swing.JButton();
        pn_left = new javax.swing.JPanel();
        btn_nombramiento = new javax.swing.JButton();
        btn_libres = new javax.swing.JButton();
        btn_calendario = new javax.swing.JButton();
        btn_configuracion = new javax.swing.JButton();
        pn_ctr = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1000, 900));

        pn_frame.setBackground(new java.awt.Color(95, 106, 116));
        pn_frame.setToolTipText("");
        pn_frame.setLayout(new java.awt.BorderLayout());

        pn_inf.setBackground(new java.awt.Color(95, 106, 116));
        pn_inf.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pn_inf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 5));

        lbl_mensaje.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lbl_mensaje.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mensaje.setText("Avisos y mensajes...");
        pn_inf.add(lbl_mensaje);
        pn_inf.add(barra_ps);

        pn_frame.add(pn_inf, java.awt.BorderLayout.PAGE_END);

        pn_sup.setBackground(new java.awt.Color(95, 106, 116));
        pn_sup.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pn_sup.setLayout(new javax.swing.BoxLayout(pn_sup, javax.swing.BoxLayout.X_AXIS));

        pn_sup_left.setBackground(new java.awt.Color(95, 106, 116));
        pn_sup_left.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 15));

        lbl_titulo.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lbl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_titulo.setText("Ventana principal...");
        pn_sup_left.add(lbl_titulo);

        pn_sup.add(pn_sup_left);

        pn_sup_right.setBackground(new java.awt.Color(95, 106, 116));
        pn_sup_right.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pn_sup_right.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 15));

        btn_cerrar.setBackground(new java.awt.Color(51, 51, 51));
        btn_cerrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cerrar.setText("Close");
        btn_cerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });
        pn_sup_right.add(btn_cerrar);

        pn_sup.add(pn_sup_right);

        pn_frame.add(pn_sup, java.awt.BorderLayout.PAGE_START);

        pn_left.setBackground(new java.awt.Color(95, 106, 116));
        pn_left.setLayout(new java.awt.GridLayout(10, 1, 0, 15));

        btn_nombramiento.setBackground(new java.awt.Color(51, 51, 51));
        btn_nombramiento.setForeground(new java.awt.Color(255, 255, 255));
        btn_nombramiento.setText("Nombramiento");
        btn_nombramiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nombramiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nombramientoActionPerformed(evt);
            }
        });
        pn_left.add(btn_nombramiento);

        btn_libres.setBackground(new java.awt.Color(51, 51, 51));
        btn_libres.setForeground(new java.awt.Color(255, 255, 255));
        btn_libres.setText("Libres");
        btn_libres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_libres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_libresActionPerformed(evt);
            }
        });
        pn_left.add(btn_libres);

        btn_calendario.setBackground(new java.awt.Color(51, 51, 51));
        btn_calendario.setForeground(new java.awt.Color(255, 255, 255));
        btn_calendario.setText("Calendario");
        btn_calendario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calendarioActionPerformed(evt);
            }
        });
        pn_left.add(btn_calendario);

        btn_configuracion.setBackground(new java.awt.Color(51, 51, 51));
        btn_configuracion.setForeground(new java.awt.Color(255, 255, 255));
        btn_configuracion.setText("Configuración");
        btn_configuracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configuracionActionPerformed(evt);
            }
        });
        pn_left.add(btn_configuracion);

        pn_frame.add(pn_left, java.awt.BorderLayout.LINE_START);

        pn_ctr.setBackground(new java.awt.Color(51, 153, 255));
        pn_ctr.setLayout(new java.awt.BorderLayout());
        pn_frame.add(pn_ctr, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_frame, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_frame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
       System.exit(0);
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_nombramientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nombramientoActionPerformed
        this.lbl_titulo.setText("Nombramiento...");
        this.lbl_mensaje.setText(" --- ");
        CtrNombramiento ctr_nombramiento = new CtrNombramiento(this.lbl_mensaje);
        try{
            this.setPanel(ctr_nombramiento.getPnNombramiento());
        } catch(NullPointerException e){
            
        }
    }//GEN-LAST:event_btn_nombramientoActionPerformed

    private void btn_libresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_libresActionPerformed
        this.lbl_titulo.setText("Libres...");
        this.lbl_mensaje.setText(" --- ");
        CtrLibres ctr_libres = new CtrLibres(this.lbl_mensaje, this.barra_ps);
        try {
            this.setPanel(ctr_libres.getPnLibres());  
        } catch(NullPointerException w){
          
        }
    }//GEN-LAST:event_btn_libresActionPerformed

    private void btn_calendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calendarioActionPerformed
        this.lbl_titulo.setText("Calendario...");
        this.lbl_mensaje.setText(" --- ");
        CtrCalendario ctr_calendario = new CtrCalendario(lbl_mensaje);
        try {
            this.setPanel(ctr_calendario.getPnCalendario());
        } catch (NullPointerException e) {

        }
    }//GEN-LAST:event_btn_calendarioActionPerformed

    private void btn_configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configuracionActionPerformed
       this.lbl_titulo.setText("Configuración...");
       this.lbl_mensaje.setText(" --- ");
       
       PnAbstract pn_configuracion = new PnConfiguracion(lbl_mensaje);
       this.setPanel(pn_configuracion);
    }//GEN-LAST:event_btn_configuracionActionPerformed

  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new FremeInit();
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra_ps;
    private javax.swing.JButton btn_calendario;
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_configuracion;
    private javax.swing.JButton btn_libres;
    private javax.swing.JButton btn_nombramiento;
    private javax.swing.JLabel lbl_mensaje;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JPanel pn_ctr;
    private javax.swing.JPanel pn_frame;
    private javax.swing.JPanel pn_inf;
    private javax.swing.JPanel pn_left;
    private javax.swing.JPanel pn_sup;
    private javax.swing.JPanel pn_sup_left;
    private javax.swing.JPanel pn_sup_right;
    // End of variables declaration//GEN-END:variables
}
