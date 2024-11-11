import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class JavaFXTemplate extends Application {

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the welcome screen
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcomeScreen.fxml"));
            primaryStage.setTitle("3 Card Poker");
            Scene scene = new Scene(root,1500,750);
            scene.getStylesheets().add("/styles/lightTheme.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
