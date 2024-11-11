import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class JavaFXTemplate extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcomeScreen.fxml"));
        primaryStage.setTitle("3 Card Poker");

        // Set the stage to full screen
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        scene.getStylesheets().add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Maximize window
        primaryStage.show();
    }
}
