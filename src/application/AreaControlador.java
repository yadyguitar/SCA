package application;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class AreaControlador implements Initializable, ControladorVentanas
{
	@FXML ScrollPane canvas_area;
	@FXML GridPane area_results;
	@FXML Label NombreProyecto =null;
	@FXML Label NombreImagen=null;
	@FXML Label inicio;
	@FXML Label nom;
	@FXML Label x;
	@FXML Label y;
	ScreensController myController; 
    ProyectoControlador pro= new ProyectoControlador();
    InicioControlador ini = new InicioControlador();
	static Image img;
	int cont =0;
	Canvas canvas;
	GraphicsContext gc;
	Integer band2 = 0;
	Integer ant = 0;
	String url2;
	int band=0,indice=-1;
	List< List<Float> > poligonos;
	List<Float> coords;
	List<Label> areas=new ArrayList<Label>();
	
	@Override
	//Funci�n que inicializa la vista-----------------------------------------------------------
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		inicializaCanvas();
		Consultas co = new Consultas();
		//System.out.println(pro.t2);
		url2=co.ruta_imagen(pro.t2, pro.t3); //obtengo la ruta pasando como parametros el min y el nombre de proyecto
		
		//System.out.println(url2);
		subirImagen(url2);
		
		inicio.setCursor(Cursor.CLOSED_HAND);
		NombreProyecto.setText(" > "+pro.t3);
		NombreProyecto.setCursor(Cursor.CLOSED_HAND);
		NombreImagen.setText(" > Minuto "+pro.t2);
		NombreImagen.setCursor(Cursor.CLOSED_HAND);
		
		canvas.setCursor(Cursor.CROSSHAIR);
		canvas.setOnMouseMoved(e->dibujaOnMove(e));
		canvas.setOnMouseClicked(e->dibujaLinea(e));
		area_results.setOnMouseClicked(e -> seleccion(e));
		canvas_area.setContent(canvas);
	}
	//Funci�n que cambia de vista---------------------------------------------------------------
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     }
	//Funci�n que cambia a la vista inicio-----------------------------------------------------
	@FXML
	 private void MostrarInicio() {
		
		 myController.setScreen(Framework.screen1ID);
	 }
	//Funci�n que cambia a la vista proyecto---------------------------------------------------
	@FXML
	private void MostrarProyecto(){
		myController.setScreen(Framework.screen2ID);
	}
	//Funci�n que cambia a la vista imagen----------------------------------------------------
	@FXML
	private void MostrarImagen(){
		myController.setScreen(Framework.screen3ID);
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
	//Funci�n que crea la imagen a seleccionar
	 public boolean subirImagen(String url){
		 try{
			 
		InputStream is = new FileInputStream(url); 
			 
		 img=new Image(is);
		 gc.drawImage(img, 0, 0);
		 }catch (Exception e) {
			System.out.println(e.getMessage());
			 return false;
		}
		return true;
	 }
	 
	 public void inicializaCanvas(){
		 canvas= new Canvas(1200,1200);
		 gc=canvas.getGraphicsContext2D(); 
		 poligonos=new ArrayList<List<Float>>();
		 coords=new ArrayList<Float>();
	 }
	 
	 
	 public void dibujaOnMove(MouseEvent e){
		 x.setText("x: " +String.valueOf(e.getX()));
		 y.setText("y: "+String.valueOf(e.getY()));
		 if(band==1){
			 actualiza();
			// System.out.println(e.getX() +", "+ e.getY());
			 if(coords.size()>=2){
				 gc.beginPath();
				 gc.lineTo(coords.get(coords.size()-2),coords.get(coords.size()-1));
				 gc.lineTo(e.getX(),e.getY());
				 gc.stroke();
			 }
		 }
	 }
	 
	 public void dibujaLinea(MouseEvent e){
		 band=1;
		 if(area_results.getChildren().size() !=0)
			 area_results.getChildren().get(ant).setStyle("-fx-background-color: transparent");
		 indice=-1;
		 gc.beginPath();
		 gc.lineTo(e.getX(),e.getY());
		 gc.stroke();
		 coords.add((float)e.getX());
		 coords.add((float)e.getY());
		 //System.out.println(coords);
		 if (e.getClickCount()==2){
			// System.out.println("doble click");
			 //lo de abajo probable en la funcion de actualizar
			 actualiza();
			 cierraArea();
			 //coords.clear();
			 band=0;
		 }
		 
	 }
	 
	 public void dibujaPoligonos(){
		 
		 int i,j;
		 List<Float> a;
		 for (i=0;i<poligonos.size();i++){
				gc.beginPath();
				a=poligonos.get(i);
				for (j=1;j<a.size();j+=2){
					gc.lineTo(a.get(j-1), a.get(j));
					gc.stroke();					
				}
		 }
		 gc.closePath();
	 }
	 
	 public void actualiza(){
		 gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		 gc.drawImage(img, 0, 0);	
		 dibujaPoligonos();
		 if (coords.size()>=4){
			 gc.beginPath();
			 for (int i=1;i<coords.size();i+=2){
				 gc.lineTo(coords.get(i-1), coords.get(i));
				 gc.stroke();		 
			 }
		 }
	 }
	 
	 public void calculaArea(){
		 List<Float> resultado= new ArrayList<Float>();
		 float temp=0f;
		 int i;
		 
		 for(i=0;i<coords.size()-3;i+=2){
			 resultado.add(coords.get(i)*coords.get(i+3)-coords.get(i+1)*coords.get(i+2));
		 }
		 for (i=0;i<resultado.size();i++){
			 temp+=resultado.get(i);
	 	 }
		 float res=Math.round( ((0.5)*Math.abs(temp)) );   
		 areas.add(new Label("A: "+res));
		 //System.out.println(areas);
		 agrega_campo();
	 }
	 
	 public void agrega_campo(){
		 area_results.addRow(areas.size()-1,areas.get(areas.size()-1));
		 area_results.getChildren().get(areas.size()-1).cursorProperty().set(Cursor.CLOSED_HAND);
		 
	 }
	 
	 public void seleccion(MouseEvent e){
		 actualiza();
		 int j;
		 		 
		 for (Node node: area_results.getChildren()){
			 
			 if(node.getBoundsInParent().contains(e.getX(), e.getY())){
				// System.out.println("x: " +coords.get(i-1)+ "y: "+coords.get(i) );
				 //if(band2 == 0){
					 indice=area_results.getRowIndex(node);
					 area_results.getChildren().get(indice).setStyle("-fx-background-color: #8fbc8f;");
					 if(area_results.getChildren().size()!=1)
					 area_results.getChildren().get(ant).setStyle("-fx-background-color: transparent");

					 ant = indice;
					// band2 = 1;
				 //}
				 //else{
					// indice=area_results.getRowIndex(node);
					// ant = indice;
					// band2 = 0;
				 //}
				 
				 List<Float> temp=poligonos.get(indice);
				 
				 gc.setStroke(Color.GREEN);
				 gc.setLineWidth(3);
				 gc.beginPath();
				 for (j=1;j<temp.size();j+=2){
					gc.lineTo(temp.get(j-1), temp.get(j));
					gc.stroke();					
				 }
				 gc.closePath();
				 gc.setStroke(Color.BLACK);
				 gc.setLineWidth(1); 
			 }
		 }
	 }
	 
	 
	 public void eliminar(){
		 if(indice!=-1){
		 //System.out.println("eliminaras el indice: "+indice+"?");
		 //pasar valores uno por uno en el grid
		 int tam=area_results.getChildren().size();
		 
		 for (int i=indice;i<tam-1;i++){
			 Label temp=(Label)area_results.getChildren().get(i);
			 temp.setText( ((Label)area_results.getChildren().get(i+1)).getText() );
		 }
		 area_results.getChildren().remove(tam-1);
		 /////////////////////////////////////////
		 areas.remove(indice);
		 poligonos.remove(indice);
		 
		 
		 indice=-1;
		 actualiza();

		 }
	 }
	 public void guardar(){
	 int id_imagen=new Consultas().id_imagen(url2);
	 System.out.println(url2);
	 if(saveToFile()){
		 if(new Consultas().agregar_poligonos(id_imagen, areas)){
				 //System.out.println("Se guarda correctamente (:");
			 }else{
				 //System.out.println("Oh oh... algo salió mal");
				 
			 }
		 }
	 }
	 
	 public boolean saveToFile() {
		 actualiza();
		 WritableImage wim = new WritableImage(1200, 1200);
		 gc.getCanvas().snapshot(null,wim);
		 File file = new File(url2);
		 try {
			 	BufferedImage bi =SwingFXUtils.fromFXImage((Image)wim, null); 
	            ImageIO.write(bi, "png", file);
	            return true;
	    } catch (Exception s) {
	        	System.out.println("Ups!... errorcito jejeje ");
	        	return false;
	    }
    }

	 public void cierraArea(){
		 coords.remove(coords.size()-1); coords.remove(coords.size()-1);
		 coords.add(coords.size(),coords.get(0)); coords.add(coords.size(),coords.get(1));
		 gc.lineTo(coords.get(0),coords.get(1));
		 gc.stroke();
		 poligonos.add(coords);
		 //System.out.println(poligonos);
		 
		 calculaArea();
		 coords=new ArrayList<Float>();  //libero el antiguo coords, y ahora poligono lo tiene
		 gc.beginPath();
	 }
	
	 //Si se pulsa la telcla ESCAPE-----------------------------------------------------------
	@FXML
	public void cancelar(KeyEvent e){
		
		if(e.getCode()==KeyCode.ESCAPE){
			coords.clear();
			actualiza();
		}
	} 	 
	@FXML
	public void tecla(KeyEvent e) throws IOException{
	
		if (e.getCode() == KeyCode.A && e.isControlDown()) { 
			Acercade();
			
	    }
		if (e.getCode() == KeyCode.S && e.isControlDown()) { 
			guardar();
			JOptionPane.showMessageDialog(null, "Se han guardado los cambios");

			
	    }
		if (e.getCode() == KeyCode.DELETE ) { 
			eliminar();
			
	    }
		
		
		
	} 	
}
