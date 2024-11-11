import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class ThreeCardLogicTest {

    // Helper method to create a card
    private Card createCard(char suit, int value) {
        return new Card(suit, value);
    }

    // Test High Card
    @Test
    public void testEvalHandHighCard() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 2));
        hand.add(createCard('D', 5));
        hand.add(createCard('S', 9));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Should be High Card");
    }

    // Test Pair
    @Test
    public void testEvalHandPair() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 5));
        hand.add(createCard('D', 5));
        hand.add(createCard('S', 9));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Should be a Pair");
    }

    // Test Flush
    @Test
    public void testEvalHandFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 2));
        hand.add(createCard('H', 7));
        hand.add(createCard('H', 10));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Should be a Flush");
    }

    // Test Straight
    @Test
    public void testEvalHandStraight() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('C', 5));
        hand.add(createCard('D', 6));
        hand.add(createCard('H', 7));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should be a Straight");
    }

    // Test Three of a Kind
    @Test
    public void testEvalHandThreeOfAKind() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 8));
        hand.add(createCard('D', 8));
        hand.add(createCard('S', 8));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Should be Three of a Kind");
    }

    // Test Straight Flush
    @Test
    public void testEvalHandStraightFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 9));
        hand.add(createCard('H', 10));
        hand.add(createCard('H', 11));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Should be a Straight Flush");
    }

    // Test Ace Low Straight (Ace, 2, 3)
    @Test
    public void testEvalHandAceLowStraight() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('S', 14)); // Ace
        hand.add(createCard('D', 2));
        hand.add(createCard('H', 3));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should be a Straight (Ace low)");
    }

    // Test Ace High Straight (Q, K, A)
    @Test
    public void testEvalHandAceHighStraight() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('C', 12)); // Queen
        hand.add(createCard('D', 13)); // King
        hand.add(createCard('H', 14)); // Ace
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should be a Straight (Ace high)");
    }

    // Test Pair with Face Cards
    @Test
    public void testEvalHandPairFaceCards() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 11)); // Jack
        hand.add(createCard('D', 11)); // Jack
        hand.add(createCard('S', 9));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Should be a Pair");
    }

    // Test Flush with Ace High
    @Test
    public void testEvalHandFlushAceHigh() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('S', 3));
        hand.add(createCard('S', 7));
        hand.add(createCard('S', 14)); // Ace
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Should be a Flush");
    }

    // Test High Card with Ace
    @Test
    public void testEvalHandHighCardWithAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 2));
        hand.add(createCard('D', 5));
        hand.add(createCard('S', 14)); // Ace
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Should be High Card");
    }

    // Test evalPPWinnings with Pair
    @Test
    public void testEvalPPWinningsPair() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 6));
        hand.add(createCard('D', 6));
        hand.add(createCard('S', 9));
        int bet = 5;
        assertEquals(bet * 1, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be bet * 1");
    }

    // Test evalPPWinnings with Flush
    @Test
    public void testEvalPPWinningsFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 2));
        hand.add(createCard('H', 7));
        hand.add(createCard('H', 10));
        int bet = 5;
        assertEquals(bet * 3, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be bet * 3");
    }

    // Test evalPPWinnings with Straight
    @Test
    public void testEvalPPWinningsStraight() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('C', 5));
        hand.add(createCard('D', 6));
        hand.add(createCard('H', 7));
        int bet = 5;
        assertEquals(bet * 6, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be bet * 6");
    }

    // Test evalPPWinnings with Three of a Kind
    @Test
    public void testEvalPPWinningsThreeOfAKind() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 8));
        hand.add(createCard('D', 8));
        hand.add(createCard('S', 8));
        int bet = 5;
        assertEquals(bet * 30, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be bet * 30");
    }

    // Test evalPPWinnings with Straight Flush
    @Test
    public void testEvalPPWinningsStraightFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 9));
        hand.add(createCard('H', 10));
        hand.add(createCard('H', 11));
        int bet = 5;
        assertEquals(bet * 40, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be bet * 40");
    }

    // Test evalPPWinnings with No Pair
    @Test
    public void testEvalPPWinningsNoPair() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(createCard('H', 2));
        hand.add(createCard('D', 5));
        hand.add(createCard('S', 9));
        int bet = 5;
        assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet), "Pair Plus Winnings should be 0");
    }

    // Test compareHands when Player Wins
    @Test
    public void testCompareHandsPlayerWins() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 2));
        dealerHand.add(createCard('D', 3));
        dealerHand.add(createCard('S', 4));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('H', 10));
        playerHand.add(createCard('D', 10));
        playerHand.add(createCard('S', 10));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(2, result, "Player should win");
    }

    // Test compareHands when Dealer Wins
    @Test
    public void testCompareHandsDealerWins() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 11));
        dealerHand.add(createCard('D', 12));
        dealerHand.add(createCard('S', 13));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('H', 2));
        playerHand.add(createCard('D', 3));
        playerHand.add(createCard('S', 4));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(1, result, "Dealer should win");
    }

    // Test compareHands when Tie
    @Test
    public void testCompareHandsTie() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 10));
        dealerHand.add(createCard('D', 11));
        dealerHand.add(createCard('S', 12));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('C', 10));
        playerHand.add(createCard('D', 11));
        playerHand.add(createCard('H', 12));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(0, result, "Should be a tie");
    }

    // Test compareHands when Both Have High Card but Player Has Higher
    @Test
    public void testCompareHandsHighCardPlayerWins() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 5));
        dealerHand.add(createCard('D', 8));
        dealerHand.add(createCard('S', 9));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('C', 6));
        playerHand.add(createCard('D', 10));
        playerHand.add(createCard('H', 7));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(2, result, "Player should win with higher card");
    }

    // Test compareHands when Both Have High Card but Dealer Has Higher
    @Test
    public void testCompareHandsHighCardDealerWins() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 6));
        dealerHand.add(createCard('D', 10));
        dealerHand.add(createCard('S', 7));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('C', 5));
        playerHand.add(createCard('D', 8));
        playerHand.add(createCard('H', 9));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(1, result, "Dealer should win with higher card");
    }

    // Test compareHands when Both Have Same Pair but Player Has Higher Kicker
    @Test
    public void testCompareHandsSamePairPlayerWins() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 10));
        dealerHand.add(createCard('D', 10));
        dealerHand.add(createCard('S', 5));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('C', 10));
        playerHand.add(createCard('D', 10));
        playerHand.add(createCard('H', 6));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(2, result, "Player should win with higher kicker");
    }

    // Test compareHands when Both Have Same Pair and Same Kicker
    @Test
    public void testCompareHandsSamePairTie() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(createCard('H', 10));
        dealerHand.add(createCard('D', 10));
        dealerHand.add(createCard('S', 5));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(createCard('C', 10));
        playerHand.add(createCard('D', 10));
        playerHand.add(createCard('H', 5));

        int result = ThreeCardLogic.compareHands(dealerHand, playerHand);
        assertEquals(0, result, "Should be a tie");
    }
}
