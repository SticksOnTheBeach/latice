package latice.ihm.view.menu;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameMenu extends BorderPane {

    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;

    // Liste des noms de joueurs
    private ArrayList<String> playerNames = new ArrayList<>();

    // Les deux zones principales
    private VBox playersBox;
    private VBox advancedBox;
    private VBox centerBox;

    // Styles boutons
    String btnStyle =
        "-fx-font-size: 16px; " +
        "-fx-font-weight: bold; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-text-fill: #1a2e35; " +
        "-fx-background-color: #a8d8a8; " +
        "-fx-background-radius: 0px; " +
        "-fx-border-color: #4a9e4a #1a5e1a #1a5e1a #4a9e4a; " +
        "-fx-border-width: 3px; " +
        "-fx-padding: 10px 30px; " +
        "-fx-cursor: hand; " +
        "-fx-min-width: 160px;";

    String btnHoverStyle =
        "-fx-font-size: 16px; " +
        "-fx-font-weight: bold; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-text-fill: #1a2e35; " +
        "-fx-background-color: #c8f8c8; " +
        "-fx-background-radius: 0px; " +
        "-fx-border-color: #4a9e4a #1a5e1a #1a5e1a #4a9e4a; " +
        "-fx-border-width: 3px; " +
        "-fx-padding: 10px 30px; " +
        "-fx-cursor: hand; " +
        "-fx-min-width: 160px;";

    String btnGhostStyle =
        "-fx-font-size: 14px; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-background-color: transparent; " +
        "-fx-border-color: rgba(168,216,168,0.4); " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-text-fill: rgba(168,216,168,0.8); " +
        "-fx-padding: 8px 14px; " +
        "-fx-cursor: hand;";
    
    String btnColorStyle =
    		"-fx-font-size: 14px; " +
    		"-fx-font-family: \"Courier New\"; " +
    		"-fx-background-color: transparent; " +
    		"-fx-border-color: rgba(168,216,168,0.4); " +
    		"-fx-border-radius: 500px; " +
    		"-fx-background-radius: 6px; " +
    		"-fx-text-fill: rgba(168,216,168,0.8); " +
    		"-fx-cursor: hand;";

    String inputStyle =
        "-fx-background-color: rgba(255,255,255,0.08); " +
        "-fx-border-color: rgba(168,216,168,0.3); " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 14px; " +
        "-fx-padding: 8px 12px;";

    public GameMenu(Runnable onBack) {

        // 2 joueurs par défaut
        playerNames.add("");
        playerNames.add("");

        setStyle("-fx-background-color: #1a2e35;");

        /* TITRE */
        Label lblTitle = new Label("> GAME SETUP");
        lblTitle.setStyle(
            "-fx-font-family: \"Courier New\"; " +
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #a8d8a8;"
        );

        /* ZONE JOUEURS */
        playersBox = new VBox(12);
        playersBox.setAlignment(Pos.CENTER);
        refreshRows();

        /* BOUTONS DU BAS */
        Button btnBack  = new Button("← Retour");
        Button btnStart = new Button("> Démarrer");

        btnBack.setStyle(btnGhostStyle);
        btnStart.setStyle(btnStyle);

        btnStart.setOnMouseEntered(e -> btnStart.setStyle(btnHoverStyle));
        btnStart.setOnMouseExited(e  -> btnStart.setStyle(btnStyle));

        btnBack.setOnAction(e -> onBack.run());
        btnStart.setOnAction(e -> handleStart());

        HBox hbButtons = new HBox(16, btnBack, btnStart);
        hbButtons.setAlignment(Pos.CENTER);

        /* COLONNE CENTRALE */
        centerBox = new VBox(30, lblTitle, playersBox, hbButtons);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        /* PANNEAU AVANCÉ (invisible, à droite) */
        advancedBox = buildAdvancedBox();
        advancedBox.setTranslateX(400);

        /* STACKPANE : superpose centerBox et advancedBox */
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        StackPane.setAlignment(advancedBox, Pos.CENTER_RIGHT);
        root.getChildren().addAll(centerBox, advancedBox);

        setCenter(root);
    }

    // -------------------------------------------------------------------------
    // Construction des lignes joueurs
    // -------------------------------------------------------------------------

    private void refreshRows() {
        playersBox.getChildren().clear();

        for (int i = 0; i < playerNames.size(); i++) {
            final int idx = i;

            /* Numéro */
            Label lblNum = new Label((i + 1) + ".");
            lblNum.setStyle(
                "-fx-font-family: \"Courier New\"; " +
                "-fx-text-fill: #a8d8a8; " +
                "-fx-font-size: 14px; " +
                "-fx-min-width: 20px;"
            );

            /* Champ nom */
            TextField tf = new TextField(playerNames.get(i));
            tf.setPromptText("Joueur " + (i + 1));
            tf.setStyle(inputStyle);
            tf.setPrefWidth(200);
            tf.textProperty().addListener((obs, oldVal, newVal) -> playerNames.set(idx, newVal));

            /* Bouton options */
            Button btnOpt = new Button("⚙ options");
            btnOpt.setStyle(btnGhostStyle);
            btnOpt.setOnAction(e -> openAdvanced(idx));

            /* Ligne du joueur */
            HBox row = new HBox(10, lblNum, tf, btnOpt);
            row.setAlignment(Pos.CENTER);

            /* Bouton + sur la ligne du joueur 2 uniquement */
            if (i == 1 && playerNames.size() < MAX_PLAYERS) {
                Button btnAdd = new Button("+");
                btnAdd.setStyle(btnGhostStyle);
                btnAdd.setOnAction(e -> {
                    playerNames.add("");
                    refreshRows();
                });
                row.getChildren().add(btnAdd);
            }

            /* Bouton × pour les joueurs 3 et 4 */
            if (i >= 2) {
                Button btnRemove = new Button("×");
                btnRemove.setStyle(
                    btnGhostStyle +
                    "-fx-border-color: rgba(255,100,100,0.4); " +
                    "-fx-text-fill: rgba(255,120,120,0.8);"
                );
                btnRemove.setOnAction(e -> {
                    playerNames.remove(idx);
                    refreshRows();
                });
                row.getChildren().add(btnRemove);
            }

            playersBox.getChildren().add(row);
        }
    }

    // -------------------------------------------------------------------------
    // Panneau options avancées
    // -------------------------------------------------------------------------

    private VBox buildAdvancedBox() {

        VBox box = new VBox(20);
        box.setPadding(new Insets(40, 30, 40, 30));
        box.setMaxWidth(280);
        box.setStyle(
            "-fx-background-color: #243b44; " +
            "-fx-border-color: rgba(168,216,168,0.15); " +
            "-fx-border-width: 0 0 0 1;"
        );

        Label lblTitle = new Label("> Options joueur");
        lblTitle.setStyle(
            "-fx-font-family: \"Courier New\"; " +
            "-fx-font-size: 15px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #a8d8a8;"
        );

        /* Choix de couleur */
        Label lblColor = new Label("Couleur :");
        lblColor.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 12px;");

        Button btnRed    = new Button("🔴");
        Button btnBlue   = new Button("🔵");
        Button btnGreen  = new Button("🟢");
        Button btnYellow = new Button("🟡");

        btnRed.setStyle(btnColorStyle);
        btnBlue.setStyle(btnColorStyle);
        btnGreen.setStyle(btnColorStyle);
        btnYellow.setStyle(btnColorStyle);

        GridPane colorGrid = new GridPane();
        colorGrid.setHgap(8);
        colorGrid.setVgap(8);
        colorGrid.add(btnRed,    0, 0);
        colorGrid.add(btnBlue,   1, 0);
        colorGrid.add(btnGreen,  0, 1);
        colorGrid.add(btnYellow, 1, 1);

        /* Choix d'icône */
        Label lblIcon = new Label("Icône :");
        lblIcon.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 12px;");

        Button btnFox    = new Button("🦊");
        Button btnDragon = new Button("🐉");
        Button btnEagle  = new Button("🦅");
        Button btnWolf   = new Button("🐺");

        btnFox.setStyle(btnGhostStyle);
        btnDragon.setStyle(btnGhostStyle);
        btnEagle.setStyle(btnGhostStyle);
        btnWolf.setStyle(btnGhostStyle);

        HBox iconRow = new HBox(8, btnFox, btnDragon, btnEagle, btnWolf);

        /* Bouton fermer */
        Button btnClose = new Button("← Fermer");
        btnClose.setStyle(btnGhostStyle);
        btnClose.setOnAction(e -> closeAdvanced());

        box.getChildren().addAll(lblTitle, lblColor, colorGrid, lblIcon, iconRow, btnClose);

        return box;
    }

    private void openAdvanced(int idx) {
    	centerBox.setMouseTransparent(true);
    	
        // Glisse les champs vers la gauche
        TranslateTransition slideLeft = new TranslateTransition(Duration.millis(420), playersBox);
        slideLeft.setToX(-200);
        slideLeft.play();

        // Fait apparaître le panneau avancé depuis la droite
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(420), advancedBox);
        slideIn.setToX(0);
        slideIn.play();
    }

    private void closeAdvanced() {
    	centerBox.setMouseTransparent(false);
    	
        // Remet les champs au centre
        TranslateTransition slideBack = new TranslateTransition(Duration.millis(420), playersBox);
        slideBack.setToX(0);
        slideBack.play();

        // Cache le panneau avancé vers la droite
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(420), advancedBox);
        slideOut.setToX(400);
        slideOut.play();
    }

    // -------------------------------------------------------------------------
    // Démarrer la partie
    // -------------------------------------------------------------------------

    private void handleStart() {
        // Remplace les noms vides par un nom par défaut
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).isBlank()) {
                playerNames.set(i, "Joueur " + (i + 1));
            }
        }
        System.out.println("[GameMenu] Démarrage avec : " + playerNames);
        // TODO : créer les Player et lancer MainPane
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}