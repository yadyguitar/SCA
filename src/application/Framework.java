package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Framework extends Application {
    
    public static String screen1ID = "Inicio";
    public static String screen1File = "InicioVista.fxml";
    public static String screen2ID = "Proyectos";
    public static String screen2File = "ProyectoVista.fxml";
    public static String screen3ID = "Imagen";
    public static String screen3File = "AreaVista.fxml"; 
    
    @Override
    public void start(Stage primaryStage) {
        
        ScreensController mainContainer = new ScreensController();
        

        mainContainer.loadScreen(this.screen1ID, this.screen1File);
        mainContainer.loadScreen(this.screen2ID,this.screen2File);
        mainContainer.loadScreen(this.screen3ID, this.screen3File);
        mainContainer.setScreen(this.screen1ID);

        mainContainer.loadScreen(Framework.screen1ID, Framework.screen1File);
        mainContainer.loadScreen(Framework.screen2ID, Framework.screen2File);
        mainContainer.loadScreen(Framework.screen3ID, Framework.screen3File);
        mainContainer.setScreen(Framework.screen3ID);
        
  
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Software Crystal Analysis");
		
		//icono parte superior ventana, y en parte inferior barra de tareas
		Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		primaryStage.getIcons().add(icon);
		
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
	