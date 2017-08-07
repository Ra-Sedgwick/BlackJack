import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by Robert Sedgwick on 4/23/2017.
 * Class Represents the 52 playing cards of a poker deck.
 */
public class Card extends Parent {

    // Enumerable used to represent Rank constants.
    // Ordinal value used by CardImages to map card to image file.
    // Rank value used to determine value of card per BlackJack rules
    public enum Rank { ACE(11), DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10);

        final int value;        // Points the card is worth in BlackJack.

        Rank(int value) {
            this.value = value;
        }
    }

    // Enumerable used to represent Suit constants.
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

    private final Rank rank;
    private final Suit suit;
    private ImageView image;        // Image associated with card.

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;

        // CardImages Class used to look up card image.
        this.image = new ImageView(CardImages.getCard(rank, suit));

        // Attach image to Parent(A hand of cards)
        getChildren().add(new StackPane(image));
    }

    // Get value of card based on BlackJack rules.
    public int getValue() {
        return this.rank.value;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

}
