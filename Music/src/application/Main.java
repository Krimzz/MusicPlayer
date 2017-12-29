package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Main extends Application {
	public static Main current;
	private double xOffset = 0;
    private double yOffset = 0;
	@Override
	public void start(Stage stage) {
		current = this;
	    
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			
			//Movable window
			
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
           	public void handle(MouseEvent event) {
               xOffset = event.getSceneX();
               yOffset = event.getSceneY();
            }
            });
       
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               stage.setX(event.getScreenX() - xOffset);
               stage.setY(event.getScreenY() - yOffset);
            }
            });
            
            
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setOnCloseRequest(e -> Platform.exit());
			stage.setTitle("Music Player App");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
