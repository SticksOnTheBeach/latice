package latice.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.controller.TileController;
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
        
        /* CES TODO SONT JUSTE REPRÉSENTATIFS DES IDÉES QUE J'AI EU, PAR CONSÉQUENT, IL
         * EN VA DE SOI, QU'IL NE FAUT PAS IMPLÉMENTER, CRÉER LEURS MÉTHODES IÇI.*/

        /* TODO : Implémenter une méthodes en capacité de vérifier le nombres de joueur,
         * et qui, en conséquence ajoutera avec une boucle for ( qui, avec un index, parcourera
         * la liste des joueurs ) et qui ajoutera "tant" de rack pour "tant" de joueur.
        Implémenté également içi.*/



        /* TODO : implémenter une classe MenuPane, qui fera office de menu, sur ce menu,
         * nous pourrons ajouter le nombres de joueurs souhaité ( min 2 ), un bouton "+" sera à coté
         * du textField, et permettra d'ajouter un nouveau joueur, en indiquant son prénom.
         *
         *  PS : Réaliser d'abord sous format console, afin de vérifier que la méthodes (qui sera contenu dans Game) fonctionne correctemnt*/


       
        Player player1 = new Player(rackJ1, "Joueur 1");
        Player player2 = new Player(rackJ2, "Joueur 2");

        Board board = new Board();
        board.createGameBoard();

        Game game = new Game(player1, player2, referee);
        game.shareTilesBetweenTwoPlayers(player1, player2);
        
        // RACK
        rackJ1.addTileFromDeck(player1.getDeck());
        rackJ2.addTileFromDeck(player2.getDeck());
        
        // CONTROLLER
        TileController tileController = new TileController(board);
        MainPane mainPane = new MainPane(board, rackJ1, tileController, player1);

        Scene scene = new Scene(mainPane, 800, 700);
        
        mainPane.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #3e2723, #6d4c41);"
        );

        primaryStage.setTitle("Latice");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}