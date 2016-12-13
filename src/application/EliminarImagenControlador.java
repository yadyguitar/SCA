package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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

public class EliminarImagenControlador implements Initializable{
	@FXML ComboBox<String> proyecto;
	@FXML AnchorPane ventanaProyecto;
	@FXML Button cancelar;
	@FXML Button aceptar;
	ScreensController myController; 
	ProyectoControlador pro = new ProyectoControlador();
	InicioControlador ini = new InicioControlador();
	Path copy_from_1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
			Consultas c = new Consultas();
	        proyecto.setPrefWidth(180);
	        ArrayList<Integer> lista=new ArrayList<Integer>();
	        ArrayList<String> lista2=new ArrayList<String>();
	        lista.addAll(c.imagen(ini.t));		//Almacena el nombre de los proyectos en una lista
	        for(int i=0;i<lista.size();i++)
	        {
	        	lista2.add(i,String.valueOf(lista.get(i)));
	        }
        	proyecto.setItems(FXCollections.observableArrayList(lista2));
	        if(lista.size()!=0){
	        	proyecto.setValue(lista2.get(0));
	        }
	        else
	        {
	        	proyecto.setValue("No existen proyectos");
	        }	
	       		
	}

	//Esta es la funcion que al darle click en aceptar en la ventana emergente de nuevo proyecto, creará el nuevo proyecto
	@FXML
	private void eliminar() throws IOException{
		
		String Texto;
		Texto = proyecto.getValue();
		if(Texto.length()!=0){
			//System.out.println(Texto);
			Consultas co = new Consultas();
			String ruta = co.ruta_imagen(Integer.valueOf(Texto),ini.t);
			Consultas c = new Consultas();
			Integer id = c.id_imagen(ruta);
			Consultas p = new Consultas();
			p.EliminaPol(id);
			Consultas h = new Consultas();
			h.EliminaId(id);
			copy_from_1 = Paths.get(ruta);
			String userDirectoryString = System.getProperty("user.home");
			File directorioActual = new File(ruta);
			Path imageD = Paths.get(ruta);
			borraCarpeta(directorioActual);
			System.out.println(directorioActual.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "Imagen eliminada");
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