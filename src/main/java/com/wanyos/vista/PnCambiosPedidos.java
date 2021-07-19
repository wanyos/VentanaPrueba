
package com.wanyos.vista;

import static com.wanyos.componentes.Configuraciones.color_panel_central;
import com.wanyos.componentes.PanelCambioDia;
import com.wanyos.componentes.PanelPedirDia;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.controlador.CtrCambiosPedidos;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author wanyos
 */
public class PnCambiosPedidos extends PnAbstract {
    
    private CtrCambiosPedidos ctr_cambios_pedidos;
    private JPanel pn_ctr, pn_sup, pn_cambio, pn_pedir;
    
    public PnCambiosPedidos(JLabel lbl_mensaje, CtrCambiosPedidos ctr_cambios_pedidos) {
        super(lbl_mensaje);
        this.ctr_cambios_pedidos = ctr_cambios_pedidos;
        setPnCtr();
        this.add(pn_ctr);
        this.updateUI();
    }
    
    private void setPnCtr(){
        pn_ctr = new JPanel();
        BoxLayout b = new BoxLayout(pn_ctr, BoxLayout.Y_AXIS);
        pn_ctr.setLayout(b);
        pn_ctr.setBackground(super.color_panel_central);
        
        setPnSup();
        pn_ctr.add(pn_sup);
        pn_ctr.updateUI();
    }
    
    private void setPnSup(){
        pn_sup = new JPanel();
        pn_sup.setBackground(super.color_panel_central);
        FlowLayout fl = new FlowLayout();
        fl.setVgap(20);
        fl.setHgap(20);
        pn_sup.setLayout(fl);
        pn_sup.setMaximumSize(new Dimension(800,80));
        pn_sup.setBorder(new BevelBorder(BevelBorder.RAISED));
        
        BtnMenu btn_cambio = new BtnMenu("Cambiar día");
        BtnMenu btn_pedir = new BtnMenu("Pedir día");
        btn_cambio.setColorFondo(color_panel_lateral);
        btn_pedir.setColorFondo(color_panel_lateral);
        
        btn_cambio.setIcono(img_azul_cambios);
        btn_cambio.setIconoFoco(img_gris_cambios);
        btn_cambio.setColorFoco(color_panel_lateral, color_panel_central);
        
        btn_pedir.setIcono(img_azul_cambios);
        btn_pedir.setIconoFoco(img_gris_cambios);
        btn_pedir.setColorFoco(color_panel_lateral, color_panel_central);
        
        btn_cambio.addActionListener(new OyenteBoton());
        btn_pedir.addActionListener(new OyenteBoton());
        
        pn_sup.add(btn_cambio);
        pn_sup.add(btn_pedir);
        
        pn_sup.updateUI();
    }
    
    private void limpiarPnCtr() {
        if (pn_cambio != null) {
            pn_ctr.remove(pn_cambio);
        }
        if (pn_pedir != null) {
            pn_ctr.remove(pn_pedir);
        }
    }
    
    private void setPnCambiarDia(){
        pn_cambio = new PanelCambioDia(img_azul_aceptar, img_gris_aceptar);

        pn_cambio.updateUI();
        pn_ctr.add(pn_cambio);
        pn_ctr.updateUI();
    }
    
    private void setPnPedirDia(){
        pn_pedir = new PanelPedirDia(img_azul_aceptar, img_gris_aceptar, img_azul_buscar, img_gris_buscar, ctr_cambios_pedidos);
        
        pn_pedir.updateUI();
        pn_ctr.add(pn_pedir);
        pn_ctr.updateUI();
    }
    
    
    private class OyenteBoton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            limpiarPnCtr();
            if(name.equalsIgnoreCase("Cambiar día")){
                setPnCambiarDia();
            } else if(name.equalsIgnoreCase("Pedir día")){
                setPnPedirDia();
            }
        }
        
        
        
    }
    
    
    
}
