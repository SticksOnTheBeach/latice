package latice.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.view.MainPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Rack;
import latice.model.Referee;

public class LaticeIhmApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Referee referee = new Referee();
        Rack rackJ1 = new Rack();
        Rack rackJ2 = new Rack();

        Player player1 = new Player(rackJ1, "Joueur 1");
        Player player2 = new Player(rackJ2, "Joueur 2");

        Board board = new Board();
        board.createGameBoard();

        Game game = new Game(player1, player2, referee);
        game.shareTilesBetweenTwoPlayers(player1, player2);

        rackJ1.addTileFromDeck(player1.getDeck());
        rackJ2.addTileFromDeck(player2.getDeck());

        MainPane mainPane = new MainPane(board, rackJ1);

        Scene scene = new Scene(mainPane, 800, 700);
        primaryStage.setTitle("Latice");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}