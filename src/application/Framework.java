package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Framework extends Application {
    
	//Vistas que existen en el proyecto-----------------------------------------
    public static String screen1ID = "Inicio";
    public static String screen1File = "InicioVista.fxml";
    public static String screen2ID = "Proyectos";
    public static String screen2File = "ProyectoVista.fxml";
    public static String screen3ID = "Imagen";
    public static String screen3File = "AreaVista.fxml"; 
    public static Stage principal;
    @Override
    public void start(Stage primaryStage) {
        principal=primaryStage;
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(this.screen1ID, this.screen1File);
        mainContainer.setScreen(this.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        
        Scene scene = new Scene(root);
        principal.setScene(scene);
        
        principal.setTitle("Software Crystal Analysis");
		
		//icono parte superior ventana, y en parte inferior barra de tareas
		Image icon = new Image(getClass().getResourceAsStream("imagenes/icono.png"));
		principal.getIcons().add(icon);
		//principal.setMaximized(true);
		principal.setMinHeight(640);
		principal.setMinWidth(1215);
		principal.setMaxHeight(640);
		principal.setMaxWidth(1215);
        principal.show();
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
	