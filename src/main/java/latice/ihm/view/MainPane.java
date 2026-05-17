package latice.ihm.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.controller.TileController;
import latice.ihm.model.RackTransition;
import latice.ihm.view.model.ActionShopDeck;
import latice.ihm.view.model.BoardPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Referee;

public class MainPane extends BorderPane {
    protected Label lblNbRound;
    protected int nbRound;
    protected Label lblPlayerRound;
    private Game game;

    private RoundController roundController;
    private RackTransition rackTransition;
    private GameController gameController;
    private Referee referee;
    private ActionShopDeck actionShop;

    public MainPane(Board board, Game game, TileController tileController,
                    RoundController roundController, GameController gameController,
                    Referee referee) {
        this.game = game;
        this.gameController = gameController;
        gameController.setView(this);
        this.roundController = roundController;
        this.referee = referee;
        this.nbRound = roundController.getRoundCount();

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
        setTop(hbTop);
        setAlignment(hbTop, Pos.CENTER);

        // --- CENTRE : StackPane avec board + racks ---
        BoardPane boardPane = new BoardPane(board, tileController, roundController, this, gameController);
        boardPane.setAlignment(Pos.CENTER);
        boardPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane centerStack = new StackPane();
        centerStack.setAlignment(Pos.CENTER);
        centerStack.getChildren().add(boardPane);

        ArrayList<Player> players = roundController.getPlayers();
        rackTransition = new RackTransition(
            centerStack,
            players,
            roundController.getCurrentPlayerIndex(),
            roundController,
            gameController,
            referee
        );

        setCenter(centerStack);
        BorderPane.setMargin(centerStack, new Insets(20));

        // --- BAS : ActionShopDeck centré, collé au bord inférieur ---
        actionShop = new ActionShopDeck(gameController, roundController, referee);
        HBox bottomWrapper = new HBox(actionShop);
        bottomWrapper.setAlignment(Pos.BOTTOM_CENTER);
        bottomWrapper.setPickOnBounds(false);
        setBottom(bottomWrapper);

        updateDisplay();
    }

    public void updateDisplay() {
        Player current = roundController.getCurrentPlayer();
        lblPlayerRound.setText("Player Turn : " + current.getName());
        lblNbRound.setText("Round : " + roundController.getRoundCount());
        rackTransition.animateToPlayer(roundController.getCurrentPlayerIndex());
        if (actionShop != null) {
            actionShop.refreshPoints();
        }
    }

    public void rafraichirRackJoueur(int playerIndex) {
        rackTransition.rafraichirRack(playerIndex);
    }
}