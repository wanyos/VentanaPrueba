
package com.wanyos.vista;


import com.toedter.calendar.JTextFieldDateEditor;
import com.wanyos.componentes.BtnMenu;
import com.wanyos.componentes.CalendarChooser;
import com.wanyos.componentes.ComboBox;
import com.wanyos.componentes.LblPanel;
import com.wanyos.componentes.TxtPanel;
import com.wanyos.controlador.CtrNombramiento;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.swing.*;


/**
 *
 * @author wanyos
 */
public class PnNombramiento extends PnAbstract {
    
    private CtrNombramiento ctr_nombramiento;
    private BtnMenu btn_crear, btn_buscar, btn_nuevo, btn_editar, btn_eliminar;
    private JButton btn_guardar;
    private CalendarChooser calendar;
    private LocalDate fecha;
    private JPanel pn_fecha, pn_descripcion, pn_turno_linea, pn_horario;
    private JLabel lbl_mensaje;
    
    
    
    public PnNombramiento(JLabel lbl_mensaje, CtrNombramiento ctr){
        this.ctr_nombramiento = ctr;
        this.lbl_mensaje = lbl_mensaje;
        this.lbl_mensaje.setText(" --- ");
        crearComponent();
        crearPnFecha();
        addComponentRight();
    }
    
    private List<JTextField> getCajasTurnoLinea(){
        List<JTextField> cajas_turno_linea = new ArrayList<>();
        Component[] c = pn_turno_linea.getComponents();
            for (Component aux : c) {
                if (aux instanceof JTextField) {
                   cajas_turno_linea.add((JTextField) aux);
                }
            }
        return cajas_turno_linea;
    }
    
    private List<JTextField> getCajasHorario() {
        List<JTextField> cajas_horario = new ArrayList<>();
        Stack<JPanel> pila;
        pila = new Stack<>();
        pila.push(pn_horario);

        while (!pila.isEmpty()) {
            JPanel p = pila.firstElement();
            pila.remove(p);
            Component[] c = p.getComponents();
            for (Component aux : c) {
                if (aux instanceof JPanel) {
                    pila.push((JPanel) aux);
                } else if (aux instanceof JTextField) {
                    cajas_horario.add((JTextField) aux);
                }
            }
        }
        return cajas_horario;
    }
    
    private JTextArea getAreaNota() {
        JTextArea txt_area = null;
        Component[] c = pn_horario.getComponents();
        for (Component aux : c) {
            if (aux instanceof JPanel) {
                if (aux.getName().contains("pn_nota")) {
                    Component[] com = ((JPanel) aux).getComponents();
                    for (Component aux_p : com) {
                        if (aux_p instanceof JScrollPane) {
                            txt_area = (JTextArea) ((JScrollPane) aux_p).getViewport().getComponent(0);

                        }
                    }

                }
            }
        }
        return txt_area;
    }
    
    
    private void addDatosTurnoLinea(String[] turno_linea) {
        int contador = 0;
        if (this.getCajasTurnoLinea() != null) {
            List<JTextField> cajas_turno_linea = this.getCajasTurnoLinea();
            for (JTextField caja : cajas_turno_linea) {
                caja.setText(turno_linea[contador]);
                contador++;
            }
        }
    }
    
    private void addDatosHorario(String [] horario) {
        int contador = 0;
        if (this.getCajasHorario() != null) {
            List<JTextField> cajas_horario = this.getCajasHorario();
            for (JTextField caja : cajas_horario) {
                caja.setText(horario[contador]);
                contador++;
            }
        }
    }
    
    private void addNota(String nota) {
        if (this.getAreaNota() != null) {
            JTextArea area = this.getAreaNota();
            area.append(nota);
        }
    }
    
   
    private void addDatos() {
        String [] tur_linea = this.getDatosTurnoLinea();
        String[] datos_turno = ctr_nombramiento.getDatosTurno(fecha, tur_linea[0], tur_linea[1]);
        if (datos_turno == null) {
             this.lbl_mensaje.setText("--Error no existe turno... ");
        } else {
            this.addDatosHorario(datos_turno);
            this.addNota(datos_turno[datos_turno.length - 1]);
            this.setEnabledPn(this.pn_turno_linea, false);
            this.setEnabledPn(this.pn_horario, false);
        }
    }
    
    private String[] getDatosTurnoLinea(){
        String [] datos_turno_linea = new String[2];
        if(this.getCajasTurnoLinea() != null){
            List<JTextField> cajas_turno_linea = this.getCajasTurnoLinea();
            datos_turno_linea[0] = cajas_turno_linea.get(0).getText();
            datos_turno_linea[1] = cajas_turno_linea.get(1).getText();
        }
        return datos_turno_linea;
    }
    
    private String[] getDatosHorario(){
        String [] datos_horario = new String[12];
        if(this.getCajasHorario() != null){
            List<JTextField> cajas_horario = this.getCajasHorario();
            for(int a = 0; a < datos_horario.length; a++){
                datos_horario[a] = cajas_horario.get(a).getText();
            }
        }
        return datos_horario;
    }
    
    private String getDatoNota(){
        String nota = "";
        if(this.getAreaNota() != null){
            JTextArea area = this.getAreaNota();
            nota = area.getText();
        }
        return nota;
    }

    
    
    private void crearComponent(){
        btn_crear = new BtnMenu("Crear");
        btn_buscar = new BtnMenu("Buscar");
        btn_nuevo = new BtnMenu("Nuevo");
        btn_editar = new BtnMenu("Editar");
        btn_eliminar = new BtnMenu("Eliminar");
        
        btn_crear.setName("btn_crear");
        btn_buscar.setName("btn_buscar");
        btn_nuevo.setName("btn_nuevo");
        btn_editar.setName("btn_editar");
        btn_eliminar.setName("btn_eliminar");
        
        btn_crear.addActionListener(new OyenteButton());
        btn_buscar.addActionListener(new OyenteButton());
        btn_nuevo.addActionListener(new OyenteButton());
        btn_editar.addActionListener(new OyenteButton());
        btn_eliminar.addActionListener(new OyenteButton());
    }
    
    
    private void crearPnFecha(){
        if(pn_fecha == null){
            pn_fecha = new JPanel();
            pn_fecha.setName("pn_fecha");
        } else {
            pn_fecha.removeAll();
            pn_fecha.updateUI();
        }
        pn_fecha.setMaximumSize(new Dimension(2147483647,50));
        pn_fecha.setBackground(super.color_panel_central);
        
        JLabel lbl_fecha = new LblPanel("Fecha: ");
        
        calendar = new CalendarChooser();
        JTextFieldDateEditor edit = calendar.getEditor();
        edit.setHorizontalAlignment(0);
        edit.setForeground(PnAbstract.color_letra_blanco);
        edit.setFont(PnAbstract.fuente_letra);
        edit.setBackground(color_panel_central);
        
        pn_fecha.add(lbl_fecha);
        pn_fecha.add(calendar);
        pn_fecha.updateUI();
        super.pn_center.add(pn_fecha);
        super.pn_center.updateUI();
    }
    
    
      private void crearPnDescripcion() {
        if (pn_descripcion == null) {
            pn_descripcion = new JPanel();
            pn_descripcion.setName("pn_descripcion");
        } else {
            pn_descripcion.removeAll();
            pn_descripcion.updateUI();
        }
       
        JLabel lbl_descripcion;
        ComboBox cbo_descripcion;
        
        pn_descripcion.setLayout(new FlowLayout());
        pn_descripcion.setMaximumSize(new Dimension(2147483647,50));
        pn_descripcion.setBackground(super.color_panel_central);
        
        lbl_descripcion = new LblPanel("Descripcion puesto: ");

        String [] items = {" ---- "," Turno en linea "," Otros"};
        cbo_descripcion = new ComboBox(items);
        cbo_descripcion.setColorFondo(super.color_panel_central);
        cbo_descripcion.setColorTexto(PnAbstract.color_letra_blanco);
        cbo_descripcion.setName("cbo_descripcion");
        cbo_descripcion.addActionListener(new OyenteCombo(cbo_descripcion));
        
        pn_descripcion.add(lbl_descripcion);
        pn_descripcion.add(cbo_descripcion);
        pn_descripcion.updateUI();
    }
    
    
      
      
    private void crearPnTurnoLinea(boolean buscar, boolean bloquear){
        if (pn_turno_linea == null){
            pn_turno_linea = new JPanel();
            pn_turno_linea.setName("pn_turno_linea");
        } else {
            pn_turno_linea.removeAll();
            pn_turno_linea.updateUI();
        }

        JLabel lbl_turno, lbl_linea, lbl_sep1, lbl_sep2;
        JTextField txt_turno, txt_linea;
        JButton btn_buscar;
        
        pn_turno_linea.setLayout(new FlowLayout());
        pn_turno_linea.setMaximumSize(new Dimension(2147483647,50));
        pn_turno_linea.setBackground(super.color_panel_central);
        
        lbl_turno = new LblPanel("Turno");
        lbl_linea = new LblPanel("Linea");
        lbl_sep1 = new LblPanel("  ---  ");
        lbl_sep2 = new LblPanel("  ---  ");
        
        txt_turno = new TxtPanel(5);
        txt_linea = new TxtPanel(5);
        txt_turno.setName("turno");
        txt_linea.setName("linea");
        
        btn_buscar = new JButton("Buscar");
        btn_buscar.setFont(PnAbstract.fuente_letra);
        btn_buscar.setName("btn_buscar_turno");
        btn_buscar.addActionListener(new OyenteButton(txt_turno, txt_linea, btn_buscar));
        
        pn_turno_linea.add(lbl_turno);
        pn_turno_linea.add(txt_turno);
        pn_turno_linea.add(lbl_sep1);
        pn_turno_linea.add(lbl_linea);
        pn_turno_linea.add(txt_linea);
        if(buscar){
           pn_turno_linea.add(lbl_sep2);
           pn_turno_linea.add(btn_buscar); 
        }
        
        this.setEnabledPn(pn_turno_linea, bloquear);
        pn_turno_linea.updateUI();
    }
    
    private void crearPnHorario(boolean guardar, boolean bloquear){
        if(pn_horario == null){
            pn_horario = new JPanel();
            pn_horario.setName("pn_horario");
        } else {
            pn_horario.removeAll();
            pn_horario.updateUI();
        }
      
        pn_horario.setMaximumSize(new Dimension(2147483647,300));
        pn_horario.setBackground(super.color_panel_central);
        pn_horario.setLayout(new BoxLayout(pn_horario, BoxLayout.Y_AXIS));
        
        pn_horario.add(getPnInit(bloquear));
        pn_horario.add(getPnFin(bloquear));
        pn_horario.add(getPnInit(bloquear));
        pn_horario.add(getPnFin(bloquear));
        pn_horario.add(getPnNotaHorario(guardar, bloquear));
        
        pn_horario.updateUI();
    }
    
    private JPanel getPnInit(boolean bloquear){
        JPanel pn_init = new JPanel();
        pn_init.setName("pn_init");
        JLabel lbl_h_init, lbl_l_init, lbl_dir_init, lbl_sep1, lbl_sep2;
        JTextField txt_h_init, txt_l_init, txt_dir_init;
        
        pn_init.setMaximumSize(new Dimension(2147483647,50));
        pn_init.setBackground(super.color_panel_central);
        pn_init.setLayout(new FlowLayout());
        
        lbl_h_init = new LblPanel("H_Init");
        lbl_l_init = new LblPanel("L_Init");
        lbl_dir_init = new LblPanel("Dir_Init");
        lbl_sep1 = new LblPanel(" --- ");
        lbl_sep2 = new LblPanel(" --- ");
        
        txt_h_init = new TxtPanel(5);
        txt_l_init = new TxtPanel(15);
        txt_dir_init = new TxtPanel(15);
        
        pn_init.add(lbl_h_init);
        pn_init.add(txt_h_init);
        pn_init.add(lbl_sep1);
        
        pn_init.add(lbl_l_init);
        pn_init.add(txt_l_init);
        pn_init.add(lbl_sep2);
        
        pn_init.add(lbl_dir_init);
        pn_init.add(txt_dir_init);
        
        this.setEnabledPn(pn_init, bloquear);
        
        return pn_init;
    }
    
    private JPanel getPnFin(boolean bloquear){
        JPanel pn_fin = new JPanel();
        pn_fin.setName("pn_fin");
        JLabel lbl_h_fin, lbl_l_fin, lbl_dir_fin, lbl_sep1, lbl_sep2;
        JTextField txt_h_fin, txt_l_fin, txt_dir_fin;
        
        pn_fin.setMaximumSize(new Dimension(2147483647,50));
        pn_fin.setBackground(super.color_panel_central);
        pn_fin.setLayout(new FlowLayout());
        
        lbl_h_fin = new LblPanel("H_Fin");
        lbl_l_fin = new LblPanel("L_Fin");
        lbl_dir_fin = new LblPanel("Dir_Fin");
        lbl_sep1 = new LblPanel(" --- ");
        lbl_sep2 = new LblPanel(" --- ");
        
        txt_h_fin = new TxtPanel(5);
        txt_l_fin = new TxtPanel(15);
        txt_dir_fin = new TxtPanel(15);
        
        pn_fin.add(lbl_h_fin);
        pn_fin.add(txt_h_fin);
        pn_fin.add(lbl_sep1);
        
        pn_fin.add(lbl_l_fin);
        pn_fin.add(txt_l_fin);
        pn_fin.add(lbl_sep2);
        
        pn_fin.add(lbl_dir_fin);
        pn_fin.add(txt_dir_fin);
        
        this.setEnabledPn(pn_fin, bloquear);
        
        return pn_fin;
    }
    
    private JPanel getPnNotaHorario(boolean ver_btn_guardar, boolean bloquear){
        JPanel pn_nota = new JPanel();
        pn_nota.setName("pn_nota");
        JLabel lbl_nota;
        JScrollPane scrol;
        JTextArea txt_nota;
        JLabel lbl_sep;
        
        lbl_sep = new LblPanel(" ----- ");
        pn_nota.setMaximumSize(new Dimension(2147483647,200));
        pn_nota.setBackground(super.color_panel_central);
        FlowLayout fl = new FlowLayout();
        fl.setHgap(25);
        pn_nota.setLayout(fl);
        
        lbl_nota = new LblPanel("Notas");
        
        scrol = new JScrollPane();
        txt_nota = new JTextArea(5,30); //filas y columnas
        txt_nota.setBackground(color_panel_central);
        txt_nota.setForeground(PnAbstract.color_letra_blanco);
        txt_nota.setFont(PnAbstract.fuente_letra);
        txt_nota.setLineWrap(true);   
        scrol.setViewportView(txt_nota);
        btn_guardar = new JButton("Guardar");
        btn_guardar.setName("btn_guardar");
        btn_guardar.setFont(PnAbstract.fuente_letra);
        btn_guardar.addActionListener(new OyenteButton());
        
        pn_nota.add(lbl_nota);
        pn_nota.add(lbl_sep);
        pn_nota.add(scrol);
        pn_nota.add(btn_guardar); 
        this.btn_guardar.setVisible(ver_btn_guardar);
        
        this.setEnabledPn(pn_nota, bloquear);
        
        return pn_nota;
    }
    
   
    
    private void addComponentRight(){
        super.pn_right.add(btn_nuevo);
        super.pn_right.add(btn_crear);
        super.pn_right.add(btn_buscar);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        super.pn_right.add(btn_editar);
        super.pn_right.add(btn_eliminar);
        super.pn_right.updateUI();
    }
    
    private void setFecha() {
        if (calendar.getDate() != null) {
            Date d = calendar.getDate();
            fecha = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            this.lbl_mensaje.setText("--Error valor fecha no válido... ");
        }
    }
    
    private void setCrear() {
        this.lbl_mensaje.setText(" --- ");
        btn_crear.setEnabled(false);
        btn_buscar.setEnabled(false);
        calendar.setEnabled(false);
        this.crearPnDescripcion();
        super.pn_center.add(pn_descripcion);
        super.pn_center.updateUI();
    }
    
    private void setNuevo() {
        btn_buscar.setEnabled(true);
        btn_crear.setEnabled(true);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        calendar.setEnabled(true);
        this.lbl_mensaje.setText(" --- ");
        this.fecha = null;
        this.btn_guardar.setText("Guardar");
        super.pn_center.removeAll();
        super.pn_center.updateUI();
        this.crearPnFecha();
    }

    private void setBuscar() {
        this.lbl_mensaje.setText(" --- ");
        this.crearPnTurnoLinea(false, false);
        this.crearPnHorario(false, false);
        this.addDatos();
        btn_crear.setEnabled(false);
        btn_editar.setEnabled(true);
        btn_eliminar.setEnabled(true);
        calendar.setEnabled(false);
        super.pn_center.add(pn_turno_linea);
        super.pn_center.add(pn_horario);
        super.pn_center.updateUI();
    }
    
    private void setEditar(){
        this.lbl_mensaje.setText(" --- ");
        btn_eliminar.setEnabled(false);
        this.setEnabledPn(this.pn_turno_linea, true);
        this.setEnabledPn(this.pn_horario, true);
        this.btn_guardar.setText("Editar");
        this.btn_guardar.setVisible(true);
    }
    
    private void setEliminar() {
        this.lbl_mensaje.setText(" --- ");
        btn_editar.setEnabled(false);
        this.btn_guardar.setText("Eliminar");
        this.btn_guardar.setVisible(true);
    }
   
    private void setEnabledPn(JPanel pn, boolean bloquear) {
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
                }
            }
        }
    }
    
    private void setPnTurnoLinea(){
        this.crearPnTurnoLinea(true, true);
        super.pn_center.add(pn_turno_linea);
        super.pn_center.updateUI();
    }
    
    private void setPnHorario() {
        this.crearPnHorario(true, true);
        this.addDatos();
        super.pn_center.add(pn_horario);
        super.pn_center.updateUI();
    }
    
    private void setOtros(){
        this.crearPnTurnoLinea(false, true);
        this.crearPnHorario(true, true);
        
        super.pn_center.add(pn_turno_linea);
        super.pn_center.add(pn_horario);
        super.pn_center.updateUI();
    }
    
    private void setGuardarDatos() {
        boolean c = ctr_nombramiento.setGuardarDatos(this.fecha, this.getDatosTurnoLinea(), this.getDatosHorario(), this.getDatoNota());
        if (c) {
            this.lbl_mensaje.setText("--Datos guardados... ");
            this.setNuevo();
        } else {
            this.lbl_mensaje.setText("--Error los datos no se han guardado...");
        }
    }
    
    private void setEditarDatos(){
        int op = JOptionPane.showConfirmDialog(null, "Confirmar editar datos?", "Editar...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(op == 0){
            boolean c = ctr_nombramiento.setEditarDatos(this.getDatosTurnoLinea(), this.getDatosHorario(), this.getDatoNota());
            if(c){
                this.lbl_mensaje.setText("--Datos editados... ");
                this.setNuevo();
            } else {
                 this.lbl_mensaje.setText("--Error los datos no se han editado...");
            }
        } 
    }
    
    private void setEliminarDatos(){
        int op = JOptionPane.showConfirmDialog(null, "Confirmar eliminar datos?", "Eliminar...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if(op == 0){
             boolean c = ctr_nombramiento.setEliminarDatos(this.getDatosTurnoLinea(), this.getDatosHorario(), this.getDatoNota());
            if(c){
                this.lbl_mensaje.setText("--Datos eliminados... ");
                this.setNuevo();
            } else {
                 this.lbl_mensaje.setText("--Error los datos no se han eliminado...");
            }
        } 
    }
    
    private boolean getTurnoCorrect(String turno){
        boolean c = false;
        if(turno != null && turno.length() > 0){
            try {
                int valor = Integer.parseInt(turno);
                c = true;
            } catch (NumberFormatException e){
                return c;
            }
        }
        return c;
    }
    
    private boolean getLineaCorrect(String linea){
        boolean c = false;
        if(linea != null && linea.length() > 0){
            try {
                int valor = Integer.parseInt(linea);
                c = true;
            } catch (NumberFormatException e){
                return c;
            }
        }
        return c;
    }
    
    
    
    
    
    
    private class OyenteButton implements ActionListener {
        
        private JTextField txt_turno;
        private JTextField txt_linea;
        private JButton btn_buscar_turno;
        
        public OyenteButton() {
        }
        
        public OyenteButton(JTextField txt_turno, JTextField txt_linea, JButton btn_buscar_turno) {
            this.txt_turno = txt_turno;
            this.txt_linea = txt_linea;
            this.btn_buscar_turno = btn_buscar_turno;
        }
        
        private void setPanelHorario() {
            if (getTurnoCorrect(txt_turno.getText()) && getLineaCorrect(txt_linea.getText())) {
                setPnHorario();
                btn_buscar_turno.setEnabled(false);
                lbl_mensaje.setText(" --- ");
            } else {
                lbl_mensaje.setText("--Error valor turno y linea...");
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj instanceof JButton) {
                setFecha();
                if (((JButton) obj).getName().equalsIgnoreCase("btn_nuevo")) {
                    setNuevo();

                } else if (((JButton) obj).getName().equalsIgnoreCase("btn_crear") && fecha != null) {
                    String mensaje = ctr_nombramiento.getDiaDisponible(fecha);
                    if(mensaje.equalsIgnoreCase("disponible")){
                        setCrear();
                    } else {
                        lbl_mensaje.setText("--Error!!! es un dia "+mensaje);
                    }
                    
                } else if (((JButton) obj).getName().equalsIgnoreCase("btn_buscar") && fecha != null) {
                    
                    setBuscar();

                } else if (((JButton) obj).getName().equalsIgnoreCase("btn_editar")) {
                    setEditar();
                    
                } else if (((JButton) obj).getName().equalsIgnoreCase("btn_eliminar")) {
                    setEliminar();
                    
                } else if (((JButton) obj).getName().equalsIgnoreCase("btn_buscar_turno")) {
                    setPanelHorario();
                    
                } else if(((JButton) obj).getName().equalsIgnoreCase("btn_guardar")){
                    if(((JButton) obj).getText().contains("Guardar")){
                        setGuardarDatos();
                    } else if(((JButton) obj).getText().contains("Editar")){
                        setEditarDatos();
                    } else if(((JButton) obj).getText().contains("Eliminar")){
                        setEliminarDatos();
                    }
                }

            }
        }

    }
    
    
    
    
    private class OyenteCombo implements ActionListener {
        
        private JComboBox cbo_descripcion;
        
        public OyenteCombo() {
        }
        
        public OyenteCombo(JComboBox cbo) {
            this.cbo_descripcion = cbo;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            String name;
            if (obj instanceof JComboBox) {
                name = ((JComboBox) obj).getName();
                if (name.equalsIgnoreCase("cbo_descripcion")) {
                    String select = (String) ((JComboBox) obj).getSelectedItem();
                    
                    if (select.contains("Turno en linea")) {
                        setPnTurnoLinea();
                        cbo_descripcion.setEnabled(false);
                        
                    } else if (select.contains("Otros")) {
                        setOtros();
                        cbo_descripcion.setEnabled(false);
                    }
                }
            }
        }
    }
    
    
   
    
    
}
