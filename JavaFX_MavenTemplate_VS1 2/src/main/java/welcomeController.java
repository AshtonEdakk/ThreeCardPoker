import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

public class welcomeController {

    @FXML
    private VBox root;

    @FXML
    private Label gameName;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    private MenuButton numberOfPlayers;

    @FXML
    private MenuItem onePlayer;

    @FXML
    private MenuItem twoPlayers;

    @FXML
    private Button styleButton;

    private static int index = 0;

    private String[] themes = {
        "/styles/lightTheme.css",
        "/styles/darkTheme.css",
        "/styles/neonTheme.css"
    };

    private int selectedNumberOfPlayers = 1; // Default to 1 player

    @FXML
    private void initialize() {
        // Set initial style
        root.getStylesheets().add(getClass().getResource(themes[index]).toExternalForm());
    }

    @FXML
    private void playPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplayScreen.fxml"));
        Parent root2 = loader.load();
        gameplayController myctr = loader.getController();

        // Pass the selected number of players to the gameplayController
        if (selectedNumberOfPlayers == 1) {
            myctr.setOnePlayerMode();
        } else {
            myctr.setTwoPlayerMode();
        }

        // Apply the selected theme to the gameplay screen
        Scene scene = new Scene(root2);
        scene.getStylesheets().add(getClass().getResource(themes[index]).toExternalForm());
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleStyleSwitch(ActionEvent event) {
        index = (index + 1) % themes.length;
        root.getStylesheets().clear();
        root.getStylesheets().add(getClass().getResource(themes[index]).toExternalForm());
    }

    @FXML
    private void onePlayer(ActionEvent event) {
        selectedNumberOfPlayers = 1;
        numberOfPlayers.setText("1 Player");
    }

    @FXML
    private void twoPlayers(ActionEvent event) {
        selectedNumberOfPlayers = 2;
        numberOfPlayers.setText("2 Players");
    }
}
