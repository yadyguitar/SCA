package application;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class ProyectoControlador  implements Initializable,ControladorVentanas{

ScreensController myController; 
static public Integer t2; //minuto de la imagen
static public String t3; //nombre del proyecto
	InicioControlador inicio = new InicioControlador();
	private int MAX =4;
	private int i = 0;
	private int j = 0;
	private int k=0;
	VBox pictureRegion;
	@FXML GridPane grid;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		agregaImagen();
		
	}
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	@FXML
	 private void MostrarInicio() {
	        //llamado desde el bot�n que existe en la vista.
		myController.unloadScreen(Framework.screen1ID);
		myController.loadScreen(Framework.screen1ID, Framework.screen1File);
		myController.setScreen(Framework.screen1ID);
	
	 }


	@FXML
	private void MostrarProyecto(){
		myController.unloadScreen(Framework.screen2ID);
		myController.loadScreen(Framework.screen2ID, Framework.screen2File);
		myController.setScreen(Framework.screen2ID);
		
	}
	 
	public void enviarImagen(javafx.scene.input.MouseEvent e,Integer texto2, String texto3){
		
		System.out.println("Texto en Area:  "+texto2);
		t2=texto2;
		t3=texto3;
		myController.unloadScreen(Framework.screen3ID);
		myController.loadScreen(Framework.screen3ID, Framework.screen3File);
		myController.setScreen(Framework.screen3ID);
		
	}

	@FXML public void agregaImagen(){
        
		RowConstraints file = null;
		//Se crea una conexi�n a la base de datos
		//Consultas c=new Consultas();
		//Los datos obtenidos se almacenan en una lista
        List<Integer> lista=new ArrayList<Integer>();
        InicioControlador ini= new InicioControlador();
        
        System.out.println("Texto en Proyecto:   "+ini.t);
        
        Consultas c = new Consultas();
        lista.addAll(c.imagen(ini.t));
       // System.out.println(lista);
        //tama�o de la lista
        int tam = lista.size();
        //Mientras no se supere ese tama�o
		while(k<tam){  
   
			pictureRegion= new VBox(10); 		
			//Crea una imagen
			final ImageView imv = new ImageView();
	        final Image image2 = new Image(Framework.class.getResourceAsStream("imagenes/imagen.png"));
	        imv.setImage(image2);
	        imv.setFitWidth(120);
	        imv.setFitHeight(118);
    
	   	 
	        //Se posiciona el VBox
	        pictureRegion.setAlignment(Pos.CENTER);
	        pictureRegion.setMinSize(260, 230);
	        pictureRegion.getChildren().add(imv);
	      //  Text temp=(Text)pictureRegion.getChildren().get(1);
	        //pictureRegion.setOnMouseClicked(e-> enviarArea(e,temp.getText()));
	        
	        ComboBox<String> cb = new ComboBox<String>();
	        cb.setPrefWidth(100);
	        cb.setItems(FXCollections.observableArrayList("Minuto 10", "Minuto 20","Minuto 30","Minuto 40","Minuto 50","Minuto 60","Minuto 70","Minuto 80","Minuto 90","Minuto 100","Minuto 110","Minuto 120","Minuto 130","Minuto 140","Minuto 150","Minuto 160","Minuto 170","Minuto 180","Minuto 190","Minuto 200","Minuto 210","Minuto 220","Minuto 230","Minuto 240"));
	        cb.setDisable(true);
	        cb.setOpacity(1.8);
	       
	        cb.setValue("Minuto " +String.valueOf(lista.get(k)));   //Valor a pasar
	       // texto.setText(lista.get(k));
	        pictureRegion.getChildren().addAll(cb);
	        Integer min=lista.get(k);
	        pictureRegion.setOnMouseClicked(e-> enviarImagen(e,min,ini.t));
	       
	        //Se agrega VBox al grid en columna i fila j
	        grid.add(pictureRegion, i, j);
	        i++;
	        //Si ya son 4 columnas
	        if(i==MAX)
	        {
	        	//Crea una fila
	        	//file = new  RowConstraints(230);
	        	//Y agregala al grid
	        	//grid.getRowConstraints().addAll(file);
	        	j++;
	        	i=0;
	        }
	        k++;
		}
		tam=0;
		
	}
	

}
