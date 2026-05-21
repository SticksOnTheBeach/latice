package latice.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.controller.GameMenuController;
import latice.ihm.view.menu.GameMenu;
import latice.ihm.view.menu.MenuPane;

public class LaticeIhmApp extends Application {

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

        GameMenu gameMenu = new GameMenu(
                e -> controller.handleBack(e),
                e -> controller.handleStart(e)
            );
        MenuPane mainMenu = new MenuPane(primaryStage);
        
        //GameMenu gameMenu = new GameMenu(e -> controller.handleBack(e));
        controller.setGameMenu(gameMenu);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Latice");
        primaryStage.setScene(new Scene(mainMenu, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}