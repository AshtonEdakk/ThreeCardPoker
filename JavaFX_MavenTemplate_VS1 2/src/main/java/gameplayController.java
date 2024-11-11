//controller for gameplay
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class gameplayController {

    // Game Objects
    private Player playerOne;
    private Player playerTwo;
    private Dealer theDealer;

    // Game State
    private enum GameState {
        PLACING_BETS,
        DEALING_CARDS,
        PLAYER_DECISION,
        REVEAL_DEALER,
        ROUND_END
    }
    private GameState currentState = GameState.PLACING_BETS;

    // Theme Management
    private int themeIndex = 0;
    private List<String> themes = new ArrayList<>();

    // FXML Components
    @FXML private MenuBar menuBar;

    @FXML private HBox dealerHandPane;
    @FXML private HBox playerOneHandPane;
    @FXML private HBox playerTwoHandPane;

    @FXML private TextField playerOneAnteBet;
    @FXML private TextField playerOnePairPlusBet;
    @FXML private Label playerOneWinnings;
    @FXML private Button placeBetsButtonPlayerOne;
    @FXML private Button playFoldButtonPlayerOne;

    @FXML private TextField playerTwoAnteBet;
    @FXML private TextField playerTwoPairPlusBet;
    @FXML private Label playerTwoWinnings;
    @FXML private Button placeBetsButtonPlayerTwo;
    @FXML private Button playFoldButtonPlayerTwo;

    @FXML private TextArea gameInfoArea;

    @FXML private Button dealCardsButton;
    @FXML private Button revealDealerButton;
    @FXML private Button continueButton;

    // Player decisions
    private boolean playerOneFolded = false;
    private boolean playerTwoFolded = false;
    private boolean playerOneDecisionMade = false;
    private boolean playerTwoDecisionMade = false;

    // Initialization
    @FXML
    public void initialize() {
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();

        // Initialize themes
        themes.add(getClass().getResource("/styles/lightMode.css").toExternalForm());
        themes.add(getClass().getResource("/styles/darkMode.css").toExternalForm());
        themes.add(getClass().getResource("/styles/neonMode.css").toExternalForm());

        updateGameInfo("Welcome to Three Card Poker! Place your bets to start.");

        // Initialize button states
        placeBetsButtonPlayerOne.setDisable(false);
        placeBetsButtonPlayerTwo.setDisable(false);
        dealCardsButton.setDisable(true);
        playFoldButtonPlayerOne.setDisable(true);
        playFoldButtonPlayerTwo.setDisable(true);
        revealDealerButton.setDisable(true);
        continueButton.setDisable(true);
    }

    // Event Handlers
    @FXML
    private void handlePlaceBetsPlayerOne(ActionEvent event) {
        if (currentState != GameState.PLACING_BETS) {
            showAlert("Action Not Allowed", "You cannot place bets at this time.");
            return;
        }
        try {
            int anteBet = Integer.parseInt(playerOneAnteBet.getText());
            int pairPlusBet = Integer.parseInt(playerOnePairPlusBet.getText());
            if (isValidBet(anteBet) && isValidBet(pairPlusBet)) {
                playerOne.anteBet = anteBet;
                playerOne.pairPlusBet = pairPlusBet;
                updateGameInfo("Player One has placed bets.");
                placeBetsButtonPlayerOne.setDisable(true);
                if (bothPlayersPlacedBets()) {
                    dealCardsButton.setDisable(false);
                }
            } else {
                showAlert("Invalid Bet", "Please enter bets between $5 and $25.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter numeric values for bets.");
        }
    }

    @FXML
    private void handlePlaceBetsPlayerTwo(ActionEvent event) {
        if (currentState != GameState.PLACING_BETS) {
            showAlert("Action Not Allowed", "You cannot place bets at this time.");
            return;
        }
        try {
            int anteBet = Integer.parseInt(playerTwoAnteBet.getText());
            int pairPlusBet = Integer.parseInt(playerTwoPairPlusBet.getText());
            if (isValidBet(anteBet) && isValidBet(pairPlusBet)) {
                playerTwo.anteBet = anteBet;
                playerTwo.pairPlusBet = pairPlusBet;
                updateGameInfo("Player Two has placed bets.");
                placeBetsButtonPlayerTwo.setDisable(true);
                if (bothPlayersPlacedBets()) {
                    dealCardsButton.setDisable(false);
                }
            } else {
                showAlert("Invalid Bet", "Please enter bets between $5 and $25.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter numeric values for bets.");
        }
    }

    @FXML
    private void handleDealCards(ActionEvent event) {
        if (currentState != GameState.PLACING_BETS) {
            showAlert("Action Not Allowed", "You cannot deal cards at this time.");
            return;
        }
        // Deal hands
        playerOne.hand = theDealer.dealHand();
        playerTwo.hand = theDealer.dealHand();
        theDealer.dealersHand = theDealer.dealHand();

        // Display hands
        displayHand(playerOneHandPane, playerOne.hand, false);
        displayHand(playerTwoHandPane, playerTwo.hand, false);
        displayHand(dealerHandPane, theDealer.dealersHand, true); // Hide dealer's cards

        currentState = GameState.PLAYER_DECISION;
        updateGameInfo("Cards have been dealt. Players decide to Play or Fold.");

        // Enable Play/Fold buttons
        playFoldButtonPlayerOne.setDisable(false);
        playFoldButtonPlayerTwo.setDisable(false);
        dealCardsButton.setDisable(true);
    }

    @FXML
    private void handlePlayOrFoldPlayerOne(ActionEvent event) {
        if (currentState != GameState.PLAYER_DECISION) {
            showAlert("Action Not Allowed", "You cannot decide to Play or Fold at this time.");
            return;
        }
        // Prompt the player to choose to Play or Fold
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Player One Decision");
        alert.setHeaderText("Do you want to Play or Fold?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypePlay = new ButtonType("Play");
        ButtonType buttonTypeFold = new ButtonType("Fold", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypePlay, buttonTypeFold);

        ButtonType result = alert.showAndWait().orElse(buttonTypeFold);

        if (result == buttonTypePlay) {
            playerOne.playBet = playerOne.anteBet;
            updateGameInfo("Player One chooses to Play.");
            playerOneFolded = false;
        } else {
            updateGameInfo("Player One chooses to Fold.");
            playerOneFolded = true;
        }
        playerOneDecisionMade = true;
        playFoldButtonPlayerOne.setDisable(true);
        if (playerDecisionsMade()) {
            revealDealerButton.setDisable(false);
            currentState = GameState.REVEAL_DEALER;
        }
    }

    @FXML
    private void handlePlayOrFoldPlayerTwo(ActionEvent event) {
        if (currentState != GameState.PLAYER_DECISION) {
            showAlert("Action Not Allowed", "You cannot decide to Play or Fold at this time.");
            return;
        }
        // Prompt the player to choose to Play or Fold
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Player Two Decision");
        alert.setHeaderText("Do you want to Play or Fold?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypePlay = new ButtonType("Play");
        ButtonType buttonTypeFold = new ButtonType("Fold", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypePlay, buttonTypeFold);

        ButtonType result = alert.showAndWait().orElse(buttonTypeFold);

        if (result == buttonTypePlay) {
            playerTwo.playBet = playerTwo.anteBet;
            updateGameInfo("Player Two chooses to Play.");
            playerTwoFolded = false;
        } else {
            updateGameInfo("Player Two chooses to Fold.");
            playerTwoFolded = true;
        }
        playerTwoDecisionMade = true;
        playFoldButtonPlayerTwo.setDisable(true);
        if (playerDecisionsMade()) {
            revealDealerButton.setDisable(false);
            currentState = GameState.REVEAL_DEALER;
        }
    }

    @FXML
    private void handleRevealDealerHand(ActionEvent event) {
        if (currentState != GameState.REVEAL_DEALER) {
            showAlert("Action Not Allowed", "You cannot reveal the dealer's hand at this time.");
            return;
        }

        // Reveal dealer's hand
        displayHand(dealerHandPane, theDealer.dealersHand, false);

        // Evaluate hands and update winnings
        evaluateHands();

        currentState = GameState.ROUND_END;
        revealDealerButton.setDisable(true);
        continueButton.setDisable(false);
    }

    @FXML
    private void handleContinue(ActionEvent event) {
        if (currentState != GameState.ROUND_END) {
            showAlert("Action Not Allowed", "You cannot continue at this time.");
            return;
        }
        resetRound();
    }

    @FXML
    private void handleFreshStart(ActionEvent event) {
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();
        resetRound();
        playerOneWinnings.setText("$0");
        playerTwoWinnings.setText("$0");
        updateGameInfo("Game has been reset. Place your bets to start a new round.");
    }

    @FXML
    private void handleNewLook(ActionEvent event) {
        Scene scene = menuBar.getScene();
        themeIndex = (themeIndex + 1) % themes.size();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(themes.get(themeIndex));
    }

    @FXML
    private void handleExit(ActionEvent event) {
        // Confirm before exiting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Do you really want to exit?");
        alert.setContentText("Your current progress will be lost.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.close();
        }
    }

    // Helper Methods
    private void displayHand(HBox handPane, ArrayList<Card> hand, boolean hideCards) {
        handPane.getChildren().clear();
        for (Card card : hand) {
            ImageView cardView;
            if (hideCards) {
                cardView = new ImageView(new Image(getClass().getResourceAsStream("/cards/back.png")));
            } else {
                String imagePath = "/cards/" + card.getSuit() + card.getValue() + ".png";
                cardView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            }
            cardView.setFitWidth(75);
            cardView.setFitHeight(100);
            handPane.getChildren().add(cardView);
        }
    }

    private boolean isValidBet(int bet) {
        return bet >= 5 && bet <= 25;
    }

    private void updateGameInfo(String message) {
        gameInfoArea.appendText(message + "\n");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void evaluateHands() {
        int dealerHandValue = ThreeCardLogic.evalHand(theDealer.dealersHand);
        int dealerHighCard = ThreeCardLogic.getHighCardValue(theDealer.dealersHand);

        updateGameInfo("Dealer's hand is: " + handToString(theDealer.dealersHand));

        // Check if dealer has at least Queen high
        boolean dealerQualifies = dealerHighCard >= 12; // Queen is 12

        if (!dealerQualifies) {
            updateGameInfo("Dealer does not have at least Queen high; play wagers are returned.");
            if (!playerOneFolded) {
                playerOne.totalWinnings += playerOne.playBet;
                updateGameInfo("Player One's play wager is returned.");
            }
            if (!playerTwoFolded) {
                playerTwo.totalWinnings += playerTwo.playBet;
                updateGameInfo("Player Two's play wager is returned.");
            }
        }

        // Evaluate Player One
        if (!playerOneFolded) {
            // Evaluate Pair Plus
            int ppWinnings = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);
            if (ppWinnings > 0) {
                playerOne.totalWinnings += ppWinnings;
                updateGameInfo("Player One wins Pair Plus: $" + ppWinnings);
            } else {
                playerOne.totalWinnings -= playerOne.pairPlusBet;
                updateGameInfo("Player One loses Pair Plus.");
            }

            if (dealerQualifies) {
                int compareResult = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
                if (compareResult == 2) { // Player wins
                    int winnings = playerOne.anteBet + playerOne.playBet;
                    playerOne.totalWinnings += winnings * 2;
                    updateGameInfo("Player One beats dealer and wins: $" + winnings * 2);
                } else if (compareResult == 1) { // Dealer wins
                    playerOne.totalWinnings -= playerOne.anteBet + playerOne.playBet;
                    updateGameInfo("Player One loses to dealer.");
                } else { // Tie
                    updateGameInfo("Player One ties with dealer.");
                }
            } else {
                // Ante bet is pushed
                updateGameInfo("Player One's ante bet is pushed.");
            }
            playerOneWinnings.setText("$" + playerOne.totalWinnings);
        } else {
            // Player One folded, loses ante and pair plus bets
            playerOne.totalWinnings -= playerOne.anteBet + playerOne.pairPlusBet;
            updateGameInfo("Player One folded and loses ante and pair plus bets.");
            playerOneWinnings.setText("$" + playerOne.totalWinnings);
        }

        // Evaluate Player Two
        if (!playerTwoFolded) {
            // Evaluate Pair Plus
            int ppWinnings = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet);
            if (ppWinnings > 0) {
                playerTwo.totalWinnings += ppWinnings;
                updateGameInfo("Player Two wins Pair Plus: $" + ppWinnings);
            } else {
                playerTwo.totalWinnings -= playerTwo.pairPlusBet;
                updateGameInfo("Player Two loses Pair Plus.");
            }

            if (dealerQualifies) {
                int compareResult = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);
                if (compareResult == 2) { // Player wins
                    int winnings = playerTwo.anteBet + playerTwo.playBet;
                    playerTwo.totalWinnings += winnings * 2;
                    updateGameInfo("Player Two beats dealer and wins: $" + winnings * 2);
                } else if (compareResult == 1) { // Dealer wins
                    playerTwo.totalWinnings -= playerTwo.anteBet + playerTwo.playBet;
                    updateGameInfo("Player Two loses to dealer.");
                } else { // Tie
                    updateGameInfo("Player Two ties with dealer.");
                }
            } else {
                // Ante bet is pushed
                updateGameInfo("Player Two's ante bet is pushed.");
            }
            playerTwoWinnings.setText("$" + playerTwo.totalWinnings);
        } else {
            // Player Two folded, loses ante and pair plus bets
            playerTwo.totalWinnings -= playerTwo.anteBet + playerTwo.pairPlusBet;
            updateGameInfo("Player Two folded and loses ante and pair plus bets.");
            playerTwoWinnings.setText("$" + playerTwo.totalWinnings);
        }

        updateGameInfo("Round has ended. Press 'Continue' to start a new round.");
    }

    private void resetRound() {
        // Clear hands and bets
        playerOne.hand.clear();
        playerTwo.hand.clear();
        theDealer.dealersHand.clear();

        playerOneAnteBet.clear();
        playerOnePairPlusBet.clear();
        playerTwoAnteBet.clear();
        playerTwoPairPlusBet.clear();

        playerOneHandPane.getChildren().clear();
        playerTwoHandPane.getChildren().clear();
        dealerHandPane.getChildren().clear();

        playerOne.anteBet = 0;
        playerOne.pairPlusBet = 0;
        playerOne.playBet = 0;

        playerTwo.anteBet = 0;
        playerTwo.pairPlusBet = 0;
        playerTwo.playBet = 0;

        playerOneFolded = false;
        playerTwoFolded = false;
        playerOneDecisionMade = false;
        playerTwoDecisionMade = false;

        // Reset button states
        placeBetsButtonPlayerOne.setDisable(false);
        placeBetsButtonPlayerTwo.setDisable(false);
        dealCardsButton.setDisable(true);
        playFoldButtonPlayerOne.setDisable(true);
        playFoldButtonPlayerTwo.setDisable(true);
        revealDealerButton.setDisable(true);
        continueButton.setDisable(true);

        currentState = GameState.PLACING_BETS;
    }

    private boolean bothPlayersPlacedBets() {
        return playerOne.anteBet > 0 && playerTwo.anteBet > 0;
    }

    private boolean playerDecisionsMade() {
        return playerOneDecisionMade && playerTwoDecisionMade;
    }

    private String handToString(ArrayList<Card> hand) {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            String cardValue;
            switch (card.getValue()) {
                case 11:
                    cardValue = "J";
                    break;
                case 12:
                    cardValue = "Q";
                    break;
                case 13:
                    cardValue = "K";
                    break;
                case 14:
                    cardValue = "A";
                    break;
                default:
                    cardValue = String.valueOf(card.getValue());
            }
            sb.append(cardValue).append(card.getSuit()).append(" ");
        }
        return sb.toString().trim();
    }
}
