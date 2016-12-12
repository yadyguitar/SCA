/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

   
    // DATOS PARA CONEXION CON BASE DE DATOS MYSQL

    private String USERNAME="root"; //EL MISMO QUE MYSQL
    private String PASSWORD="301194"; //EL MISMO QUE MYSQL
    private String HOST="localhost"; 
    private String PORT="3306";
    private String DATABASE="sca"; //LA DATABASE A USAR
    private String CLASSNAME="com.mysql.jdbc.Driver";   //EL CONECTOR PARA HACER LA CONEXI�N
    private String URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE+"?user="+USERNAME+"&password="+PASSWORD;
    private Connection conex;

    public Conexion(){
        try{
            //TRATA DE REALIZAR LA CONEXI�N 
            Class.forName(CLASSNAME);
            conex=DriverManager.getConnection(URL, USERNAME,PASSWORD);
        }
        //SI NO PUEDE MANDA ERROR
        catch(ClassNotFoundException | SQLException e){
            System.err.println("ERROR EN LA CONEXI�N " +e);
        }

    }
    //OBTEN LA CONEXI�N
    public Connection getConexion(){
        return conex;
    }

    
}
