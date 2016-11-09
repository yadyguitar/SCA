package application;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ProyectoControlador  implements Initializable,ControladorVentanas{

	private int MAX =4;
	private int i = 0;
	private int j = 0;
	ScreensController myController; 

	
	
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
	@FXML
	private void MostrarProyecto(){
		myController.setScreen(Framework.screen2ID);
	}
	 
	
	
	@FXML public void agregaImagen(){
		RowConstraints file = null;
		//agrega columnas   
	
		ColumnConstraints column1 = new ColumnConstraints(290);
		 final ImageView imv = new ImageView();
        final Image image2 = new Image(Framework.class.getResourceAsStream("imagenes/imagen.png"));
        imv.setImage(image2);
        imv.setFitWidth(120);
        imv.setFitHeight(118);
        
	    grid.getColumnConstraints().addAll(column1);
	
       
   
        
        VBox pictureRegion = new VBox(2); // spacing = 8     
        pictureRegion.setPrefHeight(225);
        pictureRegion.getChildren().add(imv);
        ComboBox<String> cb = new ComboBox<String>();
        cb.setPrefWidth(100);
        cb.setItems(FXCollections.observableArrayList("10", "20","30","40","50","60","70","80","90","100","110","120","130","140","150","160","170","180","190","200","210","220","230","240"));
        cb.setValue("10");   //Valor a pasar
        
        pictureRegion.getChildren().addAll(cb);
        //pictureRegion.getChildren().addAll(new Label("Cut"), new Button("Copy"), new Label("Paste"));
       
      
        
        pictureRegion.setAlignment(Pos.CENTER);
        grid.add(pictureRegion,i, j);
        System.out.println("hola"+ i + j);
        i++;
        if(i==MAX)
        {
        	file = new  RowConstraints(225);
        	grid.getRowConstraints().addAll(file);
        	j++;
        	i=0;
        }
        
		
	}
}
