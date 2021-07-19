
package com.wanyos.vista;

import com.wanyos.componentes.ComponentResizer;
import com.wanyos.componentes.Configuraciones;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.controlador.CtrCalendario;
import com.wanyos.controlador.CtrCambiosPedidos;
import com.wanyos.controlador.CtrLibres;
import com.wanyos.controlador.CtrNombramiento;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author wanyos
 */
public class InitApp implements Configuraciones {
    
    private JFrame frame;
    private JPanel pn_ctr;
    private ComponentResizer cr;
    private JLabel lbl_titulo, lbl_mensaje;
    private int pX = 0, pY = 0;
    private boolean max;
    private JProgressBar barra_ps;
    private JPanel pn_left;
    private ImageIcon img_azul_min = new ImageIcon(getClass().getResource("/img/min_azul32.png"));
    private ImageIcon img_gris_min = new ImageIcon(getClass().getResource("/img/min_gris32.png"));
    
    private ImageIcon img_azul_max = new ImageIcon(getClass().getResource("/img/max_azul32.png"));
    private ImageIcon img_gris_max = new ImageIcon(getClass().getResource("/img/max_azul32.png"));
    
    private ImageIcon img_azul_close = new ImageIcon(getClass().getResource("/img/close_azul32.png"));
    private ImageIcon img_gris_close = new ImageIcon(getClass().getResource("/img/close_azul32.png"));
    
    
    public InitApp(){
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(300,300));
        frame.setPreferredSize(new Dimension(1150,900));
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cr = new ComponentResizer();
        cr.registerComponent(frame);
        
        frame.getContentPane().add(this.getPnSup(), BorderLayout.NORTH);
        frame.getContentPane().add(this.getPnLeft(), BorderLayout.WEST);
        frame.getContentPane().add(this.getPnCtr(), BorderLayout.CENTER);
        frame.getContentPane().add(this.getPnInf(), BorderLayout.SOUTH);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void setPanel(JPanel p){
        this.pn_ctr.removeAll();
        this.pn_ctr.add(p);
        this.pn_ctr.updateUI();
    }
    
    private JPanel getPnSup(){
        JPanel pn_sup = new JPanel();
        BoxLayout bx = new BoxLayout(pn_sup, BoxLayout.X_AXIS);
        pn_sup.setLayout(bx);
        pn_sup.setMaximumSize(new Dimension(100100,60));
        pn_sup.setPreferredSize(new Dimension(100100,60));
        pn_sup.add(this.getPnSupLeft());
        pn_sup.add(this.getPnSupRight());
        pn_sup.updateUI();
        return pn_sup;
    }
    
    private JPanel getPnSupLeft(){
        JPanel pn_sup_left = new JPanel();
        pn_sup_left.setMaximumSize(new Dimension(100100,60));
        pn_sup_left.setPreferredSize(new Dimension(100100,60));
        pn_sup_left.setBackground(color_panel_lateral);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(30);
        fl.setVgap(15);
        pn_sup_left.setLayout(fl);
        lbl_titulo = new JLabel(" Ventana principal...");
        lbl_titulo.setForeground(color_letra_blanco);
        lbl_titulo.setFont(fuente_letra_lbl);
        pn_sup_left.add(lbl_titulo);
        pn_sup_left.updateUI();
        return pn_sup_left;
    }
    
    private JPanel getPnSupRight(){
        JPanel pn_sup_right = new JPanel();
        pn_sup_right.setMaximumSize(new Dimension(100100,60));
        pn_sup_right.setPreferredSize(new Dimension(100100,60));
        pn_sup_right.setBackground(color_panel_lateral);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.RIGHT);
        fl.setHgap(15);
        fl.setVgap(15);
        pn_sup_right.setLayout(fl);
        
        BtnMenu btn_min = this.getButtonSup("btn_min", img_azul_min, img_gris_min);
        BtnMenu btn_max = this.getButtonSup("btn_max", img_azul_max, img_gris_max);
        BtnMenu btn_close = this.getButtonSup("btn_close", img_azul_close, img_gris_close);
        
        btn_min.addActionListener(new OyenteButton());
        btn_max.addActionListener(new OyenteButton());
        btn_close.addActionListener(new OyenteButton());
        
        pn_sup_right.add(btn_min);
        pn_sup_right.add(btn_max);
        pn_sup_right.add(btn_close);
        pn_sup_right.updateUI();
        return pn_sup_right;
    }
    
    /**
     * Crea los botones minimizar, maximizar y cerrar
     * @param img_azul
     * @param img_gris
     * @return 
     */
    private BtnMenu getButtonSup(String name, ImageIcon img_azul, ImageIcon img_gris){
        BtnMenu btn = new BtnMenu("");
        btn.setName(name);
        btn.setIcono(img_azul);
        btn.setIconoFoco(img_gris);
        btn.setMaximumSize(new Dimension(32,32));
        btn.setMinimumSize(new Dimension(32,32));
        btn.setPreferredSize(new Dimension(32,32));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        return btn;
    }
    
    private JPanel getPnLeft(){
        pn_left = new JPanel();
        pn_left.setBackground(color_panel_lateral);
        pn_left.addMouseListener(new EventoRaton());
        pn_left.addMouseMotionListener(new EventoMotionRaton());
        BoxLayout bx = new BoxLayout(pn_left, BoxLayout.Y_AXIS);
        pn_left.setLayout(bx);
        
        BtnMenu btn_nombramiento = this.getBtn("btn_nombramiento", "Nombramiento");
        BtnMenu btn_libres = this.getBtn("btn_libres", "Libres");
        BtnMenu btn_calendario = this.getBtn("btn_calendario", "Calendario");
        BtnMenu btn_cambios_pedidos = this.getBtn("btn_cambios_pedidos", "Cambio - Pedidos");
        BtnMenu btn_configuracion = this.getBtn("btn_configuracion", "Configuración");
        
        btn_nombramiento.addActionListener(new OyenteButtonMenu());
        btn_libres.addActionListener(new OyenteButtonMenu());
        btn_calendario.addActionListener(new OyenteButtonMenu());
        btn_cambios_pedidos.addActionListener(new OyenteButtonMenu());
        btn_configuracion.addActionListener(new OyenteButtonMenu());
        
        pn_left.add(new JLabel("   "));
        pn_left.add(btn_nombramiento);
        pn_left.add(new JLabel("   "));
        pn_left.add(btn_libres);
        pn_left.add(new JLabel("   "));
        pn_left.add(btn_calendario);
        pn_left.add(new JLabel("   "));
        pn_left.add(btn_cambios_pedidos);
        pn_left.add(new JLabel("   "));
        pn_left.add(btn_configuracion);
        pn_left.updateUI();
        return pn_left;
    }
    
    private JPanel getPnCtr(){
        pn_ctr = new JPanel();
        pn_ctr.setLayout(new BorderLayout());
        pn_ctr.setBackground(color_panel_central);
        pn_ctr.addMouseListener(new EventoRaton());
        pn_ctr.addMouseMotionListener(new EventoMotionRaton());
        
        return pn_ctr;
    }
    
     private JPanel getPnInf(){
        JPanel pn_inf = new JPanel();
        pn_inf.setMaximumSize(new Dimension(100,40));
        pn_inf.setPreferredSize(new Dimension(100,40));
        pn_inf.setBackground(color_panel_lateral);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(30);
        fl.setVgap(10);
        pn_inf.setLayout(fl);
        lbl_mensaje = new JLabel("Mensajes y avisos...");
        lbl_mensaje.setForeground(color_letra_blanco);
        lbl_mensaje.setFont(fuente_letra_lbl);
        
        barra_ps = new JProgressBar();
        barra_ps.setMinimum(0);
        barra_ps.setMaximum(100);
        barra_ps.setVisible(false);
        barra_ps.setStringPainted(true);
        
        pn_inf.add(lbl_mensaje);
        pn_inf.add(barra_ps);
        pn_inf.updateUI();
        return pn_inf;
    }
     
     private BtnMenu getBtn(String nombre, String texto){
         BtnMenu btn = new BtnMenu(texto);
         btn.setMaximumSize(new Dimension(140,40));
         btn.setMinimumSize(new Dimension(140,40));
         btn.setPreferredSize(new Dimension(140,40));
         btn.setName(nombre);
         btn.setBackground(color_boton_menu);
         btn.setForeground(color_letra_blanco);
         btn.setFont(fuente_btn_menu);
         return btn;
     }
     
     
    private class EventoRaton extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            // Get x,y and store them
            pX = me.getX();
            pY = me.getY();
        }
    }
    
    private class EventoMotionRaton extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me) {
            frame.setLocation(frame.getLocation().x + me.getX() - pX, frame.getLocation().y + me.getY() - pY);
        }

    }
    
    
    private class OyenteButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BtnMenu btn = (BtnMenu) e.getSource();
            
            if (btn.getName().equalsIgnoreCase("btn_min")){
                 frame.setExtendedState(ICONIFIED);
           
            } else if (btn.getName().equalsIgnoreCase("btn_max")) {
                if (!max) {
                    frame.setExtendedState(MAXIMIZED_BOTH);
                    max = true;
                } else {
                    frame.setExtendedState(NORMAL);
                    max = false;
                }
                
            } else if(btn.getName().equalsIgnoreCase("btn_close")){
                 System.exit(0);
            }
        }
    }
    
    
    private class OyenteButtonMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BtnMenu btn = (BtnMenu) e.getSource();
            
            if (btn.getName().equalsIgnoreCase("btn_nombramiento")) {
                lbl_titulo.setText("Nombramiento...");
                lbl_mensaje.setText(" --- ");
                CtrNombramiento ctr_nombramiento = new CtrNombramiento(lbl_mensaje, barra_ps, pn_left);
                try {
                   setPanel(ctr_nombramiento.getPnNombramiento());
                } catch (NullPointerException ex) {

                }
                
            } else if (btn.getName().equalsIgnoreCase("btn_libres")) {
                lbl_titulo.setText("Libres...");
                lbl_mensaje.setText(" --- ");
                CtrLibres ctr_libres = new CtrLibres(lbl_mensaje, barra_ps);
                try {
                   setPanel(ctr_libres.getPnLibres());
                } catch (NullPointerException w) {

                }
                
            } else if (btn.getName().equalsIgnoreCase("btn_calendario")) {
                lbl_titulo.setText("Calendario...");
                lbl_mensaje.setText(" --- ");
                CtrCalendario ctr_calendario = new CtrCalendario(lbl_mensaje);
                try {
                    setPanel(ctr_calendario.getPnCalendario());
                } catch (NullPointerException ex) {

                }
                
            } else if (btn.getName().equalsIgnoreCase("btn_cambios_pedidos")) {
                lbl_titulo.setText("Cambios - Pedidos...");
                lbl_mensaje.setText(" --- ");
                CtrCambiosPedidos ctr_cambios_pedidos = new CtrCambiosPedidos(lbl_mensaje);
                setPanel(ctr_cambios_pedidos.getPnCambiosPedidos());
                lbl_mensaje.setText(" --- ");
                
            } else if (btn.getName().equalsIgnoreCase("btn_configuracion")) {
                lbl_titulo.setText("Configuración...");
                lbl_mensaje.setText(" --- ");
                PnAbstract pn_configuracion = new PnConfiguracion(lbl_mensaje);
                setPanel(pn_configuracion);
            }

        }
        
        
        
    }
     
     
    
    
     public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new InitApp();
            }
        });
    }
    
}
