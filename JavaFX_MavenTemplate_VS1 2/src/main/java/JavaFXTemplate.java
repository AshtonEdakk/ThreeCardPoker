import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//reads fxml file and draws interface
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcomeScreen.fxml"));
			primaryStage.setTitle("3 Card Poker");
			Scene s1 = new Scene(root, 500, 500);
			s1.getStyleSheets().add("/styles/lightMode.css");
			primaryStage.setScene(s1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
