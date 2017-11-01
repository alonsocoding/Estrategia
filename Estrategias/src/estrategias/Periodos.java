/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estrategias;

/**
 *
 * @author alonso
 */
public class Periodos {
    public String nombre_periodo;
    public boolean lunes;
    public boolean martes;
    public boolean miercoles;
    public boolean jueves;
    public boolean viernes;
    public boolean sabado;
    public boolean domingo;
    public int hora; public int minuto; public int segundo;
    
    public Periodos(String np, boolean l, boolean m, boolean mi, boolean ju, boolean vi, boolean sa, boolean dom, int hora, int minuto, int segundo){
        this.nombre_periodo = np;
        this.lunes = l;
        this.martes = m;
        this.miercoles = mi;
        this.jueves = ju;
        this.viernes = vi;
        this.sabado = sa;
        this.domingo = dom;
        this.hora = hora; this.minuto = minuto; this.segundo = segundo;
    }

}
