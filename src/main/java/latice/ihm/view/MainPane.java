package latice.ihm.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import latice.ihm.controller.RoundController;
import latice.ihm.controller.TileController;
import latice.ihm.model.RackTransition;
import latice.ihm.view.model.BoardPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;

public class MainPane extends BorderPane {
    protected Label lblNbRound;
    protected int nbRound = 1;
    protected Label lblPlayerRound;
    private Game game;

    private RoundController roundController;
    private RackTransition rackTransition;

    public MainPane(Board board, Game game, TileController tileController, RoundController roundController) {
        this.game = game;
        this.roundController = roundController;

        lblNbRound = new Label("Round : " + nbRound);
        lblPlayerRound = new Label("Player Turn :");

        lblNbRound.setStyle("-fx-text-fill: white;");
        lblPlayerRound.setStyle("-fx-text-fill: white;");

        // --- TOP : infos du tour ---
        HBox hbTop = new HBox(20);
        hbTop.setPadding(new Insets(15, 0, 20, 0));
        hbTop.getChildren().addAll(lblPlayerRound, lblNbRound);
        hbTop.setAlignment(Pos.CENTER);
        hbTop.setStyle("-fx-font-size: 20px; -fx-font-family: \"Arial\"; -fx-font-weight: bold;");

        // --- BOUTON DE TEST ---
        Button btnNextTurn = new Button("Fin de tour");
        btnNextTurn.setStyle("-fx-background-color: #a8d8a8; -fx-cursor: hand; -fx-font-weight: bold;");
        btnNextTurn.setOnAction(e -> {
            // Quand on clique, on change de joueur et on rafraîchit l'écran
            this.roundController.nextPlayerTurn();
            updateDisplay();
        });
        hbTop.getChildren().add(btnNextTurn);

        setTop(hbTop);
        setAlignment(hbTop, Pos.CENTER);

        // --- CENTRE : StackPane avec le board ET les racks autour ---
        // On utilise un StackPane pour superposer le board et les racks positionnés autour
        BoardPane boardPane = new BoardPane(board, tileController, roundController, this);
        boardPane.setAlignment(Pos.CENTER);
        boardPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane centerStack = new StackPane();
        centerStack.setAlignment(Pos.CENTER);
        centerStack.getChildren().add(boardPane);

        // Récupère la liste des joueurs depuis le RoundController
        ArrayList<Player> players = roundController.getPlayers();

        // Crée la RackTransition qui va gérer le positionnement et les animations
        rackTransition = new RackTransition(centerStack, players, roundController.getCurrentPlayerIndex());

        setCenter(centerStack);
        BorderPane.setMargin(centerStack, new Insets(20));

        updateDisplay();
    }

    public void updateDisplay() {
        Player current = roundController.getCurrentPlayer();
        lblPlayerRound.setText("Player Turn : " + current.getName());

        // Anime les racks vers la nouvelle position du joueur actif
        rackTransition.animerVersJoueur(roundController.getCurrentPlayerIndex());
    }

    /**
     * À appeler depuis SquarePane après un placement réussi,
     * pour rafraîchir le rack du joueur qui vient de jouer.
     *
     * @param playerIndex L'index du joueur dont le rack a changé
     */
    public void rafraichirRackJoueur(int playerIndex) {
        rackTransition.rafraichirRack(playerIndex);
    }
}