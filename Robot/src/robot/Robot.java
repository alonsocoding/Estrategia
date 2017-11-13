/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alonso
 */
public class Robot extends Thread {

    static Connection conn = null;
    static Statement sta = null;
    static ResultSet res = null;
    
    String line;
    OutputStream stdin = null;
    InputStream stderr = null;
    InputStream stdout = null;
    // launch EXE and grab stdin/stdout and stderr    
    
    public void run() {
        
        while(true) {
            try {
            
            res = Dao.getEvidencia(res);
            
            ResultSetMetaData Res_md = res.getMetaData();
            int cantidad_columnas = Res_md.getColumnCount();
 
            while (res.next()) {
                Object[] fila = new Object[cantidad_columnas];
                for (int i = 0; i < cantidad_columnas; i++) {
                        fila[i] = res.getObject(i+1); // Ingresa valor desde SQL
                }
                if(fila[2].equals("FALLIDO")) {
                    JOptionPane.showMessageDialog(null, "La estrategia "+fila[0]+" ha fallado, se realizara respaldo local");
                    System.out.println("FALLIDO");
    try {
      Process p = Runtime.getRuntime().exec("C:\\rman\\rman.bat");
       BufferedReader bri = new BufferedReader
        (new InputStreamReader(p.getInputStream()));
      BufferedReader bre = new BufferedReader
        (new InputStreamReader(p.getErrorStream()));
      while ((line = bri.readLine()) != null) {
        System.out.println(line);
      }
      bri.close();
      while ((line = bre.readLine()) != null) {
        System.out.println(line);
      }
      bre.close();
      p.waitFor();
      System.out.println("Done.");
      System.out.println(p.exitValue());
    }
    catch (Exception err) {
      err.printStackTrace();
    }
         
 

                  } else if(fila[2].equals("EXITOSO")) {
                    System.out.println("EXITOSO");
                }
            }
           
            // Delay de cinco segundos
            Thread.sleep(5000);
            
            } catch(Exception e) {
                e.printStackTrace();
            }
            
            
        }
    }

    public static void main(String args[]) {
        (new Robot()).start();
    }
}