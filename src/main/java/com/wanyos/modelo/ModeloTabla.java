
package com.wanyos.modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.border.*;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * @author wanyos
 * @param <T>
 */
public class ModeloTabla<T> extends AbstractTableModel {

    
    private int filas, columnas;
    private String [][] datos;
    private String [] name_colum;

    
    
    public ModeloTabla(int filas, int columnas, String [] name_colum){
        this.filas = filas;
        this.columnas = columnas;
        this.name_colum = name_colum;
    }
    
    public ModeloTabla(int filas, int columnas, String [] name_colum, String [][] datos){
        this.filas = filas;
        this.columnas = columnas;
        this.name_colum = name_colum;
        this.datos = datos;
    }
    
    
    public ModeloTabla(String [] name_colum, List<T> obj) {
        this.filas = obj.size();
        this.columnas = name_colum.length;
        this.name_colum = name_colum;
            convertObject(obj);
    }
    
    public ModeloTabla (String [] columnas, String [][] datos){
        this.filas = datos.length;
        this.columnas = columnas.length;
        this.name_colum = columnas;
        this.datos = datos;
    }
    
    
    private void convertObject(List<T> obj) {
        List<Object> lista_aux = new ArrayList<>();
        //eliminar elementos vacios o nulos
        for (Object aux : obj) {
            if (aux != null) {
                lista_aux.add(aux);
            }
        }
        filas = lista_aux.size();
        try {
            if (filas > 0) {
                Object aux = lista_aux.get(0);
                String name = aux.getClass().getName();
                Class clase = Class.forName(name);
                Field[] cam = clase.getDeclaredFields();
                //filas = total objetos, columnas = campos de cada objeto
                datos = new String[lista_aux.size()][cam.length];

                for (int f = 0; f < lista_aux.size(); f++) {
                    int c = 0;
                    aux = lista_aux.get(f);

                    for (Field field : aux.getClass().getDeclaredFields()) {
                        field.setAccessible(true); // You might want to set modifier to public first.
                        Object value = field.get(aux);
                        if (value != null) {
                            datos[f][c++] = value.toString();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(ModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public int getRowCount() {
        return this.filas;
    }

    @Override
    public int getColumnCount() {
        return this.columnas;
    }

    @Override
    public String getColumnName(int c){
        return name_colum[c++];
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        String d;
        d = datos[row][column];
        if (d == null) {
            d = "--";
        }
        return " " + d;
    }
    
    
    
    
    /**
     * Centra datos en las columnas
     * @param t 
     */
    public void centrarColumnasTabla(JTable t) {
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int a = 0; a < t.getColumnCount(); a++) {
            t.getColumnModel().getColumn(a).setCellRenderer(cr);
        }
    }
    
    /**
     * Poder seleccionar filas de la tabla
     * @param t
     * @param b 
     */
    public void setEnabledSelection(JTable t, boolean b){
        t.setRowSelectionAllowed(b);
    }
    
    
    /**
     * Seleccionar varias filas o solo una, depende el parámetro b
     * @param t
     * @param b true=multiple false=single
     */
    public void setSelectionMultiplesFiles(JTable t, boolean b) {
        if (b) {
            t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            t.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        }
    }

   
    /**
     * Mostrar/Ocultar líneas de celdas en tabla
     * @param b
     * @param t 
     */
    public void setLinesFields(boolean b, JTable t){
        t.setShowGrid(b);
    }
    
    /**
     * Quitar bordes en la cabecera de la tabla
     * @param b
     * @param t 
     */
    public void setBorderColum(boolean b, JTable t) {
        if (!b) {
            TableCellRenderer renderer = new JComponentTableCellRenderer();
            TableColumnModel c_m = t.getColumnModel();

            for (int a = 0; a < c_m.getColumnCount(); a++) {
                c_m.getColumn(a).setHeaderRenderer(renderer);
                c_m.getColumn(a).setHeaderValue(new JLabel(name_colum[a], JLabel.CENTER));
            }
        }
    }
    
    public void setColorTxtCabecera(Color c, JTable t){
        JTableHeader header = t.getTableHeader();
        header.setForeground(c);
    }
    
     public void setColorCabecera(Color c, JTable t){
        JTableHeader header = t.getTableHeader();
         header.setBackground(c);
    }
    
     public void setFontCabecera(Font f, JTable t){
         JTableHeader header = t.getTableHeader();
         header.setFont(f);
     }
    
    public void setColorTxtColumnas(Color c, JTable t){
        t.setForeground(c);
    }
    
    public void setFontTxtColumnas(Font f, JTable t){
        t.setFont(f);
    }
    
   
    public void setColorTable(Color c, JScrollPane s, JTable t){
        t.setBackground(c);
        s.getViewport().setBackground(c);
    }
    
    public void setBorderTable(Border b, JTable t) {
      t.setBorder(b);
    }
    
    public void setColorSelection(Color c, JTable t){
        t.setSelectionBackground(c);
    }
    
     public void setHorizontalLines(boolean f, JTable t){
        t.setShowHorizontalLines(f);
    }
    
    public void setVerticalLines(boolean f, JTable t){
        t.setShowVerticalLines(f);
    }
    
    /**
     * Asigna a cada columna el valor de la misma posición del array
     * @param width
     * @param t 
     */
    public void setAnchoColumn(int [] width, JTable t) {
        t.setAutoResizeMode(0);
        TableColumnModel columnModel = t.getColumnModel();
        for (int a = 0; a < width.length; a++) {
            columnModel.getColumn(a).setPreferredWidth(width[a]);
        }
    }
    
    
    public void setAltoFila(int n_fila, int al, JTable t){
        t.setRowHeight(n_fila, al);
    }
    
    /**
     * Para cambiar las medidas de la tabla se necesita cambiar el tamaño
     * del JScroolPane al cual esta vinculada
     * @param s
     * @param ancho
     * @param alto 
     */
    public void setSizeTable(JScrollPane s, int ancho, int alto){
        s.setPreferredSize(new Dimension(ancho, alto));
    }
    
    /**
     * Asignar al componente c el Oyente de mouse. Deselecciona la fila al hacer click en dicho componente
     * Por ejemplo en el JPanel donde se encuentra la tabla
     * @param c
     * @param t 
     */
    public void deseleccionarFilaTabla(JComponent c, JTable t){
        c.addMouseListener(new OyenteMouse(t));
    }
    
    
    private class JComponentTableCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Border b = new BevelBorder(BevelBorder.RAISED);
            Font f = new Font("Serif", Font.BOLD, 14);
            if (value instanceof JLabel) {
                JLabel lbl = (JLabel) value;
                //lbl.setBorder(b);
                lbl.setFont(f);
            }
//            } else if(value instanceof JTextField){
//                JTextField txt = (JTextField) value;
//                txt.setBorder(b);
//                txt.setFont(f);
//            }

            return (JComponent) value;
        }
    }
    
    
    private class OyenteMouse extends MouseAdapter {

        private JTable t;
        
        public OyenteMouse(JTable t){
            this.t = t;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            t.clearSelection();
        }

    }
    
    
    
    //ejemplos de uso
    /*
         JTable tabla = new JTable();
         ModeloTabla model = new ModeloTabla(0, nombre_columnas.length, nombre_columnas);
         tabla.setModel(model);
         JScrollPane sr = new JScrollPane(tabla);
         
         model.centrarColumnasTabla(tabla);
         model.setLinesFields(false, tabla);
         model.setBorderColum(false, tabla);
         
         model.setColorTable(super.color_panel_central, sr, tabla);
         
         model.setSelectionMultiplesFiles(tabla, true);
         model.setColorSelection(new Color(23,180,20), tabla);
         model.deseleccionarFilaTabla(this, tabla);

         model.setColorCabecera(super.color_panel_central, tabla);        
         model.setColorTxtCabecera(color_letra_blanco, tabla);
         model.setFontCabecera(fuente_letra, tabla);
         
         model.setColorTxtColumnas(color_letra_blanco, tabla);
         model.setFontTxtColumnas(fuente_letra, tabla);
    */
}
