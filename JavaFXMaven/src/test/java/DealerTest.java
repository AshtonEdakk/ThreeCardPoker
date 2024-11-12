import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;

public class DealerTest {

    // Test that dealer initializes with a deck
    @Test
    public void testDealerInitialization() {
        Dealer dealer = new Dealer();
        assertNotNull(dealer.theDeck, "Dealer should have a deck initialized");
        assertEquals(52, dealer.theDeck.size(), "Dealer's deck should have 52 cards initially");
    }

    // Test that dealer's hand is empty upon initialization
    @Test
    public void testDealersHandInitialization() {
        Dealer dealer = new Dealer();
        assertNotNull(dealer.dealersHand, "Dealer's hand should be initialized");
        assertEquals(0, dealer.dealersHand.size(), "Dealer's hand should be empty initially");
    }

    // Test that dealHand() returns a hand of 3 cards
    @Test
    public void testDealHand() {
        Dealer dealer = new Dealer();
        ArrayList<Card> hand = dealer.dealHand();
        assertNotNull(hand, "Deal hand should return a hand");
        assertEquals(3, hand.size(), "Hand should contain 3 cards");
        assertEquals(49, dealer.theDeck.size(), "Deck should have 49 cards after dealing one hand");
    }

    // Test that dealer reshuffles deck when less than or equal to 34 cards
    @Test
    public void testDealerReshufflesDeck() {
        Dealer dealer = new Dealer();
        // Remove cards to reduce deck size to 34
        for (int i = 0; i < 18; i++) {
            dealer.dealHand();
        }
        assertEquals(34, dealer.theDeck.size(), "Deck should have 34 cards");
        // Next deal should trigger reshuffle
        dealer.dealHand();
        assertEquals(49, dealer.theDeck.size(), "Deck should have been reshuffled to 52 cards minus 3");
    }

    // Test that dealer's hand gets updated after dealing
    @Test
    public void testDealersHandAfterDealing() {
        Dealer dealer = new Dealer();
        dealer.dealersHand = dealer.dealHand();
        assertNotNull(dealer.dealersHand, "Dealer's hand should not be null after dealing");
        assertEquals(3, dealer.dealersHand.size(), "Dealer's hand should have 3 cards after dealing");
    }

    // Test that dealer can deal multiple hands
    @Test
    public void testDealerDealsMultipleHands() {
        Dealer dealer = new Dealer();
        for (int i = 0; i < 5; i++) {
            ArrayList<Card> hand = dealer.dealHand();
            assertEquals(3, hand.size(), "Each hand should have 3 cards");
        }
        assertEquals(37, dealer.theDeck.size(), "Deck should have 37 cards after dealing 5 hands");
    }

    // Test that dealing hands removes cards from the deck
    @Test
    public void testDealingReducesDeckSize() {
        Dealer dealer = new Dealer();
        dealer.dealHand();
        dealer.dealHand();
        assertEquals(46, dealer.theDeck.size(), "Deck should have 46 cards after dealing 2 hands");
    }

    // Test that dealer's hand does not interfere with deck
    @Test
    public void testDealersHandSeparateFromDeck() {
        Dealer dealer = new Dealer();
        dealer.dealersHand = dealer.dealHand();
        for (Card card : dealer.dealersHand) {
            assertFalse(dealer.theDeck.contains(card), "Dealer's hand cards should not be in the deck");
        }
    }

    // Test that after reshuffling, deck contains all cards again
    @Test
    public void testDeckContainsAllCardsAfterReshuffle() {
        Dealer dealer = new Dealer();
        for (int i = 0; i < 18; i++) {
            dealer.dealHand();
        }
        dealer.dealHand(); // Triggers reshuffle
        HashSet<String> cardSet = new HashSet<>();
        for (Card card : dealer.theDeck) {
            String cardId = card.getSuit() + "-" + card.getValue();
            cardSet.add(cardId);
        }
        assertEquals(49, dealer.theDeck.size(), "Deck should have 49 cards after reshuffle and dealing");
        // Including the 3 cards in dealer's hand
        for (Card card : dealer.dealersHand) {
            String cardId = card.getSuit() + "-" + card.getValue();
            cardSet.add(cardId);
        }
        assertEquals(52, cardSet.size(), "All 52 cards should be present between deck and dealer's hand");
    }

    // Test that dealer can deal hand after deck has been emptied and reshuffled
    @Test
    public void testDealerDealsAfterDeckEmpty() {
        Dealer dealer = new Dealer();
        // Remove all cards from the deck
        for (int i = 0; i < 17; i++) {
            dealer.dealHand();
        }
        assertEquals(1, dealer.theDeck.size(), "Deck should have 1 card left");
        dealer.dealHand(); // Should trigger reshuffle
        assertEquals(49, dealer.theDeck.size(), "Deck should have 49 cards after reshuffle and dealing one hand");
    }
}
