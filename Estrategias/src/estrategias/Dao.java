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
        rs = st.executeQuery("select * from Estrategia where nombre_servidor = '"+nombre_servidor+"'");
        return rs;
    }
    public static ResultSet getTablespacesServidor(ResultSet rs, String nombre_servidor) throws SQLException {
        st = Sta(st);
        rs = st.executeQuery("select tablespace_name from sys.dba_segments@"+ nombre_servidor+" group by tablespace_name");
        return rs;
    }
    
    public static void insertServidor(Servidor se) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Servidor values('"+se.nombre_servidor+"','"+se.nombre_base+"','"+se.nombre_usuario+"','"+se.contrasena+"','"+se.ip+"','"+se.puerto+"')");
        st.executeQuery("commit");
    }
    
    public static void updateServidor(Servidor se) throws SQLException {
        st = Sta(st);
        st.executeQuery("update Servidor set nombre_base = '"+se.nombre_base+"', nombre_usuario = '"+se.nombre_usuario+"', contrasena = '"+se.contrasena+"', ip = '"+se.ip+"', puerto = '"+se.puerto+"' where nombre_servidor = '"+se.nombre_servidor+"'");
        st.executeQuery("commit");
    }
    
    public static void deleteServidor(String nombre_servidor) throws SQLException {
        st = Sta(st);
        st.executeQuery("delete from Estrategia where nombre_servidor = '"+nombre_servidor+"'");
        st.executeQuery("delete from Servidor where nombre_servidor = '"+nombre_servidor+"'");
        st.executeQuery("drop public database link "+nombre_servidor);
        st.executeQuery("commit");
    }
     public static void deleteEstrategiaFromServerr(String nombre_servidor) throws SQLException {
        st = Sta(st);
        
        st.executeQuery("delete from Estrategia where nombre_servidor = '"+nombre_servidor+"'");
        //st.executeQuery("dbms_scheduler.drop_job ( job_name    =>'"+nombre_estrategia+"');");
        st.executeQuery("commit");
    }
      public static void deleteEstrategia(String nombre_estrategia) throws SQLException {
        st = Sta(st);
        st.executeQuery("delete from Estrategia where nombre_estrategia = '"+nombre_estrategia+"'");
        //st.executeQuery("dbms_scheduler.drop_job ( job_name    =>'"+nombre_estrategia+"');");
        st.executeQuery("commit");
    }
    
    public static void insertEstrategia(Estrategia es) throws SQLException {
        st = Sta(st);
        st.executeQuery("insert into Estrategia values('"+es.nombre_estrategia+"','"+es.tipo_respaldo+"','"+es.modo_respaldo+"','"+es.metodo_respaldo+"','"+es.objetos+"','"+es.nombre_periodo+"',"+es.activo+",'"+es.nombre_servidor+"')");
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
    
    public static void createDatabaseLink(String ServerName,String BaseName, String UserName, String Pass, String IP, String port ) throws SQLException {
        st = Sta(st);
        st.executeQuery("CREATE PUBLIC DATABASE LINK "+ServerName+"\n" +
            "CONNECT TO "+UserName+" identified BY \""+Pass+"\"\n" +
            "USING\n" +
            "'(DESCRIPTION =\n" +
            "    (ADDRESS = (PROTOCOL = TCP)(HOST = "+IP+")(PORT = "+port+"))\n" +
            "    (CONNECT_DATA =\n" +
            "      (SERVER = DEDICATED)\n" +
            "      (SERVICE_NAME = "+BaseName+")\n" +
            "      (SID = XE)\n" +
            "    )\n" +
        "  )'");
    }
    
    
     public static void createJob(String nombre_estrategia, String ruta, String frecuencia, Timestamp inicio) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec sp_createJob('" + nombre_estrategia + "', '" + ruta + "', '" + frecuencia + "', '" + inicio + "')");
     }
 
     public static void dropJob(String nombre_estrategia) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec sp_dropJob('" + nombre_estrategia + "')");
     }
     
     public static void alterJob(String nombre_estrategia, String frecuencia) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec alterJob('" + nombre_estrategia + "', '" + frecuencia + "')");
     }
     
     public static void deshabilitar(String nombre_estrategia) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec deshabilitarJob('" + nombre_estrategia + "')");
     }
     
     public static void habilitar(String nombre_estrategia) throws SQLException {
          st = Sta(st);
          st.executeQuery("exec habilitarJob('" + nombre_estrategia + "')");
      }
 
     public static void eliminarServidor(String nombre_servidor) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec eliminarServidor('" + nombre_servidor + "')");
      }
     public static void elimnarEstrategia(String nombre_estrategia) throws SQLException {
         st = Sta(st);
         st.executeQuery("exec eliminarEstrategia('" + nombre_estrategia + "')");
      }
     
     public static void createJob2(String nombre_estrategia, String ruta, String frecuencia, Timestamp inicio) throws SQLException {
         st = Sta(st);
         st.executeQuery("begin \n" +
"    dbms_scheduler.create_job( \n" +
"        job_name        => 'RAMDSDS', \n" +
"        job_type        => 'EXECUTABLE', \n" +
"        job_action      => 'C:\\rman\\rman.bat', \n" +
"        start_date      => SYSTIMESTAMP, \n" +
"        repeat_interval => 'FREQ=DAILY;BYHOUR=4;BYMINUTE=1', \n" +
"        enabled         => false, \n" +
"        comments        => 'Backup database using RMAN'); \n" +
"end;");
      }
     
     
    
}