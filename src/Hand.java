import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

import java.util.List;

/**
 * Created by Robert Sedgwick on 4/25/2017.
 * Class represents a users hand in BlackJack
 */
public class Hand {

    // A list of card Nodes representing Cards.
    private List<Node> cards;

    // Value of current hand. Use of property allows for binding.
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    private int aces = 0;   // Aces in current hand, used to determine value of Ace, 1 or 11.

    public Hand(List<Node> cards) {
        this.cards = cards;
    }

    // Push new card into hand
    public void pushCard(Card card) {
        cards.add(card);

        // Update score
        if (card.rank() == Card.Rank.ACE) {
            aces++;
        }

        // Ace value may be 1 or 11, if there is an ace in the hand and the value is > 21, change ace value to 1.
        if (value.get() + card.getValue() > 21 && aces > 0) {
            value.set(value.get() + card.getValue() - 10);
            aces--;
        }
        // If there are no aces simply add value.
        else {
            value.set(value.get() + card.getValue());
        }
    }

    // Empty hand
    public void clear() {
        cards.clear();
        value.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }
}
