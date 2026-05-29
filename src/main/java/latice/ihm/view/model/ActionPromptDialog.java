package latice.ihm.view.model;

import java.util.Optional;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class ActionPromptDialog {

    private static final int COUT_ACTION = 2;

    /**
     * Affiche une boîte de dialogue demandant au joueur s'il veut acheter une action.
     *
     * @return true si le joueur clique sur "Oui", false sinon
     */
    //TODO : ameliorer les appels de cette methode pour rendre le jeu plsu agreable
    public boolean askBuyAction() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Action Shop");

        // Style de la dialog pane
        DialogPane pane = dialog.getDialogPane();
        pane.setStyle(
                "-fx-background-color: rgba(20, 20, 30, 0.85); " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: rgba(255,255,255,0.15); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-padding: 24px 28px;"
        );

        // Contenu
        Label lblMsg = new Label("Do you want to buy an action?");
        lblMsg.setStyle("-fx-text-fill: rgba(255,255,255,0.65); -fx-font-size: 14px;");

        Label lblCost = new Label("⬤  Cost : "+ COUT_ACTION + "points");
        lblCost.setStyle(
                "-fx-text-fill: rgba(250,200,100,0.9); " +
                        "-fx-font-size: 13px; -fx-font-weight: 500; " +
                        "-fx-background-color: rgba(255,255,255,0.07); " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: rgba(255,255,255,0.12); " +
                        "-fx-border-radius: 8px; -fx-border-width: 1px; " +
                        "-fx-padding: 6px 12px;"
        );

        VBox content = new VBox(12, lblMsg, lblCost);
        pane.setContent(content);

        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo  = new ButtonType("No",  ButtonBar.ButtonData.NO);
        pane.getButtonTypes().setAll(btnYes, btnNo);
        String baseYes = "-fx-background-color: rgba(255,255,255,0.12); -fx-background-radius: 20px; " +
                "-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 20px; " +
                "-fx-border-width: 1px; -fx-text-fill: white; -fx-padding: 7px 22px; -fx-cursor: hand;";
        String baseNo  = "-fx-background-color: transparent; -fx-background-radius: 20px; " +
                "-fx-border-color: rgba(255,255,255,0.15); -fx-border-radius: 20px; " +
                "-fx-border-width: 1px; -fx-text-fill: rgba(255,255,255,0.5); -fx-padding: 7px 22px; -fx-cursor: hand;";

        Button yesBtn = (Button) pane.lookupButton(btnYes);
        Button noBtn  = (Button) pane.lookupButton(btnNo);
        yesBtn.setStyle(baseYes);
        noBtn.setStyle(baseNo);

        // Fond de la scène transparent pour afficher le style plus haut
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);

        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() && result.get() == btnYes;
    }

}