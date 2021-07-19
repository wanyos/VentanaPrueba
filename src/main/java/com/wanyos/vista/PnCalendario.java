
package com.wanyos.vista;

import com.toedter.calendar.JYearChooser;
import static com.wanyos.componentes.Configuraciones.color_panel_central;
import com.wanyos.componentes.comunes.ComboBox;
import com.wanyos.componentes.comunes.LblPanel;
import com.wanyos.componentes.calendario.PnYear;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.TxtPanel;
import com.wanyos.controlador.CtrCalendario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author wanyos
 */
public class PnCalendario extends PnAbstract {
    
    private CtrCalendario ctr_calendario;
    private PnYear pn_year;
    private JYearChooser cbo_year; 
    private ComboBox cbo_grupo, cbo_subgrupo;
    private JPanel pn_ctr, pn_sup, pn_inf, pn_usr;
    
    
    public PnCalendario(JLabel lbl_mensaje, CtrCalendario ctr_calendario){
        super(lbl_mensaje);
        this.ctr_calendario = ctr_calendario;
        setPnCentral();
        this.add(pn_ctr);
        this.setColorYear();
        this.updateUI();
    }
    
    private int getYear(){
        return this.cbo_year.getYear();
    }
    
    private String getGrupo(){
        return (String) this.cbo_grupo.getSelectedItem();
    }
    
    private String getSubgrupo(){
        return (String) this.cbo_subgrupo.getSelectedItem();
    }
    
    
    private void resetPnCtr(){
        pn_ctr.remove(pn_year);
        pn_ctr.remove(pn_inf);
        setPnCalendario();
        pn_ctr.add(pn_year);
        pn_ctr.add(pn_inf);
        pn_ctr.updateUI();
        this.updateUI();
    }
    
    
    private void setPnCentral(){
        pn_ctr = new JPanel();
        pn_ctr.setBackground(super.color_panel_central);
        BoxLayout ly = new BoxLayout(pn_ctr, BoxLayout.Y_AXIS);
        pn_ctr.setLayout(ly);
        
        setPnSuperior();
        setPnCalendario();
        setPnInferior();
        
        pn_ctr.add(pn_sup);
        pn_ctr.add(pn_year);
        pn_ctr.add(pn_inf);
        
        pn_ctr.updateUI();
    }
    
    private void setPnCalendario(){
        pn_year = new PnYear(getYear(), super.color_panel_central, super.color_panel_frame);
        pn_year.setMaximumSize(new Dimension(700,850));
    }
    
    
    private void setPnSuperior(){
        pn_sup = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setHgap(20);
        fl.setVgap(10);
        pn_sup.setLayout(fl);
        pn_sup.setMaximumSize(new Dimension(800,50));
        pn_sup.setBorder(new BevelBorder(BevelBorder.RAISED));
        pn_sup.setBackground(super.color_panel_central);
        
        JLabel lbl_year, lbl_grupo, lbl_subgrupo;
        
        BtnMenu btn_buscar;
        String [] grupo = {"1", "2", "3", "4", "5", "usr"};
        String [] subgrupo = {"A", "B", "C", "D", "E", "F", "G", "H"};
        
        lbl_year = new LblPanel("Year");
        lbl_grupo = new LblPanel("Grupo");
        lbl_subgrupo = new LblPanel("Subgrupo");
        
        cbo_year = new JYearChooser();
        cbo_year.setMaximum(2050);
        
        cbo_grupo = new ComboBox(grupo);
        cbo_subgrupo = new ComboBox(subgrupo);
        
        
        btn_buscar = new BtnMenu("Buscar");
        btn_buscar.setColorFondo(color_panel_lateral);
        btn_buscar.setIcono(img_azul_buscar);
        btn_buscar.setIconoFoco(img_gris_buscar);
        btn_buscar.setColorFoco(color_panel_lateral, color_panel_central);
        btn_buscar.addActionListener(new OyenteBuscar());
        
        pn_sup.add(lbl_year);
        pn_sup.add(cbo_year);
        pn_sup.add(new LblPanel(" --- "));
        
        pn_sup.add(lbl_grupo);
        pn_sup.add(cbo_grupo);
        pn_sup.add(new LblPanel(" --- "));
        
        pn_sup.add(lbl_subgrupo);
        pn_sup.add(cbo_subgrupo);
        pn_sup.add(new LblPanel(" --- "));
        
        pn_sup.add(btn_buscar);
                
        pn_sup.updateUI();
    }
    
    
    private void setPnInferior(){
        pn_inf = new JPanel();
        FlowLayout fl = new FlowLayout();
        pn_inf.setLayout(fl);
        pn_inf.setBackground(super.color_panel_central);
        pn_inf.setPreferredSize(new Dimension(500,50));
        
        pn_inf.add(getPnLeyendaLibres());
        pn_inf.updateUI();
    }
    
    private JPanel getPnLeyendaLibres(){
        JPanel pn_leyinf = new JPanel();
        FlowLayout fl = new FlowLayout();
        pn_leyinf.setLayout(fl);
        pn_leyinf.setBackground(super.color_panel_central);
        
        JLabel lbl_libre, lbl_subgrupo, lbl_sub1, lbl_sub2; 
        JTextField txt_libre, txt_subgrupo, txt_sub1, txt_sub2; 
        
        lbl_libre = new LblPanel("Libre");
        lbl_subgrupo = new LblPanel("Subgrupo");
        lbl_sub1 = new LblPanel("A-C-E-G");
        lbl_sub2 = new LblPanel("B-D-F-H");
        
        txt_libre = new TxtPanel(2);
        txt_libre.setBorder(null);
        txt_libre.setBackground(super.color_txt_libre);
        
        txt_subgrupo = new TxtPanel(2);
        txt_subgrupo.setBorder(null);
        txt_subgrupo.setBackground(super.color_txt_subgrupo);
        
        txt_sub1 = new TxtPanel(2);
        txt_sub1.setBorder(null);
        txt_sub1.setBackground(super.color_txt_sub1);
        
        txt_sub2 = new TxtPanel(2);
        txt_sub2.setBorder(null);
        txt_sub2.setBackground(super.color_txt_sub2);
        
        pn_leyinf.add(lbl_libre);
        pn_leyinf.add(txt_libre);
        pn_leyinf.add(new LblPanel(" --- "));
        
        pn_leyinf.add(lbl_subgrupo);
        pn_leyinf.add(txt_subgrupo);
        pn_leyinf.add(new LblPanel(" --- "));
        
        pn_leyinf.add(lbl_sub1);
        pn_leyinf.add(txt_sub1);
        pn_leyinf.add(new LblPanel(" --- "));
        
        pn_leyinf.add(lbl_sub2);
        pn_leyinf.add(txt_sub2);
        
        pn_leyinf.updateUI();
        return pn_leyinf;
    }
    
    private void setComponentesUsr(){
        pn_usr = new JPanel();
        FlowLayout fl = new FlowLayout();
        pn_usr.setLayout(fl);
        pn_usr.setBackground(super.color_panel_central);
        
        JLabel lbl_vacacion, lbl_libre_vacacion, lbl_pedido, lbl_concedido;
        JTextField txt_vacacion, txt_libre_vacacion, txt_pedido, txt_concedido;
        
        lbl_vacacion = new LblPanel("Vacación");
        lbl_libre_vacacion = new LblPanel("Li_Vac");
        lbl_pedido = new LblPanel("Pedido");
        lbl_concedido = new LblPanel("Concedido");
        
        txt_vacacion = new TxtPanel(2);
        txt_vacacion.setBorder(null);
        txt_vacacion.setBackground(super.color_txt_vacacion);
        
        txt_libre_vacacion = new TxtPanel(2);
        txt_libre_vacacion.setBorder(null);
        txt_libre_vacacion.setBackground(super.color_txt_libre_vacacion);
        
        txt_pedido = new TxtPanel(2);
        txt_pedido.setBorder(null);
        txt_pedido.setBackground(super.color_txt_pedido);
        
        txt_concedido = new TxtPanel(2);
        txt_concedido.setBorder(null);
        txt_concedido.setBackground(super.color_txt_concedido);
        
        pn_usr.add(new LblPanel(" --- "));
        pn_usr.add(lbl_vacacion);
        pn_usr.add(txt_vacacion);
        pn_usr.add(new LblPanel(" --- "));
        
        pn_usr.add(lbl_libre_vacacion);
        pn_usr.add(txt_libre_vacacion);
        pn_usr.add(new LblPanel(" --- "));
        
        pn_usr.add(lbl_pedido);
        pn_usr.add(txt_pedido);
        pn_usr.add(new LblPanel(" --- "));
        
        pn_usr.add(lbl_concedido);
        pn_usr.add(txt_concedido);
        
        pn_usr.updateUI();
        pn_inf.add(pn_usr);
        pn_inf.updateUI();
        this.updateUI();
    }
    
    private void removeComUsr(){
        if(pn_usr != null){
             pn_inf.remove(pn_usr);
        }
        pn_inf.updateUI();
        this.updateUI();
    }
    
    private void setColorYearUsr(){
        pn_year.resetColor(color_panel_frame);
        setComponentesUsr();
        int y = getYear();
        setColorFestivos(y);
        setColorLibresUsr(y);
        setColorVacacionUsr(y);
        setColorPedidosUsr(y);
        setColorConcedidosUsr(y);
    }
    
    private void setColorYear() {
        pn_year.resetColor(color_panel_frame);
        int y = getYear();
        String g = getGrupo();
        String sub = getSubgrupo();
        ctr_calendario.setDiasLibres(y, g, sub); //calcula los libres

        setColorLibresYear();
        setColorSubgrupoYear();
        setColorSubComunesYear();
        setColorFestivos(y);
    }
    
    private void setColorLibresYear() {
        Map<Integer, List<String>> map_libres = ctr_calendario.getListaLibres();
          setColor(map_libres, color_txt_libre, false);
    }
    
    private void setColorSubgrupoYear(){
        Map<Integer, List<String>> map_libres = ctr_calendario.getListaSubgrupo();
          setColor(map_libres, color_txt_subgrupo, false);
    }
    
    private void setColorSubComunesYear(){
        Map<Integer, List<String>> map_libres = ctr_calendario.getListaSubComunes();
            String sub = getSubgrupo();
            //A-C-E-G
            if(sub.equalsIgnoreCase("A") || sub.equalsIgnoreCase("C") || sub.equalsIgnoreCase("E") || sub.equalsIgnoreCase("G")){
                setColor(map_libres, color_txt_sub1, false);
            } else {
                setColor(map_libres, color_txt_sub2, false);
            }
    }
    
    private void setColorFestivos(int year){
         Map<Integer, List<String>> map_festivos = ctr_calendario.getListaFestivos(year);
          setColor(map_festivos, color_txt_festivos , true);
    }
    
    
    /**
     * Da color al calendario del usr
     * @param year 
     */
    private void setColorLibresUsr(int year) {
        Map<Integer, List<String>> map_libres;
        map_libres = ctr_calendario.getLibresUsr(year, "grupo");
        setColor(map_libres, color_txt_libre, false);
        map_libres.clear();
        map_libres = ctr_calendario.getLibresUsr(year, "subgrupo");
        setColor(map_libres, color_txt_subgrupo, false);
        map_libres.clear();
        map_libres = ctr_calendario.getLibresUsr(year, "sub_com");
        setColor(map_libres, color_txt_sub2, false); //obtener la letra subgrupo del usr
    }
    
    private void setColorVacacionUsr(int year){
        Map<Integer, List<String>> map_vacacion = ctr_calendario.getListaVacacionUsr(year);
         for(int a = 1; a <= 12; a++){
             pn_year.setColorVacacion(map_vacacion.get(a), a, color_txt_libre, color_txt_vacacion, color_txt_libre_vacacion);
         }
    }
    
    private void setColorPedidosUsr(int year){
        Map<Integer, List<String>> map_pedidos = ctr_calendario.getListaPedidosUsr(year);
          setColor(map_pedidos, color_txt_pedido, false);
    }
    
     private void setColorConcedidosUsr(int year){
        Map<Integer, List<String>> map_concedidos = ctr_calendario.getListaConcedidosUsr(year);
          setColor(map_concedidos, color_txt_concedido, false);
    }
    
     
     /**
      * Colorea las casillas de cada mes
      * @param map_libres
      * @param c
      * @param foreground 
      */
    private void setColor(Map<Integer, List<String>> map_libres, Color c, boolean foreground){
        for(int a = 1; a <= 12; a++){
            pn_year.setColorDias(map_libres.get(a), c, a, foreground);
        }
    }
    
    
    
    

    
    private class OyenteBuscar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            resetPnCtr();
            removeComUsr();
            if(getGrupo().equalsIgnoreCase("usr")){
                setColorYearUsr();
            } else {
                setColorYear();
            }
            
        }
        
        
        
    }
    
    
}
