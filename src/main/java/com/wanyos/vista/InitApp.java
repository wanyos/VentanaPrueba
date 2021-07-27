package com.wanyos.vista;

import com.wanyos.componentes.ComponentResizer;
import com.wanyos.componentes.Configuraciones;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.controlador.CtrCalendario;
import com.wanyos.controlador.CtrCambiosPedidos;
import com.wanyos.controlador.CtrLibres;
import com.wanyos.controlador.CtrNombramiento;
import com.wanyos.modelo.dao.MySqlManagerDao;

import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author wanyos
 */
public class InitApp implements Configuraciones {

    private final JFrame frame;
    private JPanel pn_ctr;
    private static JPanel pn_left;
    private ComponentResizer cr;
    private JLabel lbl_titulo;
    private static JLabel lbl_mensaje;
    private int pX = 0, pY = 0;
    private boolean max;
    private static JProgressBar barra_ps;
    private MySqlManagerDao manager_dao;
    private ImageIcon img_azul_min, img_gris_min, img_azul_max, img_gris_max, img_azul_close, img_gris_close; 
    

    
    public InitApp() {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setPreferredSize(new Dimension(1150, 900));
        frame.setUndecorated(true);
        //se usa el objeto que hace que el frame se pueda agrandar sin el marco
        cr = new ComponentResizer();
        cr.registerComponent(frame);
        crearIconosBotones();
        
        frame.getContentPane().add(this.getPnSup(), BorderLayout.NORTH);
        frame.getContentPane().add(this.getPnLeft(), BorderLayout.WEST);
        frame.getContentPane().add(this.getPnCtr(), BorderLayout.CENTER);
        frame.getContentPane().add(this.getPnInf(), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPanel(JPanel p) {
        this.pn_ctr.removeAll();
        this.pn_ctr.add(p);
        this.pn_ctr.updateUI();
    }

    /**
     * Bloquea/Desbloquea los botones del panel left
     *
     * @param b
     */
    public static void setEnabledPn(boolean b) {
        Component[] c = pn_left.getComponents();
        for (Component aux : c) {
            if (aux instanceof JButton) {
                aux.setEnabled(b);
            }
        }
    }

    public static JProgressBar getBarraPs() {
        return barra_ps;
    }

    public static void setVisibleBarra(boolean b) {
        barra_ps.setVisible(b);
    }

    public static void setMensajeLbl(String m) {
        lbl_mensaje.setText(" --- " + m);
    }

    private void crearIconosBotones() {
        img_azul_min = new ImageIcon(getClass().getResource(R_IMG_MIN_AZUL));
        img_gris_min = new ImageIcon(getClass().getResource(R_IMG_MIN_GRIS));

        img_azul_max = new ImageIcon(getClass().getResource(R_IMG_MAX_AZUL));
        img_gris_max = new ImageIcon(getClass().getResource(R_IMG_MAX_GRIS));

        img_azul_close = new ImageIcon(getClass().getResource(R_IMG_CLOSE_AZUL));
        img_gris_close = new ImageIcon(getClass().getResource(R_IMG_CLOSE_GRIS));
    }

    private JPanel getPnSup() {
        JPanel pn_sup = new JPanel();
        BoxLayout bx = new BoxLayout(pn_sup, BoxLayout.X_AXIS);
        pn_sup.setLayout(bx);
        pn_sup.setMaximumSize(new Dimension(100100, 60));
        pn_sup.setPreferredSize(new Dimension(100100, 60));
        pn_sup.add(this.getPnSupLeft());
        pn_sup.add(this.getPnSupRight());
        pn_sup.updateUI();
        return pn_sup;
    }

    private JPanel getPnSupLeft() {
        JPanel pn_sup_left = new JPanel();
        pn_sup_left.setMaximumSize(new Dimension(100100, 60));
        pn_sup_left.setPreferredSize(new Dimension(100100, 60));
        pn_sup_left.setBackground(COLOR_PANEL_LATERAL);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(30);
        fl.setVgap(15);
        pn_sup_left.setLayout(fl);
        lbl_titulo = new JLabel(" Ventana principal...");
        lbl_titulo.setForeground(COLOR_LETRA_BLANCO);
        lbl_titulo.setFont(FUENTE_LETRA_LBL);
        pn_sup_left.add(lbl_titulo);
        pn_sup_left.updateUI();
        return pn_sup_left;
    }

    private JPanel getPnSupRight() {
        JPanel pn_sup_right = new JPanel();
        pn_sup_right.setMaximumSize(new Dimension(100100, 60));
        pn_sup_right.setPreferredSize(new Dimension(100100, 60));
        pn_sup_right.setBackground(COLOR_PANEL_LATERAL);
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
     *
     * @param img_azul
     * @param img_gris
     * @return
     */
    private BtnMenu getButtonSup(String name, ImageIcon img_azul, ImageIcon img_gris) {
        BtnMenu btn = new BtnMenu("");
        btn.setName(name);
        btn.setIcono(img_azul);
        btn.setIconoFoco(img_gris);
        btn.setMaximumSize(new Dimension(32, 32));
        btn.setMinimumSize(new Dimension(32, 32));
        btn.setPreferredSize(new Dimension(32, 32));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private JPanel getPnLeft() {
        pn_left = new JPanel();
        pn_left.setBackground(COLOR_PANEL_LATERAL);
        pn_left.addMouseListener(new EventoRaton());
        pn_left.addMouseMotionListener(new EventoMotionRaton());
        BoxLayout bx = new BoxLayout(pn_left, BoxLayout.Y_AXIS);
        pn_left.setLayout(bx);

        BtnMenu btn_nombramiento = this.getBtn("btn_nombramiento", "Nombramiento");
        BtnMenu btn_libres = this.getBtn("btn_libres", "Libres");
        BtnMenu btn_calendario = this.getBtn("btn_calendario", "Calendario");
        BtnMenu btn_cambios_pedidos = this.getBtn("btn_cambios_pedidos", "Cambio - Pedidos");
        BtnMenu btn_configuracion = this.getBtn("btn_configuracion", "Configuración");

        btn_nombramiento.addActionListener(new OyenteBtnNombramiento());
        btn_libres.addActionListener(new OyenteBtnLibres());
        btn_calendario.addActionListener(new OyenteBtnCalendario());
        btn_cambios_pedidos.addActionListener(new OyenteBtnCambiosPedidos());
        btn_configuracion.addActionListener(new OyenteBtnConfiguracion());

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

    private JPanel getPnCtr() {
        pn_ctr = new JPanel();
        pn_ctr.setLayout(new BorderLayout());
        pn_ctr.setBackground(COLOR_PANEL_CENTRAL);
        pn_ctr.addMouseListener(new EventoRaton());
        pn_ctr.addMouseMotionListener(new EventoMotionRaton());

        return pn_ctr;
    }

    private JPanel getPnInf() {
        JPanel pn_inf = new JPanel();
        pn_inf.setMaximumSize(new Dimension(100, 40));
        pn_inf.setPreferredSize(new Dimension(100, 40));
        pn_inf.setBackground(COLOR_PANEL_LATERAL);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(30);
        fl.setVgap(10);
        pn_inf.setLayout(fl);
        lbl_mensaje = new JLabel("Mensajes y avisos...");
        lbl_mensaje.setForeground(COLOR_LETRA_BLANCO);
        lbl_mensaje.setFont(FUENTE_LETRA_LBL);

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

    private BtnMenu getBtn(String nombre, String texto) {
        BtnMenu btn = new BtnMenu(texto);
        btn.setMaximumSize(new Dimension(140, 40));
        btn.setMinimumSize(new Dimension(140, 40));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setName(nombre);
        btn.setBackground(COLOR_BOTON_MENU);
        btn.setForeground(COLOR_LETRA_BLANCO);
        btn.setFont(FUENTE_BTN_MENU);
        return btn;
    }

    //Metodos para mover el componenete por la pantalla con el ratón
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

    /**
     * Clase que usan los botones, maximizar, minimizar y cerrar
     */
    private class OyenteButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BtnMenu btn = (BtnMenu) e.getSource();

            if (btn.getName().equalsIgnoreCase("btn_min")) {
                frame.setExtendedState(ICONIFIED);

            } else if (btn.getName().equalsIgnoreCase("btn_max")) {
                if (!max) {
                    frame.setExtendedState(MAXIMIZED_BOTH);
                    max = true;
                } else {
                    frame.setExtendedState(NORMAL);
                    max = false;
                }

            } else if (btn.getName().equalsIgnoreCase("btn_close")) {
                System.exit(0);
            }
        }
    }
    
    
    private class OyenteBtnNombramiento implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_titulo.setText("Nombramiento...");
            if (getManagerDao() != null) {
                CtrNombramiento ctr_nombramiento = new CtrNombramiento(getManagerDao());
                try {
                    setPanel(ctr_nombramiento.getPnNombramiento());
                } catch (NullPointerException ex) {
                    lbl_mensaje.setText(" --- Error crear panel CtrNombramiento... " + ex.getMessage());
                }
            }
        }

    }
    
    private class OyenteBtnLibres implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_titulo.setText("Libres...");
            if (getManagerDao() != null) {
                CtrLibres ctr_libres = new CtrLibres(getManagerDao());
                try {
                    setPanel(ctr_libres.getPnLibres());
                } catch (NullPointerException ex) {
                    lbl_mensaje.setText(" --- Error crear panel CtrLibres... " + ex.getMessage());
                }
            }
        }
    }
    
    private class OyenteBtnCalendario implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_titulo.setText("Calendario...");
            if (getManagerDao() != null) {
                CtrCalendario ctr_calendario = new CtrCalendario(getManagerDao());
                try {
                    setPanel(ctr_calendario.getPnCalendario());
                } catch (NullPointerException ex) {
                    lbl_mensaje.setText(" --- Error crear panel CtrCalendario... " + ex.getMessage());
                }
            }
        }
    }
    
    private class OyenteBtnCambiosPedidos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_titulo.setText("Cambios - Pedidos...");
            if (getManagerDao() != null) {
                CtrCambiosPedidos ctr_cambios_pedidos = new CtrCambiosPedidos(getManagerDao());
                try {
                    setPanel(ctr_cambios_pedidos.getPnCambiosPedidos());
                } catch (NullPointerException ex) {
                    lbl_mensaje.setText(" --- Error crear panel CtrCambiosPedidos... " + ex.getMessage());
                }
            }
        }
    }
    
    private class OyenteBtnConfiguracion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_titulo.setText("Configuración...");
            if (getManagerDao() != null) {
                PnAbstract pn_configuracion = new PnConfiguracion();
                try {
                    setPanel(pn_configuracion);
                } catch (NullPointerException ex) {
                    lbl_mensaje.setText(" --- Error crear panel CtrConfiguracion... " + ex.getMessage());
                }
            }
        }
    }
    
    
    private MySqlManagerDao getManagerDao(){
        lbl_mensaje.setText(" --- ");
        manager_dao = MySqlManagerDao.getManagerDao();
        if(manager_dao == null){
            lbl_mensaje.setText(" --- No existe ManagerDao o conexión BD...");
        }
        return manager_dao;
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
