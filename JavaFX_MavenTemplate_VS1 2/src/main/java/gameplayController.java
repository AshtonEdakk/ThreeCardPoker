//controller for gameplay
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    @FXML private TextField playerTwoAnteBet;
    @FXML private TextField playerTwoPairPlusBet;
    @FXML private Label playerTwoWinnings;

    @FXML private TextArea gameInfoArea;

    // Initialization
    @FXML
    public void initialize() {
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();

        // Initialize themes
        themes.add(getClass().getResource("lightMode.css").toExternalForm());
        themes.add(getClass().getResource("darkMode.css").toExternalForm());
        themes.add(getClass().getResource("neonMode.css").toExternalForm());

        updateGameInfo("Welcome to Three Card Poker! Place your bets to start.");
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
            } else {
                showAlert("Invalid Bet", "Please enter bets between $5 and $25.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter numeric values for bets.");
        }
    }

    @FXML
    private void handlePlaceBetsPlayerTwo(ActionEvent event) {
        // Similar implementation as handlePlaceBetsPlayerOne
    }

    @FXML
    private void handleDealCards(ActionEvent event) {
        if (currentState != GameState.PLACING_BETS) {
            showAlert("Action Not Allowed", "You cannot deal cards at this time.");
            return;
        }
        // Ensure both players have placed bets
        if (playerOne.anteBet == 0 || playerTwo.anteBet == 0) {
            showAlert("Bets Not Placed", "Both players must place their bets before dealing cards.");
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
    }

    @FXML
    private void handlePlayOrFoldPlayerOne(ActionEvent event) {
        if (currentState != GameState.PLAYER_DECISION) {
            showAlert("Action Not Allowed", "You cannot decide to Play or Fold at this time.");
            return;
        }
        // Implement logic for Player One to Play or Fold
        // For simplicity, assume Player One always plays
        playerOne.playBet = playerOne.anteBet;
        updateGameInfo("Player One chooses to Play.");
    }

    @FXML
    private void handlePlayOrFoldPlayerTwo(ActionEvent event) {
        // Similar implementation as handlePlayOrFoldPlayerOne
    }

    @FXML
    private void handleRevealDealerHand(ActionEvent event) {
        if (currentState != GameState.PLAYER_DECISION) {
            showAlert("Action Not Allowed", "You cannot reveal the dealer's hand at this time.");
            return;
        }

        // Reveal dealer's hand
        displayHand(dealerHandPane, theDealer.dealersHand, false);

        // Evaluate hands and update winnings
        evaluateHands();

        currentState = GameState.ROUND_END;
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
        // Implement logic to compare hands, calculate winnings, and update UI
        // Update total winnings for each player
        // Example:
        int dealerRank = ThreeCardLogic.evalHand(theDealer.dealersHand);

        // Evaluate Player One
        int playerOneRank = ThreeCardLogic.evalHand(playerOne.hand);
        int resultPlayerOne = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
        // Update winnings and game info based on the result

        // Similarly evaluate Player Two

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

        currentState = GameState.PLACING_BETS;
    }
}
