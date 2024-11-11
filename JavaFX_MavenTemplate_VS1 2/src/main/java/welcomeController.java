import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.application.Platform;

public class welcomeController {

    @FXML
    private void playPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplayScreen.fxml"));
        Parent root = loader.load();
        // Optionally pass data to the controller
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("lightMode.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onePlayer(ActionEvent event) {
        // Implement logic for one player mode
    }

    @FXML
    private void twoPlayers(ActionEvent event) {
        // Implement logic for two players mode
    }
}
