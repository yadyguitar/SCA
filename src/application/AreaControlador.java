package application;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;



public class AreaControlador implements Initializable, ControladorVentanas
{
	@FXML ScrollPane canvas_area;
	@FXML GridPane area_results;
	ScreensController myController; 
	
	Image img;
	int cont =0;
	Canvas canvas;
	GraphicsContext gc;
	String url;
	int band=0;
	List< List<Float> > poligonos;
	List<Float> coords;
	List<Label> areas=new ArrayList<Label>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		inicializaCanvas();
		subirImagen("application/imagenes/cristal.png");
		
		
		canvas.setOnMouseClicked(e->dibujaLinea(e));
		canvas.setOnMouseMoved(e->dibujaOnMove(e));
		area_results.setOnMouseClicked(e -> seleccion(e));
		canvas_area.setContent(canvas);
	}
	
	public void setScreenParent(ScreensController screenParent){ 
        myController = screenParent; 
     }
	@FXML
	 private void MostrarInicio() {
	        //llamado desde el bot�n que existe en la vista.
		 myController.setScreen(Framework.screen1ID);
	 }
	@FXML
	private void MostrarProyecto(){
		myController.setScreen(Framework.screen2ID);
	}
	@FXML
	private void MostrarImagen(){
		myController.setScreen(Framework.screen3ID);
	}
	
	
	 public void subirImagen(String url){
		 img=new Image(url);
		 gc.drawImage(img, 0, 0);
		 
	 }
	 public void inicializaCanvas(){
		 canvas= new Canvas(1200,1200);
		 gc=canvas.getGraphicsContext2D(); 
		 poligonos=new ArrayList<List<Float>>();
		 coords=new ArrayList<Float>();
	 }
	 
	 
	 public void dibujaOnMove(MouseEvent e){
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
		 gc.lineTo(e.getX(),e.getY());
		 gc.stroke();
		 coords.add((float)e.getX());
		 coords.add((float)e.getY());
		 //System.out.println(coords);
		 if (e.getClickCount()==2){
			// System.out.println("doble click");
			 //lo de abajo probable en la funci�n de actualizar
			 actualiza();
			 cierraArea();
			 //coords.clear();
			 band=0;
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
		 areas.add(new Label(""+res));
		 //System.out.println(areas);
		 agrega_campo();
	 }
	 
	 public void agrega_campo(){
		 
		 area_results.addRow(areas.size()-1,areas.get(areas.size()-1));
		System.out.println(area_results.getChildren());
		 
	 }
	 
	 public void seleccion(MouseEvent e){
		 
		 for (Node node: area_results.getChildren()){
			 
			 if(node.getBoundsInParent().contains(e.getX(), e.getY()))
                 System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
		 
		 
		 }
	 }
}
