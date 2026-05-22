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

public class GamePane extends BorderPane {
    protected Label lblNbRound;
    protected int   nbRound;
    protected Label lblPlayerRound;
    private Game    game;

    private RoundController roundController;
    private RackTransition  rackTransition;
    private GameController  gameController;
    private Referee         referee;
    private ActionShopDeck  actionShop;

    public GamePane(Board board, Game game, TileController tileController,
                    RoundController roundController, GameController gameController,
                    Referee referee) {
        this.game            = game;
        this.gameController  = gameController;
        gameController.setView(this);
        this.roundController = roundController;
        this.referee         = referee;
        this.nbRound         = roundController.getRoundCount();


        lblNbRound     = new Label("Round : " + nbRound);
        lblPlayerRound = new Label("Player Turn :");
        
        Button btnEndTurn = new Button("End Turn");
        String btnEndTurnStyle =
                "-fx-background-color: rgba(255, 255, 255, 0.12); " +
                "-fx-background-radius: 20px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.35); " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20px; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-family: \"SF Pro Display\", \"Helvetica Neue\", Arial; " +
                "-fx-font-weight: 500; " +
                "-fx-padding: 8px 22px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 10, 0, 0, 4);";
        
        String btnEndTurnHoverStyle =
                "-fx-background-color: rgba(255, 255, 255, 0.22); " +
                "-fx-background-radius: 20px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.55); " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20px; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-family: \"SF Pro Display\", \"Helvetica Neue\", Arial; " +
                "-fx-font-weight: 500; " +
                "-fx-padding: 8px 22px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 14, 0, 0, 6);";
        
        String btnEndTurnPressedStyle =
                "-fx-background-color: rgba(255, 255, 255, 0.50); " +
                "-fx-background-radius: 20px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.86); " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20px; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-family: \"SF Pro Display\", \"Helvetica Neue\", Arial; " +
                "-fx-font-weight: 500; " +
                "-fx-padding: 8px 22px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 14, 0, 0, 6);";
        
        
        
        btnEndTurn.setStyle(btnEndTurnStyle);
        btnEndTurn.setOnMouseEntered(e-> btnEndTurn.setStyle(btnEndTurnHoverStyle));
        btnEndTurn.setOnMouseExited(e-> btnEndTurn.setStyle(btnEndTurnStyle));
        btnEndTurn.setOnMousePressed(e-> btnEndTurn.setStyle(btnEndTurnPressedStyle));
        btnEndTurn.setOnMouseReleased(e-> btnEndTurn.setStyle(btnEndTurnHoverStyle));
        btnEndTurn.setOnAction(e -> {
            gameController.passTurn();
        });
        
        
        lblNbRound.setStyle("-fx-text-fill: white;");
        lblPlayerRound.setStyle("-fx-text-fill: white;");

        // --- TOP ---
        HBox hbTop = new HBox(20);
        hbTop.setPadding(new Insets(15, 0, 20, 0));
        hbTop.getChildren().addAll(lblPlayerRound, lblNbRound, btnEndTurn);
        hbTop.setAlignment(Pos.CENTER);
        hbTop.setStyle("-fx-font-size: 20px; -fx-font-family: \"Arial\"; -fx-font-weight: bold;");
        setTop(hbTop);
        setAlignment(hbTop, Pos.CENTER);

        // --- CENTRE ---
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

        // --- ActionShopDeck à droite du rack ---
        actionShop = new ActionShopDeck(gameController, roundController, referee, this);
        actionShop.setPickOnBounds(false);
        StackPane.setAlignment(actionShop, Pos.CENTER);
        actionShop.setTranslateX(360);
        actionShop.setTranslateY(25);
        centerStack.getChildren().add(actionShop);

        setCenter(centerStack);
        BorderPane.setMargin(centerStack, new Insets(20));

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