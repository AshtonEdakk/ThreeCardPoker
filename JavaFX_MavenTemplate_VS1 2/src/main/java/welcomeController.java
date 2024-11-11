import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class welcomeController {
    @FXML
    private VBox root;

    @FXML
    private Label gameName;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton; 

    private static int index = 0;

    private String[] themes = {
            "/styles/lightTheme.css",
            "/styles/darkTheme.css",
            "/styles/neonTheme.css"
        };
    private static int numPlayers = 1;

    @FXML
    private void playPressed(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/gameplayScreen.fxml"));
        Parent root2 = loader.load();
        root2.getStylesheets().add(themes[index]);//set style
        welcomeController myctr = loader.getController();
        root.getScene().setRoot(root2);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleStyleSwitch(ActionEvent event) {
        index = (index + 1) % 3;
        root.getStylesheets().clear();
        root.getStylesheets().add(themes[index]);
    }

    @FXML
    private void onePlayer(ActionEvent event) {
        numPlayers = 1;
    }

    @FXML
    private void twoPlayers(ActionEvent event) {
        numPlayers = 2;
    }
}