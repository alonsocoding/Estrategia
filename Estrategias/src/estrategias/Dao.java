package estrategias;

import java.io.*;
import java.sql.*;
import javax.swing.*;

public class Dao {

    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;

    static String bd = "XE";
    static String username = "bases";
    static String password = "bases";
    static String url = "jdbc:oracle:thin:@LAPTOP-I8IV3KN9:1522:XE";

    public static Connection Enlace(Connection conn) throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.print("Clase no encontrada...");
        }
        return conn;
    }

    public static Statement Sta(Statement st) throws SQLException {
        conn = Enlace(conn);
        st = conn.createStatement();
        return st;
    }
    
    public static ResultSet getServidores(ResultSet rs) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select * from Servidor");
        return rs;
    }
    
    public static ResultSet getEstrategias(ResultSet rs) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select * from Estrategia");
        return rs;
    }
    
    public static ResultSet getPeriodo(ResultSet rs, String nombre_periodo) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select * from Periodo where nombre_periodo = '" + nombre_periodo + "'");
        return rs;
    }
    
    public static ResultSet getEstrategiasServidor(ResultSet rs, String nombre_servidor) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select Estrategia.nombre_estrategia, Estrategia.tipo_respaldo, Estrategia.modo_respaldo, Estrategia.metodo_respaldo \n" +
                "from Servidor, Estrategia, Contiene \n" +
                "where Servidor.nombre_servidor = Contiene.nombre_servidor\n" +
                "and Estrategia.nombre_estrategia = Contiene.nombre_estrategia \n" +
                "and Servidor.nombre_servidor = '"+nombre_servidor+"'");
        return rs;
    }
    
    public static void insertServidor(Servidor se) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Servidor values('"+se.nombre_servidor+"','"+se.nombre_base+"','"+se.nombre_usuario+"','"+se.contrasena+"','"+se.ip+"','"+se.puerto+"')");
        st.executeQuery("commit");
    }
    
    public static void insertEstrategia(Estrategia es) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Estrategia values('"+es.nombre_estrategia+"','"+es.tipo_respaldo+"','"+es.modo_respaldo+"','"+es.metodo_respaldo+"','"+es.objetos+"','"+es.nombre_periodo+"')");
        st.executeQuery("commit");
    }
    
    public static void insertPeriodo(Periodos pe) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Periodo values('"+pe.nombre_periodo+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+pe.hora+"','"+pe.minuto+"','"+pe.segundo+"')");
        st.executeQuery("commit");
    }
    
    public static void insertContiene(String nombre_servidor, String nombre_estrategia) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Contiene values('"+nombre_servidor+"', '"+nombre_estrategia+"')");
        st.executeQuery("commit");
    }
    
    
}