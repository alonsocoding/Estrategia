/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alonso
 */
public class Dao {
        static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;

    static String bd = "XE";
    static String username = "sys as sysdba";
    static String password = "root";
    static String url = "jdbc:oracle:thin:@alonso-PC:1521:XE";

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
    
    public static void insertPrueba() throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Prueba values(1,1,1)");
        st.executeQuery("commit");
    }
    public static ResultSet getEvidencia(ResultSet rs) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select * from Evidencia");
        return rs;
    }
}
