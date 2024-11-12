import java.io.InputStream;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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

<<<<<<< HEAD
=======
    private static int moneyAmountP1 = 1000;
    private static int moneyAmountP2 = 1000;

>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
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
<<<<<<< HEAD
    private static int moneyAmountP1 = 1000;
    private static int moneyAmountP2 = 1000;
=======

>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
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
        moneyP2.setVisible(false);
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
            
            //gets ante value
            anteBetP1 = anteDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if ante bet is valid
            if(moneyAmountP1 - anteBetP1 < 0) {
            	showAlert("Invalid Bet", "Youre broke lol.");
            	return;
            }
            if (anteBetP1 < 5 || anteBetP1 > 25) {
                showAlert("Invalid Bet", "Ante bet must be between $5 and $25.");
                return;
            }

            // Prompt for Pair Plus bet
            TextInputDialog pairPlusDialog = new TextInputDialog();
            pairPlusDialog.setTitle("Place Pair Plus Bet");
            pairPlusDialog.setHeaderText("Enter Pair Plus Bet for Player One");
            pairPlusDialog.setContentText("Enter a value between $0 and $25:");
            
            //get pairplus bet value
            pairPlusBetP1 = pairPlusDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if Pair Plus bet is valid
            if(moneyAmountP1 - (anteBetP1+pairPlusBetP1) < 0) {
            	showAlert("Invalid Bet", "Youre broke lol.");
            	return;
            }
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
<<<<<<< HEAD
            
            //places the bet
            moneyAmountP1 -= (anteBetP1 + pairPlusBetP1);
            moneyP1.setText("$" + moneyAmountP1);//reflects that in savings
            if (isOnePlayerMode) {//checks if we can move to the next step in the game
=======

            moneyAmountP1 -= (anteBetP1 + pairPlusBetP1);
            moneyP1.setText("$" + moneyAmountP1);
            if (isOnePlayerMode) {
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
                dealCardsButton.setDisable(false);
            } else {
                if (playerOneBetted && playerTwoBetted) {
                    dealCardsButton.setDisable(false);
                }
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
        try {
            if (!playerOneFolded) {//checks if p1 folded
                boolean fold = getPlayOrFoldDecision("Player One");
                if (fold) {
                    playerOneFolded = true;
                    updateGameInfo("Player One has folded.");//updates the game
                } else {
                    updateGameInfo("Player One chose to play.");

<<<<<<< HEAD
                    moneyAmountP1 -= anteBetP1;//adds the play wager
                    moneyP1.setText("$" + moneyAmountP1);
                }
                playerOneFoldedOrPlayed = true;
                if(moneyAmountP1 < 0) {
                	moneyAmountP1 += anteBetP1;
                	playerOneFolded = true;
                	updateGameInfo("Player 1 is broke lol. Automatically folds.");
                }

                playFoldButtonPlayerOne.setDisable(true);
                if (playerOneFoldedOrPlayed && (isOnePlayerMode || playerTwoFoldedOrPlayed)) {//moves to the next stage
                    revealDealerButton.setDisable(false);
                }
=======
                    moneyAmountP1 -= anteBetP1;
                    moneyP1.setText("$" + moneyAmountP1);
                }
                playerOneFoldedOrPlayed = true;

                playFoldButtonPlayerOne.setDisable(true);
                if (playerOneFoldedOrPlayed && (isOnePlayerMode || playerTwoFoldedOrPlayed)) {
                    revealDealerButton.setDisable(false);
                }
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
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
            anteDialog.setHeaderText("Enter Ante Bet for Player Two");
            anteDialog.setContentText("Enter a value between $5 and $25:");

            anteBetP2 = anteDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if ante bet is valid
            if(moneyAmountP2 - (anteBetP2) < 0) {
            	showAlert("Invalid Bet", "Youre broke lol.");
            	return;
            }
            if (anteBetP2 < 5 || anteBetP2 > 25) {
                showAlert("Invalid Bet", "Ante bet must be between $5 and $25.");
                return;
            }

            // Prompt for Pair Plus bet
            TextInputDialog pairPlusDialog = new TextInputDialog();
            pairPlusDialog.setTitle("Place Pair Plus Bet");
            pairPlusDialog.setHeaderText("Enter Pair Plus Bet for Player Two");
            pairPlusDialog.setContentText("Enter a value between $0 and $25:");

            pairPlusBetP2 = pairPlusDialog.showAndWait()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("No input provided"));

            // Check if Pair Plus bet is valid
            if(moneyAmountP2 - (anteBetP2+pairPlusBetP2) < 0) {
            	showAlert("Invalid Bet", "Youre broke lol.");
            	return;
            }
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
<<<<<<< HEAD

            moneyAmountP2 -= (anteBetP2 + pairPlusBetP2);//makes bet
            moneyP2.setText("$" + moneyAmountP2);//reflects previous line in visuals

            if (playerOneBetted && playerTwoBetted) {//checks if we cna move to the next stage 
=======

            moneyAmountP2 -= (anteBetP2 + pairPlusBetP2);
            moneyP2.setText("$" + moneyAmountP2);

            if (playerOneBetted && playerTwoBetted) {
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
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
        try {
<<<<<<< HEAD
            if (!playerTwoFolded) {//checks if folded
=======
            if (!playerTwoFolded) {
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
                boolean fold = getPlayOrFoldDecision("Player Two");
                if (fold) {
                    playerTwoFolded = true;
                    updateGameInfo("Player Two has folded.");
                } else {
                    updateGameInfo("Player Two chose to play.");

<<<<<<< HEAD
                    moneyAmountP2 -= anteBetP2;//withdraws play wager
                    moneyP2.setText("$" + moneyAmountP2);
                }
                playerTwoFoldedOrPlayed = true;
                
                if(moneyAmountP2 < 0) {
                	moneyAmountP2 += anteBetP2;
                	playerTwoFolded = true;
                	updateGameInfo("Player Two is broke lol. Automatically folds.");
                }
                
                playFoldButtonPlayerTwo.setDisable(true);

                if (playerOneFoldedOrPlayed && playerTwoFoldedOrPlayed) {//moves to next stage
=======
                    moneyAmountP2 -= anteBetP2;
                    moneyP2.setText("$" + moneyAmountP2);
                }
                playerTwoFoldedOrPlayed = true;
                playFoldButtonPlayerTwo.setDisable(true);

                if (playerOneFoldedOrPlayed && playerTwoFoldedOrPlayed) {
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
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
        try {
            theDealer.deal(playerOne, isOnePlayerMode ? null : playerTwo);

            // Display hands with animations
            displayHand(dealerHandPane, theDealer.getHand(), true); // Hidden dealer hand initially
            displayHand(playerOneHandPane, playerOne.getHand(), false);//displays p1
            if (!isOnePlayerMode) {
                displayHand(playerTwoHandPane, playerTwo.getHand(), false);//displays p2 if needed
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
        try {
            displayHand(dealerHandPane, theDealer.getHand(), false); // Show dealer's hand
            updateGameInfo("Dealer's hand has been revealed.");

            // Evaluate and compare hands
            evaluateHands();

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
        try {
            resetRound();
            updateGameInfo("Starting a fresh game.");
            moneyAmountP1 = 1000;//resets money
            moneyAmountP2 = 1000;
            moneyP1.setText("$" + moneyAmountP1);
            moneyP2.setText("$" + moneyAmountP2);

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
        try {
            styleIndex = (styleIndex + 1) % themes.length;//wraps around through the list
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
     * Displays the player's or dealer's hand on the specified HBox with delay and flip animation.
     *
     * @param handPane  The HBox where the hand will be displayed.
     * @param hand      The list of Card objects to display.
     * @param hideCards If true, displays the back of the cards; otherwise, displays the actual cards.
     */
    private void displayHand(HBox handPane, ArrayList<Card> hand, boolean hideCards) {
        handPane.getChildren().clear();
        Timeline timeline = new Timeline();

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            final int index = i; // Needed for use inside lambda
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 600), event -> {
                ImageView cardImageView = new ImageView();
                cardImageView.setFitWidth(100);
                cardImageView.setFitHeight(150);

                // Initially show back of the card
                cardImageView.setImage(new Image(getClass().getResourceAsStream("/cards/back.png")));

                // Add the card to the handPane
                handPane.getChildren().add(cardImageView);

                // Create flip animation: 0° -> 90°, switch image, 90° -> 0°
                RotateTransition flipToMid = new RotateTransition(Duration.millis(250), cardImageView);
                flipToMid.setAxis(Rotate.Y_AXIS);
                flipToMid.setFromAngle(0);
                flipToMid.setToAngle(90);
                flipToMid.setInterpolator(Interpolator.LINEAR);

                flipToMid.setOnFinished(e -> {
                    // After reaching 90 degrees, change the image
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
                        }
                    }
                });

                RotateTransition flipToEnd = new RotateTransition(Duration.millis(250), cardImageView);
                flipToEnd.setAxis(Rotate.Y_AXIS);
                flipToEnd.setFromAngle(90);
                flipToEnd.setToAngle(0);
                flipToEnd.setInterpolator(Interpolator.LINEAR);

                // Combine the two flip animations into a SequentialTransition
                SequentialTransition flipAnimation = new SequentialTransition(flipToMid, flipToEnd);

                flipAnimation.play();
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
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

    /**
     * Evaluates the hands and updates the game state accordingly.
     */
    private void evaluateHands() {
        // Evaluate and compare hands
        int dealerHandValue = ThreeCardLogic.evalHand(theDealer.getHand());

        if (!playerOneFolded) {
            int playerOneHandValue = ThreeCardLogic.evalHand(playerOne.getHand());
            int result = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
            processResultForPlayer("Player One", playerOne, anteBetP1, pairPlusBetP1, result, dealerHandValue, moneyP1);
        }

        if (!isOnePlayerMode && !playerTwoFolded) {
            int playerTwoHandValue = ThreeCardLogic.evalHand(playerTwo.getHand());
            int result = ThreeCardLogic.compareHands(theDealer.getHand(), playerTwo.getHand());
            processResultForPlayer("Player Two", playerTwo, anteBetP2, pairPlusBetP2, result, dealerHandValue, moneyP2);
        }
    }

    /**
     * Processes the game result for a player.
     */
    private void processResultForPlayer(String playerName, Player player, int anteBet, int pairPlusBet, int result, int dealerHandValue, Button moneyButton) {
        if (ThreeCardLogic.dealerQualifies(theDealer.getHand())) {
            if (result == 2) {
                updateGameInfo(playerName + " wins against the Dealer.");
                int winnings = anteBet * 2; // Ante bet and play bet win
                if (ThreeCardLogic.evalHand(player.getHand()) == 1) {
                    winnings += anteBet * 5; // Bonus for straight flush
                }
                addMoneyToPlayer(playerName, winnings, moneyButton);
            } else if (result == 1) {
                updateGameInfo(playerName + " loses to the Dealer.");
            } else {
                updateGameInfo(playerName + " ties with the Dealer. Bets are returned.");
                addMoneyToPlayer(playerName, anteBet * 2, moneyButton);
            }
        } else {
            updateGameInfo("Dealer does not qualify. " + playerName + " wins Ante bet.");
            addMoneyToPlayer(playerName, anteBet * 2, moneyButton);
        }

        // Process Pair Plus bet
        int ppWinnings = ThreeCardLogic.evalPPWinnings(player.getHand(), pairPlusBet);
        if (ppWinnings > 0) {
            updateGameInfo(playerName + " wins the Pair Plus bet.");
            addMoneyToPlayer(playerName, ppWinnings, moneyButton);
        } else {
            updateGameInfo(playerName + " loses the Pair Plus bet.");
        }
    }

    /**
     * Adds money to the player's total and updates the UI.
     */
    private void addMoneyToPlayer(String playerName, int amount, Button moneyButton) {
        if (playerName.equals("Player One")) {
            moneyAmountP1 += amount;
            moneyButton.setText("$" + moneyAmountP1);
        } else if (playerName.equals("Player Two")) {
            moneyAmountP2 += amount;
            moneyButton.setText("$" + moneyAmountP2);
        }
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 8cb9a18cd734c6be6d15fa3618858097ec88487a
