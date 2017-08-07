import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Sedgwick on 4/24/2017.
 * Class generates and stores images for a deck of 52 poker cards.
 */

public class CardImages {

    private static final String IMAGE_LOCATION = "file:src/images/";
    private static final String IMAGE_SUFFIX = ".png";

    // Ordinal values of Card.rank and Card.suit enums are mapped to RANK_CODES and SUIT_CODES to build image paths.
    private static final String[] RANK_CODES = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack",
                                                "queen", "king" };
    private static final String[] SUIT_CODES = { "clubs", "diamonds", "hearts", "spades" };

    // Once initialized, card images are stored in the deck Hash for future use.
    private static Map<String, Image> deck = new HashMap<>();


    // Builds path string to image then calls getCard(String dir) to get image.
    public static Image getCard (Card.Rank rank, Card.Suit suit) {
        StringBuilder imgDirectory = new StringBuilder();
        imgDirectory.append(IMAGE_LOCATION);
        imgDirectory.append(RANK_CODES[ rank.ordinal() ]);
        imgDirectory.append("_of_");
        imgDirectory.append(SUIT_CODES[ suit.ordinal() ]);
        imgDirectory.append(IMAGE_SUFFIX);

        Image image = getCard(imgDirectory.toString());

        return image;
    }

    /**
     *
     * @param imgDirectory, The relative path to the target cards image file
     * @return Image, A javaFX Image of the target card.
     * If the Image has already be generated is is pulled from the deck Hash, if not the new Image
     * is initialized, and added to the deck Hash.
     */
    private static Image getCard(String imgDirectory) {
        Image image = deck.get( imgDirectory );
        if( image == null )
        {
            image = new Image(imgDirectory, 75, 100, true, false);
            deck.put( imgDirectory, image );
        }
        return image;
    }
}
