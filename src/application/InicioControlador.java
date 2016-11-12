package application;


import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;


public class InicioControlador  implements Initializable,ControladorVentanas{

	
	ScreensController myController; 
	static public String t;
	private int MAX =4;
	private int i = 0;
	private int j = 0;
	private int k=0;
	VBox pictureRegion;
	
	@FXML GridPane grid;
	@FXML ScrollPane scroll;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		agregaProyecto();
		
	}
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	@FXML 
	private void CrearNuevo(){
		
	}
	
	@FXML
	 private void MostrarInicio() {
	        //llamado desde el botón que existe en la vista.
		 myController.setScreen(Framework.screen1ID);
	 }
	public void enviarProyecto(javafx.scene.input.MouseEvent e,String text){
		
		//System.out.println("Texto en inicioControlador:  "+text);
		t=text;
		myController.unloadScreen(Framework.screen2ID);
		myController.loadScreen(Framework.screen2ID, Framework.screen2File);
		myController.setScreen(Framework.screen2ID);
		
	}


	public void agregaProyecto(){
		
		//RowConstraints file = null;
		//Se crea una conexión a la base de datos
		Consultas c=new Consultas();
		//Los datos obtenidos se almacenan en una lista
        ArrayList<String> lista=new ArrayList<String>();
        lista.addAll(c.carpeta());
       
        //tamaño de la lista
        int tam = lista.size();
        //Mientras no se supere ese tamaño
		while(k<tam){  
			//Crea una columna y elementos
			pictureRegion= new VBox(); 
			
		    Text texto = new Text();
		    
			//Crea una imagen
			final ImageView imv = new ImageView();
	        final Image image2 = new Image(Framework.class.getResourceAsStream("imagenes/carpeta.png"));
	        imv.setImage(image2);
	        imv.setFitWidth(160);
	        imv.setFitHeight(180);
	        
	        pictureRegion.getChildren().add(imv);
	        
	        //Crea el texto correspondiente a la carpeta
	        texto.setText(lista.get(k));
	    	
	    	//Se agrega el texto
	        pictureRegion.getChildren().add(texto);
	        Text temp=(Text)pictureRegion.getChildren().get(1);
	        pictureRegion.setOnMouseClicked(e-> enviarProyecto(e,temp.getText()));
	       
	        //Se posiciona el VBox
	        pictureRegion.setAlignment(Pos.CENTER);
	        pictureRegion.setMinSize(260, 230);
	        
	        //Se agrega VBox al grid en columna i fila j
	        
	        grid.add(pictureRegion, i, j);
	        
	        i++;
	        //Si ya son 4 columnas
	        if(i==MAX)
	        {
	        	
	        	j++;
	        	i=0;
	        }
	        k++;
		}
		
	}
	
}