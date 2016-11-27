package application;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


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
	 InicioControlador ini= new InicioControlador();
	 @FXML
	 Label textoProyecto = null;
	@FXML GridPane grid;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		textoProyecto.setText(ini.t);
		agregaImagen();
		
		
	}
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	@FXML
	 private void MostrarInicio() {
	        //llamado desde el botï¿½n que existe en la vista.
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
	@FXML
	private void AbrirVentana() throws IOException{
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\cristales"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Images", "*.png"));
				
		List<File> selectedFiles = fc.showOpenMultipleDialog(null);
				
		if(selectedFiles!=null){
			for(int i=0;i<selectedFiles.size();i++){
				//System.out.println(selectedFiles.get(i).getAbsolutePath());  //ruta completa
				//System.out.println(selectedFiles.get(i).getParentFile()); //ruta sin nombre
				//System.out.println(selectedFiles.get(i).getName());  //Nombre
				
				String ruta = new String("application/Proyectos/"+ini.t+"/"+selectedFiles.get(i).getName());
				
				//System.out.println(ruta);
				//mostrarImagen.setItems("1","3");
				//.add(i, selectedFiles.get(i).getAbsolutePath());
				//mostrarImagen.getItems().add(selectedFiles.get(i).getAbsolutePath());
				
				Path copy_from_1 = Paths.get(""+selectedFiles.get(i).getParentFile(), selectedFiles.get(i).getName());
				
		        Path copy_to_1 = Paths.get("C:/Users/Lalo/workspace/SCA/src/application/Proyectos/"+ ini.t, copy_from_1
		            .getFileName().toString());
		        try {
		          Files.copy(copy_from_1, copy_to_1, REPLACE_EXISTING, COPY_ATTRIBUTES,
		              NOFOLLOW_LINKS);
		        } catch (IOException e) {
		          System.err.println(e);
		        }
				
		        Consultas c= new Consultas();
		        System.out.println(ini.id);
		        c.agregar_imagen(ini.id, 10, ruta);

				
			}
		}
		else{
			System.out.println("El archivo es inválido");
		}
		
	}

	@FXML public void agregaImagen(){
        
		RowConstraints file = null;
		//Se crea una conexiï¿½n a la base de datos
		//Consultas c=new Consultas();
		//Los datos obtenidos se almacenan en una lista
        List<Integer> lista=new ArrayList<Integer>();
       
        
        System.out.println("Texto en Proyecto:   "+ini.t);
        
        Consultas c = new Consultas();
        lista.addAll(c.imagen(ini.t));
       // System.out.println(lista);
        //tamaï¿½o de la lista
        int tam = lista.size();
        //Mientras no se supere ese tamaï¿½o
        if(tam!=0){
			while(k<tam){  
	   
				pictureRegion= new VBox(10); 		
				
		        
		      //  Text temp=(Text)pictureRegion.getChildren().get(1);
		        //pictureRegion.setOnMouseClicked(e-> enviarArea(e,temp.getText()));
		        
		        //ComboBox<String> cb = new ComboBox<String>();
		        //cb.setPrefWidth(100);
		        //cb.setItems(FXCollections.observableArrayList("Minuto 10", "Minuto 20","Minuto 30","Minuto 40","Minuto 50","Minuto 60","Minuto 70","Minuto 80","Minuto 90","Minuto 100","Minuto 110","Minuto 120","Minuto 130","Minuto 140","Minuto 150","Minuto 160","Minuto 170","Minuto 180","Minuto 190","Minuto 200","Minuto 210","Minuto 220","Minuto 230","Minuto 240"));
		        //cb.setDisable(true);
		        //cb.setOpacity(1.8);
		       
		        
		        //cb.setValue("Minuto " +String.valueOf(lista.get(k)));   //Valor a pasar
		        Label l = new Label();
		        l.setText("Minuto "+String.valueOf(lista.get(k)));
		        Consultas co = new Consultas();
		        String tx = new String(co.ruta_imagen(lista.get(k), ini.t));
		      //Crea una imagen
				final ImageView imv = new ImageView();
			    
		        Image image2=new Image(tx);
		        imv.setImage(image2);
		        imv.setFitWidth(180);
		        imv.setFitHeight(178);

		        imv.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,61,0.8), 5, 1, 0.5, 0.5); ");
		   	 
		        //Se posiciona el VBox
		        pictureRegion.setAlignment(Pos.CENTER);
		        pictureRegion.setMinSize(260, 230);
		        pictureRegion.getChildren().add(imv);
		        
		        
		        
		        
		        // texto.setText(lista.get(k));
		        pictureRegion.getChildren().addAll(l);
		        Integer min=lista.get(k);
		        pictureRegion.setOnMouseClicked(e-> enviarImagen(e,min,ini.t));
		       
		       // Consultas co = new Consultas();
		        //String ruta = new String("imagenes\\cristales\\min"+min+".png");
		        //String origen = "origen.txt";
		       	//String destino = "destino.txt";
		      
		        
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
        else{
        	
        	Label sinImagen = new Label("En este proyecto no existe ninguna imagen");
        	sinImagen.setStyle("-fx-font-size: 25px; -fx-margin:200;");
        	//sinImagen.setEffect(new Glow());
        	 sinImagen.setPrefWidth(600);
        	 sinImagen.setAlignment(Pos.CENTER);
        	 sinImagen.setPrefHeight(30);
        	  sinImagen.setWrapText(true);
        	 
        	  grid.add(sinImagen,0,0);
        }
			
	}
	
	

}
