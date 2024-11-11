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

	private List<String> themes = Arrays.asList("/styles/lightTheme.css", "/styles/darkTheme.css", "/styles/neonTheme.css");

    @FXML
    private void playPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplayScreen.fxml"));
        Parent root2 = loader.load();
        root2.getStylesheets().add("/styles/style2.css");//set style
        gameplayController myctr = loader.getController();
        root.getScene().setRoot(root2);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handleStyleSwitch(ActionEvent event) {
    	private List<String> themes = Arrays.asList("/styles/lightTheme.css", "/styles/darkTheme.css", "/styles/neonTheme.css");

    	index = (index + 1) % themes.size();
        root.getStylesheets().clear();
        root.getStylesheets().add(themes.get(index))
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
