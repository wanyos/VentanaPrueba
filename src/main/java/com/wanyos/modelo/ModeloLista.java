package com.wanyos.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;

/**
 *
 * @author wanyos
 */
public class ModeloLista extends DefaultListModel {

   

    public ModeloLista() {}
    
    
    public void setArray(String[] datos) {
        if (datos != null) {
            for (String aux : datos) {
                this.addElement(aux);
            }
        }
    }

    public void setLista(List<String> datos) {
        if (datos != null) {
            for (String aux : datos) {
                this.addElement(aux);
            }
        }
    }

    public void setMap(Map<LocalDate, String[]> datos_map) {
        if (!datos_map.isEmpty()) {
            for (Object clave : datos_map.keySet()) {
                String[] valor = (String[]) datos_map.get(clave);
                String fecha_turno = clave.toString();
                this.addElement(fecha_turno);
                String turno_leido = "";
                for (String aux : valor) {
                    if (aux != null) {
                        turno_leido = turno_leido.concat("  " + aux);
                    }
                }
                this.addElement("\n" + turno_leido);
            }
        }
    }

}
