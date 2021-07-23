
package com.wanyos.vista;

import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.ComboBox;
import com.wanyos.componentes.comunes.LblPanel;
import com.wanyos.modelo.ModeloTabla;
import com.wanyos.componentes.comunes.TxtPanel;
import com.wanyos.controlador.CtrLibres;
import com.wanyos.modelo.LibreGenerado;
import static java.awt.Cursor.HAND_CURSOR;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author wanyos
 */
public class PnLibreGenerado extends PnAbstract {
    
    private final CtrLibres ctr_libres;
    private JComboBox cbo_tipo;
    private JCheckBox chk_disponible;
    private JPanel pn_ctr, pn_sup, pn_tabla, pn_inf, pn_nuevo_cambio;
    private BtnMenu btn_buscar, btn_editar, btn_eliminar, btn_actualizar;
    private JTable tabla;
    private ModeloTabla model;
    private JScrollPane sr;
    private String [] tipo_libres;
    private final String [] nombre_columnas = {"ID","Tipo","Fecha generado","Fecha cobro","Fecha disfrute"};
    private String id;
    private JTextField txt_fecha_c, txt_fecha_d;
    
    
     public PnLibreGenerado(CtrLibres ctr_libres){
        super();
        this.ctr_libres = ctr_libres;
        crearComponent();
        addComponentRight();
        setPnCtr();
        this.add(pn_ctr);
        this.updateUI();
    }
     
     private void crearComponent() {
        btn_buscar = new BtnMenu("Buscar");
        btn_editar = new BtnMenu("Editar");
        btn_eliminar = new BtnMenu("Eliminar");
        btn_actualizar = new BtnMenu("Actualizar");

        btn_buscar.setName("btn_buscar");
        btn_editar.setName("btn_editar");
        btn_eliminar.setName("btn_eliminar");
        btn_actualizar.setName("btn_actualizar");

        btn_buscar.setCursor(new Cursor(HAND_CURSOR));
        btn_editar.setCursor(new Cursor(HAND_CURSOR));
        btn_eliminar.setCursor(new Cursor(HAND_CURSOR));
        btn_actualizar.setCursor(new Cursor(HAND_CURSOR));

        btn_buscar.addActionListener(new OyenteButton());
        btn_editar.addActionListener(new OyenteButton());
        btn_eliminar.addActionListener(new OyenteButton());
        btn_actualizar.addActionListener(new OyenteButton());
    }

    private void addComponentRight() {
        super.pn_right.add(btn_buscar);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        super.pn_right.add(btn_editar);
        super.pn_right.add(btn_eliminar);
        super.pn_right.add(btn_actualizar);
        super.pn_right.updateUI();
    }

     private void setPnCtr(){
        pn_ctr = new JPanel();
        pn_ctr.setBackground(super.COLOR_PANEL_CENTRAL);
        BoxLayout ly = new BoxLayout(pn_ctr, BoxLayout.Y_AXIS);
        pn_ctr.setLayout(ly);
        pn_ctr.setMaximumSize(new Dimension(800,600));
        
        setPnSup();
        pn_ctr.add(pn_sup);
        
        pn_ctr.updateUI();
     }
     
     private void setPnSup(){
         pn_sup = new JPanel();
         pn_sup.setBackground(super.COLOR_PANEL_CENTRAL);
         FlowLayout fl = new FlowLayout();
         fl.setVgap(10);
         pn_sup.setLayout(fl);
         pn_sup.setMaximumSize(new Dimension(800,50));
         pn_sup.setBorder(new BevelBorder(BevelBorder.RAISED));
         
         JLabel lbl_tipo, lbl_disponible;
         
         lbl_tipo = new LblPanel("Tipo");
         lbl_disponible = new LblPanel("Disponible");
         
         tipo_libres = ctr_libres.getTipoLibres();  //obtiene los tipos de la tabla generado
         cbo_tipo = new ComboBox(tipo_libres);
         cbo_tipo.addItem("Todos");
         cbo_tipo.addItemListener(new OyenteChange());
         chk_disponible = new JCheckBox();
         chk_disponible.setBackground(super.COLOR_PANEL_CENTRAL);
         chk_disponible.addItemListener(new OyenteChange());
         
         pn_sup.add(lbl_tipo);
         pn_sup.add(cbo_tipo);
         pn_sup.add(new LblPanel(" --- "));
         
         pn_sup.add(lbl_disponible);
         pn_sup.add(chk_disponible);
         
         pn_sup.updateUI();
     }
     
    private void setPnTabla() {
        if (pn_tabla == null) {
            pn_tabla = new JPanel();
        } else {
            pn_tabla.removeAll();
            pn_ctr.remove(pn_tabla);
        }
        pn_tabla.setMaximumSize(new Dimension(800,550));
        pn_tabla.setBackground(super.COLOR_PANEL_CENTRAL);
        pn_tabla.setLayout(new FlowLayout());
        setTable();
        pn_tabla.add(sr);
        pn_tabla.updateUI();
        pn_ctr.add(pn_tabla);
        pn_ctr.updateUI();
    }
    
     private void setTable(){
         tabla = new JTable();
         tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
         tabla.addMouseListener(new OyenteMouse());
         if(model == null){
             model = new ModeloTabla(0, nombre_columnas.length, nombre_columnas);
         }
         tabla.setModel(model);
         sr = new JScrollPane(tabla);
         
         model.centrarColumnasTabla(tabla);
         model.setLinesFields(false, tabla);
         model.setBorderColum(false, tabla);
         
         model.setColorTable(super.COLOR_PANEL_CENTRAL, sr, tabla);
         
         model.setSelectionMultiplesFiles(tabla, true);
         model.setColorSelection(new Color(23,180,20), tabla);
         model.deseleccionarFilaTabla(this, tabla);

         model.setColorCabecera(super.COLOR_PANEL_CENTRAL, tabla);        
         model.setColorTxtCabecera(COLOR_LETRA_BLANCO, tabla);
         model.setFontCabecera(FUENTE_LETRA, tabla);
         
         model.setColorTxtColumnas(COLOR_LETRA_BLANCO, tabla);
         model.setFontTxtColumnas(FUENTE_LETRA, tabla);
         
         int alto_tabla = model.getRowCount() * 40;
         if(alto_tabla > 500){
             alto_tabla = 500;
         }
         model.setSizeTable(sr, 500, alto_tabla);
         int [] ancho_colum = {60,60,120,120,120};
         model.setAnchoColumn(ancho_colum, tabla);
     }
    
    private void setPnInf(String [] fila){
        if(pn_inf == null){
            pn_inf = new JPanel();
        } else {
            pn_inf.removeAll();
            pn_ctr.remove(pn_inf);
        }
        pn_inf.setBackground(super.COLOR_PANEL_CENTRAL);
        pn_inf.setLayout(new GridLayout(2,5,8,8));
        pn_inf.setMaximumSize(new Dimension(800,50));
        
        JLabel lbl_id, lbl_tipo, lbl_fecha_g, lbl_fecha_c, lbl_fecha_d;
        JTextField txt_id, txt_tipo, txt_fecha_g;
        
        
        lbl_id = new LblPanel("ID");
        lbl_tipo = new LblPanel("Tipo");
        lbl_fecha_g = new LblPanel("Fecha generado");
        lbl_fecha_c = new LblPanel("Fecha cobro");
        lbl_fecha_d = new LblPanel("Fecha disfrute");
        
        txt_id = new TxtPanel(5);
        txt_id.setEditable(false);
        txt_id.setText(fila[0]);
        this.id = fila[0].trim();
        
        txt_tipo = new TxtPanel(5);
        txt_tipo.setEditable(false);
        txt_tipo.setText(fila[1]);
        
        txt_fecha_g = new TxtPanel(10);
        txt_fecha_g.setEditable(false);
        txt_fecha_g.setText(fila[2]);
        
        txt_fecha_c = new TxtPanel(10);
        txt_fecha_c.addFocusListener(new OyenteFoco());
        txt_fecha_c.setText(fila[3]);
                
        txt_fecha_d = new TxtPanel(10);
        txt_fecha_d.addFocusListener(new OyenteFoco());
        txt_fecha_d.setText(fila[4]);
                
        pn_inf.add(lbl_id);
        pn_inf.add(lbl_tipo);
        pn_inf.add(lbl_fecha_g);
        pn_inf.add(lbl_fecha_c);
        pn_inf.add(lbl_fecha_d);
        
        pn_inf.add(txt_id);
        pn_inf.add(txt_tipo);
        pn_inf.add(txt_fecha_g);
        pn_inf.add(txt_fecha_c);
        pn_inf.add(txt_fecha_d);
        
        pn_inf.updateUI();
        pn_ctr.updateUI();
    }
    
    public void setPnNuevosCambios(List<LibreGenerado> lista_nuevos, List<LibreGenerado> lista_cambios){
        if(pn_nuevo_cambio == null){
            pn_nuevo_cambio = new JPanel();
        } else {
            pn_nuevo_cambio.removeAll();
            pn_ctr.remove(pn_nuevo_cambio);
        }
        
        pn_nuevo_cambio.setMaximumSize(new Dimension(800,300));
        pn_nuevo_cambio.setBackground(super.COLOR_PANEL_CENTRAL);
        
        JTextArea txt_area = new JTextArea();
        txt_area.setColumns(40);
        txt_area.setRows(10);
        txt_area.setEditable(false);
        txt_area.setBackground(super.COLOR_PANEL_CENTRAL);
        txt_area.setFont(FUENTE_LETRA);
        txt_area.setForeground(COLOR_LETRA_BLANCO);
      
        if(!lista_nuevos.isEmpty()){
            txt_area.append("Dias nuevos añadidos: \n");
            for(LibreGenerado aux: lista_nuevos){
                txt_area.append(" "+aux.getTipo()+" "+aux.getFechaGenerado()+"\n");
            }
            txt_area.append("\n\n");
        }
        
        if(!lista_cambios.isEmpty()){
            txt_area.append("Cambios en dias: \n");
            for(LibreGenerado aux: lista_cambios){
                txt_area.append(" "+aux.getTipo()+" - "+aux.getFechaGenerado()+" - "+aux.getFechaCobro()+" - "+aux.getFechaDisfrute()+"\n");
            }
            txt_area.append("\n");
        }
        
        if(lista_nuevos.isEmpty() && lista_cambios.isEmpty()){
            txt_area.append("--No existen cambios \n");
        }
        
        pn_nuevo_cambio.add(txt_area);
        pn_nuevo_cambio.updateUI();
        pn_ctr.add(pn_nuevo_cambio);
        pn_ctr.updateUI();
    }
     
     
    
     
     
     private void setDatosCombo(){
         String tipo = (String) this.cbo_tipo.getSelectedItem();
         boolean chk = this.chk_disponible.isSelected();
         List<LibreGenerado> listado = ctr_libres.getListadoLibres(tipo, chk);
         if(listado == null || listado.isEmpty()){
             super.setMensajeLbl("No existen libres...");
             model = null;
         } else {
             model = new ModeloTabla(nombre_columnas, listado);
         }
          setPnTabla();
     }
     
     
     private void getFilaSelect(int r){
         String [] fila = new String[5];
         for(int a = 0; a < fila.length; a++){
             fila[a] = (String) tabla.getValueAt(r, a);
         }
         mostrarDatos(fila);
     }
     
     private void mostrarDatos(String [] fila){
         this.setPnInf(fila);
         pn_ctr.add(pn_inf);
         pn_ctr.updateUI();
     }
     
     private void setComprobarEditar(){
        String regexp = "\\d{4}-\\d{1,2}-\\d{1,2}";
        String f_c = this.txt_fecha_c.getText().trim();
        String f_d = this.txt_fecha_d.getText().trim();
        
        if(Pattern.matches(regexp, f_c) && Pattern.matches(regexp, f_d)){
            super.setMensajeLbl("Error no pueden existir fechas en fecha_cobro y fecha_disfrute...");
        } else {
            setEditar();
        }
     }
     
     private void setEditar(){
         boolean correcto = ctr_libres.setEditarLibre(id, this.txt_fecha_c.getText().trim(), this.txt_fecha_d.getText().trim());
         if(correcto){
             JOptionPane.showMessageDialog(this, "!!!Los datos han sido editados...", "Datos editados...", JOptionPane.INFORMATION_MESSAGE);
         } else {
             JOptionPane.showMessageDialog(this, "!!!Los datos no se han editado...", "Datos editados...", JOptionPane.ERROR_MESSAGE);
         }
         resetPn();
     }
     
    private void setEliminar() {
        int op = JOptionPane.showConfirmDialog(null, "Confirmar eliminar datos?", "Eliminar...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (op == 0) {
            boolean correcto = ctr_libres.setEliminarLibre(id);
            if (correcto) {
                JOptionPane.showMessageDialog(this, "!!!El libre ha sido eliminado...", "Eliminar libre...", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "!!!El libre no se ha eliminado...", "Eliminar libre...", JOptionPane.ERROR_MESSAGE);
            }
                resetPn();
        }
    }
     
    private void resetPn() {
        super.setMensajeLbl(" --- ");
        if(pn_tabla != null){
            pn_ctr.remove(pn_tabla);
        }
        if(pn_inf != null){
            pn_ctr.remove(pn_inf);
        }
        if(pn_nuevo_cambio != null){
            pn_ctr.remove(pn_nuevo_cambio);
        }
        btn_buscar.setEnabled(true);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        pn_ctr.updateUI();
    }
     
     
     private class OyenteButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
               resetPn();
            if (e.getActionCommand().equalsIgnoreCase("Buscar")) {
                setDatosCombo();
            } else if (e.getActionCommand().equalsIgnoreCase("Editar")) {
                setComprobarEditar();
            } else if (e.getActionCommand().equalsIgnoreCase("Eliminar")) {
                setEliminar();
            } else if (e.getActionCommand().equalsIgnoreCase("Actualizar")) {
                ctr_libres.actualizarDiasGenerados();
            }
                
        }
    }
     
     private class OyenteChange implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
           resetPn();
        }
     }
     
     
     private class OyenteFoco implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            Object obj = e.getSource();
            if(obj instanceof JTextField){
                ((JTextField) obj).setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            Object obj = e.getSource();
            if(obj instanceof JTextField){
                String m = ((JTextField) obj).getText();
                if(m == null || m.isEmpty()){
                    ((JTextField) obj).setText("--");
                }
            }
        }
     }
     
     
    private class OyenteMouse extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            getFilaSelect(tabla.getSelectedRow());
            btn_editar.setEnabled(true);
            btn_eliminar.setEnabled(true);
            btn_buscar.setEnabled(false);
        }

    }

     
     
    
}
