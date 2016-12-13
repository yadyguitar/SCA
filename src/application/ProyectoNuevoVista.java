package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProyectoNuevoVista implements Initializable{
	@FXML TextField NombreProyectoNuevo;
	@FXML AnchorPane ventanaProyecto;
	@FXML Button cancelar;
	@FXML Button aceptar;
	ScreensController myController; 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//ventanaProyecto.setStyle("-fx-background-color: #92A4E8;");
		
	}

	//Esta es la funcion que al darle click en aceptar en la ventana emergente de nuevo proyecto, creará el nuevo proyecto
	@FXML
	private void AgregarProyectoNuevo() throws IOException{
		
		String Texto = (NombreProyectoNuevo.getText());
		if(Texto.length()!=0){
			Consultas co = new Consultas();
			
			String userDirectoryString = System.getProperty("user.home");
	        File directorio = new File(userDirectoryString+"/SCA/Proyectos/"+Texto);
	        System.out.println(directorio.mkdir()); 

			co.agregar_proyecto(Texto);
			changeLocale();
		}
		Stage stage = (Stage) cancelar.getScene().getWindow();
	    stage.close();
	    
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
			AgregarProyectoNuevo();
		}
	} 	 

	
	
}
