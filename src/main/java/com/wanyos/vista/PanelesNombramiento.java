
package com.wanyos.vista;

import com.wanyos.componentes.Configuraciones;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.TxtPanel;
import com.wanyos.componentes.comunes.LblPanel;
import com.wanyos.componentes.comunes.ComboBox;
import com.wanyos.componentes.comunes.Lista;
import com.wanyos.componentes.comunes.TxtAreaPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Crea los paneles de la ventana de nombramiento
 * @author wanyos
 */
public class PanelesNombramiento implements Configuraciones {
    
    private JTextField txt_turno, txt_linea, txt_puesto, txt_descripcion;
    private List<JTextField> cajas_horario;
    private ComboBox cbo_descripcion;
    private BtnMenu btn_buscar_turno, btn_guardar_servicio;
    private TxtAreaPanel txt_area_nota;
    private ImageIcon img_azul_buscar, img_gris_buscar, img_azul_aceptar, img_gris_aceptar;
    private Lista lista;
   
    
    public PanelesNombramiento(){
       img_azul_buscar =  new ImageIcon(getClass().getResource(R_IMG_AZUL_BUSCAR));
       img_gris_buscar = new ImageIcon(getClass().getResource(R_IMG_GRIS_BUSCAR));
       img_azul_aceptar = new ImageIcon(getClass().getResource(R_IMG_AZUL_ACEPTAR));
       img_gris_aceptar = new ImageIcon(getClass().getResource(R_IMG_GRIS_ACEPTAR));
    }
    
    
    public String getTxtTurno() {
        if(txt_turno == null){
            return "";
        }
        return txt_turno.getText();
    }

    public void setTxtTurno(String t) {
        this.txt_turno.setText(t);
    }

    public String getTxtLinea() {
        if(txt_linea == null){
            return "";
        }
        return txt_linea.getText();
    }

    public void setTxtLinea(String t) {
        this.txt_linea.setText(t);
    }

    public String getTxtPuesto() {
        if(txt_puesto == null){
            return "";
        }
        return txt_puesto.getText();
    }

    public void setTxtPuesto(String t) {
        this.txt_puesto.setText(t);
    }

    public String getTxtDescripcion() {
        if(txt_descripcion == null){
            return "";
        }
        return txt_descripcion.getText();
    }

    public void setTxtDescripcion(String t) {
        this.txt_descripcion.setText(t);
    }
   
    public String getTxtNota(){
        if(txt_area_nota == null){
            return "";
        }
        return this.txt_area_nota.getText();
    }
    
    public void setTxtNota(String t){
        this.txt_area_nota.setText(t);
    }
    
    public Lista getLista(){
        return this.lista;
    }
    
    
    public void setVisibleBtnGuardar(boolean b){
        this.btn_guardar_servicio.setVisible(b);
    }
    
    public void setResetCampos() {
        if (txt_turno != null) {
            txt_turno.setText(null);
        }
        if (txt_linea != null) {
            txt_linea.setText(null);
        }
        if (txt_puesto != null) {
            txt_puesto.setText(null);
        }
        if (txt_descripcion != null) {
            txt_descripcion.setText(null);
        }
        if (txt_area_nota != null) {
            txt_area_nota.setText(null);
        }
    }
        
   
    
    
    public List<JTextField> getTxtCajasHorario(){
        return this.cajas_horario;
    }
    
    public ComboBox getCboDescripcion(){
        return this.cbo_descripcion;
    }
    
    public JButton getBtnBuscarTurno(){
        return this.btn_buscar_turno;
    }
    
    public JButton getBtnBuscarOtroServicio(){
        return this.btn_guardar_servicio;
    }
    
    public JButton getBtnGuardarServicio(){
        return this.btn_guardar_servicio;
    }
    
    public JTextField getJTxtTurno(){
        return this.txt_turno;
    }
    
    public JTextField getJTxtLinea(){
        return this.txt_linea;
    }
    
    public JTextField getJTxtPuesto(){
        return this.txt_puesto;
    }
    
    public JTextField getJTxtDescripcion(){
        return this.txt_descripcion;
    }
    
    
    
    public JPanel getPnDescripcion() {
        JPanel pn_descripcion = new JPanel();
        pn_descripcion.setName("pn_descripcion");
        JLabel lbl_descripcion;
        
        FlowLayout fl = new FlowLayout();
        fl.setVgap(20);
        pn_descripcion.setLayout(fl);
        pn_descripcion.setMaximumSize(new Dimension(2147483647,75));
        pn_descripcion.setBackground(COLOR_PANEL_CENTRAL);
        
        lbl_descripcion = new LblPanel("Descripcion puesto: ");

        String [] items = {" ---- "," Turno en linea "," Otros"};
        cbo_descripcion = new ComboBox(items);
        cbo_descripcion.setColorFondo(COLOR_PANEL_CENTRAL);
        cbo_descripcion.setColorTexto(COLOR_LETRA_BLANCO);
        cbo_descripcion.setName("cbo_descripcion");
        
        pn_descripcion.add(lbl_descripcion);
        pn_descripcion.add(cbo_descripcion);
        pn_descripcion.updateUI();
        return pn_descripcion;
    }
    
    
    public JPanel getPnTurnoLinea(boolean buscar, boolean bloquear){
        JPanel pn_turno_linea = new JPanel();
        pn_turno_linea.setName("pn_turno_linea");
        JLabel lbl_turno, lbl_linea, lbl_sep1, lbl_sep2;
        
        pn_turno_linea.setLayout(new FlowLayout());
        pn_turno_linea.setMaximumSize(new Dimension(2147483647,75));
        pn_turno_linea.setBackground(COLOR_PANEL_CENTRAL);
        
        lbl_turno = new LblPanel("Turno");
        lbl_linea = new LblPanel("Linea");
        lbl_sep1 = new LblPanel("  ---  ");
        lbl_sep2 = new LblPanel("  ---  ");
        
        txt_turno = new TxtPanel(5);
        txt_linea = new TxtPanel(5);
        txt_turno.setName("turno");
        txt_linea.setName("linea");
        
        btn_buscar_turno = new BtnMenu("Buscar");
        btn_buscar_turno.setColorFondo(COLOR_PANEL_LATERAL);
        btn_buscar_turno.setIcono(img_azul_buscar);
        btn_buscar_turno.setIconoFoco(img_gris_buscar);
        btn_buscar_turno.setColorFoco(COLOR_PANEL_LATERAL, COLOR_PANEL_CENTRAL);
        btn_buscar_turno.setName("btn_buscar_turno");
        
        pn_turno_linea.add(lbl_turno);
        pn_turno_linea.add(txt_turno);
        pn_turno_linea.add(lbl_sep1);
        pn_turno_linea.add(lbl_linea);
        pn_turno_linea.add(txt_linea);
        if(buscar){
           pn_turno_linea.add(lbl_sep2);
           pn_turno_linea.add(btn_buscar_turno); 
        }
        
        this.setEnabledPn(pn_turno_linea, bloquear);
        pn_turno_linea.updateUI();
        return pn_turno_linea;
    }
    
    
    /**
     * 
     * @param bloquear
     * @param isturno puede ser llamado desde un turno en linea u otro servicio
     * @return 
     */
    public JPanel getPnHorario(boolean bloquear, boolean isturno){
        JPanel pn_horario = new JPanel();
        pn_horario.setName("pn_horario");
        cajas_horario = new ArrayList<>();
        pn_horario.setBackground(COLOR_PANEL_CENTRAL);
        pn_horario.setLayout(new BoxLayout(pn_horario, BoxLayout.Y_AXIS));
        
        pn_horario.add(getPnInit());
        pn_horario.add(getPnFin());
        if (isturno) {
            pn_horario.add(getPnInit());
            pn_horario.add(getPnFin());
        }
       
        this.setEnabledPn(pn_horario, bloquear);
        pn_horario.updateUI();
        return pn_horario;
    }
    
    private JPanel getPnInit(){
         return this.getH("pn_init", "h_init", "l_init", "dir_init");
    }
    
    private JPanel getPnFin(){
         return this.getH("pn_fin", "h_fin", "l_fin", "dir_fin");
    }
    
    private JPanel getH(String nombre_pn, String t1, String t2, String t3){
        JPanel pn_h = new JPanel();
        pn_h.setName(nombre_pn);
        JLabel lbl_1, lbl_2, lbl_3, lbl_sep1, lbl_sep2;
        JTextField txt_1, txt_2, txt_3;
        
        pn_h.setMaximumSize(new Dimension(2147483647,50));
        pn_h.setBackground(COLOR_PANEL_CENTRAL);
        pn_h.setLayout(new FlowLayout());
        
        lbl_1 = new LblPanel(t1);
        lbl_2 = new LblPanel(t2);
        lbl_3 = new LblPanel(t3);
        lbl_sep1 = new LblPanel(" --- ");
        lbl_sep2 = new LblPanel(" --- ");
        
        txt_1 = new TxtPanel(7);
        txt_2 = new TxtPanel(15);
        txt_3 = new TxtPanel(15);
        
        cajas_horario.add(txt_1);
        cajas_horario.add(txt_2);
        cajas_horario.add(txt_3);
        
        pn_h.add(lbl_1);
        pn_h.add(txt_1);
        pn_h.add(lbl_sep1);
        
        pn_h.add(lbl_2);
        pn_h.add(txt_2);
        pn_h.add(lbl_sep2);
        
        pn_h.add(lbl_3);
        pn_h.add(txt_3);
        
        return pn_h;
    }
    
    public JPanel getPnNota(boolean bloquear){
        JPanel pn_nota = new JPanel();
        pn_nota.setName("pn_nota");
        JLabel lbl_nota;
        JLabel lbl_sep;
        
        lbl_sep = new LblPanel(" ----- ");
        pn_nota.setMaximumSize(new Dimension(2147483647,100));
        pn_nota.setBackground(COLOR_PANEL_CENTRAL);
        FlowLayout fl = new FlowLayout();
        fl.setHgap(25);
        pn_nota.setLayout(fl);
        
        lbl_nota = new LblPanel("Notas");
        txt_area_nota = new TxtAreaPanel(5,30);
        
        pn_nota.add(lbl_nota);
        pn_nota.add(lbl_sep);
        pn_nota.add(txt_area_nota.getScrolTxtArea());
        
        this.setEnabledPn(pn_nota, bloquear);
        return pn_nota;
    }


    public JPanel getPnBtnGuardar() {
        JPanel pn_guardar = new JPanel();
        pn_guardar.setName("pn_guardar");
        
        pn_guardar.setMaximumSize(new Dimension(2147483647,80));
        pn_guardar.setBackground(COLOR_PANEL_CENTRAL);
        FlowLayout fl = new FlowLayout();
        fl.setVgap(15);
        pn_guardar.setLayout(fl);
        
        btn_guardar_servicio = new BtnMenu("Guardar");
        btn_guardar_servicio.setColorFondo(COLOR_PANEL_LATERAL);
        btn_guardar_servicio.setIcono(img_azul_aceptar);
        btn_guardar_servicio.setIconoFoco(img_gris_aceptar);
        btn_guardar_servicio.setColorFoco(COLOR_PANEL_LATERAL, COLOR_PANEL_CENTRAL);
        btn_guardar_servicio.setName("btn_guardar");
        
        pn_guardar.add(btn_guardar_servicio);
        return pn_guardar;
    }
    
    public JPanel getPnPuestoDescripcion(boolean bloquear){
        JPanel pn_puesto = new JPanel();
        pn_puesto.setName("pn_puesto_descripcion");
        JLabel lbl_puesto, lbl_descripcion, lbl_sep;
        
        pn_puesto.setMaximumSize(new Dimension(2147483647,80));
        pn_puesto.setBackground(COLOR_PANEL_CENTRAL);
        pn_puesto.setLayout(new FlowLayout());
        
        lbl_puesto = new LblPanel("Puesto");
        lbl_descripcion = new LblPanel("Descripcion");
        lbl_sep = new LblPanel("  ---  ");
        
        txt_puesto = new TxtPanel(10);
        txt_descripcion = new TxtPanel(10);
        
        pn_puesto.add(lbl_puesto);
        pn_puesto.add(txt_puesto);
        pn_puesto.add(lbl_sep);
        pn_puesto.add(lbl_descripcion);
        pn_puesto.add(txt_descripcion);
        
        this.setEnabledPn(pn_puesto, bloquear);
        return pn_puesto;
    }
    
    public JPanel getPnLeerCorreo(){
        JPanel pn_leer_correo = new JPanel();
        pn_leer_correo.setName("pn_leer_correo");
        Border white_border = BorderFactory.createLineBorder(Color.WHITE);
        pn_leer_correo.setBorder(BorderFactory.createTitledBorder​(white_border, "Turnos leidos...", TitledBorder.LEFT, 0, FUENTE_LETRA_LBL, COLOR_LETRA_BLANCO));
        
        pn_leer_correo.setMaximumSize(new Dimension(850,450));
        pn_leer_correo.setBackground(COLOR_PANEL_CENTRAL);
        pn_leer_correo.setLayout(new FlowLayout());
        
        lista = new Lista();
        Font fuente = new Font("Bookman Old Style",2,12);
        lista.setFont(fuente);
        lista.setAlinear(1);
       
        pn_leer_correo.add(lista);
        pn_leer_correo.updateUI();
        
        return pn_leer_correo;
    }

    public void setEnabledPn(JPanel pn, boolean bloquear) {
        Stack<JPanel> pila = new Stack();
        pila.push(pn);

        while (!pila.isEmpty()) {
            JPanel panel = pila.firstElement();
            pila.remove(panel);

            Component[] c = panel.getComponents();
            for (Component aux : c) {
                if (aux instanceof JPanel) {
                    pila.push((JPanel) aux);
                } else if (aux instanceof JTextField) {
                    aux.setEnabled(bloquear);
                } else if (aux instanceof JScrollPane) {
                    Component[] cx = ((JScrollPane) aux).getViewport().getComponents();
                    for (Component aux_j : cx) {
                        aux_j.setEnabled(bloquear);
                    }
                } else if (aux instanceof BtnMenu) {
                    BtnMenu bt = (BtnMenu) aux;
                    bt.setEnabled(bloquear);
                    if (!bloquear) {
                        bt.setColorFoco(COLOR_PANEL_CENTRAL, COLOR_PANEL_CENTRAL);
                    }
                } else if (aux instanceof JButton) {
                    aux.setEnabled(bloquear);
                }
            }
        }
    }
    
    

    
    
}
