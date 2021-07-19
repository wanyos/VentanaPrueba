
package com.wanyos.componentes;

import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.TxtPanel;
import com.wanyos.componentes.comunes.Lista;
import com.wanyos.componentes.comunes.LblPanel;
import com.wanyos.componentes.comunes.CalendarChooser;
import com.wanyos.componentes.comunes.ComboBox;
import com.wanyos.controlador.CtrCambiosPedidos;
import com.wanyos.modelo.LibreGenerado;
import com.wanyos.modelo.ModeloLista;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import java.util.List;
import java.util.regex.Pattern;



/**
 * @author wanyos
 */
public class PanelPedirDia extends JPanel implements Configuraciones {
    
    private JPanel pn_pedido, pn_ofrecido, pn_boton;
    private CalendarChooser datechooser;
    private JTextField txt_ofrecido;
    private JComboBox cbo_tipo;
    private String [] tipo_libres;
    private Lista lista_dias;
    private ImageIcon img_azul_aceptar, img_gris_aceptar, img_azul_buscar, img_gris_buscar;
    private CtrCambiosPedidos ctr_cambios_pedidos;
    private List<LibreGenerado> listado_libres;
    private LibreGenerado dia_seleccionado;
    private BtnMenu btn_buscar;
    
    
    public PanelPedirDia(ImageIcon img_azul_aceptar, ImageIcon img_gris_aceptar, ImageIcon img_azul_buscar, ImageIcon img_gris_buscar, CtrCambiosPedidos ctr_cambios_pedidos){
        this.img_azul_aceptar = img_azul_aceptar;
        this.img_gris_aceptar = img_gris_aceptar;
        this.img_azul_buscar = img_azul_buscar;
        this.img_gris_buscar = img_gris_buscar;
        this.ctr_cambios_pedidos = ctr_cambios_pedidos;
        
        this.tipo_libres = ctr_cambios_pedidos.getListadoTipoDias();
        this.setBackground(color_panel_central);
        BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(b);
        crearPaneles();
        setPnPedido();
        setPnOfrecido();
        setPnBoton();
        setEnabledPn(pn_ofrecido, false);
        setEnabledPn(pn_boton, false);
        ctr_cambios_pedidos.setLblMensaje(" --- ");
        this.updateUI();
    }
    
    
    public LocalDate getFechaPedido() {
        if (datechooser.getDate() != null) {
            return datechooser.getFechaChooser();
        }
        return null;
    }
    
    public LocalDate getFechaOfrecido() {
        LocalDate fecha_ofrecido = null;
        if (txt_ofrecido.getText() != null) {
            try {
                fecha_ofrecido = LocalDate.parse(txt_ofrecido.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                return fecha_ofrecido;
            }
        }
        return fecha_ofrecido;
    }
    
    private void resetPanel(){
        pn_pedido.removeAll();
        pn_ofrecido.removeAll();
        pn_boton.removeAll();
        setEnabledPn(pn_ofrecido, false);
        setEnabledPn(pn_boton, false);
    }
    
    
    private void crearPaneles() {
        pn_pedido = new JPanel();
        pn_ofrecido = new JPanel();
        pn_boton = new JPanel();

        pn_pedido.setBackground(color_panel_central);
        pn_ofrecido.setBackground(color_panel_central);
        pn_boton.setBackground(color_panel_central);

        pn_pedido.setMaximumSize(new Dimension(800, 50));
        pn_ofrecido.setMaximumSize(new Dimension(800, 150));
        pn_boton.setMaximumSize(new Dimension(800, 50));
    }
    
    
    private LayoutManager getLayoutPn() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        fl.setHgap(15);
        fl.setVgap(15);
        return fl;
    }
    
    private void setPnPedido(){
        pn_pedido.setLayout(this.getLayoutPn());
        JLabel lbl_pedido = new LblPanel("Dia pedido: ");
        datechooser = new CalendarChooser();
        datechooser.addPropertyChangeListener(new OyenteChooser());
        
        btn_buscar = new BtnMenu("Buscar");
        btn_buscar.setName("btn_aceptar");
        btn_buscar.addActionListener(new OyenteButtonBuscar());
        btn_buscar.setIcono(img_azul_buscar);
        btn_buscar.setIconoFoco(img_gris_buscar);
        btn_buscar.setColorFoco(color_boton_menu, color_panel_central);
        enabledBtn(btn_buscar, false);
        
        pn_pedido.add(lbl_pedido);
        pn_pedido.add(datechooser);
        pn_pedido.add(btn_buscar);
        
        this.add(pn_pedido);
    }
    
    private void enabledBtn(BtnMenu btn, boolean b){
        btn.setEnabled(b);
        btn.isEnabled(b, color_boton_menu, color_panel_central);
    }
    
    private void setPnOfrecido(){
        pn_ofrecido.setLayout(this.getLayoutPn());
        JLabel lbl_ofrecido = new LblPanel("Dia ofrecido: ");
        txt_ofrecido = new TxtPanel(10);
        txt_ofrecido.setEditable(false);
        JLabel lbl_sep = new LblPanel(" --- ");
        JLabel lbl_tipo = new LblPanel("Tipo");
        cbo_tipo = new ComboBox(tipo_libres);
        cbo_tipo.addItemListener(new OyenteChange());
        
        lista_dias = new Lista();
        lista_dias.addMouseListener(new OyenteSelect());
        setList();
        
        JScrollPane sr;
        sr = new JScrollPane();
        sr.setMaximumSize(new Dimension(200,100));
        sr.setPreferredSize(new Dimension(150,100));
        sr.setViewportView(lista_dias);
        
        pn_ofrecido.add(lbl_ofrecido);
        pn_ofrecido.add(txt_ofrecido);
        pn_ofrecido.add(lbl_sep);
        pn_ofrecido.add(lbl_tipo);
        pn_ofrecido.add(cbo_tipo);
        pn_ofrecido.add(sr);
        
        this.add(pn_ofrecido);
    }
    
    private void setPnBoton(){
        pn_boton.setLayout(this.getLayoutPn());
        BtnMenu btn_guardar = new BtnMenu("Guardar");
        btn_guardar.addActionListener(new OyenteButtonGuardar());
        btn_guardar.setIcono(img_azul_aceptar);
        btn_guardar.setIconoFoco(img_gris_aceptar);
        btn_guardar.setColorFoco(color_boton_menu, color_panel_central);
        pn_boton.add(btn_guardar);
        this.add(pn_boton);
    }
    
    /**
     * Recoge la lista de dias disponibles por el tipo seleccionado en cbo
     * De cada dia escoge su fecha y la muestra en la lista
     * Crea el modelo lista con las fechas de los dias
     */
    private void setList() {
        String tipo = (String) this.cbo_tipo.getSelectedItem();
        listado_libres = ctr_cambios_pedidos.getListadoLibres(tipo);
        int size = listado_libres.size();
        String[] lista = new String[size];
        int contador = 0;

        for (LibreGenerado aux : listado_libres) {
            String fecha_generado = aux.getFechaGenerado().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
            lista[contador++] = fecha_generado;
        }

        ModeloLista md = new ModeloLista();
        md.setArray(lista);
        lista_dias.setModel(md);
    }
    
    
    
    /**
     * true = desbloquea   false = bloquea
     * @param pn
     * @param bloquear 
     */
    public void setEnabledPn(JPanel pn, boolean bloquear) {
        if (pn != null) {
            Component[] c = pn.getComponents();
            for (Component aux : c) {
                aux.setEnabled(bloquear);
                if (aux instanceof BtnMenu) {
                    ((BtnMenu) aux).isEnabled(bloquear, color_boton_menu, color_panel_central);
                }
            }
        }
    }
    
   
    private void setDiaPedido(){
        LocalDate fecha = datechooser.getFechaChooser();
        boolean correcto = ctr_cambios_pedidos.comprobarDiaPedido(fecha);
        if(correcto){
            //desbloquear panel y bloquear datechooser 
            setEnabledPn(pn_pedido, false);
            setEnabledPn(pn_ofrecido, true);
            ctr_cambios_pedidos.setLblMensaje(" --- ");
        }
    }
    
    
    /**
     * Comprueba el formato de la fecha
     * @param fecha
     * @return 
     */
    private boolean getPatternFecha(String fecha) {
        boolean c = false;
        if (fecha != null) {
            String pattern = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
            c = Pattern.matches(pattern, fecha);
        }
        return c;
    }
    
    private boolean getFormatoPartesFecha(String fecha) {
        boolean c = true;
        if (fecha != null) {
            String[] part = fecha.split("-");
            int dia = Integer.parseInt(part[0]);
            int mes = Integer.parseInt(part[1]);
            int year = Integer.parseInt(part[2]);
            if (dia <= 0 || dia > 31) {
                return false;
            }
            if (mes <= 0 || mes > 12) {
                return false;
            }
            return !(year <= 1900 || year > 2500);
        }
        return c;
    }
    
    
    
    
    
    private class OyenteChooser implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (datechooser.getFechaChooser() != null) {
                enabledBtn(btn_buscar, true);
            }
        }

    }
    
    
        
    private class OyenteChange implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
                txt_ofrecido.setText("");
                setList();
            }

        }
    }
    
    
    private class OyenteSelect extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            String s = (String) lista_dias.getSelectedValue();
            int v = lista_dias.getSelectedIndex();
            if(s != null){
                txt_ofrecido.setText(s);
                dia_seleccionado = listado_libres.get(v);
                setEnabledPn(pn_boton, true);
            }
        }
        
    }
    
    
    private class OyenteButtonBuscar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setDiaPedido();
        }
        
    }
    
    private class OyenteButtonGuardar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalDate fecha_peticion = LocalDate.now();
            int respuesta = JOptionPane.showConfirmDialog(null, "Quiere cambiar la fecha de la peticion?", "Obtener fecha...", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (respuesta == 0) {

                boolean c = false;
                String f = "";
                while (!c && f != null) {
                    f = JOptionPane.showInputDialog("Escriba la fecha en formato: dd-mm-yyyy");

                    if (getPatternFecha(f) && getFormatoPartesFecha(f)) {
                        fecha_peticion = LocalDate.parse(f, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        c = true;
                    }
                }
            }
            LocalDate fecha_pedido = datechooser.getFechaChooser();
            //se añade la peticion a la tabla de dias pedidos
            boolean correcto = ctr_cambios_pedidos.setPedirDia(fecha_peticion, fecha_pedido, dia_seleccionado);
            if (correcto) {
                ctr_cambios_pedidos.setLblMensaje("--Dia pedido guardado en tabla dia_pedido...");
                resetPanel();
            }
        }

        
        
    }
    
   
    
    
}
