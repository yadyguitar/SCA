package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class InicioControlador  implements Initializable,ControladorVentanas{

	//Variables de la clase-------------------------------------------------------------------------
	
	ScreensController myController; 
	static public String textonuevo;
	static public String ruta;
	public static int  EntraIncial=0;    
	static public String t; 	//Sirve para almacenar el nombre del proyecto cuando es seleccionado
	static public Integer id; 	//Almacena el id del proyecto seleccionado
	private int MAX =5;			//Es las columnas que contiene el grid
	private int i = 0;			//para moverse por las columnas del grid
	private int j = 0;			//para moverse por las filas del grid
	private int k=0;			//Un contador para recorrer las carpetas totales
	int Exportar = 0;
	VBox pictureRegion;			
	//Variables del fxml---------------------------------------------------------------------------
	@FXML GridPane grid;
	@FXML ScrollPane scroll;
	@FXML Label inicio;
	//Funciï¿½n inicial------------------------------------------------------------------------------
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		agregaProyecto();
		{
			
		}
	}
	@FXML
	private void cambiarNombreP() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("cambiarNombreProyecto.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
        principal.setScene(scene);
        principal.show();	
	}

	//Funciï¿½n que sirve para mandar una vista-----------------------------------------------------
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	//Funciones para el fxml----------------------------------------------------------------------
	@FXML
	 private void MostrarInicio() {	//Esta funciï¿½n sirve para mandar al inicio del proyecto
	        //llamado desde el botï¿½n que existe en la vista.
		//myController.unloadScreen(Framework.screen1ID);
		myController.loadScreen(Framework.screen1ID, Framework.screen1File);
		myController.setScreen(Framework.screen1ID);
	 }
	@FXML
	public void CerrarPrograma(){
		Framework.principal.close();
	}
	
	public void MostrarProyecto(){	//funciï¿½n que sirve para mandar a la vista de los proyectos
		System.out.println("Mostrando vista proyecto|Funcion MostrarProyecto()->InicioControlador");
		//myController.unloadScreen(Framework.screen2ID);
		myController.loadScreen(Framework.screen2ID, Framework.screen2File);
		myController.setScreen(Framework.screen2ID);
	}
	@FXML
	private void AbrirVentana() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("ProyectoNuevo.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
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
	@FXML
	private void EliminarProyecto() throws IOException{ //Esta funcion permite agregar un proyecto nuevo
		Stage principal = new Stage();
		principal.initStyle(StageStyle.UNDECORATED);
		Parent mainLayout = FXMLLoader.load(getClass().getResource("EliminarProyecto.fxml"));
        Scene scene = new Scene(mainLayout);
        //Agrega icono 
        Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		
        principal.setScene(scene);
        principal.show();	
	}
	/*Metodo que copia completamente una carpeta usando recursividad
	 *PARAMETRO1:FOrigen:Fichero o carpeta que se desea copiar
	 *PARAMETRO2:FDestino:Carpeta destino
	 */
	public static void Copiar(File FOrigen,File FDestino){
        //si el origen no es una carpeta
        if(!FOrigen.isDirectory()){
            //Llamo la funcion que lo copia
            CopiarFichero(FOrigen,FDestino);
        }else{
           //incremento el contador de entradas a esta funcion 
           EntraIncial++; 
           //si es el directorio padre(1 carpeta a copiar)
           if(EntraIncial==1){
                //Cambio la ruta destino por el nombre que tenia mas el nombre de
                //la carpeta padre
                FDestino=new File(FDestino.getAbsolutePath()+"/"+FOrigen.getName()); 
                //si la carpeta no existe la creo
                if(!FDestino.exists()){
                    FDestino.mkdir();
                }
           } 
           //obtengo el nombre de todos los archivos y carpetas que 
           //petenecen a este fichero(FOrigen)
           String []Rutas=FOrigen.list();
           //recorro uno a uno el contenido de la carpeta
           for(int i=0;i<Rutas.length;i++){
              //establesco el nombre del nuevo archivo origen 
              File FnueOri=new File(FOrigen.getAbsolutePath()+"/"+Rutas[i]);
              //establesco el nombre del nuevo archivo destino 
              File FnueDest= new File(FDestino.getAbsolutePath()+"/"+Rutas[i]);
              //si no existe el archivo destino lo creo
              if(FnueOri.isDirectory() && !FnueDest.exists()){
                  FnueDest.mkdir();                        
              }
              //uso recursividad y llamo a esta misma funcion has llegar
              //al ultimo elemento    
              Copiar(FnueOri,FnueDest); 
           }
        }
	        
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
	
	private void cambiarCursorI(ImageView imv){
		
		imv.getScene().setCursor(Cursor.HAND);
	}
	private void restaurarCursorI(ImageView imv){
		imv.getScene().setCursor(Cursor.DEFAULT);
	}
	private void cambiarCursorT(Text texto){
		
		texto.getScene().setCursor(Cursor.HAND);
	}
	private void restaurarCursorT(Text texto){
		texto.getScene().setCursor(Cursor.DEFAULT);
	}

	private void ClickImagen(ImageView imv, Text temp,Text texto){
		if(Exportar==0)
		{
			enviarProyecto(temp.getText());
		}
		else{
			ruta=(texto.getText());
			Exportar();
		}
	}
	
	@FXML
	private void Exp(){
		Exportar=1;
	}
	
	private void Exportar(){
		if(Exportar==1){
			DirectoryChooser explorador = new DirectoryChooser();
			explorador.setTitle("Exportar proyecto");
			File directorioDestino = explorador.showDialog(new Stage());
			if(directorioDestino!=null){
				String userDirectoryString = System.getProperty("user.home");
				File directorioActual = new File(userDirectoryString+"/SCA/Proyectos/"+ruta);
				//System.out.println(directorioDestino.getAbsolutePath());
				//System.out.print(directorioActual.getAbsolutePath());
		        Copiar(directorioActual,directorioDestino);
		        EntraIncial=0; 
			}
			 Exportar=0;
		}
		System.out.println(Exportar);
		
	}


	//Esta funciï¿½n sirve para mandar el nombre del proyecto y su id
		public void enviarProyecto(String text){

			Consultas c = new Consultas();
			t=text;
			id=c.id_proyecto(text);
			
			MostrarProyecto(); //muestra la vista de proyecto
		}	
	
	//Esta funciï¿½n permite crear las carpetas automaticamente de los proyectos que se encuentran en la base de datos y mostrarlas
	//aqui no estÃ¡ el onclick de aceptar de la ventana emergente, esta en controller proyectonuevoVista
	public void agregaProyecto(){
		Consultas c=new Consultas();	//Se crea una conexiï¿½n con la base de datos
        ArrayList<String> lista=new ArrayList<String>();
        lista.addAll(c.carpeta());		//Almacena el nombre de los proyectos en una lista
        int tam = lista.size();			//Obtengo el tamaï¿½o de la lista
        if(tam!=0){						//Si existe una lista
			while(k<tam){  				//Recorro la lista

				pictureRegion= new VBox();
			    

				//Crea una imagen
				final ImageView imv = new ImageView();
		        final Image image2 = new Image(Framework.class.getResourceAsStream("imagenes/carpeta2.png"));
		        imv.setImage(image2);
		        imv.setFitWidth(160);
		        imv.setFitHeight(180);
		        
		        //Agrego la imagen
		        pictureRegion.getChildren().add(imv);
		        
		        //Crea el texto correspondiente a la carpeta
		        Text texto = new Text();
		        texto.setStyle("-fx-font: NORMAL 18 Tahoma;");
		        texto.setText(lista.get(k));
		        texto.wrappingWidthProperty();
		        //importarEliminar(texto);
		        
		    	//Se agrega el texto
		        pictureRegion.getChildren().add(texto);

		        Text temp=(Text)pictureRegion.getChildren().get(1);	//Leo el nombre del proyecto  
		       // imv.setOnMouseClicked(e-> enviarProyecto(e,temp.getText())); //Lo guardo(?), se supone que aquÃ­ va a mostrar la siguiente vista de las imagenes del proyecto seleccionado
		        	        										//temp.getText(), obtengo el nombre del proyecto donde di click
		        imv.setOnMouseEntered((e->{cambiarCursorI(imv);}));
		        imv.setOnMouseExited((e->{restaurarCursorI(imv);}));
		        imv.setOnMouseClicked(e->{ClickImagen(imv,temp,texto);});
		        texto.setOnMouseEntered((e->{cambiarCursorT(texto);}));
		        texto.setOnMouseExited((e->{restaurarCursorT(texto);}));
				textonuevo = texto.getText();
		        texto.setOnMouseClicked(e->{try {
		        	
					cambiarNombreP();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}});

		        pictureRegion.setAlignment(Pos.CENTER);
		        pictureRegion.setMinSize(240, 210);
		       
		      		        
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
        else{
        	//Si no tengo proyectos,
        	Label sinProyecto = new Label("No existe ningún proyecto");
        	sinProyecto.setStyle("-fx-font-size: 25px; -fx-margin:200;");
        	sinProyecto.setPrefWidth(600);
        	sinProyecto.setAlignment(Pos.CENTER);
        	sinProyecto.setPrefHeight(30);
        	sinProyecto.setWrapText(true);
        	grid.add(sinProyecto,0,0);
        	grid.setAlignment(Pos.CENTER);
        }
        
        
	}
	
	@FXML
	public void tecla(KeyEvent e) throws IOException{
	
		if (e.getCode() == KeyCode.N && e.isControlDown()) { 
			AbrirVentana();
	    }
		if (e.getCode() == KeyCode.D && e.isControlDown()) { 
			EliminarProyecto();
	    }
		if (e.getCode() == KeyCode.E && e.isControlDown()) { 
			agregaProyecto();
			Exportar=1;
	    }
		if (e.getCode() == KeyCode.A && e.isControlDown()) { 
			Acercade();
			
	    }
		
	} 	
	
	//En el menubar, la opcion "nuevo", lanza esta funciÃ³n, desde el fxml de ProyectoNuevo
	
}