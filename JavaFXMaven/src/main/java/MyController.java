//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.application.Platform;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class MyController {
//
//    // Game objects
//    private Player playerOne;
//    private Player playerTwo;
//    private Dealer theDealer;
//
//    private boolean isOnePlayerMode = false;
//    private boolean playerOneFolded = false;
//    private boolean playerTwoFolded = false;
//
//    // FXML Components for the Gameplay Screen
//    @FXML private HBox dealerHandPane;
//    @FXML private HBox playerOneHandPane;
//    @FXML private HBox playerTwoHandPane;
//    @FXML private TextField playerOneAnteBet;
//    @FXML private TextField playerOnePairPlusBet;
//    @FXML private Label playerOneWinnings;
//    @FXML private Button placeBetsButtonPlayerOne;
//    @FXML private Button playFoldButtonPlayerOne;
//    @FXML private TextField playerTwoAnteBet;
//    @FXML private TextField playerTwoPairPlusBet;
//    @FXML private Label playerTwoWinnings;
//    @FXML private Button placeBetsButtonPlayerTwo;
//    @FXML private Button playFoldButtonPlayerTwo;
//    @FXML private TextArea gameInfoArea;
//    @FXML private Button dealCardsButton;
//    @FXML private Button revealDealerButton;
//    @FXML private Button continueButton;
//    @FXML private MenuBar menuBar;
//
//    // FXML Components for the Welcome Screen
//    @FXML private VBox root;
//    @FXML private Label gameName;
//    @FXML private Button playButton;
//    @FXML private Button exitButton;
//
//    // Style switching variables
//    private static int index = 0;
//    private String[] themes = {
//        "/styles/lightTheme.css",
//        "/styles/darkTheme.css",
//        "/styles/neonTheme.css"
//    };
//    private static int numPlayers = 1;
//
//    // Initialization
//    @FXML
//    public void initialize() {
//    }
//
//    // Welcome Screen Handlers
//    @FXML
//    private void playPressed(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/gameplayScreen.fxml"));
//        Parent gameplayRoot = loader.load();
//        gameplayRoot.getStylesheets().add(themes[index]); // Set style
//        root.getScene().setRoot(gameplayRoot);
//    }
//
//    @FXML
//    private void handleExit(ActionEvent event) {
//        Platform.exit();
//    }
//
//    @FXML
//    private void handleStyleSwitch(ActionEvent event) {
//        index = (index + 1) % 3;
//        root.getStylesheets().clear();
//        root.getStylesheets().add(themes[index]);
//    }
//
//    @FXML
//    private void onePlayer(ActionEvent event) {
//        numPlayers = 1;
//        setOnePlayerMode();
//    }
//
//    @FXML
//    private void twoPlayers(ActionEvent event) {
//        numPlayers = 2;
//        setTwoPlayerMode();
//    }
//
//    // Gameplay Screen Methods
//    public void setOnePlayerMode() {
//        isOnePlayerMode = true;
//        playerTwoHandPane.setVisible(false);
//        placeBetsButtonPlayerTwo.setVisible(false);
//        playFoldButtonPlayerTwo.setVisible(false);
//        playerTwoAnteBet.setVisible(false);
//        playerTwoPairPlusBet.setVisible(false);
//        playerTwoWinnings.setVisible(false);
//        playerTwoAnteBet.setDisable(true);
//        playerTwoPairPlusBet.setDisable(true);
//        placeBetsButtonPlayerTwo.setDisable(true);
//        playFoldButtonPlayerTwo.setDisable(true);
//    }
//
//    public void setTwoPlayerMode() {
//        isOnePlayerMode = false;
//        playerTwoHandPane.setVisible(true);
//        placeBetsButtonPlayerTwo.setVisible(true);
//        playFoldButtonPlayerTwo.setVisible(true);
//        playerTwoAnteBet.setVisible(true);
//        playerTwoPairPlusBet.setVisible(true);
//        playerTwoWinnings.setVisible(true);
//        playerTwoAnteBet.setDisable(false);
//        playerTwoPairPlusBet.setDisable(false);
//        placeBetsButtonPlayerTwo.setDisable(false);
//    }
//
//    @FXML
//    private void handlePlaceBetsPlayerOne(ActionEvent event) {
//        try {
//            int anteBet = Integer.parseInt(playerOneAnteBet.getText());
//            int pairPlusBet = Integer.parseInt(playerOnePairPlusBet.getText());
//            if (isValidBet(anteBet) && isValidBet(pairPlusBet)) {
//                playerOne.anteBet = anteBet;
//                playerOne.pairPlusBet = pairPlusBet;
//                updateGameInfo("Player One has placed bets.");
//                placeBetsButtonPlayerOne.setDisable(true);
//                playerOneAnteBet.setDisable(true);
//                playerOnePairPlusBet.setDisable(true);
//                if (bothPlayersPlacedBets()) {
//                    dealCardsButton.setDisable(false);
//                }
//            } else {
//                showAlert("Invalid Bet", "Please enter bets between $5 and $25.");
//            }
//        } catch (NumberFormatException e) {
//            showAlert("Invalid Input", "Please enter numeric values for bets.");
//        }
//    }
//
//    @FXML
//    private void handlePlaceBetsPlayerTwo(ActionEvent event) {
//        try {
//            int anteBet = Integer.parseInt(playerTwoAnteBet.getText());
//            int pairPlusBet = Integer.parseInt(playerTwoPairPlusBet.getText());
//            if (isValidBet(anteBet) && isValidBet(pairPlusBet)) {
//                playerTwo.anteBet = anteBet;
//                playerTwo.pairPlusBet = pairPlusBet;
//                updateGameInfo("Player Two has placed bets.");
//                placeBetsButtonPlayerTwo.setDisable(true);
//                playerTwoAnteBet.setDisable(true);
//                playerTwoPairPlusBet.setDisable(true);
//                if (bothPlayersPlacedBets()) {
//                    dealCardsButton.setDisable(false);
//                }
//            } else {
//                showAlert("Invalid Bet", "Please enter bets between $5 and $25.");
//            }
//        } catch (NumberFormatException e) {
//            showAlert("Invalid Input", "Please enter numeric values for bets.");
//        }
//    }
//
//    @FXML
//    private void handleDealCards(ActionEvent event) {
//        if (theDealer.theDeck.size() <= 34) {
//            theDealer.theDeck.newDeck();
//            updateGameInfo("Deck reshuffled.");
//        }
//
//        playerOne.hand = theDealer.dealHand();
//        displayHand(playerOneHandPane, playerOne.hand, false);
//
//        if (!isOnePlayerMode) {
//            playerTwo.hand = theDealer.dealHand();
//            displayHand(playerTwoHandPane, playerTwo.hand, false);
//        }
//
//        theDealer.dealersHand = theDealer.dealHand();
//        displayHand(dealerHandPane, theDealer.dealersHand, true); // Hide dealer's hand
//
//        playFoldButtonPlayerOne.setDisable(false);
//        if (!isOnePlayerMode) {
//            playFoldButtonPlayerTwo.setDisable(false);
//        }
//
//        dealCardsButton.setDisable(true);
//    }
//
//    @FXML
//    private void handlePlayOrFoldPlayerOne(ActionEvent event) {
//        boolean play = getPlayOrFoldDecision("Player One");
//        playerOneFolded = !play;
//        if (play) {
//            playerOne.playBet = playerOne.anteBet;
//            updateGameInfo("Player One chooses to Play.");
//        } else {
//            updateGameInfo("Player One chooses to Fold.");
//        }
//        playFoldButtonPlayerOne.setDisable(true);
//        if (allDecisionsMade()) {
//            revealDealerButton.setDisable(false);
//        }
//    }
//
//    @FXML
//    private void handlePlayOrFoldPlayerTwo(ActionEvent event) {
//        boolean play = getPlayOrFoldDecision("Player Two");
//        playerTwoFolded = !play;
//        if (play) {
//            playerTwo.playBet = playerTwo.anteBet;
//            updateGameInfo("Player Two chooses to Play.");
//        } else {
//            updateGameInfo("Player Two chooses to Fold.");
//        }
//        playFoldButtonPlayerTwo.setDisable(true);
//        if (allDecisionsMade()) {
//            revealDealerButton.setDisable(false);
//        }
//    }
//
//    @FXML
//    private void handleRevealDealerHand(ActionEvent event) {
//        displayHand(dealerHandPane, theDealer.dealersHand, false);
//        evaluateHands();
//        revealDealerButton.setDisable(true);
//        continueButton.setDisable(false);
//    }
//
//    @FXML
//    private void handleContinue(ActionEvent event) {
//        resetRound();
//    }
//
//    @FXML
//    private void handleFreshStart(ActionEvent event) {
//        playerOne = new Player();
//        playerTwo = new Player();
//        theDealer = new Dealer();
//        resetRound();
//        playerOneWinnings.setText("$0");
//        playerTwoWinnings.setText("$0");
//        updateGameInfo("Game has been reset. Place your bets to start a new round.");
//    }
//
//    @FXML
//    private void handleNewLook(ActionEvent event) {
//        index = (index + 1) % themes.length;
//        Scene scene = menuBar.getScene();
//        scene.getStylesheets().clear();
//        scene.getStylesheets().add(getClass().getResource(themes[index]).toExternalForm());
//    }
//
//    private boolean isValidBet(int bet) {
//        return bet >= 5 && bet <= 25;
//    }
//
//    private boolean bothPlayersPlacedBets() {
//        if (isOnePlayerMode) {
//            return playerOne.anteBet > 0;
//        } else {
//            return playerOne.anteBet > 0 && playerTwo.anteBet > 0;
//        }
//    }
//
//    private boolean allDecisionsMade() {
//        if (isOnePlayerMode) {
//            return playFoldButtonPlayerOne.isDisabled();
//        } else {
//            return playFoldButtonPlayerOne.isDisabled() && playFoldButtonPlayerTwo.isDisabled();
//        }
//    }
//
//    private void displayHand(HBox handPane, ArrayList<Card> hand, boolean hideCards) {
//        handPane.getChildren().clear();
//        for (Card card : hand) {
//            ImageView cardView;
//            if (hideCards) {
//                cardView = new ImageView(new Image(getClass().getResourceAsStream("/cards/back.png")));
//            } else {
//                String cardFileName = getCardFileName(card);
//                cardView = new ImageView(new Image(getClass().getResourceAsStream("/cards/" + cardFileName)));
//            }
//            cardView.setFitWidth(75);
//            cardView.setFitHeight(100);
//            handPane.getChildren().add(cardView);
//        }
//    }
//
//    private String getCardFileName(Card card) {
//        String valueStr;
//        switch (card.getValue()) {
//            case 11: valueStr = "J"; break;
//            case 12: valueStr = "Q"; break;
//            case 13: valueStr = "K"; break;
//            case 14: valueStr = "A"; break;
//            default: valueStr = String.valueOf(card.getValue());
//        }
//        return valueStr + card.getSuit() + ".png";
//    }
//
//    private void updateGameInfo(String message) {
//        gameInfoArea.appendText(message + "\n");
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private boolean getPlayOrFoldDecision(String playerName) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle(playerName + " Decision");
//        alert.setHeaderText("Do you want to Play or Fold?");
//        alert.setContentText("Choose your option.");
//        ButtonType buttonTypePlay = new ButtonType("Play");
//        ButtonType buttonTypeFold = new ButtonType("Fold", ButtonBar.ButtonData.CANCEL_CLOSE);
//        alert.getButtonTypes().setAll(buttonTypePlay, buttonTypeFold);
//        ButtonType result = alert.showAndWait().orElse(buttonTypeFold);
//        return result == buttonTypePlay;
//    }
//
//    private void evaluateHands() {
//        boolean dealerQualifies = ThreeCardLogic.dealerQualifies(theDealer.dealersHand);
//        updateGameInfo("Dealer's hand: " + handToString(theDealer.dealersHand));
//
//        if (!playerOneFolded) {
//            evaluatePlayerHand(playerOne, "Player One", dealerQualifies);
//        } else {
//            updateGameInfo("Player One folded and loses ante and pair plus bets.");
//            playerOne.totalWinnings -= playerOne.anteBet + playerOne.pairPlusBet;
//            playerOneWinnings.setText("$" + playerOne.totalWinnings);
//        }
//
//        if (!isOnePlayerMode) {
//            if (!playerTwoFolded) {
//                evaluatePlayerHand(playerTwo, "Player Two", dealerQualifies);
//            } else {
//                updateGameInfo("Player Two folded and loses ante and pair plus bets.");
//                playerTwo.totalWinnings -= playerTwo.anteBet + playerTwo.pairPlusBet;
//                playerTwoWinnings.setText("$" + playerTwo.totalWinnings);
//            }
//        }
//    }
//
//    private void evaluatePlayerHand(Player player, String playerName, boolean dealerQualifies) {
//        int ppWinnings = ThreeCardLogic.evalPPWinnings(player.hand, player.pairPlusBet);
//        if (ppWinnings > 0) {
//            player.totalWinnings += ppWinnings;
//            updateGameInfo(playerName + " wins Pair Plus: $" + ppWinnings);
//        } else {
//            player.totalWinnings -= player.pairPlusBet;
//            updateGameInfo(playerName + " loses Pair Plus.");
//        }
//
//        if (dealerQualifies) {
//            int compareResult = ThreeCardLogic.compareHands(theDealer.dealersHand, player.hand);
//            if (compareResult == 2) {
//                int winnings = player.anteBet + player.playBet;
//                player.totalWinnings += winnings * 2;
//                updateGameInfo(playerName + " beats dealer and wins: $" + winnings * 2);
//            } else if (compareResult == 1) {
//                player.totalWinnings -= player.anteBet + player.playBet;
//                updateGameInfo(playerName + " loses to dealer.");
//            } else {
//                updateGameInfo(playerName + " ties with dealer.");
//            }
//        } else {
//            updateGameInfo("Dealer does not qualify. " + playerName + "'s play bet is returned.");
//            player.totalWinnings += player.playBet;
//        }
//
//        if (playerName.equals("Player One")) {
//            playerOneWinnings.setText("$" + player.totalWinnings);
//        } else {
//            playerTwoWinnings.setText("$" + player.totalWinnings);
//        }
//    }
//
//    private String handToString(ArrayList<Card> hand) {
//        StringBuilder sb = new StringBuilder();
//        for (Card card : hand) {
//            String cardValue;
//            switch (card.getValue()) {
//                case 11: cardValue = "J"; break;
//                case 12: cardValue = "Q"; break;
//                case 13: cardValue = "K"; break;
//                case 14: cardValue = "A"; break;
//                default: cardValue = String.valueOf(card.getValue());
//            }
//            sb.append(cardValue).append(card.getSuit()).append(" ");
//        }
//        return sb.toString().trim();
//    }
//
//    private void resetRound() {
//        playerOne.hand.clear();
//        playerOneAnteBet.clear();
//        playerOnePairPlusBet.clear();
//        playerOneAnteBet.setDisable(false);
//        playerOnePairPlusBet.setDisable(false);
//        playerOne.anteBet = 0;
//        playerOne.pairPlusBet = 0;
//        playerOne.playBet = 0;
//        playerOneFolded = false;
//        playFoldButtonPlayerOne.setDisable(true);
//        placeBetsButtonPlayerOne.setDisable(false);
//        playerOneHandPane.getChildren().clear();
//
//        if (!isOnePlayerMode) {
//            playerTwo.hand.clear();
//            playerTwoAnteBet.clear();
//            playerTwoPairPlusBet.clear();
//            playerTwoAnteBet.setDisable(false);
//            playerTwoPairPlusBet.setDisable(false);
//            playerTwo.anteBet = 0;
//            playerTwo.pairPlusBet = 0;
//            playerTwo.playBet = 0;
//            playerTwoFolded = false;
//            playFoldButtonPlayerTwo.setDisable(true);
//            placeBetsButtonPlayerTwo.setDisable(false);
//            playerTwoHandPane.getChildren().clear();
//        }
//
//        theDealer.dealersHand.clear();
//        dealerHandPane.getChildren().clear();
//        resetButtons();
//    }
//
//    private void resetButtons() {
//        dealCardsButton.setDisable(true);
//        revealDealerButton.setDisable(true);
//        continueButton.setDisable(true);
//        playFoldButtonPlayerOne.setDisable(true);
//        playFoldButtonPlayerTwo.setDisable(true);
//        placeBetsButtonPlayerOne.setDisable(false);
//        if (!isOnePlayerMode) {
//            placeBetsButtonPlayerTwo.setDisable(false);
//        }
//    }
//}