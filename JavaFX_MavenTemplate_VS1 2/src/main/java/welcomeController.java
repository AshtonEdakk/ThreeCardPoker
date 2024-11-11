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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gameplayScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onePlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gameplayScreen.fxml"));
        Parent root = loader.load();
        gameplayController controller = loader.getController();
        controller.setOnePlayerMode(); // Set to one player mode
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void twoPlayers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gameplayScreen.fxml"));
        Parent root = loader.load();
        gameplayController controller = loader.getController();
        controller.setTwoPlayerMode(); // Set to two player mode
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
