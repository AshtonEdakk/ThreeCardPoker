//controller for the welcome screen
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class welcomeController implements Initializable{
	
	@FXML
	private VBox root;
	
	@FXML
	private BorderPane root2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        
	}
	public void playPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gameplayScreen.fxml"));
		Parent root2 = loader.load(); //load view into parent;
		gameplayController myctr = loader.getController();
		root2.getStylesheets().add("/styles/lightMode.css");
		root.getScene().setRoot(root2);
	}
	
}