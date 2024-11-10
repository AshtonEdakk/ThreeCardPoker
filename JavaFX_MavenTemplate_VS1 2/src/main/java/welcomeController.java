//controller for the welcome screen
import java.io.IOException;

public class welcomeController implements Initializable{
	
	@FXML
	private VBox root;
	
	@FXML
	private BorderPane root2;
	
	@FXML
	private TextField textField;
	
	@FXML
	private TextField putText;
	
	
	public void playPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResources("/FXML/gameplayScreen.fxml"));
		Parent root2 = loader.load(); //load view into parent;
		gameplayController myctr = loader.getController();
		
		root2.getStylesheets().add("/styles/lightMode.css");
		root.getScene().setRoot(root2);
	}
	
}