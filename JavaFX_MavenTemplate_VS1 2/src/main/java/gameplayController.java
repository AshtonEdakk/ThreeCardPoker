import java.io.InputStream;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextInputDialog;

/**
 * Controller class for the Gameplay Screen.
 * Manages game logic, UI interactions, and state transitions.
 */
public class gameplayController {

    // FXML Components
    @FXML private MenuBar menuBar;

    @FXML private HBox dealerHandPane;

    @FXML private HBox playerOneHandPane;
    @FXML private Button placeBetsButtonPlayerOne;
    @FXML private Button playFoldButtonPlayerOne;

    @FXML private Label playerTwoLabel;
    @FXML private HBox playerTwoHandPane;
    @FXML private Button placeBetsButtonPlayerTwo;
    @FXML private Button playFoldButtonPlayerTwo;

    @FXML private TextArea gameInfoArea;

    @FXML private Button dealCardsButton;
    @FXML private Button revealDealerButton;
    @FXML private Button continueButton;
    @FXML private Button moneyP1;
    @FXML private Button moneyP2;
    private static int moneyAmountP1 = 1000;
    private static int moneyAmountP2 = 1000;
    // Game Logic Objects
    private Player playerOne;
    private Player playerTwo;
    private Dealer theDealer;

    private static boolean isOnePlayerMode = true; // Default to 1-player mode
    private static boolean playerOneFolded = false;
    private static boolean playerTwoFolded = false;
    private static boolean playerOneBetted = false;
    private static boolean playerTwoBetted = false;
    private static boolean playerOneFoldedOrPlayed = false;
    private static boolean playerTwoFoldedOrPlayed = false;
    
    private static int anteBetP1 = 0;
    private static int pairPlusBetP1 = 0;
    private static int anteBetP2 = 0;
    private static int pairPlusBetP2 = 0;
    
    // Stylesheet Themes
    private int styleIndex = 0;
    private final String[] themes = {
        "/styles/lightTheme.css",
        "/styles/darkTheme.css",
        "/styles/neonTheme.css"
    };

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize game objects
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();

        // Initialize UI
        updateGameInfo("Welcome to Three Card Poker! Place your bets to start.");
        resetButtons();
        moneyP1.setText("$" + moneyAmountP1);
        moneyP2.setText("$" + moneyAmountP2);

    }

    /**
     * Sets the game to one-player mode. Hides Player Two's UI components.
     */
    public void setOnePlayerMode() {
        isOnePlayerMode = true;
        playerTwoLabel.setVisible(false);
        playerTwoHandPane.setVisible(false);
        placeBetsButtonPlayerTwo.setVisible(false);
        playFoldButtonPlayerTwo.setVisible(false);
    }

    /**
     * Sets the game to two-player mode. Shows Player Two's UI components.
     */
    public void setTwoPlayerMode() {
        isOnePlayerMode = false;
        playerTwoLabel.setVisible(true);
        playerTwoHandPane.setVisible(true);
        placeBetsButtonPlayerTwo.setVisible(true);
        playFoldButtonPlayerTwo.setVisible(true);
        moneyP2.setVisible(true);
    }

    /**
     * Handles the action when Player One places bets.
     *
     * @param event The action event triggered by clicking the Place Bets button.
     */
    @FXML
    private void handlePlaceBetsPlayerOne(ActionEvent event) {
        try {
            // Prompt for ante bet
            TextInputDialog anteDialog = new TextInputDialog();
            anteDialog.setTitle("Place Ante Bet");
            anteDialog.setHeaderText("Enter Ante Bet for Player One");
            anteDialog.setContentText("Enter a value between $5 and $25:");

            anteBetP1 = anteDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if ante bet is valid
            if (anteBetP1 < 5 || anteBetP1 > 25) {
                showAlert("Invalid Bet", "Ante bet must be between $5 and $25.");
                return;
            }

            // Prompt for Pair Plus bet
            TextInputDialog pairPlusDialog = new TextInputDialog();
            pairPlusDialog.setTitle("Place Pair Plus Bet");
            pairPlusDialog.setHeaderText("Enter Pair Plus Bet for Player One");
            pairPlusDialog.setContentText("Enter a value between $0 and $25:");

            pairPlusBetP1 = pairPlusDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if Pair Plus bet is valid
            if (pairPlusBetP1 < 0 || pairPlusBetP1 > 25) {
                showAlert("Invalid Bet", "Pair Plus bet must be between $0 and $25.");
                return;
            }

            // Set bets if both are valid
            playerOne.setAnteBet(anteBetP1);
            playerOne.setPairPlusBet(pairPlusBetP1);
            updateGameInfo("Player One placed bets: Ante - $" + anteBetP1 + ", Pair Plus - $" + pairPlusBetP1);

            // Disable button after placing bets
            placeBetsButtonPlayerOne.setDisable(true);
            playerOneBetted = true;
            
            moneyAmountP1 -= (anteBetP1 + pairPlusBetP1);
            moneyP1.setText("$" + moneyAmountP1);
            if(isOnePlayerMode) {
            	dealCardsButton.setDisable(false);
            }
            else {
            	if(playerOneBetted && playerTwoBetted){
            		dealCardsButton.setDisable(false);            	}
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for the bets.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while placing bets for Player One.");
        }        
    }


    /**
     * Handles the action when Player One decides to play or fold.
     *
     * @param event The action event triggered by clicking the Play/Fold button.
     */
    @FXML
    private void handlePlayOrFoldPlayerOne(ActionEvent event) {
        // Implementation for Player One's play or fold decision
        try {
            if (!playerOneFolded) {
                boolean fold = getPlayOrFoldDecision("Player One");
                if (fold) {
                    playerOneFolded = true;
                    updateGameInfo("Player One has folded.");
                } else {
                    updateGameInfo("Player One chose to play.");
                    
                    moneyAmountP1 -= anteBetP1;
                    moneyP1.setText("$" + moneyAmountP1);
                    
                                        // Proceed with game logic, e.g., revealing hands or comparing with dealer
                }
                playerOneFoldedOrPlayed = true;
                
                playFoldButtonPlayerOne.setDisable(true);
                if (playerOneFoldedOrPlayed && (isOnePlayerMode || playerTwoFoldedOrPlayed)) {
                    revealDealerButton.setDisable(false);
                }

            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing Player One's decision.");
        }
    }

    /**
     * Handles the action when Player Two places bets.
     *
     * @param event The action event triggered by clicking the Place Bets button.
     */
    @FXML
    private void handlePlaceBetsPlayerTwo(ActionEvent event) {
    	try {
            // Prompt for ante bet
            TextInputDialog anteDialog = new TextInputDialog();
            anteDialog.setTitle("Place Ante Bet");
            anteDialog.setHeaderText("Enter Ante Bet for Player One");
            anteDialog.setContentText("Enter a value between $5 and $25:");

            anteBetP2 = anteDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if ante bet is valid
            if (anteBetP2 < 5 || anteBetP2 > 25) {
                showAlert("Invalid Bet", "Ante bet must be between $5 and $25.");
                return;
            }

            // Prompt for Pair Plus bet
            TextInputDialog pairPlusDialog = new TextInputDialog();
            pairPlusDialog.setTitle("Place Pair Plus Bet");
            pairPlusDialog.setHeaderText("Enter Pair Plus Bet for Player One");
            pairPlusDialog.setContentText("Enter a value between $0 and $25:");

            pairPlusBetP2 = pairPlusDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if Pair Plus bet is valid
            if (pairPlusBetP2 < 0 || pairPlusBetP2 > 25) {
                showAlert("Invalid Bet", "Pair Plus bet must be between $0 and $25.");
                return;
            }

            // Set bets if both are valid
            playerTwo.setAnteBet(anteBetP2);
            playerTwo.setPairPlusBet(pairPlusBetP2);
            updateGameInfo("Player Two placed bets: Ante - $" + anteBetP2 + ", Pair Plus - $" + pairPlusBetP2);

            // Disable button after placing bets
            placeBetsButtonPlayerTwo.setDisable(true);
            playerTwoBetted = true;
            
            moneyAmountP2 -= (anteBetP2 + pairPlusBetP2);
            moneyP2.setText("$" + moneyAmountP2);

            if(playerOneBetted && playerTwoBetted){
            		dealCardsButton.setDisable(false);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for the bets.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while placing bets for Player Two.");
        }        
    }

    /**
     * Handles the action when Player Two decides to play or fold.
     *
     * @param event The action event triggered by clicking the Play/Fold button.
     */
    @FXML
    private void handlePlayOrFoldPlayerTwo(ActionEvent event) {
        // Implementation for Player Two's play or fold decision
    	try {
            if (!playerTwoFolded) {
                boolean fold = getPlayOrFoldDecision("Player Two");
                if (fold) {
                    playerTwoFolded = true;
                    updateGameInfo("Player Two has folded.");
                } else {
                    updateGameInfo("Player Two chose to play.");
                    
                    moneyAmountP2 -= anteBetP2;
                    moneyP2.setText("$" + moneyAmountP2);
                    
                                        // Proceed with game logic, e.g., revealing hands or comparing with dealer
                }
                playerTwoFoldedOrPlayed = true;
                playFoldButtonPlayerTwo.setDisable(true);
                
                if(playerOneFoldedOrPlayed && playerTwoFoldedOrPlayed) {
                	revealDealerButton.setDisable(false);
                }

            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing Player Two's decision.");
        }
    }

    /**
     * Handles the action when the Deal Cards button is clicked.
     *
     * @param event The action event triggered by clicking the Deal Cards button.
     */
    @FXML
    private void handleDealCards(ActionEvent event) {
        // Implementation for dealing cards
        try {
            theDealer.deal(playerOne, isOnePlayerMode ? null : playerTwo);

            displayHand(dealerHandPane, theDealer.getHand(), true); // Hidden dealer hand initially
            displayHand(playerOneHandPane, playerOne.getHand(), false);
            if (!isOnePlayerMode) {
                displayHand(playerTwoHandPane, playerTwo.getHand(), false);
            }

            updateGameInfo("Cards have been dealt.");
            dealCardsButton.setDisable(true);
            playFoldButtonPlayerOne.setDisable(false);
            if (!isOnePlayerMode) {
            	playFoldButtonPlayerTwo.setDisable(false);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while dealing cards.");
        }
    }

    /**
     * Handles the action when the Reveal Dealer's Hand button is clicked.
     *
     * @param event The action event triggered by clicking the Reveal Dealer's Hand button.
     */
    @FXML
    private void handleRevealDealerHand(ActionEvent event) {
        // Implementation for revealing dealer's hand
        try {
            displayHand(dealerHandPane, theDealer.getHand(), false); // Show dealer's hand
            updateGameInfo("Dealer's hand has been revealed.");
            
            int playerOneWinner = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
            int playerTwoWinner = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
            if(!isOnePlayerMode) {
            	playerTwoWinner = ThreeCardLogic.compareHands(theDealer.getHand(), playerTwo.getHand());
            }
            
            if(!playerOneFolded) {
	            if(playerOneWinner == 2) {
	            	updateGameInfo("Player One won against the Dealer.");
	            	moneyAmountP1 += (4*anteBetP1);
	            	moneyP1.setText("$"+moneyAmountP1);
	            }
	            else if(playerOneWinner == 1) {
	            	updateGameInfo("Player One lost against the Dealer.");
	            }
	            else {
	            	updateGameInfo("Player One tied the Dealer. Ante and Play Wager returned to player");
	            	moneyAmountP1 += 2*anteBetP1;
	            	moneyP1.setText("$"+moneyAmountP1);
	            }
            }
            if(!playerTwoFolded && !isOnePlayerMode) {
            	if(playerTwoWinner == 2) {
	            	updateGameInfo("Player Two won against the Dealer.");
	            	moneyAmountP2 += (4*anteBetP1);
	            	moneyP2.setText("$"+moneyAmountP2);
	            }
	            else if(playerTwoWinner == 1) {
	            	updateGameInfo("Player Two lost against the Dealer.");
	            }
	            else {
	            	updateGameInfo("Player Two tied the Dealer. Ante returned to player");
	            	moneyAmountP2 += anteBetP2;
	            	moneyP2.setText("$"+moneyAmountP2);
	            }
            }
            
            //checks pairplus
            if(ThreeCardLogic.evalHand(playerOne.getHand()) != 0) {
            	updateGameInfo("Player One won the Pair Plus.");
            	moneyAmountP1 += pairPlusBetP1 + ThreeCardLogic.evalPPWinnings(playerOne.getHand(),pairPlusBetP1);
            	moneyP1.setText("$"+moneyAmountP1);
            }
            else {
            	updateGameInfo("Player One lost the Pair Plus.");
            }
            if(!isOnePlayerMode) {
	            if(ThreeCardLogic.evalHand(playerTwo.getHand()) != 0) {
	            	updateGameInfo("Player Two won the Pair Plus.");
	            	moneyAmountP2 += pairPlusBetP2 + ThreeCardLogic.evalPPWinnings(playerTwo.getHand(),pairPlusBetP2);
	            	moneyP2.setText("$"+moneyAmountP2);
	            }
	            else {
	            	updateGameInfo("Player Two lost the Pair Plus.");
	            }
            }
            
            revealDealerButton.setDisable(true);
            continueButton.setDisable(false);
            
        } catch (Exception e) {
            showAlert("Error", "An error occurred while revealing dealer's hand.");
        }
    }

    /**
     * Handles the action when the Continue button is clicked.
     *
     * @param event The action event triggered by clicking the Continue button.
     */
    @FXML
    private void handleContinue(ActionEvent event) {
        // Implementation for continuing to the next round
        try {
            resetRound();
            updateGameInfo("Continuing to the next round.");
            continueButton.setDisable(true);
            dealCardsButton.setDisable(true);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while continuing the game.");
        }
    }

    /**
     * Handles the action when the Fresh Start menu item is selected.
     *
     * @param event The action event triggered by selecting the Fresh Start menu item.
     */
    @FXML
    private void handleFreshStart(ActionEvent event) {
        // Implementation for starting a fresh game
        try {
            resetRound();
            updateGameInfo("Starting a fresh game.");
            moneyAmountP1 = 1000;
            moneyAmountP2 = 1000;
            moneyP1.setText("$"+moneyAmountP1);
            moneyP2.setText("$"+moneyAmountP2);

        } catch (Exception e) {
            showAlert("Error", "An error occurred while starting a fresh game.");
        }
    }

    /**
     * Handles the action when the New Look menu item is selected.
     *
     * @param event The action event triggered by selecting the New Look menu item.
     */
    @FXML
    private void handleNewLook(ActionEvent event) {
        // Implementation for changing the theme/look
        try {
            styleIndex = (styleIndex + 1) % themes.length;
            menuBar.getScene().getStylesheets().clear();
            menuBar.getScene().getStylesheets().add(getClass().getResource(themes[styleIndex]).toExternalForm());
            updateGameInfo("Theme changed to: " + themes[styleIndex].replace("/styles/", "").replace(".css", ""));
        } catch (Exception e) {
            showAlert("Error", "An error occurred while changing the theme.");
        }
    }

    /**
     * Handles the action when the Exit menu item is selected.
     *
     * @param event The action event triggered by selecting the Exit menu item.
     */
    @FXML
    private void handleExit(ActionEvent event) {
        // Implementation for exiting the game
        System.exit(0);
    }

    // -------------------- Helper Methods --------------------

    /**
     * Validates if the bet amount is within the allowed range.
     *
     * @param bet The bet amount to validate.
     * @return True if valid, false otherwise.
     */
    private boolean isValidBet(int bet) {
        return bet >= 5 && bet <= 25;
    }

    /**
     * Updates the game information area with a new message.
     *
     * @param message The message to append to the game information area.
     */
    private void updateGameInfo(String message) {
        gameInfoArea.appendText(message + "\n");
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Prompts the player to decide whether to play or fold.
     *
     * @param playerName The name of the player making the decision.
     * @return True if the player chooses to fold, false to play.
     */
    private boolean getPlayOrFoldDecision(String playerName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(playerName + " Decision");
        alert.setHeaderText(null);
        alert.setContentText(playerName + ", do you want to Play? Click OK to Play or Cancel to Fold.");

        ButtonType playButton = new ButtonType("Play");
        ButtonType foldButton = new ButtonType("Fold", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(playButton, foldButton);

        return alert.showAndWait().orElse(foldButton) == foldButton;
    }

    /**
     * Displays the player's or dealer's hand on the specified HBox.
     *
     * @param handPane  The HBox where the hand will be displayed.
     * @param hand      The list of Card objects to display.
     * @param hideCards If true, displays the back of the cards; otherwise, displays the actual cards.
     */
    private void displayHand(HBox handPane, ArrayList<Card> hand, boolean hideCards) {
        handPane.getChildren().clear();
        for (Card card : hand) {
            ImageView cardImageView = new ImageView();
            if (hideCards) {
                cardImageView.setImage(new Image(getClass().getResourceAsStream("/cards/back.png")));
            } else {
                String imagePath = getCardFileName(card);
                InputStream is = getClass().getResourceAsStream(imagePath);
                if (is != null) {
                    Image cardImage = new Image(is);
                    cardImageView.setImage(cardImage);
                } else {
                    showAlert("Image Not Found", "Card image not found: " + imagePath);
                    continue; // Skip adding this card image
                }
            }
            cardImageView.setFitWidth(100);
            cardImageView.setFitHeight(150);
            handPane.getChildren().add(cardImageView);
        }
    }

    /**
     * Constructs the image path for a given card.
     *
     * @param card The Card object.
     * @return The relative path to the card's image file.
     */
    private String getCardFileName(Card card) {
        String valueStr;
        switch (card.getValue()) {
            case 11:
                valueStr = "J";
                break;
            case 12:
                valueStr = "Q";
                break;
            case 13:
                valueStr = "K";
                break;
            case 14:
                valueStr = "A";
                break;
            default:
                valueStr = String.valueOf(card.getValue());
        }
        return "/cards/" + valueStr + card.getSuit() + ".png";
    }

    /**
     * Resets the game state for a new round.
     */
    private void resetRound() {
        // Reset players and dealer for a new round
        playerOne.reset();
        playerTwo.reset();
        theDealer.reset();

        // Clear all hand panes
        dealerHandPane.getChildren().clear();
        playerOneHandPane.getChildren().clear();
        playerTwoHandPane.getChildren().clear();

        // Clear game information area
        gameInfoArea.clear();

        // Update game information
        //updateGameInfo("New round started. Place your bets.");

        // Reset buttons
        resetButtons();
    }

    /**
     * Resets the state of buttons after a round.
     */
    private void resetButtons() {
        placeBetsButtonPlayerOne.setDisable(false);
        playFoldButtonPlayerOne.setDisable(true);
        if (!isOnePlayerMode) {
            placeBetsButtonPlayerTwo.setDisable(false);
            playFoldButtonPlayerTwo.setDisable(true);
        }
        dealCardsButton.setDisable(true);
        revealDealerButton.setDisable(true);
        continueButton.setDisable(true);
        
        playerOneFolded = false;
        playerTwoFolded = false;
        playerOneBetted = false;
        playerTwoBetted = false;
        playerOneFoldedOrPlayed = false;
        playerTwoFoldedOrPlayed = false;
        
        anteBetP1 = 0;
        pairPlusBetP1 = 0;
        anteBetP2 = 0;
        pairPlusBetP2 = 0;
    }

    // -------------------- End of Helper Methods --------------------
}
