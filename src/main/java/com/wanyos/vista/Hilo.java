package com.wanyos.vista;

import com.wanyos.componentes.comunes.CalendarChooser;
import javax.swing.JProgressBar;

/**
 * @author wanyos
 */
public class Hilo extends Thread {

    private JProgressBar barra;
    private CalendarChooser calendar;
    private PanelesNombramiento pn;
    private String msm;

    public Hilo(CalendarChooser calendar, PanelesNombramiento pn) {
        this.barra = InitApp.getBarraPs();
        this.calendar = calendar;
        this.pn = pn;
    }

    
    public void setMsm(String m){
        this.msm = m;
    }

    
    
    @Override
    public void run() {
        if (barra.isVisible()) {
            InitApp.setEnabledPn(false);
            calendar.setEnabled(false);
            PnAbstract.setEnabledPnRight(false);
            pn.setVisibleBtnGuardarCorreos(false);
        }
        while (barra.isVisible()) {
            InitApp.setMensajeLbl(msm);
        }
        InitApp.setEnabledPn(true);
        calendar.setEnabled(true);
        PnAbstract.setEnabledPnRight(true);
        pn.setVisibleBtnGuardarCorreos(true);
        
        
        InitApp.setMensajeLbl("Fin lectura");
    }



}
