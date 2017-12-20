package application;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		String fxmlDocPath = "C:\\Users\\Andrei\\eclipse-workspace\\Music\\src\\MusicPlayer.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane root = (AnchorPane) loader.load(fxmlStream);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Music Player");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
