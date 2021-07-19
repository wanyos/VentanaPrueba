
package com.wanyos.componentes;

import static com.wanyos.componentes.Configuraciones.color_panel_central;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.CalendarChooser;
import com.wanyos.componentes.comunes.LblPanel;
import com.wanyos.componentes.comunes.TxtAreaPanel;
import com.wanyos.componentes.comunes.TxtPanel;
import com.wanyos.modelo.dao.MySqlLibreGeneradoDao;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author wanyos
 */
public class PanelCambioDia extends JPanel implements Configuraciones {
    
    private MySqlLibreGeneradoDao mysql_libres;
    private JPanel pn_fecha, pn_dias, pn_conductor, pn_guardar;
    private CalendarChooser chooser_fecha, chooser_pedido, chooser_ofrecido;
    private ImageIcon img_azul_aceptar, img_gris_aceptar;
    
    
    public PanelCambioDia(ImageIcon img_azul_aceptar, ImageIcon img_gris_aceptar){
        //this.mysql_libres = mysql;
        this.img_azul_aceptar = img_azul_aceptar;
        this.img_gris_aceptar = img_gris_aceptar;
        this.setBackground(color_panel_central);
        BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(b);
        setPaneles();
        setPnFecha();
        setPnDias();
        setPnConductor();
        setPnGuardar();
        
        this.updateUI();
    }
    
    
    private void setPaneles(){
        pn_fecha = new JPanel();
        pn_dias = new JPanel();
        pn_conductor = new JPanel();
        pn_guardar = new JPanel();
        
        pn_fecha.setBackground(color_panel_central);
        pn_dias.setBackground(color_panel_central);
        pn_conductor.setBackground(color_panel_central);
        pn_guardar.setBackground(color_panel_central);
        
        pn_fecha.setMaximumSize(new Dimension(800,60));
        pn_dias.setMaximumSize(new Dimension(800,60));
        pn_conductor.setMaximumSize(new Dimension(800,100));
        pn_guardar.setMaximumSize(new Dimension(800,60));
    }
    
    private void setPnFecha(){
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        pn_fecha.setLayout(fl);
        LblPanel lbl_fecha = new LblPanel("Fecha: ");
        chooser_fecha = new CalendarChooser();
        
        pn_fecha.add(lbl_fecha);
        pn_fecha.add(chooser_fecha);
        pn_fecha.updateUI();
        this.add(pn_fecha);
    }
    
    private void setPnDias(){
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        pn_dias.setLayout(fl);
        
        LblPanel lbl_pedido = new LblPanel("Día pedido: ");
        LblPanel lbl_ofrecido = new LblPanel("Día ofrecido: ");
        LblPanel lbl_sep = new LblPanel(" --- ");
        
        chooser_pedido = new CalendarChooser();
        chooser_ofrecido = new CalendarChooser();
        
        pn_dias.add(lbl_pedido);
        pn_dias.add(chooser_pedido);
        pn_dias.add(lbl_sep);
        pn_dias.add(lbl_ofrecido);
        pn_dias.add(chooser_ofrecido);
        pn_dias.updateUI();
        this.add(pn_dias);
    }
    
    private void setPnConductor(){
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        pn_conductor.setLayout(fl);
        
        LblPanel lbl_conductor = new LblPanel("Conductor: ");
        LblPanel lbl_nota = new LblPanel("Nota: ");
        LblPanel lbl_sep = new LblPanel(" --- ");
        TxtPanel txt_conductor = new TxtPanel(10);
        TxtAreaPanel txt_area_nota = new TxtAreaPanel(3,20);
        
        pn_conductor.add(lbl_conductor);
        pn_conductor.add(txt_conductor);
        pn_conductor.add(lbl_sep);
        pn_conductor.add(lbl_nota);
        pn_conductor.add(txt_area_nota.getScrolTxtArea());
        pn_conductor.updateUI();
        this.add(pn_conductor);
    }
    
    private void setPnGuardar(){
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        pn_guardar.setLayout(fl);
        
        BtnMenu btn_guardar = new BtnMenu("Guardar");
        btn_guardar.setIcono(img_azul_aceptar);
        btn_guardar.setIconoFoco(img_gris_aceptar);
        btn_guardar.setColorFoco(color_boton_menu, color_panel_central);
        
        pn_guardar.add(btn_guardar);
        pn_guardar.updateUI();
        this.add(pn_guardar);
    }
    
    
}
