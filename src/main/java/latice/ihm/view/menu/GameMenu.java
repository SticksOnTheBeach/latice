package latice.ihm.view.menu;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class GameMenu extends BorderPane {
	
	
	/* TODO : Menu qui s'affiche après avoir cliqué sur "Play" 
	 * Ce menu, indiquera et demandera dans des textField, le nom des joueurs,
	 * avec un bouton "+" à côté qui permettra d'ajouter un nouveau textfield afin d'ajouter 
	 * un nouveau joueur. 
	 * 
	 * PS : il y'aura deux textField minimum, étant donné qu'on ne peux jouer qu'à minimum deux, alors 2 noms attendus
	 * Le bouton addPlayer, sera à la position du nouveau joueur ajouté, étant donné que le bouton ajoute un nouveau
	 * joueur.
	 * */
	
	private Label lblInputName;
	private TextField txtPlayer1;
	private TextField txtPlayer2;
	private Button btnAddPlayer;
	
	public GameMenu() {
		
		lblInputName = new Label("Please enter player's name :");
		txtPlayer1 = new TextField("name..");
		txtPlayer2 = new TextField("name..");
	}
}
