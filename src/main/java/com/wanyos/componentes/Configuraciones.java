
package com.wanyos.componentes;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author wanyos
 */
public interface Configuraciones {
    
    final Color COLOR_PANEL_CENTRAL = new Color(51,153,255);
    final Color COLOR_LETRA_BLANCO = new Color(255,255,255);
    final Font FUENTE_LETRA = new Font("Bookman Old Style",2,14);
    final Color COLOR_BOTON_MENU = new Color(51,51,51);
    final Font FUENTE_BTN_MENU = new Font("Dialog", 1, 12);
    final Font FUENTE_LETRA_LBL = new Font("Monospaced", 0, 14);
    final Color COLOR_PANEL_LATERAL = new Color(95,106,116);
    
    //Color calendar
    final Color COLOR_PN_MONTH_CALENDAR = new Color(95,106,116);
    final Color COLOR_TXT_LIBRE = new Color(50,219,16);
    final Color COLOR_TXT_SUBGRUPO = new Color(44,55,42);
    final Color COLOR_TXT_SUB1 = new Color(212,216,82);
    final Color COLOR_TXT_SUB2 = new Color(232,132,37);
    final Color COLOR_TXT_VACACION = new Color(68,88,200);
    final Color COLOR_TXT_PEDIDO = new Color(118,23,154);
    final Color COLOR_TXT_CONCEDIDO = new Color(9,110,61);
    final Color COLOR_TXT_LIBRE_VACACION = new Color(23,154,128);
    final Color COLOR_TXT_FESTIVOS = new Color(232, 51, 19);
    
    //rutas iconos botones
    String R_IMG_AZUL_CAMBIOS = "/img/cambios_azul32.png";
    String R_IMG_GRIS_CAMBIOS = "/img/cambios_gris32.png";
    String R_IMG_AZUL_BUSCAR = "/img/buscar_azul32.png";
    String R_IMG_GRIS_BUSCAR = "/img/buscar_gris32.png";
    String R_IMG_AZUL_ACEPTAR = "/img/accept_azul32.png";
    String R_IMG_GRIS_ACEPTAR = "/img/accept_gris32.png";
    
}
