import java.util.Collections;
import java.util.Stack;

/**
 * Created by Robert Sedgwick on 4/25/2017.
 * Class Represents a deck of 52 Poker cards.
 */
public class Deck {

    // Stack holding cards
    private static Stack<Card> deck;

    public Deck() {
        deck = new Stack();
        reset();
        shuffle();
    }

    // Create a new deck
    private void reset() {
        deck.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add( new Card(rank, suit) );
            }
        }
    }

    // Shuffle cards in deck
    public void shuffle() {
        reset();
        Collections.shuffle(deck);
    }

    // Put card onto deck
    public void push(Card card) {
        if (card != null) {
            deck.push(card);
        }
    }

    // Draw new card  from deck
    public Card draw() {

        // Reset the deck if it is empty
        if (deck.isEmpty()) {
            this.reset();
        }
        return deck.pop();
    }
}
