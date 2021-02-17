
package com.wanyos.vista;


import com.toedter.calendar.JTextFieldDateEditor;
import com.wanyos.componentes.BtnMenu;
import com.wanyos.componentes.CalendarChooser;
import com.wanyos.componentes.ComboBox;
import static com.wanyos.componentes.Configuraciones.color_letra_blanco;
import com.wanyos.componentes.LblPanel;
import com.wanyos.componentes.PanelesNombramiento;
import com.wanyos.controlador.CtrNombramiento;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private JButton btn_buscar_turno, btn_guardar_servicio;
    private ComboBox cbo_descripcion;
    private CalendarChooser calendar;
    private LocalDate fecha;
    private PanelesNombramiento pn_nombramiento;
    private JPanel pn_fecha, pn_turno_linea;
    private JLabel lbl_mensaje;
    private JTextField aux1, aux2;
    
    
    
    public PnNombramiento(JLabel lbl_mensaje, CtrNombramiento ctr){
        this.ctr_nombramiento = ctr;
        this.lbl_mensaje = lbl_mensaje;
        this.lbl_mensaje.setText(" --- ");
        crearComponent();
        crearPnFecha();
        addComponentRight();
        pn_nombramiento = new PanelesNombramiento();
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
        calendar.addPropertyChangeListener(new OyenteCalendar()); 
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
      
      
     private void setListenerBtnBuscarTurno(){
         btn_buscar_turno = pn_nombramiento.getBtnBuscarTurno();
         btn_buscar_turno.addActionListener(new OyenteButton());
     } 
      
     private void setListenerBtnGuardarServicio(){
        btn_guardar_servicio = pn_nombramiento.getBtnGuardarServicio();
        btn_guardar_servicio.addActionListener(new OyenteButton());
     }   
     
     private void setListenerCboDescripcion(){
         cbo_descripcion = pn_nombramiento.getCboDescripcion();
         cbo_descripcion.addActionListener(new OyenteCombo());
     }
     
      
     private void setFecha() {
        if (calendar.getDate() != null) {
            Date d = calendar.getDate();
            fecha = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            this.lbl_mensaje.setText("--Error valor fecha no válido... ");
        }
    }
     
    @Override
     public void setMensajeLbl(String mensaje){
         this.lbl_mensaje.setText("--"+mensaje);
     }
     
     
   
    
    
    
    
    private void setCrearServicio() {
        btn_crear.setEnabled(false);
        btn_buscar.setEnabled(false);
        calendar.setEnabled(false);
        JPanel pn_des = pn_nombramiento.getPnDescripcion();
        this.setListenerCboDescripcion();
        super.pn_center.add(pn_des);
        super.pn_center.updateUI();
    }
    
    private void setNuevoServicio() {
        btn_buscar.setEnabled(true);
        btn_crear.setEnabled(true);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        calendar.setEnabled(true);
        this.fecha = null;
        pn_nombramiento.setResetCampos();
        super.pn_center.removeAll();
        super.pn_center.updateUI();
        this.crearPnFecha();
    }

    private void setBuscarServicio() {
       String [] datos = ctr_nombramiento.getDatosServicio(fecha);
       if(datos != null){
           this.btn_buscar.setEnabled(false);
           JPanel pn_turno_linea, pn_horario, pn_nota, pn_puesto_descripcion;
           String [] datos_servicio;
           boolean turno = false;
           
           if(datos[0] != null && datos[1] != null){
               //mostrar datos turno y linea
               pn_turno_linea = pn_nombramiento.getPnTurnoLinea(false, false);
               pn_nombramiento.setTxtTurno(datos[0]);
               pn_nombramiento.setTxtLinea(datos[1]);
               datos_servicio = ctr_nombramiento.getDatosTurno(fecha, datos[0], datos[1]);
               turno = true;
               super.pn_center.add(pn_turno_linea);
           } else {
               //mostrar datos otro servicio
               pn_puesto_descripcion = pn_nombramiento.getPnPuestoDescripcion(false);
               pn_nombramiento.setTxtPuesto(datos[2]);
               pn_nombramiento.setTxtDescripcion(datos[3]);
               datos_servicio = ctr_nombramiento.getDatosOtroServicio(fecha, datos[2]);
               turno = false;
               super.pn_center.add(pn_puesto_descripcion);
           }
           
           pn_horario = pn_nombramiento.getPnHorario(false, turno);
           if(datos_servicio != null){
               setDatosHorario(datos_servicio);
           }
           
           pn_nota = pn_nombramiento.getPnNota(false);
           pn_nombramiento.setTxtNota(datos[4]);
           
           //cargar paneles
           btn_crear.setEnabled(false);
           btn_editar.setEnabled(true);
           btn_eliminar.setEnabled(true);
           super.pn_center.add(pn_horario);
           super.pn_center.add(pn_nota);
           calendar.setEnabled(false);
           
       } else {
           lbl_mensaje.setText("--No existe servicio..."); 
       }
        super.pn_center.updateUI();
    }

    
    
    private void setDatosHorario(String [] datos){
        List<JTextField> cajas_horario = pn_nombramiento.getTxtCajasHorario();
        int contador = 0;
        for(JTextField aux: cajas_horario){
            aux.setText(datos[contador++]);
        }
    }
    
    
    private void setPnTurnoLinea(){
        this.cbo_descripcion.setEnabled(false);
        pn_turno_linea = pn_nombramiento.getPnTurnoLinea(true, true);
        this.setListenerBtnBuscarTurno();
        super.pn_center.add(pn_turno_linea);
        super.pn_center.updateUI();
    }
    
    private JPanel getPnHorarioTurno (String [] datos_turno){
        JPanel pn_horario_turno = pn_nombramiento.getPnHorario(false, true);
        List<JTextField> cajas_horario = pn_nombramiento.getTxtCajasHorario();
            int contador = 0;
            for (JTextField aux : cajas_horario) {
                aux.setText(datos_turno[contador++]);
            }
        return pn_horario_turno;
    }
    
    private String[] getHorarioOtroServicio() {
        String[] datos_otro = new String[6];
        List<JTextField> cajas_horario = pn_nombramiento.getTxtCajasHorario();
        int contador = 0;
        for (JTextField aux : cajas_horario) {
            datos_otro[contador++] = aux.getText();
        }
        return datos_otro;
    }
    
    private void setPnHorarioTurno(JPanel pn_horario_turno) {
        pn_nombramiento.setEnabledPn(pn_turno_linea, false);
        JPanel pn_nota = pn_nombramiento.getPnNota(true);
        JPanel pn_btn_guardar = pn_nombramiento.getPnBtnGuardar();
        this.setListenerBtnGuardarServicio();
        this.btn_guardar_servicio.setName("btn_guardar_servicio");
        
        this.lbl_mensaje.setText(" --- ");
        super.pn_center.add(pn_horario_turno);
        super.pn_center.add(pn_nota);
        super.pn_center.add(pn_btn_guardar);
        super.pn_center.updateUI();
    }
    
    private void setOtroServicio(){
        cbo_descripcion.setEnabled(false);
        super.pn_center.add(pn_nombramiento.getPnPuestoDescripcion(true));
        
        pn_nombramiento.getJTxtPuesto().addFocusListener(new OyenteFocoOtro());
        pn_nombramiento.getJTxtDescripcion().addFocusListener(new OyenteFocoOtro());
        super.pn_center.add(pn_nombramiento.getPnHorario(false, false));
        
        List<JTextField> cajas_horario = pn_nombramiento.getTxtCajasHorario();
        aux1 = cajas_horario.get(0);
        aux2 = cajas_horario.get(3);
        aux1.addFocusListener(new OyenteFocoHora());
        aux2.addFocusListener(new OyenteFocoHora());
        
        super.pn_center.add(pn_nombramiento.getPnNota(true));
        super.pn_center.add(pn_nombramiento.getPnBtnGuardar());
        this.setListenerBtnGuardarServicio();
        bloquearPanel("pn_guardar");
        this.btn_guardar_servicio.setName("btn_guardar_servicio");
        super.pn_center.updateUI();
    }

    
    private void setGuardarDatos() {
        boolean c = false;
        if (!pn_nombramiento.getTxtTurno().isEmpty() || !pn_nombramiento.getTxtLinea().isEmpty()) {
            c = ctr_nombramiento.setGuardarDatosTurno(fecha, pn_nombramiento.getTxtTurno(), pn_nombramiento.getTxtLinea(), pn_nombramiento.getTxtNota());
        } else {
            c = ctr_nombramiento.setGuardarDatosOtro(fecha, pn_nombramiento.getTxtPuesto(), pn_nombramiento.getTxtDescripcion(), getHorarioOtroServicio(), pn_nombramiento.getTxtNota());
        }
        if (c) {
            JOptionPane.showMessageDialog(this, "!!! Datos guardados...");
            this.setNuevoServicio();
        } else {
            this.lbl_mensaje.setText("--Error los datos no se han guardado...");
        }
    }
    
    private void setGuardarEditar() {
        boolean c = false;
        if (!pn_nombramiento.getTxtTurno().isEmpty() || !pn_nombramiento.getTxtLinea().isEmpty()) {
            c = ctr_nombramiento.setGuardarEditarTurno(fecha, pn_nombramiento.getTxtTurno(), pn_nombramiento.getTxtLinea(), pn_nombramiento.getTxtNota());
        } else {
            c = ctr_nombramiento.setGuardarEditarOtro(fecha, pn_nombramiento.getTxtPuesto(), pn_nombramiento.getTxtDescripcion(), getHorarioOtroServicio(), pn_nombramiento.getTxtNota());
        }

        if (c) {
            JOptionPane.showMessageDialog(this, "!!! Datos editados...");
            this.setNuevoServicio();
        } else {
            this.lbl_mensaje.setText("--Error los datos no se han editado...");
        }
    }
    

    private void setEditar() {
        String[] nombres_pn;
        if (!pn_nombramiento.getTxtTurno().isEmpty() && !pn_nombramiento.getTxtLinea().isEmpty()) {
            pn_nombramiento.getJTxtTurno().addFocusListener(new OyenteFocoTurno());
            pn_nombramiento.getJTxtLinea().addFocusListener(new OyenteFocoTurno());
            nombres_pn = new String[]{"pn_turno_linea", "pn_nota"};
            for (String name : nombres_pn) {
                desbloquearPanel(name);
            }
        } else {
            nombres_pn = new String[]{"pn_puesto_descripcion", "pn_nota"};
            for (String name : nombres_pn) {
                desbloquearPanel(name);
            }
            List<JTextField> cajas_horario = pn_nombramiento.getTxtCajasHorario();   //si el puesto no existe lo creara en la tabla puesto       
            aux1 = cajas_horario.get(0);
            aux2 = cajas_horario.get(3);
            aux1.addFocusListener(new OyenteFocoHora());
            aux2.addFocusListener(new OyenteFocoHora());
            pn_nombramiento.getJTxtPuesto().addFocusListener(new OyenteFocoOtro());
            pn_nombramiento.getJTxtDescripcion().addFocusListener(new OyenteFocoOtro());
        }
        super.pn_center.add(pn_nombramiento.getPnBtnGuardar());
        this.setListenerBtnGuardarServicio();
        this.btn_guardar_servicio.setText("Guardar Edición");
        this.btn_guardar_servicio.setName("btn_guardar_editar");
        bloquearPanel("pn_guardar");
        this.btn_editar.setEnabled(false);
        this.btn_eliminar.setEnabled(false);
        super.pn_center.updateUI();
    }
    
    private List<JPanel> getPaneles(){
        List<JPanel> lista_pn = new ArrayList<>();
        Stack<JPanel> pila = new Stack<>();
        pila.push(this);
        
        while(!pila.isEmpty()){
            JPanel pn = pila.firstElement();
            pila.remove(pn);
            int v = pila.size();
            Component [] com = pn.getComponents();
            for(Component aux: com){
                if(aux instanceof JPanel){
                    pila.push((JPanel) aux);
                } 
            }
            if(pila.size() == v){
                lista_pn.add(pn);
            }
        }
        return lista_pn;
    }
    
    private void bloquearPanel(String name){
        List<JPanel> lista_pn = this.getPaneles();
        for(JPanel aux: lista_pn){
            if(aux.getName() != null && aux.getName().equalsIgnoreCase(name)){
                pn_nombramiento.setEnabledPn(aux, false);
            }
        }
    }
    
    private void desbloquearPanel(String nombre_pn) {
        List<JPanel> lista_pn = this.getPaneles();
        for (JPanel pn : lista_pn) {
            if (pn.getName() != null && nombre_pn != null && pn.getName().equalsIgnoreCase(nombre_pn)) {
                pn_nombramiento.setEnabledPn(pn, true);
            }
        }
    }
    
    private void limpiarTxtHorario(){
        List<JTextField> lista_txt = pn_nombramiento.getTxtCajasHorario();
        for(JTextField aux: lista_txt){
            aux.setText("");
        }
    }
    
    private void setEliminarServicio(){
        int op = JOptionPane.showConfirmDialog(null, "Confirmar eliminar datos?", "Eliminar...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if(op == 0){
             boolean c = ctr_nombramiento.setEliminarDatos(fecha);
            if(c){
                this.lbl_mensaje.setText("--Datos eliminados... ");
            } else {
                 this.lbl_mensaje.setText("--Error los datos no se han eliminado...");
            }
        } else {
            setNuevoServicio();
        }
    }
    
    private boolean getTurnoCorrect(String turno){
        boolean c = false;
        if(turno != null && turno.length() > 0){
            try {
                Integer.parseInt(turno);
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
                Integer.parseInt(linea);
                c = true;
            } catch (NumberFormatException e){
                return c;
            }
        }
        return c;
    }
    
    
    
    
    
    
    private class OyenteButton implements ActionListener {
        
      
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            
            if (obj instanceof JButton) {
                setFecha();

                 lbl_mensaje.setText(" --- ");
                switch (((JButton) obj).getName()) {
                    
                    //botones menu lateral
                    case "btn_nuevo":
                        setNuevoServicio();
                        break;

                    case "btn_crear":
                        boolean crear = true;
                        eventoComprobarFecha(crear);
                        break;

                    case "btn_buscar":
                        boolean buscar = false;
                        eventoComprobarFecha(buscar);
                        break;

                    case "btn_editar":
                        setEditar();
                        break;
                    case "btn_eliminar":
                        setEliminarServicio();
                        break;

                    //botones internos
                    case "btn_buscar_turno":
                        eventoBtnBuscarTurno();
                        break;

                    case "btn_guardar_servicio":
                        setGuardarDatos();
                        break;
                        
                    case "btn_editar_servicio":
                        //setEditarServicio();
                        break;
                        
                    case "btn_guardar_editar":
                        setGuardarEditar();
                        break;
                }

            }
        }
        
        
        private void eventoComprobarFecha(boolean crear) {
            if (fecha != null) {
                String mensaje = ctr_nombramiento.getDiaDisponible(fecha);
                
                if (!crear) {
                    setBuscarServicio();
                } else if (mensaje.equalsIgnoreCase("disponible") && crear) {
                    setCrearServicio();
                } else {
                    lbl_mensaje.setText("--Error!!! es un dia " + mensaje);
                }
            } else {
                lbl_mensaje.setText("--Error!!! no existe fecha...");
            }
        }
        
        private void eventoBtnBuscarTurno() {
            if (getTurnoCorrect(pn_nombramiento.getTxtTurno()) && getLineaCorrect(pn_nombramiento.getTxtLinea())) {
                
                String turno = pn_nombramiento.getTxtTurno();
                String linea = pn_nombramiento.getTxtLinea();
                String [] datos_turno =  ctr_nombramiento.getDatosTurno(fecha, turno, linea);
                
                if (datos_turno[0].equalsIgnoreCase("")) {
                    lbl_mensaje.setText("--No existen datos con ese turno... ");
                } else {
                    JPanel pn_turno = getPnHorarioTurno(datos_turno);
                    setPnHorarioTurno(pn_turno);
                }
                
            } else {
                lbl_mensaje.setText("--Error valor turno y linea...");
            }
        }
        
    }
    
    
    
    
    private class OyenteCombo implements ActionListener {

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

                    } else if (select.contains("Otros")) {
                        setOtroServicio();
                    }

                }
            }
        }
    }
    
    
    

       private class OyenteCalendar implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            lbl_mensaje.setText(" --- ");
        }
           
       }
       
       
       
       
       
     private class OyenteFocoHora implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            JTextField obj = (JTextField) e.getSource();
            if (obj.getText().length() > 0) {
                obj.setText("");
                lbl_mensaje.setText(" --- ");
                obj.setForeground(color_letra_blanco);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField obj = (JTextField) e.getSource();
            try {
                if (obj.getText().length() > 0) {
                    LocalTime.parse(((JTextField) obj).getText());
                }
                Color c1 = aux1.getForeground();
                Color c2 = aux2.getForeground();
                if ((aux1.getText().length() > 0 && aux2.getText().length() > 0) && (!c1.equals(Color.red) && !c2.equals(Color.red))) {
                    desbloquearPanel("pn_guardar");
                }
            } catch (Exception ex) {
                lbl_mensaje.setText("--Error en formato hora...");
                obj.setForeground(Color.red);
                bloquearPanel("pn_guardar");
            }

        }
    }
      
      
    private class OyenteFocoOtro implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            JTextField obj = (JTextField) e.getSource();
            if (obj.getText().length() > 0) {
                obj.setText("");
                pn_nombramiento.getJTxtDescripcion().setText("");
                lbl_mensaje.setText(" --- ");
                obj.setForeground(color_letra_blanco);
            }
        }

         @Override
        public void focusLost(FocusEvent e) {
            String puesto = pn_nombramiento.getTxtPuesto();
            String descripcion = pn_nombramiento.getTxtDescripcion();
            if (puesto.length() > 0 && descripcion.length() > 0) {
                String[] datos = ctr_nombramiento.getPuesto(puesto, descripcion);
                if (datos != null) {
                    setDatosHorario(datos);
                    desbloquearPanel("pn_guardar");
                } else {
                    String[] nombre_pn = {"pn_init", "pn_fin"};
                    for (String name : nombre_pn) {
                        desbloquearPanel(name);
                        limpiarTxtHorario();
                    }
                }
            }
        }

    }
     
     
    private class OyenteFocoTurno implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            JTextField obj = (JTextField) e.getSource();
            if (obj.getText().length() > 0) {
                obj.setText("");
                pn_nombramiento.getJTxtLinea().setText("");
                lbl_mensaje.setText(" --- ");
                obj.setForeground(color_letra_blanco);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            String turno = pn_nombramiento.getTxtTurno();
            String linea = pn_nombramiento.getTxtLinea();
            if (turno.length() > 0 && linea.length() > 0) {
                String[] datos = ctr_nombramiento.getDatosTurno(fecha, turno, linea);
                if (!datos[0].isEmpty()) {
                    setDatosHorario(datos);
                    desbloquearPanel("pn_guardar");
                } else {
                    lbl_mensaje.setText("--No existe el turno...");
                }
            }
        }

    }
    
    
   
        
        
    }
    
   
    
    

