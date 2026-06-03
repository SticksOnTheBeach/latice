package latice.ihm;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import latice.ihm.controller.GameMenuController;
import latice.ihm.view.menu.MainMenuPane;
import latice.ihm.view.menu.PlayerMenu;

public class LaticeIhmApp extends Application {
	
	private MediaPlayer mediaPlayer;
	
    @Override
    public void start(Stage primaryStage) {
    	
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


        GameMenuController controller = new GameMenuController(primaryStage);

        PlayerMenu gameMenu = new PlayerMenu(
                e -> controller.handleBack(e),
                e -> controller.handleStart(e)
            );
        MainMenuPane mainMenu = new MainMenuPane(primaryStage);
        
        //GameMenu gameMenu = new GameMenu(e -> controller.handleBack(e));
        controller.setGameMenu(gameMenu, mainMenu);
        
        String musicFile = getClass().getResource("/images/menu/music.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);  // ← pas "MediaPlayer mediaPlayer = ..."
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnError(() -> {
            System.err.println("Erreur MediaPlayer : " + mediaPlayer.getError());
        });

        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Latice");
        primaryStage.setScene(new Scene(mainMenu, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);
    }
}