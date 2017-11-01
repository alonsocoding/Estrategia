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
public class Servidor {
    public String nombre_servidor;
    public String nombre_base;
    public String nombre_usuario;
    public String contrasena;
    public String ip;
    public int puerto;
    
    public Servidor(String ns, String nb, String nu, String con, String ip, int p) {
        this.nombre_servidor = ns;
        this.nombre_base = nb;
        this.nombre_usuario = nu;
        this.contrasena = con;
        this.ip = ip;
        this.puerto = p;
    }
    
}
