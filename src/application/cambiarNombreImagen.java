package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class cambiarNombreImagen implements Initializable{
	@FXML ComboBox<String> minuto;
	@FXML AnchorPane ventanaProyecto;
	@FXML Button cancelar;
	@FXML Button aceptar;
	ScreensController myController; 
	ProyectoControlador pro = new ProyectoControlador();
	InicioControlador ini = new InicioControlador();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//ventanaProyecto.setStyle("-fx-background-color: #92A4E8;");
	        minuto.setPrefWidth(180);
	        minuto.setItems(FXCollections.observableArrayList("10", "20","30","40","50","60","70","80","90","100","110","120","130","140","150","160","170","180","190","200","210","220","230","240"));
	        minuto.setValue("10");
	       		
	}

	//Esta es la funcion que al darle click en aceptar en la ventana emergente de nuevo proyecto, crearÃ¡ el nuevo proyecto
	@FXML
	private void actualizarNombreI() throws IOException{
		
		String Texto;
		Texto = minuto.getValue();
		if(Texto.length()!=0){
			Consultas co = new Consultas();
			Integer min = Integer.valueOf(Texto);
			System.out.println(min);
			System.out.println(ini.t);
			System.out.println(pro.ProNuevo.substring(7));
			co.actualizaImagen(ini.t,min,Integer.valueOf(pro.ProNuevo.substring(7)));
			JOptionPane.showMessageDialog(null, "Operación exitosa");
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
			actualizarNombreI();
		}
	} 	
}