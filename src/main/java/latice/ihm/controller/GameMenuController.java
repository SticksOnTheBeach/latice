package latice.ihm.controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.view.GamePane;
import latice.ihm.view.menu.PlayerMenu;
import latice.ihm.view.menu.MainMenuPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Rack;
import latice.model.Referee;

public class GameMenuController {

    private Stage stage;
    private PlayerMenu gameMenu;

    public GameMenuController(Stage stage) {
        this.stage = stage;
    }

    public void setGameMenu(PlayerMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    // Retour au menu principal
    public void handleBack(ActionEvent e) {
        stage.setScene(new Scene(new MainMenuPane(stage), 1000, 700));
    }

    public void handleStart(ActionEvent e) {
        handleStart(gameMenu.getPlayerNames());
    }

    // Démarrer la partie
    public void handleStart(ArrayList<String> playerNames) {

        // Créer les joueurs dynamiquement selon les noms saisis
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Rack> racks = new ArrayList<>();

        for (String name : playerNames) {
            Rack rack = new Rack();
            racks.add(rack);
            players.add(new Player(rack, name));
        }

        Board board = new Board();
        Referee referee = new Referee(board);

        Game game = new Game(players, referee, board);
        //game.shareTilesBetweenTwoPlayers(players.get(0), players.get(1));
        game.startGame();
        
        for (Player p : players) {
            p.getRack().addTileFromDeck(p.getDeck());
        }

        TileController tileController = new TileController(board, referee);
        RoundController roundController = new RoundController(players);
        GameController gameController = new GameController(roundController, tileController);
        GamePane mainPane = new GamePane(board, game, tileController, roundController, gameController, referee);
        mainPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #3e2723, #6d4c41);");

        stage.setScene(new Scene(mainPane, 1000, 900));
    }
}