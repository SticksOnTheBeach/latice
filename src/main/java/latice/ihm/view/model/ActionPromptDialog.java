package latice.ihm.view.model;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ActionPromptDialog {

    private static final int COUT_ACTION = 2;

    /**
     * Affiche une boîte de dialogue demandant au joueur s'il veut acheter une action.
     *
     * @return true si le joueur clique sur "Oui", false sinon
     */
    //TODO : ameliorer les apels de cette methode pour rendre le jeu plsu agreable 
    public boolean askBuyAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Action Shop");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to buy an action ? (Cost : " + COUT_ACTION + " points)");


        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == btnYes;
    }
}