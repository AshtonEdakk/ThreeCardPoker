public class Card {
    char suit;
    int value;

    // Constructor
    public Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    // Getters
    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}
