package application;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class cambiarNombreProyecto implements Initializable{
	@FXML TextField NombreProyectoNuevo;
	@FXML AnchorPane ventanaProyecto;
	@FXML Button cancelar;
	@FXML Button aceptar;
	ScreensController myController; 
	InicioControlador pro = new InicioControlador();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//ventanaProyecto.setStyle("-fx-background-color: #92A4E8;");
		
	}

	//Esta es la funcion que al darle click en aceptar en la ventana emergente de nuevo proyecto, crear√° el nuevo proyecto
	@FXML
	private void actualizarNombreP() throws IOException{
		
		String Texto = (NombreProyectoNuevo.getText());
		if(Texto.length()!=0){
			Consultas co = new Consultas();
			
			String userDirectoryString = System.getProperty("user.home");
			File directorioActual = new File(userDirectoryString+"/SCA/Proyectos/"+pro.textonuevo);
	        File directorioDest = new File(userDirectoryString+"/SCA/Proyectos/"+Texto);
	        System.out.println("Ac: "+directorioActual.getAbsolutePath());
	        System.out.println("Ac: "+directorioDest.getAbsolutePath());
	        directorioDest.mkdir();
	        
	        
	        
	        ArrayList<String> lista=new ArrayList<String>();
	        Consultas c = new Consultas();
	        lista.addAll(c.rutas(pro.textonuevo));
	        System.out.println(lista);
	        
	        for(int i=0;i<lista.size();i++){
	        	String cad = lista.get(i);
	        	cad = cad.replaceAll(pro.textonuevo, Texto);
	        	Consultas d = new Consultas();
	        	d.actualizaRuta(lista.get(i), cad);
	        	Path imageO = Paths.get(lista.get(i));
	        	Path imageD = Paths.get(cad);
	        	System.out.println(lista.get(i));
	        	System.out.println(cad);
	        	Files.copy(imageO, imageD, REPLACE_EXISTING, COPY_ATTRIBUTES,
			              NOFOLLOW_LINKS);
	        	File temp = new File(lista.get(i));
	        	System.out.println("borrar: "+lista.get(i) );
	        	temp.delete();
	        }
	        co.actualizaProyecto(Texto,pro.textonuevo);
	        if(directorioActual.isDirectory())
	        	directorioActual.delete();
	        
	        //lista.addAll(co.rutas());
			JOptionPane.showMessageDialog(null, "OperaciÛn exitosa");
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
			actualizarNombreP();
		}
	} 	
}