package com.wanyos.vista;

import com.wanyos.componentes.Configuraciones;
import com.wanyos.componentes.comunes.BtnMenu;
import com.wanyos.componentes.comunes.CalendarChooser;
import com.wanyos.componentes.comunes.LblPanel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * @author wanyos
 */
public class DialogOpcionesLeerCorreo extends JDialog implements Configuraciones {

    private JPanel pn, pn_icono;
    private JRadioButton rd_todos, rd_ultima_fecha;
    private CalendarChooser calendar;
    private LocalDate fecha;
    private boolean todos;
    private int pX = 0, pY = 0;
    private Border b;
    private String close = "";

    
    public DialogOpcionesLeerCorreo() {
        this.setModal(true);
        this.setSize(new Dimension(400,300));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        b = new BevelBorder(BevelBorder.RAISED);
        Container cp = this.getContentPane();
        BoxLayout bl = new BoxLayout(cp, BoxLayout.Y_AXIS);
        cp.setLayout(bl);
        setPnIcono();
        setPn();
        close = "aceptar";
        todos = true;
        this.add(pn_icono);
        this.add(pn);
        this.setVisible(true);
    }
    
    
    
    public void setEvento(String e){
        this.close = e;
    }
    
    public String getEvento(){
        return this.close;
    }
    
    
    private void setPnIcono(){
        pn_icono = new JPanel();
        pn_icono.setBorder(b);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        pn_icono.setLayout(fl);
        pn_icono.setPreferredSize(new Dimension(300,35));
        pn_icono.setBackground(COLOR_PANEL_CENTRAL);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/eye.png"));
        JLabel lbl = new JLabel("");
        lbl.setIcon(icono);
        pn_icono.add(lbl);
        pn_icono.updateUI();
    }

    private void setPn() {
        pn = new JPanel();
        pn.setBorder(b);
        pn.addMouseListener(new EventoRaton());
        pn.addMouseMotionListener(new EventoMotionRaton());
        ButtonGroup g1 = new ButtonGroup();

        pn.setPreferredSize(new Dimension(300,200));
        pn.setBackground(COLOR_PANEL_CENTRAL);

        pn.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        LblPanel lbl_leer = new LblPanel("Leer: ");
        pn.add(lbl_leer, c);

        rd_todos = new JRadioButton("Todos", true);
        rd_ultima_fecha = new JRadioButton("Ultima fecha");
        
        rd_todos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setDate(null);
            }
        });

        rd_ultima_fecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setDate(null);
            }

        });
        
        rd_todos.setBackground(COLOR_PANEL_CENTRAL);
        rd_ultima_fecha.setBackground(COLOR_PANEL_CENTRAL);
        
        g1.add(rd_todos);
        g1.add(rd_ultima_fecha);
        
        c.gridx = 1;
        c.gridwidth = 1;
        pn.add(rd_todos,c);
        
        c.gridy = 1;
        c.gridwidth = 1;
        pn.add(rd_ultima_fecha,c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        LblPanel lbl_desde = new LblPanel("Desde fecha: ");
        pn.add(lbl_desde,c);
        
        c.gridx = 1;
        c.gridwidth = 1;
        calendar = new CalendarChooser();
        
        calendar.getCalendarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 g1.clearSelection();
            } 
        });
        
        pn.add(calendar,c);
        
        c.insets = new Insets(30, 5, 5, 5);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        BtnMenu btn_aceptar = new BtnMenu("Aceptar");
        btn_aceptar.addActionListener(new OyenteBoton());
        pn.add(btn_aceptar,c);
        
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        BtnMenu btn_cancelar = new BtnMenu("Cancelar");
        btn_cancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 addWindowListener(new DialogOyente());
            }
            
        });
        pn.add(btn_cancelar,c);
        pn.updateUI();
    }
    
    public LocalDate getFecha(){
        return fecha;
    }
    
    public boolean getLeerTodosCorreos(){
        return todos;
    }
    
        private class OyenteBoton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (calendar.getFechaChooser() != null) {
                fecha = calendar.getFechaChooser();
                todos = false;
            }
            if (rd_ultima_fecha.isSelected()) {
                todos = false;
            }
            dispose();
        }
    }
    
    
     private class EventoRaton extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            // Get x,y and store them
            pX = me.getX();
            pY = me.getY();
        }
    }
    
    private class EventoMotionRaton extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent me) {
            setLocation(getLocation().x + me.getX() - pX, getLocation().y + me.getY() - pY);
        }
    }
    
    private class DialogOyente extends WindowAdapter {
        
//        @Override
//        public void windowActivated(WindowEvent e){
//            setEvento("activar");
//        }
        
        @Override
             public void windowDeactivated(WindowEvent e){
                 setEvento("cancelar");
                 dispose();
             }
    }
    
   
   
    

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new DialogOpcionesLeerCorreo();
//            }
//
//        });
//    }

}
