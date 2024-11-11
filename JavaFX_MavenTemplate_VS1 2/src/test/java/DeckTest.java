import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.ArrayList;

public class DeckTest {

    // Test that a new deck has 52 cards
    @Test
    public void testNewDeckHas52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.size(), "New deck should have 52 cards");
    }

    // Test that newDeck() resets and shuffles the deck
    @Test
    public void testNewDeckMethod() {
        Deck deck = new Deck();
        deck.remove(0);
        deck.newDeck();
        assertEquals(52, deck.size(), "After newDeck(), deck should have 52 cards");
    }

    // Test that the deck contains all unique cards
    @Test
    public void testDeckContainsUniqueCards() {
        Deck deck = new Deck();
        HashSet<String> cardSet = new HashSet<>();
        for (Card card : deck) {
            String cardId = card.getSuit() + "-" + card.getValue();
            cardSet.add(cardId);
        }
        assertEquals(52, cardSet.size(), "Deck should contain unique cards");
    }

    // Test that the deck is shuffled (not in order)
    @Test
    public void testDeckIsShuffled() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        boolean decksAreDifferent = false;
        for (int i = 0; i < 52; i++) {
            Card card1 = deck1.get(i);
            Card card2 = deck2.get(i);
            if (card1.getValue() != card2.getValue() || card1.getSuit() != card2.getSuit()) {
                decksAreDifferent = true;
                break;
            }
        }
        assertTrue(decksAreDifferent, "Decks should be shuffled differently");
    }

    // Test removing cards from the deck
    @Test
    public void testRemovingCards() {
        Deck deck = new Deck();
        Card card = deck.remove(0);
        assertEquals(51, deck.size(), "Deck size should be 51 after removing one card");
        assertNotNull(card, "Removed card should not be null");
    }

    // Test dealing all cards from the deck
    @Test
    public void testDealingAllCards() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.remove(0);
        }
        assertEquals(0, deck.size(), "Deck should be empty after dealing all cards");
    }

    // Test dealing more than available cards
    @Test
    public void testDealingMoreThanAvailableCards() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.remove(0);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {
            deck.remove(0);
        }, "Should throw exception when removing from empty deck");
    }

    // Test multiple decks have different orders
    @Test
    public void testMultipleDecksDifferentOrders() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        boolean differentOrder = false;
        for (int i = 0; i < 52; i++) {
            if (deck1.get(i).getValue() != deck2.get(i).getValue() ||
                deck1.get(i).getSuit() != deck2.get(i).getSuit()) {
                differentOrder = true;
                break;
            }
        }
        assertTrue(differentOrder, "Different decks should have different card orders");
    }

    // Test that newDeck() shuffles the deck differently
    @Test
    public void testNewDeckShufflesDifferently() {
        Deck deck = new Deck();
        ArrayList<Card> firstOrder = new ArrayList<>(deck);
        deck.newDeck();
        boolean orderChanged = false;
        for (int i = 0; i < 52; i++) {
            if (deck.get(i).getValue() != firstOrder.get(i).getValue() ||
                deck.get(i).getSuit() != firstOrder.get(i).getSuit()) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged, "newDeck() should shuffle the deck differently");
    }

    // Test that the deck contains correct number of each suit
    @Test
    public void testDeckHasCorrectNumberOfSuits() {
        Deck deck = new Deck();
        int countHearts = 0;
        int countDiamonds = 0;
        int countClubs = 0;
        int countSpades = 0;
        for (Card card : deck) {
            switch (card.getSuit()) {
                case 'H':
                    countHearts++;
                    break;
                case 'D':
                    countDiamonds++;
                    break;
                case 'C':
                    countClubs++;
                    break;
                case 'S':
                    countSpades++;
                    break;
            }
        }
        assertEquals(13, countHearts, "There should be 13 Hearts");
        assertEquals(13, countDiamonds, "There should be 13 Diamonds");
        assertEquals(13, countClubs, "There should be 13 Clubs");
        assertEquals(13, countSpades, "There should be 13 Spades");
    }

    // Test that the deck contains correct number of each value
    @Test
    public void testDeckHasCorrectNumberOfValues() {
        Deck deck = new Deck();
        int[] valueCounts = new int[13]; // Indices 0-12 correspond to values 2-14
        for (Card card : deck) {
            int index = card.getValue() - 2;
            valueCounts[index]++;
        }
        for (int i = 0; i < valueCounts.length; i++) {
            assertEquals(4, valueCounts[i], "There should be 4 cards of value " + (i + 2));
        }
    }
}
