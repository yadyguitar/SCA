package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Consultas extends Conexion{
	
	
	public ArrayList<String> lista1=new ArrayList<String>();

	public List<Integer> lista2=new ArrayList<Integer>();
	
       
	 public ArrayList<String> carpeta(){
		
	 
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select * from proyecto";
	            //PREPARA LA CONEXIÓN
	            pst = getConexion().prepareStatement(consultaUsuario);
	            //OBTEN LOS DATOS
	           
				//pst.setString(1, NombreProyecto);
			
	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	           
		        boolean r=rs.next();
				while (r) {
					
					lista1.add(rs.getString("NombreProyecto"));
					
					r=rs.next();
				}
	            
	        return lista1;
	        //EN CASO DE HABER ERROR MANDAR MENSAJE
	        }catch(Exception e){
	            System.err.println("ERROR " +e);
	        }
	        finally{
	            //SI SE PUDO HACER TODO, DESPUES DE MANDAR RESULTADO, CERRAR TODO
	            try{
	                if(getConexion()!=null) getConexion().close();
	                if(pst != null) pst.close();
	                if(rs!=null) rs.close();
	            }
	            //SI NO MANDAR ERROR
	            catch (Exception e)
	            {
	                System.err.println("ERROR "+ e);
	            }
	        }
	        
			return null;
	 }
	 
	 public List<Integer> imagen(String NombreProyecto){
			System.out.println(NombreProyecto);
		
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="SELECT * from proyecto,imagen WHERE NombreProyecto='"+NombreProyecto+"' and proyecto.id_proyecto = imagen.id_proyecto";
	            //PREPARA LA CONEXIÓN
	            pst = getConexion().prepareStatement(consultaUsuario);
	           
	            //OBTEN LOS DATOS
	           
				//pst.setString(1, NombreProyecto);
			
	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	           
		        boolean r=rs.next();
				while (r) {
					lista2.add(rs.getInt("minuto"));
					//System.out.println("Hola");
					r=rs.next();
				}
	            
	        return lista2;
	        //EN CASO DE HABER ERROR MANDAR MENSAJE
	        }catch(Exception e){
	        	System.out.println("HOLA");
	            System.err.println("ERROR " +e);
	        }
	        finally{
	            //SI SE PUDO HACER TODO, DESPUES DE MANDAR RESULTADO, CERRAR TODO
	            try{
	                if(getConexion()!=null) getConexion().close();
	                if(pst != null) pst.close();
	                if(rs!=null) rs.close();
	            }
	            //SI NO MANDAR ERROR
	            catch (Exception e)
	            {
	                System.err.println("ERROR "+ e);
	            }
	        }
	        
			return null;
	 }
	 public String ruta_imagen(int minuto,String NombreProyecto){
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select URL from proyecto,imagen where imagen.minuto ="+minuto+" and proyecto.NombreProyecto='"+NombreProyecto+"' and proyecto.id_proyecto=imagen.id_proyecto";
	            //PREPARA LA CONEXIÓN
	            pst = getConexion().prepareStatement(consultaUsuario);
	            //OBTEN LOS DATOS
	           // pst.setInt(1, id_imagen);
	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	            //SI TIENE REGISTROS LA TABLA
	            if(rs.absolute(1)){
	                return rs.getString(1);
	            }
	            
	        //EN CASO DE HABER ERROR MANDAR MENSAJE
	        }catch(Exception e){
	            System.err.println("ERROR " +e);
	        }
	        finally{
	            //SI SE PUDO HACER TODO, DESPUES DE MANDAR RESULTADO, CERRAR TODO
	            try{
	                if(getConexion()!=null) getConexion().close();
	                if(pst != null) pst.close();
	                if(rs!=null) rs.close();
	            }
	            //SI NO MANDAR ERROR
	            catch (Exception e)
	            {
	                System.err.println("ERROR "+ e);
	            }
	        }
	        
	         return null;
	    }

  
}