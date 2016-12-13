package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.scene.control.Label;


public class Consultas extends Conexion{
	
	
	public ArrayList<String> lista1=new ArrayList<String>();

	public List<Integer> lista2=new ArrayList<Integer>();
	public List<String> rutas=new ArrayList<String>();
	public List<Integer> areas=new ArrayList<Integer>();
	public List<Integer> id_imagen=new ArrayList<Integer>();
	
	//Funciones para buscar datos---------------------------------------------------------------------
	
     //Devuelve un array de strings de los nombres de las carpetas
	 public ArrayList<String> carpeta(){
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select * from proyecto";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);
	            
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
	 
	 //Devuelve una lista de las imagenes que se encuentran en el proyecto ingresado como parÃ¡metro
	 public List<Integer> imagen(String NombreProyecto){
			System.out.println(NombreProyecto);
		
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="SELECT * from proyecto,imagen WHERE NombreProyecto='"+NombreProyecto+"' and proyecto.id_proyecto = imagen.id_proyecto";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);
	           
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
	 
	 //Devuelve la ruta de la imagen, para abrir la vista area
	 public String ruta_imagen(int minuto,String NombreProyecto){
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select URL from proyecto,imagen where imagen.minuto ="+minuto+" and proyecto.NombreProyecto='"+NombreProyecto+"' and proyecto.id_proyecto=imagen.id_proyecto";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);
	          
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
	 
	 //Devuelve el id de la imagen de una url
	 public int id_imagen(String url){
		 	PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select id_imagen from imagen where URL ='"+url+"'";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);
	         
	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	            //SI TIENE REGISTROS LA TABLA
	            if(rs.absolute(1)){
	                return rs.getInt(1);
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
	        
	         return 0;
	 }
	 //Obtiene el id de un proyecto con respecto a un nombre
	 public int id_proyecto(String NombreProyecto){
		 	PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="select id_proyecto from proyecto where NombreProyecto ='"+NombreProyecto+"'";
	            //PREPARA LA CONEXI�N
	            pst = getConexion().prepareStatement(consultaUsuario);
	           
	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	            //SI TIENE REGISTROS LA TABLA
	            if(rs.absolute(1)){
	                return rs.getInt(1);
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
	        
	         return 0;
	 }

	 public List<Integer> momentos(Integer min, String NombreProyecto){
			
	        PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="SELECT * FROM poligono,imagen,proyecto where poligono.id_imagen = imagen.id_imagen && imagen.minuto = '"+min+"' && imagen.id_proyecto = proyecto.id_proyecto && proyecto.NombreProyecto = '"+NombreProyecto+"';";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);

	            //EJECUTA LA CONSULTA
	            rs = pst.executeQuery();
	            
	           
		        boolean r=rs.next();
				while (r) {
					areas.add(rs.getInt("area"));
					//System.out.println("Hola");
					r=rs.next();
				}
	            
	        return areas;
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
	 
	 //Funciones para insertar datos------------------------------------------------------------------
	 public boolean agregar_proyecto(String NombreProyecto){
		 PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="Insert into proyecto (NombreProyecto) values (?);";
	            //PREPARA LA CONEXI�N
	            pst = getConexion().prepareStatement(consultaUsuario);
	            pst.setString(1, NombreProyecto);
	            
	            //EJECUTA LA CONSULTA
	            pst.execute();
	            return true;
	            
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
	       
		 return false;
	 }
	 
	 public boolean agregar_imagen(Integer id_proyecto,Integer minuto, String URL){
		 PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="Insert into imagen (id_proyecto,minuto,URL) values (?,?,?);";
	            //PREPARA LA CONEXI�N
	            pst = getConexion().prepareStatement(consultaUsuario);
	            pst.setInt(1, id_proyecto);
	            pst.setInt(2, minuto);
	            pst.setString(3, URL);
	            
	            //EJECUTA LA CONSULTA
	            pst.execute();
	            return true;
	            
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
	       
		 return false;
	 }
	 
	 public boolean agregar_poligonos(int id_imagen, List<Label> areas){
		 PreparedStatement pst;
	        pst = null;
	        ResultSet rs= null;
	      
	        //CONSULTA EN LA BD
	        try{
	            //COMANDO PARA HACER LA CONSULTA EN LA BD
	            String consultaUsuario ="Insert into poligono (id_imagen, area) values (?,?);";
	            //PREPARA LA CONEXIï¿½N
	            pst = getConexion().prepareStatement(consultaUsuario);
	            
	            for (Label area : areas){
	            //OBTEN LOS DATOS
	            pst.setInt(1, id_imagen);
	            pst.setFloat(2, Float.parseFloat(area.getText()));
	            //EJECUTA LA CONSULTA
	            pst.execute();
	            
	            }
	            
	            return true;
	            
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
	       
		 return false;
	 }
	//Devuelve una lista de las imagenes que se encuentran en el proyecto ingresado como parÃ¡metro
		 public boolean actualizaProyecto(String nombre,String NombreProyecto){
			
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="Update proyecto set NombreProyecto = '"+nombre +"' where NombreProyecto = '"+NombreProyecto+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		            pst = getConexion().prepareStatement(consultaUsuario);
		           
		            //EJECUTA LA CONSULTA
		            pst.executeUpdate(consultaUsuario);
		            return true;
		           				
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
		        
				return false;
		 }
		//Devuelve una lista de las imagenes que se encuentran en el proyecto ingresado como parÃ¡metro
		 public List<String> rutas(String NombreProyecto){
			
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		            String consultaUsuario ="SELECT * from proyecto,imagen WHERE NombreProyecto='"+NombreProyecto+"' and proyecto.id_proyecto = imagen.id_proyecto";
		            //PREPARA LA CONEXIï¿½N
		            pst = getConexion().prepareStatement(consultaUsuario);
		           
		            //EJECUTA LA CONSULTA
		            rs = pst.executeQuery(); 
		           
			        boolean r=rs.next();
					while (r) {
						rutas.add(rs.getString("URL"));
						System.out.println(rutas);
						//System.out.println("Hola");
						r=rs.next();
					}
		            
		        return rutas;
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
		//Devuelve una lista de las imagenes que se encuentran en el proyecto ingresado como parÃ¡metro
		 public List<Integer> id_i(String NombreProyecto){
				System.out.println(NombreProyecto);
			
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		            String consultaUsuario ="SELECT * FROM proyecto, imagen where proyecto.id_proyecto = imagen.id_proyecto && proyecto.NombreProyecto = '"+NombreProyecto+"';";
		            //PREPARA LA CONEXIï¿½N
		            pst = getConexion().prepareStatement(consultaUsuario);
		           
		            //EJECUTA LA CONSULTA
		            rs = pst.executeQuery(); 
		           
			        boolean r=rs.next();
					while (r) {
						id_imagen.add(rs.getInt("id_imagen"));
						//System.out.println("Hola");
						r=rs.next();
					}
		            
		        return id_imagen;
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
		 public boolean actualizaRuta(String rutaO, String rutaD){
			
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="Update imagen set URL = '"+rutaD +"' where URL = '"+rutaO+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 
		 public boolean actualizaImagen(String NombreProyecto, Integer minuto1, Integer minuto2){
				
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="UPDATE IMAGEN, PROYECTO SET MINUTO = '"+minuto1+"' WHERE imagen.id_proyecto = proyecto.id_proyecto && proyecto.NombreProyecto='"+NombreProyecto+"' && imagen.minuto = '"+minuto2+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 public boolean EliminaPol(Integer id_imagen){
				
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="delete from poligono where poligono.id_imagen = '"+id_imagen+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 public boolean EliminaI(Integer id_proyecto){
				
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="DELETE FROM sca.imagen where imagen.id_proyecto = '"+id_proyecto+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 public boolean EliminaId(Integer id_imagen){
				
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="DELETE FROM sca.imagen where imagen.id_imagen = '"+id_imagen+"'"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 public boolean EliminaP(String NombreProyecto){
				
		        PreparedStatement pst;
		        pst = null;
		        ResultSet rs= null;
		      
		        //CONSULTA EN LA BD
		        try{
		            //COMANDO PARA HACER LA CONSULTA EN LA BD
		        	String consultaUsuario ="DELETE FROM proyecto where proyecto.NombreProyecto = '"+NombreProyecto+"';"; 		

		        	//PREPARA LA CONEXIï¿½N
		        	 pst = getConexion().prepareStatement(consultaUsuario);
			           
			            //EJECUTA LA CONSULTA
			            pst.executeUpdate(consultaUsuario);
			            return true;
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
		        
				return false;
		 }
		 
	 
	 
	 
	 
}