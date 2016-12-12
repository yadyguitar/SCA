package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class ProyectoControlador  implements Initializable,ControladorVentanas{

	//Variables de la clase-----------------------------------------------------------------
	static public Integer t2; //minuto de la imagen
	static public String t3; //nombre del proyecto
	static public String ProNuevo;
	InicioControlador ini= new InicioControlador(); //aquí accedarás al nombre del proyecto que se encuentra en otra vista static
	ScreensController myController; 
	int expI = 0;
	private int contador = 0;
	private int MAX =5;		//El m�ximo de columnas del grid
	private int i = 0;		//Sirve para recorrer las columnas
	private int j = 0;		//Sirve para recorrer las filas
	private int k=0;		//Sirve como contador para imprimir todas las im�genes del proyecto
	VBox pictureRegion;
	
	//Variables del fxml------------------------------------------------------------------------------
	@FXML Label textoProyecto;
	@FXML GridPane grid;
	@FXML Label inicio;
	//Funci�n inicializadora-------------------------------------------------------------------------
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
		textoProyecto.setText(" > "+ini.t);   //Guardo el texto del proyecto seleccionado
		inicio.setCursor(Cursor.CLOSED_HAND);
		textoProyecto.setCursor(Cursor.CLOSED_HAND);
		try {
			agregaImagen();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void cambiarNombreI(String t) throws IOException{ 
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("cambiarNombreImagen.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		ProNuevo = t;
        principal.setScene(scene);
        principal.show();	
	}
	//Funci�n para mandar vista---------------------------------------------------------------------
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	//Funciones para el fxml-----------------------------------------------------------------------
	@FXML
	 private void MostrarInicio() { //funci�n que sirve para mandar al inicio del proyecto
        //llamado desde el bot�n si ya existe la vista
		//myController.unloadScreen(Framework.screen1ID);
		myController.loadScreen(Framework.screen1ID, Framework.screen1File);
		myController.setScreen(Framework.screen1ID);
	
	 }

	@FXML
	private void MostrarProyecto(){	//funci�n que sirve para mandar a la vista de los proyectos
		//myController.unloadScreen(Framework.screen2ID);
		myController.loadScreen(Framework.screen2ID, Framework.screen2File);
		myController.setScreen(Framework.screen2ID);
	}
	@FXML
	public void CerrarPrograma(){
		Framework.principal.close();
	}
	@FXML
	private void Acercade() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("Acercade.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	 
	//Funci�n que muestra las im�genes que tiene el proyecto
	@FXML public void agregaImagen() throws FileNotFoundException{
		
		RowConstraints file = null;
        List<Integer> lista=new ArrayList<Integer>();
        //System.out.println("Texto en Proyecto:   "+ini.t);
       
        Consultas c = new Consultas();	//Se crea una conexi�n con la base de datos
        lista.addAll(c.imagen(ini.t));	//Se obtienen los minutos existentes
       
        int tam = lista.size();			//Obtengo la cantidad de im�genes que tiene el proyecto
        //Mientras no haya recorrido todas las im�genes, y al menos tenga una
        if(tam!=0){
			while(k<tam){  
				pictureRegion= new VBox(10); 		
				
				//Minuto de la imagen
				Label l = new Label();
		        l.setText("Minuto "+String.valueOf(lista.get(k)));
		        contador = lista.get(k);
		        //Ruta de la imagen
		        Consultas co = new Consultas();
		        String tx = new String(co.ruta_imagen(lista.get(k), ini.t));
		        
				ImageView imv = new ImageView();
		        imv.setFitWidth(160);
		        imv.setFitHeight(120);
		        imv.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,61,0.8), 5, 1, 0.5, 0.5); ");
		        InputStream is = new FileInputStream(tx);
		        Image image2 = new Image(is);
		        System.out.println(image2);
		        imv.setImage(image2);
		        
		        //Se agregan los elementos al grid
		        pictureRegion.setAlignment(Pos.CENTER);
		        pictureRegion.setMinSize(240, 210);
		        pictureRegion.getChildren().add(imv);
		        pictureRegion.getChildren().addAll(l);
		        l.setOnMouseClicked(e->{
					try {
						cambiarNombreI(l.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
		        
		        Integer min=lista.get(k);
		        //Y se guarda el minuto de la imagen si se selecciona
		        imv.setOnMouseClicked(e-> enviarImagen(e,min,ini.t));

		        //Se agrega VBox al grid en columna i fila j
		        grid.add(pictureRegion, i, j);
		        i++;
		        //Si ya son 4 columnas
		        if(i==MAX)
		        {
		        	j++;	//Cambio de fila
		        	i=0;
		        }
		        k++;
			}
			tam=0;
        }
        else{
        	//Si no tiene im�genes, se crea un texto indicando que no tiene
        	Label sinProyecto = new Label("Este proyecto no tiene im�genes");
        	sinProyecto.setStyle("-fx-font-size: 25px; -fx-margin:200;");
        	sinProyecto.setPrefWidth(600);
        	//sinProyecto.setAlignment(Pos.CENTER);
        	sinProyecto.setPrefHeight(30);
        	sinProyecto.setWrapText(true);
        	sinProyecto.setTranslateX(440);
        	sinProyecto.setTranslateY(270);
        	grid.add(sinProyecto,0,0);
        	
        }
        
			
	}
	//Funci�n que envia el minuto de la imagen seleccionada a la vista �rea
	public void enviarImagen(javafx.scene.input.MouseEvent e,Integer texto2, String texto3){
		
		//System.out.println("Texto en Area:  "+texto2);
		t2=texto2;
		t3=texto3;
		//myController.unloadScreen(Framework.screen3ID);
		myController.loadScreen(Framework.screen3ID, Framework.screen3File);
		myController.setScreen(Framework.screen3ID);
	}
	
	@FXML
	private void AbrirVentana() throws IOException{	//funci�n que crea las im�genes del proyecto
		
		FileChooser fc = new FileChooser();
		//fc.setInitialDirectory(new File("C:\\cristales"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Images", "*.png"));		
		List<File> selectedFiles = fc.showOpenMultipleDialog(null);
				
		if(selectedFiles!=null){
			for(int i=0;i<selectedFiles.size();i++){
				//System.out.println(selectedFiles.get(i).getAbsolutePath());  //ruta completa
				//System.out.println(selectedFiles.get(i).getParentFile()); //ruta sin nombre
				//System.out.println(selectedFiles.get(i).getName());  //Nombre
				contador = contador + 10;
				
				
				String userDirectoryString = System.getProperty("user.home");
				System.out.println(userDirectoryString);
				String name=LocalDateTime.now().toString();
				name = name.replaceAll(":", "-");
				String ruta = new String(userDirectoryString+"/SCA/Proyectos/"+ini.t+"/"+name+".png");
				Path copy_from_1 = Paths.get(""+selectedFiles.get(i).getParentFile(), selectedFiles.get(i).getName());
				//System.out.println(copy_from_1.getFileName().toString());
		        Path copy_to_1 = Paths.get(userDirectoryString+"/SCA/Proyectos/"+ ini.t, name+".png");
		        
			
		        try {
		          Files.copy(copy_from_1, copy_to_1, REPLACE_EXISTING, COPY_ATTRIBUTES,
		              NOFOLLOW_LINKS);
		        } catch (IOException e) {
		          System.err.println(e.getMessage());
		        }
		        Consultas c= new Consultas();
		        //System.out.println(ini.id);
		        
		        //Agrega las im�genes
		        ruta = ruta.replace("\\", "/");
		        System.out.println("La ruta" +ruta);
		        c.agregar_imagen(ini.id, contador, ruta);
		       
			}
			myController.unloadScreen(Framework.screen2ID);
			myController.loadScreen(Framework.screen2ID, Framework.screen2File);
			myController.setScreen(Framework.screen2ID);
			agregaImagen();
		}
		else{
			System.out.println("El archivo es inv�lido");
		}
		
	}
	@FXML
	private void GraficaM0() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("GraficaM0.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	@FXML
	private void GraficaM1() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("GraficaM1.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	@FXML
	private void GraficaM2() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("GraficaM2.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	@FXML
	private void GraficaM3() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("GraficaM3.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	
}
