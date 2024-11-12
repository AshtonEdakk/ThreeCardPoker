import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller class for the Welcome Screen.
 * Manages UI interactions and transitions to the gameplay screen.
 */
public class welcomeController {

    @FXML private RadioButton onePlayerRadio;
    @FXML private RadioButton twoPlayersRadio;
    @FXML private Button playButton;
    @FXML private Button exitButton;
    @FXML private Button styleButton;

    private int styleIndex = 0;
    private final String[] themes = {
        "/styles/lightTheme.css",
        "/styles/darkTheme.css",
        "/styles/neonTheme.css"
    };

    private int selectedNumberOfPlayers = 1; // Default to 1 player

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the ToggleGroup for RadioButtons
        ToggleGroup toggleGroup = new ToggleGroup();
        onePlayerRadio.setToggleGroup(toggleGroup);
        twoPlayersRadio.setToggleGroup(toggleGroup);
        onePlayerRadio.setSelected(true);
    }

    /**
     * Handles the action when the Play button is pressed.
     *
     * @param event The action event triggered by clicking the Play button.
     * @throws IOException If loading the gameplay FXML fails.
     */
    @FXML
    private void playPressed(ActionEvent event) throws IOException {
        // Determine number of players
        selectedNumberOfPlayers = onePlayerRadio.isSelected() ? 1 : 2;

        // Load gameplayScreen.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gameplayScreen.fxml"));
        Parent gameplayRoot = loader.load();

        // Get controller and pass number of players
        gameplayController gameplayController = loader.getController();
        if (selectedNumberOfPlayers == 1) {
            gameplayController.setOnePlayerMode();
        } else {
            gameplayController.setTwoPlayerMode();
        }

        // Apply selected theme
        Scene gameplayScene = new Scene(gameplayRoot, 1500, 750);
        gameplayScene.getStylesheets().add(getClass().getResource(themes[styleIndex]).toExternalForm());

        // Get current stage and set new scene
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.setScene(gameplayScene);
        stage.setTitle("Three Card Poker - Gameplay");
        stage.show();
    }

    /**
     * Handles the action when the Exit button is pressed.
     *
     * @param event The action event triggered by clicking the Exit button.
     */
    @FXML
    private void handleExit(ActionEvent event) {
        // Exit the application
        System.exit(0);
    }

    /**
     * Handles the action when the Change Theme button is pressed.
     *
     * @param event The action event triggered by clicking the Change Theme button.
     */
    @FXML
    private void handleStyleSwitch(ActionEvent event) {
        // Cycle through themes
        styleIndex = (styleIndex + 1) % themes.length;

        // Apply new theme
        Scene currentScene = playButton.getScene();
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass().getResource(themes[styleIndex]).toExternalForm());
    }
}
