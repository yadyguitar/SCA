package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EliminarProyectoControlador implements Initializable{
	@FXML ComboBox<String> proyecto;
	@FXML AnchorPane ventanaProyecto;
	@FXML Button cancelar;
	@FXML Button aceptar;
	ScreensController myController; 
	ProyectoControlador pro = new ProyectoControlador();
	InicioControlador ini = new InicioControlador();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
			Consultas c = new Consultas();
	        proyecto.setPrefWidth(180);
	        ArrayList<String> lista=new ArrayList<String>();
	        lista.addAll(c.carpeta());		//Almacena el nombre de los proyectos en una lista
	        
	        proyecto.setItems(FXCollections.observableArrayList(lista));
	        if(lista.size()!=0)
	        	proyecto.setValue(lista.get(0));
	        else
	        {
	        	proyecto.setValue("No existen proyectos");
	        }	
	       		
	}

	//Esta es la funcion que al darle click en aceptar en la ventana emergente de nuevo proyecto, crear√° el nuevo proyecto
	@FXML
	private void eliminar() throws IOException{
		
		String Texto;
		Texto = proyecto.getValue();
		if(Texto.length()!=0){
			//System.out.println(Texto);
			Consultas co = new Consultas();
			ArrayList<Integer> id_i=new ArrayList<Integer>();
			id_i.addAll(co.id_i(Texto));
			for(int i=0;i<id_i.size();i++)
			{
				Consultas d = new Consultas();
				d.EliminaPol(id_i.get(i));
			}
			Consultas p = new Consultas();
			int id_p=p.id_proyecto(Texto);
			Consultas j = new Consultas();
			j.EliminaI(id_p);
			Consultas h = new Consultas();
			h.EliminaP(Texto);
			String userDirectoryString = System.getProperty("user.home");
			File directorioActual = new File(userDirectoryString+"/SCA/Proyectos/"+Texto);
			System.out.println(directorioActual.getAbsolutePath());
			borraCarpeta(directorioActual);
			JOptionPane.showMessageDialog(null, "Proyecto eliminado con Èxito");
			changeLocale();
		}
		Stage stage = (Stage) cancelar.getScene().getWindow();
	    stage.close();
	    
	}
	public void borraCarpeta(File fileDel){
	        if(fileDel.isDirectory()){            
	            
	            if(fileDel.list().length == 0)
	                fileDel.delete();
	            else{
	                
	               for (String temp : fileDel.list()) {
	                   File fileDelete = new File(fileDel, temp);
	                   //recursive delete
	                   borraCarpeta(fileDelete);
	               }

	               //check the directory again, if empty then delete it
	               if(fileDel.list().length==0)
	                   fileDel.delete();
	               
	            }

	        }else{
	            
	            //if file, then delete it
	            fileDel.delete();            
	        }
	}
	
	
	public void changeLocale() throws IOException{
		//ScreensController mainContainer = new ScreensController();
        //mainContainer.loadScreen(Framework.screen1ID, Framework.screen1File);
        //mainContainer.setScreen(Framework.screen1ID); 
		Framework.principal.close();
		new Framework().start(new Stage());
		
	}
	@FXML
	private void CerrarVentana(){
		Stage stage = (Stage) cancelar.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	@FXML
	public void tecla(KeyEvent e) throws IOException{
	
		if(e.getCode()==KeyCode.ESCAPE){
			CerrarVentana();
		}
		if(e.getCode()==KeyCode.ENTER){
			eliminar();
		}
	} 	
}