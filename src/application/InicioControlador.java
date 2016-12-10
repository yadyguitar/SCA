package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class InicioControlador  implements Initializable,ControladorVentanas{

	//Variables de la clase-------------------------------------------------------------------------
	ScreensController myController; 
	static public String t; 	//Sirve para almacenar el nombre del proyecto cuando es seleccionado
	static public Integer id; 	//Almacena el id del proyecto seleccionado
	private int MAX =4;			//Es las columnas que contiene el grid
	private int i = 0;			//para moverse por las columnas del grid
	private int j = 0;			//para moverse por las filas del grid
	private int k=0;			//Un contador para recorrer las carpetas totales
	VBox pictureRegion;			
	//Variables del fxml---------------------------------------------------------------------------
	@FXML GridPane grid;
	@FXML ScrollPane scroll;
	@FXML Label inicio;
	//Funci�n inicial------------------------------------------------------------------------------
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		agregaProyecto();
		
		inicio.setCursor(Cursor.CLOSED_HAND);
	}
	//Funci�n que sirve para mandar una vista-----------------------------------------------------
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     } 
	//Funciones para el fxml----------------------------------------------------------------------
	@FXML
	 private void MostrarInicio() {	//Esta funci�n sirve para mandar al inicio del proyecto
	        //llamado desde el bot�n que existe en la vista.
		//myController.unloadScreen(Framework.screen1ID);
		myController.loadScreen(Framework.screen1ID, Framework.screen1File);
		myController.setScreen(Framework.screen1ID);
	 }
	public void MostrarProyecto(){	//funci�n que sirve para mandar a la vista de los proyectos
		System.out.println("Mostrando vista proyecto|Funcion MostrarProyecto()->InicioControlador");
		//myController.unloadScreen(Framework.screen2ID);
		myController.loadScreen(Framework.screen2ID, Framework.screen2File);
		myController.setScreen(Framework.screen2ID);
	}

	//Esta funci�n permite crear las carpetas automaticamente de los proyectos que se encuentran en la base de datos y mostrarlas
	//aqui no está el onclick de aceptar de la ventana emergente, esta en controller proyectonuevoVista
	public void agregaProyecto(){
		Consultas c=new Consultas();	//Se crea una conexi�n con la base de datos
        ArrayList<String> lista=new ArrayList<String>();
        lista.addAll(c.carpeta());		//Almacena el nombre de los proyectos en una lista
        int tam = lista.size();			//Obtengo el tama�o de la lista
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
		    	
		    	//Se agrega el texto
		        pictureRegion.getChildren().add(texto);

		        Text temp=(Text)pictureRegion.getChildren().get(1);	//Leo el nombre del proyecto  
		        pictureRegion.setOnMouseClicked(e-> enviarProyecto(e,temp.getText())); //Lo guardo(?), se supone que aquí va a mostrar la siguiente vista de las imagenes del proyecto seleccionado
		        	        										//temp.getText(), obtengo el nombre del proyecto donde di click
		        
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
        else{
        	//Si no tengo proyectos, indico que no hay
        	Label sinProyecto = new Label("No existe ningún proyeto");
        	sinProyecto.setStyle("-fx-font-size: 25px; -fx-margin:200;");
        	sinProyecto.setPrefWidth(600);
        	sinProyecto.setAlignment(Pos.CENTER);
        	sinProyecto.setPrefHeight(30);
        	sinProyecto.setWrapText(true);
        	grid.add(sinProyecto,0,0);
        }
        
        //-------------------------C�LCULO DE LOS MOMENTOS DE DISTRIBUCI�N-----------------------------
       /* Integer id_imagen = 102;
        ArrayList<Integer> areas=new ArrayList<Integer>();
        areas.addAll(new Consultas().momentos(id_imagen));
        if(areas.size()!=0){
	        ArrayList<Double> d=new ArrayList<Double>();
	        for(int k=0;k<areas.size(); k++)
	        {
	        	
	        	Double r = (Math.sqrt(areas.get(k))/0.005)/40.0;
	        	 d.add(k, r);
	        }
	        Collections.sort(d);
	        System.out.println(d);
	        ArrayList<Double> clase1=new ArrayList<Double>();
	        ArrayList<Double> clase2=new ArrayList<Double>();
	        Double inc = (d.get(d.size()-1)-d.get(0))/d.size();
	        System.out.println(inc);
	        clase1.add(0, d.get(0));
	        for(int q=1;q<d.size();q++)
	        {
	        	clase1.add(q, clase1.get(q-1)+inc);
	        	clase2.add(q-1,clase1.get(q-1)+inc);
		
	        }
	        clase2.add(clase2.size(), clase2.get(clase2.size()-1)+inc);
	        ArrayList<Double> di=new ArrayList<Double>();
	        ArrayList<Double> di2=new ArrayList<Double>(); 
	        ArrayList<Double> di3=new ArrayList<Double>(); 
	        ArrayList<Double> di4=new ArrayList<Double>(); 
	        Double SD1 = 0.0;
	        Double SD2 = 0.0;
	        Double SD3 = 0.0;
	        Double SD4 = 0.0;
	      
	       for(int q=0;q<clase2.size();q++)
	       {
	    	   di.add(q, (Math.sqrt((clase1.get(q)*clase2.get(q)))));
	    	   SD1=SD1 +di.get(q);
	    	   di2.add(q,Math.pow(di.get(q), 2));
	    	   SD2=SD2 +di2.get(q);
	    	   di3.add(q,Math.pow(di.get(q), 3));
	    	   SD3=SD3 +di3.get(q);
	    	   di4.add(q,Math.pow(di.get(q), 4));
	    	   SD4=SD4 +di4.get(q);
	       }
	       System.out.println(di);
	       System.out.println(di2);
	       System.out.println(di3);
	       System.out.println(di4);
	       System.out.println("D1"+SD1+" D2"+SD2+" D3"+SD3+" D4"+SD4);
	       //IMPORTAN----------------------------------
	       Double D10 = SD1 / di.size();
	       Double D20 = Math.sqrt((SD2/di.size()));
	       Double D30 = Math.pow((SD3/di.size()), 1.0/3.0);
	       Double D21 = SD2 /SD1;
	       Double D31 = Math.sqrt(SD3/SD1);
	       Double D32 = SD3/SD2;
	       Double D43 = SD4/SD3;
	       //---------------------------
	       System.out.println(D10);
	       System.out.println(D20);
	       System.out.println(D30);
	       System.out.println(D21);
	       System.out.println(D31);
	       System.out.println(D32);
	       System.out.println(D43);
	       ArrayList<Double> m0=new ArrayList<Double>(); 
	       ArrayList<Double> m1=new ArrayList<Double>(); 
	       ArrayList<Double> m2=new ArrayList<Double>(); 
	       ArrayList<Double> m3=new ArrayList<Double>(); 
	       Double kv=3.1416/6.0;
	       for(int q=0;q<areas.size();q++)
	       {
	    	   m3.add(q,(areas.get(q)/10.0)/1.588*Math.pow(1000, 3)/kv);
	    	   m2.add(q,m3.get(q)/D32);
	    	   m1.add(q, m2.get(q)/D21);
	    	   m0.add(q, m1.get(q)/D10);
	    	  
	       }
	       //IMPORTAN-----------------------
	       System.out.println(m3);
	       System.out.println(m2);
	       System.out.println(m1);
	       System.out.println(m0);
	       //IMPORTAN----------------------
        }
		*/
	}
	
	//En el menubar, la opcion "nuevo", lanza esta función, desde el fxml de ProyectoNuevo
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
	
	//Esta funci�n sirve para mandar el nombre del proyecto y su id
	public void enviarProyecto(javafx.scene.input.MouseEvent e,String text){

		Consultas c = new Consultas();
		t=text;
		id=c.id_proyecto(text);
		
		MostrarProyecto(); //muestra la vista de proyecto
	}	
}