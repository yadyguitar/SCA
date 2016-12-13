package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
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
	static public Integer momento = -1;
	InicioControlador ini= new InicioControlador(); //aquÃ­ accedarÃ¡s al nombre del proyecto que se encuentra en otra vista static
	ScreensController myController; 
	int expI = 0;
	private int contador = 0;
	private int MAX =5;		//El mï¿½ximo de columnas del grid
	private int i = 0;		//Sirve para recorrer las columnas
	private int j = 0;		//Sirve para recorrer las filas
	private int k=0;		//Sirve como contador para imprimir todas las imï¿½genes del proyecto
	VBox pictureRegion;
	
	//Variables del fxml------------------------------------------------------------------------------
	@FXML Label textoProyecto;
	@FXML GridPane grid;
	@FXML Label inicio;
	//Funciï¿½n inicializadora-------------------------------------------------------------------------
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
		
		textoProyecto.setText(" > "+ini.t);   //Guardo el texto del proyecto seleccionado
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
	//Funciï¿½n para mandar vista---------------------------------------------------------------------
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	
	//Funciones para el fxml-----------------------------------------------------------------------
	@FXML
	 private void MostrarInicio() { //funciï¿½n que sirve para mandar al inicio del proyecto
        //llamado desde el botï¿½n si ya existe la vista
		//myController.unloadScreen(Framework.screen1ID);
		myController.loadScreen(Framework.screen1ID, Framework.screen1File);
		myController.setScreen(Framework.screen1ID);
	
	 }

	@FXML
	private void MostrarProyecto(){	//funciï¿½n que sirve para mandar a la vista de los proyectos
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
	private void cambiarCursorI(ImageView imv){
		
		imv.getScene().setCursor(Cursor.HAND);
	}
	private void restaurarCursorI(ImageView imv){
		imv.getScene().setCursor(Cursor.DEFAULT);
	}
	private void cambiarCursorT(Label texto){
		
		texto.getScene().setCursor(Cursor.HAND);
	}
	private void restaurarCursorT(Label texto){
		texto.getScene().setCursor(Cursor.DEFAULT);
	}
	private void ClickImagen(String nombre, Integer min,String ruta)
	{
		if(expI==0)
		{
			enviarImagen(min,nombre);
		}
		else{
			Exportar(ruta);
		}
	}
	private void Exportar(String ruta){
		DirectoryChooser explorador = new DirectoryChooser();
		explorador.setTitle("Exportar imagen");
		
		//String userDirectoryString = System.getProperty("user.home");
		File directorioActual = new File(ruta);
		String r = (explorador.showDialog(new Stage()) +"\\"+directorioActual.getName());
		File directorioDestino = new File(r);
		System.out.println(directorioDestino);
		System.out.println(directorioActual);
		//System.out.println(directorioDestino.getAbsolutePath());
		//System.out.print(directorioActual.getAbsolutePath());
        CopiarFichero(directorioActual,directorioDestino);
        expI=0;
		JOptionPane.showMessageDialog(null, "Operación exitosa");

		
	}
	public static void CopiarFichero(File FOrigen,File FDestino){
		try {
			//Si el archivo a copiar existe
			if(FOrigen.exists()){
			    String copiar="S";
			    //si el fichero destino ya existe
			    if(FDestino.exists()){
			      // System.out.println("El fichero ya existe, Desea Sobre Escribir:S/N ");
			       //copiar = ( new BufferedReader(new InputStreamReader(System.in))).readLine();
			    }
			    //si puedo copiar
			    if(copiar.toUpperCase().equals("S")){
			        //Flujo de lectura al fichero origen(que se va a copiar)            
			        FileInputStream LeeOrigen= new FileInputStream(FOrigen);
			        //Flujo de lectura al fichero destino(donde se va a copiar)
			        OutputStream Salida = new FileOutputStream(FDestino);
			        //separo un buffer de 1MB de lectura
			        byte[] buffer = new byte[1024];
			        int tamaño;
			        //leo el fichero a copiar cada 1MB
			        while ((tamaño = LeeOrigen.read(buffer)) > 0) {
			        	//Escribe el MB en el fichero destino
			        	Salida.write(buffer, 0, tamaño);
			        }
			        //System.out.println(FOrigen.getName()+" Copiado con Exito!!");
			        //cierra los flujos de lectura y escritura
			        Salida.close();
			        LeeOrigen.close();
			    }
	                
	        }else{//l fichero a copiar no existe                
	                //System.out.println("El fichero a copiar no existe..."+FOrigen.getAbsolutePath());
	        }
            
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        
        }
	    
	}
	@FXML 
	private void ExpI(){
		expI=1;
	}

	 
	//Funciï¿½n que muestra las imï¿½genes que tiene el proyecto
	@FXML public void agregaImagen() throws FileNotFoundException{
		
		RowConstraints file = null;
        List<Integer> lista=new ArrayList<Integer>();
        //System.out.println("Texto en Proyecto:   "+ini.t);
       
        Consultas c = new Consultas();	//Se crea una conexiï¿½n con la base de datos
        lista.addAll(c.imagen(ini.t));	//Se obtienen los minutos existentes
       
        int tam = lista.size();			//Obtengo la cantidad de imï¿½genes que tiene el proyecto
        //Mientras no haya recorrido todas las imï¿½genes, y al menos tenga una
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
		     
		        Integer min=lista.get(k);
		        //Y se guarda el minuto de la imagen si se selecciona

		        imv.setOnMouseEntered((e->{cambiarCursorI(imv);}));
		        imv.setOnMouseExited((e->{restaurarCursorI(imv);}));
		        imv.setOnMouseClicked(e->{ClickImagen(ini.t,min,tx);});
		        l.setStyle("-fx-font: NORMAL 18 Tahoma;");

		        l.setOnMouseEntered((e->{cambiarCursorT(l);}));
		        l.setOnMouseExited((e->{restaurarCursorT(l);}));
		        l.setOnMouseClicked(e->{try {
					cambiarNombreI(l.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}});
		        
		      
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
        	//Si no tiene imï¿½genes, se crea un texto indicando que no tiene
        	Label sinProyecto = new Label("Este proyecto no tiene imágenes");
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
	//Funciï¿½n que envia el minuto de la imagen seleccionada a la vista ï¿½rea
	public void enviarImagen(Integer texto2, String texto3){
		
		//System.out.println("Texto en Area:  "+texto2);
		t2=texto2;
		t3=texto3;
		//myController.unloadScreen(Framework.screen3ID);
		myController.loadScreen(Framework.screen3ID, Framework.screen3File);
		myController.setScreen(Framework.screen3ID);
	}
	
	@FXML
	private void AbrirVentana() throws IOException{	//funciï¿½n que crea las imï¿½genes del proyecto
		
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
		        
		        //Agrega las imï¿½genes
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
			System.out.println("El archivo es invï¿½lido");
		}
		
	}
	@FXML
	private void EliminarImagen() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("EliminarImagen.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	private void Grafica() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("Grafica.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
        principal.setScene(scene);
        principal.show();	
	}
	@FXML
	private void GraficaM0() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		momento = 0;
		Grafica();
	}
	@FXML
	private void GraficaM1() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		momento = 1;
		Grafica();
	}
	@FXML
	private void GraficaM2() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		momento = 2;
		Grafica();
		
	}
	@FXML
	private void GraficaM3() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		momento = 3;
		Grafica();
		
	}
	@FXML
	private void GraficaTodos() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		momento = 4;
		Grafica();
		
	}
	@FXML
	public void tecla(KeyEvent e) throws IOException{
	
		if (e.getCode() == KeyCode.N && e.isControlDown()) { 
			AbrirVentana();
	    }
		if (e.getCode() == KeyCode.D && e.isControlDown()) { 
			EliminarImagen();
	    }
		if (e.getCode() == KeyCode.E && e.isControlDown()) { 
			agregaImagen();
			expI=1;
	    }
		if (e.getCode() == KeyCode.A && e.isControlDown()) { 
			Acercade();
	    }
		if (e.getCode() == KeyCode.F1) { 
			GraficaM0();
			
	    }
		if (e.getCode() == KeyCode.F2) { 
			GraficaM1();
	    }
		if (e.getCode() == KeyCode.F3) { 
			GraficaM2();
	    }
		if (e.getCode() == KeyCode.F4) { 
			GraficaM3();	
	    }
		if (e.getCode() == KeyCode.M && e.isControlDown()) { 
			GraficaTodos();
	    }
	
		
	} 	
	
	
}
