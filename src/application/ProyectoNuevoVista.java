package application;

import java.io.File;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProyectoNuevoVista implements Initializable{

	@FXML
	TextField NombreProyectoNuevo = null;
	@FXML
	AnchorPane ventanaProyecto;
	@FXML
	Button cancelar;
	Button aceptar;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ventanaProyecto.setStyle("-fx-background-color: #92A4E8;");
	}
	@FXML
	private void AgregarProyectoNuevo(){
		
		String Texto = (NombreProyectoNuevo.getText());
		Consultas co = new Consultas();
		File directorio = new File("src\\application\\Proyectos\\"+Texto); 
		directorio.mkdir(); 
		co.agregar_proyecto(Texto);
		Stage stage = (Stage) cancelar.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	@FXML
	private void CerrarVentana(){
		Stage stage = (Stage) cancelar.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}

	
	
}
