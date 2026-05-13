package latice.ihm.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.view.menu.GameMenu;
import latice.ihm.view.menu.MenuPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Rack;
import latice.model.Referee;
import latice.ihm.view.MainPane;

public class GameMenuController {

    private Stage stage;
    private GameMenu gameMenu;

    public GameMenuController(Stage stage) {
        this.stage = stage;
    }

    public void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    // Retour au menu principal
    public void handleBack(ActionEvent e) {
        stage.setScene(new Scene(new MenuPane(), 1000, 700));
    }

    public void handleStart(ActionEvent e) {
        handleStart(gameMenu.getPlayerNames());
    }

    // Démarrer la partie
    public void handleStart(java.util.ArrayList<String> playerNames) {
        Referee referee = new Referee();

        // Créer les joueurs dynamiquement selon les noms saisis
        java.util.ArrayList<Player> players = new java.util.ArrayList<>();
        java.util.ArrayList<Rack> racks = new java.util.ArrayList<>();

        for (String name : playerNames) {
            Rack rack = new Rack();
            racks.add(rack);
            players.add(new Player(rack, name));
        }

        Board board = new Board();
        board.createGameBoard();

        Game game = new Game(players, referee);
        //game.shareTilesBetweenTwoPlayers(players.get(0), players.get(1));
        game.shareTilesDynamically();
        
        for (Player p : players) {
            p.getRack().addTileFromDeck(p.getDeck());
        }

        TileController tileController = new TileController(board);
        RoundController roundController = new RoundController(players);
        GameController gameController = new GameController(roundController, tileController);
        MainPane mainPane = new MainPane(board, game, tileController, roundController, gameController);
        mainPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #3e2723, #6d4c41);");

        stage.setScene(new Scene(mainPane, 1000, 900));
    }
}