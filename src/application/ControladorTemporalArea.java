package application;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ControladorTemporalArea extends Application{

	@Override 
	public void start(Stage principal) throws Exception{
		Parent mainLayout = FXMLLoader.load(getClass().getResource("interfaz_area_yadi.fxml"));
        Scene scene = new Scene(mainLayout, 700, 700);
        principal.setTitle("area");
        principal.setScene(scene);
        principal.show();	

}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
