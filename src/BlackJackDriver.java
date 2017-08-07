import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Robert Sedgwick on 5/1/2017.
 * A BlackJack Game.
 */
public class BlackJackDriver extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Game Variables
    private Deck deck = new Deck();         // 52 Card Deck
    private Text message = new Text();      // Message describing game state to user
    private Hand dealer;                    // Represents dealers hand
    private Hand player;                    // Represents players hand

    // Used to disable control buttons when needed, use of property allows for binding
    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);  

    // buildScene()
    // Creates all elements to be displayed on Scene
    private Pane buildScene() {

        // GUI Elements
        //==============================================================================================================

        // Root Node
        Pane root = new Pane();             // Empty layout
        root.setStyle("-fx-background-color: #008CA8");
        root.setPrefSize(800, 600);         // Set size of game area

        // Set root layout
        VBox rootLayout = new VBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));

        // Card Pane - Displays hand of cards and scores
        VBox cardPane = new VBox(50);
        cardPane.setAlignment(Pos.TOP_CENTER);
        cardPane.setPadding(new Insets(5, 5, 5, 5));
        cardPane.setMinWidth(800);
        cardPane.setMinHeight(400);

        // Score of current hand
        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");

        // Style Text
        message.setFont(Font.font("Consolas", 26));
        playerScore.setFont(Font.font("Consolas", 16));
        dealerScore.setFont(Font.font("Consolas", 16));

        // Containers for images representing player and dealers hand.
        HBox dealerCards = new HBox(20);
        HBox playerCards = new HBox(20);

        // Hand Objects
        dealer = new Hand(dealerCards.getChildren());
        player = new Hand(playerCards.getChildren());

        /// Attach score and hands to cardPane
        cardPane.getChildren().addAll(dealerScore, dealerCards, message, playerCards, playerScore);

        // Control Pane - Displays game controls
        HBox controlPane = new HBox(20);
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setMinWidth(800);
        cardPane.setMinHeight(200);

        // Control buttons
        Button play = new Button("PLAY");
        Button hit = new Button("HIT");
        Button stand = new Button("STAND");

        // Container for game buttons.
        HBox buttonGroup = new HBox(20, play, hit, stand);
        buttonGroup.setAlignment(Pos.CENTER);

        // Attach buttons to controlPane
        controlPane.getChildren().addAll(buttonGroup);

        // Attach  elements to root
        rootLayout.getChildren().addAll(cardPane, controlPane);
        root.getChildren().add(rootLayout);

        // Disable buttons when necessary
        play.disableProperty().bind(playable);          // If there is an active hand disable play button
        hit.disableProperty().bind(playable.not());     // If game is not active disable hit button
        stand.disableProperty().bind(playable.not());   // If game is not active disable stand button

        // Print Scores
        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));
        //==============================================================================================================


        // Game Logic
        //==============================================================================================================

        // Listener looks for hand value >= to 21, if found the hand ends
        player.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endHand();
            }
        });

        dealer.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endHand();
            }
        });
        //==============================================================================================================


        // Button Events
        //==============================================================================================================
        play.setOnAction(event -> {
            newHand();
        });

        hit.setOnAction(event -> {
            player.pushCard(deck.draw());
        });

        // After player has decided to stand, draw new cards until hand value  > 16.
        stand.setOnAction(event -> {
            while (dealer.valueProperty().get() <= 16) {
                dealer.pushCard(deck.draw());
            }
            endHand();
        });
        //==============================================================================================================

        return root;
    } // End of buildScene()


    // Set up a new round of play.
    private void newHand() {

        // Set playable to true and clear message, and hands
        playable.set(true);
        message.setText("");
        dealer.clear();
        player.clear();

        // Draw initial two cards
        dealer.pushCard(deck.draw());
        dealer.pushCard(deck.draw());
        player.pushCard(deck.draw());
        player.pushCard(deck.draw());
    }

    // Determine winner
    private void endHand() {

        // Set playable to false
        playable.set(false);

        // Get hand values.
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        String winner = "";

        // Dealer wins ties, check dealers score first
        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
                || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "Dealer Wins, You Lose!";
            message.setFill(Color.RED);
        }
        else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
            winner = "Player Wins!";
            message.setFill(Color.YELLOW);
        }
        message.setText(winner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(buildScene()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }
}
