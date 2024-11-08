import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {

    public static int evalHand(ArrayList<Card> hand) {
        boolean flush = isFlush(hand);
        boolean straight = isStraight(hand);
        boolean threeOfKind = isThreeOfKind(hand);
        boolean pair = isPair(hand);

        if (flush && straight) {
            return 1; // Straight Flush
        } else if (threeOfKind) {
            return 2; // Three of a Kind
        } else if (straight) {
            return 3; // Straight
        } else if (flush) {
            return 4; // Flush
        } else if (pair) {
            return 5; // Pair
        } else {
            return 0; // High Card
        }
    }

    // Helper methods
    private static boolean isFlush(ArrayList<Card> hand) {
        char suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }

    private static boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Card card : hand) {
            values.add(card.getValue());
        }
        Collections.sort(values);
        // Check for Ace, 2, 3 straight
        if (values.get(0) == 2 && values.get(1) == 3 && values.get(2) == 14) {
            return true;
        }
        return values.get(2) - values.get(0) == 2 && 
               values.get(1) - values.get(0) == 1;
    }

    private static boolean isThreeOfKind(ArrayList<Card> hand) {
        int value = hand.get(0).getValue();
        return hand.get(1).getValue() == value && hand.get(2).getValue() == value;
    }

    private static boolean isPair(ArrayList<Card> hand) {
        int v1 = hand.get(0).getValue();
        int v2 = hand.get(1).getValue();
        int v3 = hand.get(2).getValue();
        return v1 == v2 || v1 == v3 || v2 == v3;
    }
    
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        int handValue = evalHand(hand);

        switch (handValue) {
            case 1: // Straight Flush
                return bet * 40;
            case 2: // Three of a Kind
                return bet * 30;
            case 3: // Straight
                return bet * 6;
            case 4: // Flush
                return bet * 3;
            case 5: // Pair
                return bet * 1;
            default:
                return 0; // No winnings
        }
    }
    
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        int dealerHandValue = evalHand(dealer);
        int playerHandValue = evalHand(player);

        if (playerHandValue > dealerHandValue) {
            return 2; // Player wins
        } else if (playerHandValue < dealerHandValue) {
            return 1; // Dealer wins
        } else {
            // Hands are of the same type, compare highest cards
            int playerHighCard = getHighCard(player);
            int dealerHighCard = getHighCard(dealer);

            if (playerHighCard > dealerHighCard) {
                return 2; // Player wins
            } else if (playerHighCard < dealerHighCard) {
                return 1; // Dealer wins
            } else {
                return 0; // Tie
            }
        }
    }

    private static int getHighCard(ArrayList<Card> hand) {
        int high = 0;
        for (Card card : hand) {
            if (card.getValue() == 1) {
                return 14; // Ace is high
            }
            if (card.getValue() > high) {
                high = card.getValue();
            }
        }
        return high;
    }

}
