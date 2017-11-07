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
public class Estrategia {
    public String nombre_estrategia;
    public String tipo_respaldo;
    public String modo_respaldo;
    public String metodo_respaldo;
    public String objetos;
    public String nombre_periodo;
    public int activo;
    public String nombre_servidor;
    
    public Estrategia(String ne, String tr, String mr, String mer, String ob, String np, int act, String nombre_servidor) {
        this.nombre_estrategia = ne;
        this.tipo_respaldo = tr;
        this.modo_respaldo = mr;
        this.metodo_respaldo = mer;
        this.objetos = ob;
        this.nombre_periodo = np;
        this.activo = act;
        this.nombre_servidor = nombre_servidor;
    }
}
