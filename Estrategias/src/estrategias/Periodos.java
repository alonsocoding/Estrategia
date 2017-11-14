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

    public String getNombre_periodo() {
        return nombre_periodo;
    }

    public void setNombre_periodo(String nombre_periodo) {
        this.nombre_periodo = nombre_periodo;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
    

}
