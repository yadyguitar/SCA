package application;


import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;

public class InicioControlador  implements Initializable,ControladorVentanas{

	ScreensController myController; 
	
	private int MAX =4;
	private int i = 0;
	private int j = 0;
	@FXML GridPane grid;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	@FXML
	 private void MostrarInicio() {
	        //llamado desde el botón que existe en la vista.
		 myController.setScreen(Framework.screen1ID);
	 }
	
	


	@FXML public void agregaProyecto(){
		RowConstraints file = null;
		//agrega columnas   
	
		ColumnConstraints column1 = new ColumnConstraints(290);
		 final ImageView imv = new ImageView();
        final Image image2 = new Image(Framework.class.getResourceAsStream("imagenes/carpeta.png"));
        imv.setImage(image2);
        imv.setFitWidth(160);
        imv.setFitHeight(180);
       
        
	    grid.getColumnConstraints().addAll(column1);
		
	    VBox pictureRegion = new VBox(2); // spacing = 8     
        pictureRegion.setPrefHeight(225);
        pictureRegion.getChildren().add(imv);
        Text texto = new Text("PROYECTO");
        pictureRegion.getChildren().add(texto);
        
        
        
        pictureRegion.setAlignment(Pos.CENTER);
        grid.add(pictureRegion, i, j);
        System.out.println("hola"+ i + j);
        i++;
        if(i==MAX)
        {
        	file = new  RowConstraints(230);
        	grid.getRowConstraints().addAll(file);
        	j++;
        	i=0;
        }
        
		
	}
}
