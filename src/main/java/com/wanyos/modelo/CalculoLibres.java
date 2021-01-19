
package com.wanyos.modelo;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanyos
 */
public class CalculoLibres {
    
       
    //Array con las fechas de inicio de cada grupo cada posición del array corresponde al número del grupo+1
    //la secuencia de libres comienza en xj, vsd, dlm, xj de grupo respectivamente
    private final LocalDate [] fechas_init_2013 = {LocalDate.of(2013,1,2), LocalDate.of(2013,1,4), LocalDate.of(2013,1,5), LocalDate.of(2013,1,6), LocalDate.of(2013,1,9)};
    
    //matriz que guarda todos los libres de un año
    //se le das los valores 13 y 32 donde las filas son los meses y las columnas los dias
    //el valor de un día se coloca en su posición en el array
    private boolean [][] libres_year;
    
   
    //para el calculo del dia subgrupo se toman como referencia los dias del año 2013
    //se guardan en una matriz todas las fechas de los dias de cada subgrupo de ese año
    private final LocalDate [] grupo1_subgrupoA = {LocalDate.of(2013,1,26), LocalDate.of(2013,5,11), LocalDate.of(2013,8,24), LocalDate.of(2013,12,7)};
    private final LocalDate [] grupo1_subgrupoB = {LocalDate.of(2013,3,2), LocalDate.of(2013,6,15), LocalDate.of(2013,9,28)};
    private final LocalDate [] grupo1_subgrupoC = {LocalDate.of(2013,4,6), LocalDate.of(2013,7,20), LocalDate.of(2013,11,2)};
    
    private final LocalDate [] grupo2_subgrupoA = {LocalDate.of(2013,2,23), LocalDate.of(2013,6,8), LocalDate.of(2013,9,21)};
    private final LocalDate [] grupo2_subgrupoB = {LocalDate.of(2013,3,30), LocalDate.of(2013,7,13), LocalDate.of(2013,10,26)};
    private final LocalDate [] grupo2_subgrupoC = {LocalDate.of(2013,1,19), LocalDate.of(2013,5,4), LocalDate.of(2013,8,17), LocalDate.of(2013,11,30)};
    
    private final LocalDate [] grupo3_subgrupoA = {LocalDate.of(2013,2,16), LocalDate.of(2013,6,1), LocalDate.of(2013,9,14), LocalDate.of(2013,12,28)};
    private final LocalDate [] grupo3_subgrupoB = {LocalDate.of(2013,3,23), LocalDate.of(2013,7,6), LocalDate.of(2013,10,19)};
    private final LocalDate [] grupo3_subgrupoC = {LocalDate.of(2013,1,12), LocalDate.of(2013,4,27), LocalDate.of(2013,8,10), LocalDate.of(2013,11,23)};
    
    private final LocalDate [] grupo4_subgrupoA = {LocalDate.of(2013,2,9), LocalDate.of(2013,5,25), LocalDate.of(2013,9,7), LocalDate.of(2013,12,21)};
    private final LocalDate [] grupo4_subgrupoB = {LocalDate.of(2013,3,16), LocalDate.of(2013,6,29), LocalDate.of(2013,10,12)};
    private final LocalDate [] grupo4_subgrupoC = {LocalDate.of(2013,1,5), LocalDate.of(2013,4,20), LocalDate.of(2013,8,3), LocalDate.of(2013,11,16)};
    
    private final LocalDate [] grupo5_subgrupoA = {LocalDate.of(2013,2,2), LocalDate.of(2013,5,18), LocalDate.of(2013,8,31), LocalDate.of(2013,12,14)};
    private final LocalDate [] grupo5_subgrupoB = {LocalDate.of(2013,3,9), LocalDate.of(2013,6,22), LocalDate.of(2013,10,5)};
    private final LocalDate [] grupo5_subgrupoC = {LocalDate.of(2013,4,13), LocalDate.of(2013,7,27), LocalDate.of(2013,11,9)};
    
    
    //se cambian los subgrupos a partir del año 2020
    //se crea un array por cada grupo y cada posición del array corresponde a su letra a,b,c,d...
    //se guarda en cada posición la fecha de inicio de su respectiva letra
    private final LocalDate [] grupo1 = {LocalDate.of(2020,3,4), LocalDate.of(2020,2,3), LocalDate.of(2020,1,9), LocalDate.of(2020,2,13), 
                                         LocalDate.of(2020,3,19), LocalDate.of(2020,2,4), LocalDate.of(2020,3,10), LocalDate.of(2020,1,29)};
   
    private final LocalDate [] grupo2 = {LocalDate.of(2020,2,26), LocalDate.of(2020,1,27), LocalDate.of(2020,1,2), LocalDate.of(2020,2,6), 
                                         LocalDate.of(2020,3,12), LocalDate.of(2020,1,28), LocalDate.of(2020,3,3), LocalDate.of(2020,1,22)};
    
    private final LocalDate [] grupo3 = {LocalDate.of(2020,2,19), LocalDate.of(2020,3,25), LocalDate.of(2020,2,24), LocalDate.of(2020,1,30), 
                                         LocalDate.of(2020,3,5), LocalDate.of(2020,1,21), LocalDate.of(2020,2,25), LocalDate.of(2020,1,15)};
    
    private final LocalDate [] grupo4 = {LocalDate.of(2020,2,12), LocalDate.of(2020,1,13), LocalDate.of(2020,2,17), LocalDate.of(2020,3,23), 
                                         LocalDate.of(2020,2,27), LocalDate.of(2020,1,14), LocalDate.of(2020,2,18), LocalDate.of(2020,3,24)};
    
    private final LocalDate [] grupo5 = {LocalDate.of(2020,2,5), LocalDate.of(2020,3,11), LocalDate.of(2020,2,10), LocalDate.of(2020,3,16), 
                                         LocalDate.of(2020,2,20), LocalDate.of(2020,3,26), LocalDate.of(2020,2,11), LocalDate.of(2020,3,17)};
    
    
    //sábados de subgrupos comunes a dos grupos A-C-E-G y B-D-F-H a partir del 2020
    //las posiciones en el array están en el mismo orden
    private final LocalDate [] sub_comunes_g1 = {LocalDate.of(2020,2,29), LocalDate.of(2020,1,25)};
    private final LocalDate [] sub_comunes_g2 = {LocalDate.of(2020,2,22), LocalDate.of(2020,1,18)};
    private final LocalDate [] sub_comunes_g3 = {LocalDate.of(2020,2,15), LocalDate.of(2020,1,11)};
    private final LocalDate [] sub_comunes_g4 = {LocalDate.of(2020,2,8), LocalDate.of(2020,1,4)};
    private final LocalDate [] sub_comunes_g5 = {LocalDate.of(2020,2,1), LocalDate.of(2020,3,7)};
    
    
    
    
    

//    public boolean[] getLibresMes(int year, int month, int grupo) {  
//        return libres_year[month];
//    }
    
    
//    public boolean [][] getLibresYear(int year, int grupo){
//        return libres_year;
//    }
    
    
    /**
     * Matriz bidimensional con los dias libres
     * @return 
     */
    public boolean [][] getLibresYear(){
        return libres_year;
    }
    
    
    /**
     * Convierte la matriz de libres a una lista de fechas
     * @param year necesita el valor del año por no ser una variable global
     * @return fechas de todos los libres del año
     */
    public List<LocalDate> getListaLibres(int year){
        List<LocalDate> lista_libres = new ArrayList<>();
        
        for(int f = 1; f<13; f++){
            for(int c = 1; c<32; c++){
                if(libres_year[f][c]){
                    LocalDate fecha = LocalDate.of(year, f, c);
                    lista_libres.add(fecha);
                }
            }
        }
        
        return lista_libres;
    }
    
    
    /**
     * Calcula los dias de subgrupo de cualquier grupo de libres y su letra
     * Calcula del año 2013 en adelante hasta el 2020
     * @param grupo_libres
     * @param letra
     * @param year
     * @return lista con fechas de subgrupo siempre antes del 2020
     */
    public ArrayList<LocalDate> lista_dias_subgrupo(int grupo_libres, String letra, int year) {
        ArrayList<LocalDate> lista_dias = new ArrayList<>();

        //Las filas equivalen a los grupo, las columas a la letra, A=0 B=1 C=2
        //En cada letra guarda la lista de los dias de subgrupo del año 2013
        Object [][] matriz_subgrupo = {{}, //equivale a la fila cero, se deja vacia
        {this.grupo1_subgrupoA, this.grupo1_subgrupoB, this.grupo1_subgrupoC},
        {this.grupo2_subgrupoA, this.grupo2_subgrupoB, this.grupo2_subgrupoC},
        {this.grupo3_subgrupoA, this.grupo3_subgrupoB, this.grupo3_subgrupoC},
        {this.grupo4_subgrupoA, this.grupo4_subgrupoB, this.grupo4_subgrupoC},
        {this.grupo5_subgrupoA, this.grupo5_subgrupoB, this.grupo5_subgrupoC}};

        int letra_subgrupo = -1;

        if (letra.equalsIgnoreCase("A")) {
            letra_subgrupo = 0;
        } else if (letra.equalsIgnoreCase("B")) {
            letra_subgrupo = 1;
        } else if (letra.equalsIgnoreCase("C")) {
            letra_subgrupo = 2;
        }

        //el valor de la última fecha que tenga el subgrupo del año 2013
        LocalDate [] lista_subgrupo = (LocalDate[]) matriz_subgrupo[grupo_libres][letra_subgrupo];
        LocalDate fecha_calcular = lista_subgrupo[lista_subgrupo.length - 1];

        if (year > 2013) {
            LocalDate aux = null;
            while (fecha_calcular.getYear() <= year) {
                aux = fecha_calcular.plusWeeks(15);
                if (aux.getYear() == year) {
                    lista_dias.add(aux);
                    fecha_calcular = aux;
                } else {
                    fecha_calcular = aux;
                }
            }
        }
        return lista_dias;
    }
    
    
    //----------------------------------------------------------------------------------------------
    
    
    /**
     * Rellena la matriz de los libres del año. Si no es llamado el primero no se calculan los libres
     * @param year
     * @param grupo 
     */
    public void setListaLibresYear(int year, int grupo){
        libres_year = new boolean[13][32];
        //secuencia de las distancias entre libres el orden: miércoles, viernes, sábado, domingo
        int [] distancias = {8,6,7,8};
        
        LocalDate fecha_init = this.getFechaInitLibres(grupo, year);
        
        while (fecha_init.getYear() <= year) {
            //dependiendo del día de la semana del primer libre se le da un valor a secuencia para colocar la fecha en los siguientes libres
            int d_week = fecha_init.getDayOfWeek().getValue();
            int c_secuencia = 0;
            if (d_week == 5 || d_week == 6 || d_week == 7) {
                c_secuencia = (d_week - 5) + 1;
            }

            if (fecha_init.getYear() == year) {
                libres_year[fecha_init.getMonthValue()][fecha_init.getDayOfMonth()] = true;
            }
            //se aumenta en un día el libre
            fecha_init = fecha_init.plusDays(1);

            if (fecha_init.getYear() == year) {
                libres_year[fecha_init.getMonthValue()][fecha_init.getDayOfMonth()] = true;
            }

            if (d_week == 5 || d_week == 7) {
                fecha_init = fecha_init.plusDays(1);
                if (fecha_init.getYear() == year) {
                    libres_year[fecha_init.getMonthValue()][fecha_init.getDayOfMonth()] = true;
                }
            }

            //se aumenta la fecha init a la siguiente secuencia
            fecha_init = fecha_init.plusDays(distancias[c_secuencia]);
        }
    }
    
    
    private LocalDate getFechaInitLibres(int grupo, int year) {
        LocalDate fecha_init = LocalDate.of(year, 1, 1);
        LocalDate fecha_grupo_2013 = this.getFechaInitGrupo2013(grupo);

        long dias = DAYS.between(fecha_grupo_2013, fecha_init);
        float re = (float) dias / 35;
        float p_dec = (float) re % 1;
        float t_dias = (float) (0.35 * (1 - p_dec)) * 100;
        fecha_init = fecha_init.plusDays(Math.round(t_dias));

        //se obtiene la primera fecha que equivale al mismo día del 2013 en el año del parámetro
        //ahora hay que busca hacia atra el primer día libre de este año
        //se crea una matriz con estos valores, las filas son miércoles, viernes, sábado, domingo
        int [][] valores_retroceso = {{10,8,8,9}, {9,10,8,8}, {8,9,10,8}, {8,8,9,10}};
        
        int day = fecha_init.getDayOfWeek().getValue();
        int fila = 0;
        switch(day){
            case 5:
                fila = 1;
                break;
            case 6:
                fila = 2;
                break;
            case 7:
                fila = 3;
                break;
            default:
                fila = 0;
                break;
          }
        int colum = 0;
        
        fecha_init = fecha_init.minusDays(valores_retroceso[fila][colum++]);
        while (fecha_init.getYear() == year) {
            if (colum > 3) {
                colum = 0;
            }
            fecha_init = fecha_init.minusDays(valores_retroceso[fila][colum++]);
        }
        return fecha_init;
    }

    
    
    /**
     * @param grupo
     * @return fecha del año 2013 que tiene cada grupo 
     */
    private LocalDate getFechaInitGrupo2013(int grupo){
        LocalDate fecha = null;
        if(grupo >= 1 && grupo <= 5){
            fecha = fechas_init_2013[grupo-1];
        }
        return fecha;
    }
    
    
    
    
    //------------------------------------------------------------------------------------------
    
    
    
    /**
     * Se cambian los dias de subgrupo apartir del año 2020
     * calcula le primer día de subgrupo y va añdiendo a la lista el resto de dias del año
     * @param grupo_libres
     * @param letra
     * @param year
     * @return lista de fechas con los dias de subgrupo
     */
    public ArrayList<LocalDate> lista_dias_nuevo_subgrupo(int grupo_libres, String letra, int year) {
        ArrayList<LocalDate> lista_subgrupo = new ArrayList<>();

        //distancias entre dias, la secuencia de dias es jueves, lunes, miercoles y martes
        //el array sigue la misma secuencia
        int[] secu = {60, 65, 76, 79};

        int l = this.getLetraSubgrupo(letra);
        LocalDate fecha_init_subgrupo = this.getFechaSubgrupoNuevo(grupo_libres, l);
        LocalDate fecha_init = this.getFechaInitYearSubgrupo(fecha_init_subgrupo, year);
        int day = fecha_init.getDayOfWeek().getValue();
        int contador = 0;
        if (day == 1) {
            contador = 1;
        }
        if (day == 3) {
            contador = 2;
        }
        if (day == 2) {
            contador = 3;
        }
        //si es jueves contador vale 0
        while (fecha_init.getYear() == year) {
            if (contador > 3) {
                contador = 0;
            }
            lista_subgrupo.add(fecha_init);
            fecha_init = fecha_init.plusDays(secu[contador++]);
        }
        return lista_subgrupo;
    }
    
    
    
    /**
     * el valor de letra referencia la poaisción en el array
     * @param grupo
     * @param letra
     * @return 
     */
    private LocalDate getFechaSubgrupoNuevo(int grupo, int letra){
        LocalDate fecha = null;
        if(grupo >= 1 && grupo <= 5){
            switch(grupo){
                case 1:
                    fecha = grupo1[letra];
                    break;
                case 2:
                    fecha = grupo2[letra];
                    break;
                case 3:
                    fecha = grupo3[letra];
                    break;
                case 4:
                    fecha = grupo4[letra];
                    break;
                case 5:
                    fecha = grupo5[letra];
                    break;
                default:
                    fecha = LocalDate.of(1900, 1, 1);
                    break;
            }
        }
        return fecha;
    }
    
    
    /**
     * La fecha_init es la primera fecha del subgrupo del año 2020
     * El year es el año que vamos a calcular
     * @param fecha_init
     * @param year
     * @return la primera fecha del subgrupo ese año(year)
     */
    private LocalDate getFechaInitYearSubgrupo(LocalDate fecha_init_subgrupo, int year) {
        //secuencia de días que hay que retroceder
        //de juaves a martes, de martes a miércoles, de miércoles a lunes
        int[] secu = {79, 76, 65, 60};
        //se crea el día del año que queremos calcular
        LocalDate fecha_fin = LocalDate.of(year, 1, 1);

        if (year == 2020) {
            return fecha_init_subgrupo;
        } else {
            long dias = DAYS.between(fecha_init_subgrupo, fecha_fin);
            float re = (float) dias / 280;
            float p_dec = (float) re % 1;
            float t_dias = (float) (2.8 * (1 - p_dec)) * 100;
            fecha_fin = fecha_fin.plusDays(Math.round(t_dias));
            //devuelve la fecha que corresponde al día que se inicia en el 2020
            //pero en el año que se quiere calcular pueden existir fechas anteriores
            int contador = 0;
            if (fecha_fin.getDayOfWeek().getValue() == 2) {
                contador = 1;
            }
            if (fecha_fin.getDayOfWeek().getValue() == 3) {
                contador = 2;
            }
            if (fecha_fin.getDayOfWeek().getValue() == 1) {
                contador = 3;
            }
            LocalDate fecha_aux = fecha_fin;
            while (fecha_aux.getYear() == year) {
                if (contador > 3) {
                    contador = 0;
                }
                fecha_aux = fecha_fin.minusDays(secu[contador++]);
                if (fecha_aux.getYear() == year) {
                    fecha_fin = fecha_aux;
                }
            }
        }
        return fecha_fin;
    }
    
    
     private int getLetraSubgrupo(String letra) {
        int l = 0;
        switch (letra) {
            case "A":
                l = 0;
                break;
            case "B":
                l = 1;
                break;
            case "C":
                l = 2;
                break;
            case "D":
                l = 3;
                break;
            case "E":
                l = 4;
                break;
            case "F":
                l = 5;
                break;
            case "G":
                l = 6;
                break;
            case "H":
                l = 7;
                break;
        }
        return l;
    }
    
     
     
     
     //------------------------------------------------------------------------------
     
     
    
    /**
     * Se calcula los libres comunes a los grupos en dos conjuntos, siempre es un sábado
     * @param grupo
     * @param letra
     * @param year
     * @return lista de fechas con los dias subgrupos comunes
     */
    public ArrayList<LocalDate> getSubgrupoComunes(int grupo, String letra, int year) {
        ArrayList<LocalDate> lista_sub_comunes = new ArrayList<>();
        LocalDate fecha_init_sub_comunes = this.getFechaInitSubComun(grupo, letra, year);

        while (fecha_init_sub_comunes.getYear() == year) {
            lista_sub_comunes.add(fecha_init_sub_comunes);
            fecha_init_sub_comunes = fecha_init_sub_comunes.plusDays(70);
        }
        return lista_sub_comunes;
    }

    
    /**
     * Calcula la primera fecha donde se debe comenzar a calcular los libres comunes
     * Si es el año 2020 se envia la primera fecha del grupo correspondiente
     * @param grupo
     * @param letra
     * @param year
     * @return 
     */
    private LocalDate getFechaInitSubComun(int grupo, String letra, int year) {
        LocalDate fecha_init_sub_comunes = this.getFechaSubComunes(grupo, letra);
        LocalDate fecha_fin_sub_comunes = LocalDate.of(year, 1, 1);

        if (year == 2020) {
            return fecha_init_sub_comunes;
        } else {
            long dias = DAYS.between(fecha_init_sub_comunes, fecha_fin_sub_comunes);
            float re = (float) dias / 70;
            float p_dec = (float) re % 1;
            float t_dias = (float) (0.7 * (1 - p_dec)) * 100;
            fecha_fin_sub_comunes = fecha_fin_sub_comunes.plusDays(Math.round(t_dias));
            //devuelve la fecha que corresponde al día que se inicia en el 2020
            //pero en el año que se quiere calcular pueden existir fechas anteriores

            LocalDate fecha_aux = fecha_fin_sub_comunes;
            while (fecha_aux.getYear() == year) {
                fecha_aux = fecha_fin_sub_comunes.minusDays(70);
                if (fecha_aux.getYear() == year) {
                    fecha_fin_sub_comunes = fecha_aux;
                }
            }
        }
        return fecha_fin_sub_comunes;
    }
    
    
    private LocalDate getFechaSubComunes(int grupo, String letra){
        LocalDate fecha = null;
        int pos = 0;
        //A-C-E-G = 0   B-D-F-H=1
        if(letra.equals("B") || letra.equals("D") || letra.equals("F") || letra.equals("H")){
            pos = 1;
        }
        if(grupo >= 1 || grupo <= 5){
            switch(grupo){
                case 1:
                    fecha = sub_comunes_g1[pos];
                    break;
                case 2:
                    fecha = sub_comunes_g2[pos];
                    break;
                case 3:
                    fecha = sub_comunes_g3[pos];
                    break;
                case 4:
                    fecha = sub_comunes_g4[pos];
                    break;
                case 5:
                    fecha = sub_comunes_g5[pos];
                    break;
                default:
                    fecha = LocalDate.of(1900, 1, 1);
                    break;
            }
        }
        return fecha;
    }
   
    
   
    
}
