package com.wanyos.vista;

import com.wanyos.componentes.comunes.CalendarChooser;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author wanyos
 */
public class Hilo extends Thread {

    private JProgressBar barra;
    private JPanel pn_right, pn_left;
    private CalendarChooser calendar;
    private JLabel lbl_mensaje;
    private String msm;

    public Hilo(JPanel pn_r, CalendarChooser calendar, JPanel pn_l, JLabel lbl) {
        this.pn_right = pn_r;
        this.calendar = calendar;
        this.pn_left = pn_l;
        this.lbl_mensaje = lbl;
    }

    public void setBarra(JProgressBar b) {
        this.barra = b;
    }
    
    public void setMsm(String m){
        this.msm = m;
    }

    @Override
    public void run() {
        while (barra.isVisible()) {
            setEnabledPn(false);
            calendar.setEnabled(false);
            lbl_mensaje.setText("-- "+msm);
        }
        setEnabledPn(true);
        calendar.setEnabled(true);
        this.lbl_mensaje.setText("-- Fin lectura");
    }

    private void setEnabledPn(boolean b) {
        Component[] c_r = pn_right.getComponents();
        Component[] c_l = pn_left.getComponents();

        for (Component aux : c_r) {
            if (aux instanceof JButton) {
                aux.setEnabled(b);
            }
        }
        for (Component aux : c_l) {
            if (aux instanceof JButton) {
                aux.setEnabled(b);
            }
        }
    }

}
