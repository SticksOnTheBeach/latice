package latice.ihm.view.menu;

import java.util.ArrayList;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameMenu extends BorderPane {
	/* TODO : 2 possibilités pour les lignes des joueurs
	 * soit faire que joueur 3 et joueur 4 soit déjà déclaré mais non affiché et donc que dès 
	 * que l'on clique sur le "+" ça s'affiche et donc un écouteur
	 * INCONVÉNIENTS : 
	 *  - c'est "hardcodeer", si plus tard on veut augmenter et laisser place à plus de joueurs, 
	 * on aura un problème qui est que ce sera moins modulaire, et moins optimisé
	 *  - Les objets Player 3 et 4 existent en mémoire même s'ils ne jouent pas
	 *  - Plus difficile à maintenir
	 *  
	 * 
	 * soit faire comme déjà implémenté, réaliser une boucle qui parcours la listes des joueurs et donc que pour 
	 * le i-ième joueur tu fais joueur i + 1, et que la liste des joueurs est géré dynamiquement
	 * */
    private static final int MAX_PLAYERS = 4;

    private ArrayList<String> playerNames = new ArrayList<>();
    private int currentPlayerIndex = -1;
    private ArrayList<String> playerColors = new ArrayList<>();

    private Label lblTitle;
    private VBox playersBox;
    private VBox advancedBox;
    private VBox centerBox;
    private HBox hbButtons;
    
    // private Label msgError;
    private ArrayList<Label> errorLabels = new ArrayList<>();

    private EventHandler<ActionEvent> onBack;
    private EventHandler<ActionEvent> onStart;

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
        "-fx-border-radius: 50em; " +
        "-fx-background-radius: 50em; " +
        "-fx-min-width: 28px; " +
        "-fx-min-height: 28px; " +
        "-fx-max-width: 28px; " +
        "-fx-max-height: 28px; " +
        "-fx-padding: 0; " +
        "-fx-alignment: center; " +
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

    public GameMenu(EventHandler<ActionEvent> onBack, EventHandler<ActionEvent> onStart) {
        this.onBack = onBack;
        this.onStart = onStart;

        playerNames.add("");
        playerNames.add("");
        playerColors.add("rgba(168,216,168,0.3)");
        playerColors.add("rgba(168,216,168,0.3)");

        setStyle("-fx-background-color: #1a2e35;");

        lblTitle = new Label("> GAME SETUP");
        lblTitle.setStyle(
            "-fx-font-family: \"Courier New\"; " +
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #a8d8a8;"
        );

        playersBox = new VBox(12);
        playersBox.setAlignment(Pos.CENTER);
        refreshRows();

        Button btnBack  = new Button("← Retour");
        Button btnStart = new Button("> Démarrer");

        btnBack.setStyle(btnGhostStyle);
        btnStart.setStyle(btnStyle);

        btnStart.setOnMouseEntered(e -> btnStart.setStyle(btnHoverStyle));
        btnStart.setOnMouseExited(e  -> btnStart.setStyle(btnStyle));

        // Branchement des handlers
        btnBack.setOnAction(onBack);
        btnStart.setOnAction(e -> {
        	if (handleStart()) {
                onStart.handle(e);
            }
        });

        hbButtons = new HBox(16, btnBack, btnStart);
        hbButtons.setAlignment(Pos.CENTER);

        centerBox = new VBox(30, lblTitle, playersBox, hbButtons);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        advancedBox = buildAdvancedBox();
        advancedBox.setTranslateX(400);

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        StackPane.setAlignment(advancedBox, Pos.CENTER_RIGHT);
        root.getChildren().addAll(centerBox, advancedBox);

        setCenter(root);
    }
    
    private void refreshRows() {
        playersBox.getChildren().clear();
        for (int i = 0; i < playerNames.size(); i++) {
            playersBox.getChildren().add(buildPlayerRow(i));
        }
    }

    private HBox buildPlayerRow(int i) {
        Label lblNum = new Label((i + 1) + ".");
        lblNum.setStyle(
            "-fx-font-family: \"Courier New\"; " +
            "-fx-text-fill: #a8d8a8; " +
            "-fx-font-size: 14px; " +
            "-fx-min-width: 20px;"
        );
        
        Label lblError = new Label("ERROR : PLEASE ENTER A NAME");
        lblError.setStyle(
            "-fx-text-fill: #ff5555; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-font-size: 12px;"
        );
        lblError.setVisible(false);

        if (i < errorLabels.size()) {
            errorLabels.set(i, lblError);
        } else {
            errorLabels.add(lblError);
        }

        TextField tf = new TextField(playerNames.get(i));
        tf.setPromptText("Joueur " + (i + 1));
        tf.setStyle(inputStyle);
        tf.setPrefWidth(200);
        tf.textProperty().addListener((obs, oldVal, newVal) -> playerNames.set(i, newVal));

        Button btnOpt = new Button("⚙ options");
        btnOpt.setStyle(btnGhostStyle);
        btnOpt.setOnAction(e -> openAdvanced(i));
        // VBox afin d'accueillir les messages d'erreurs	
        VBox tfBox = new VBox(4, tf, lblError);
        
        HBox row = new HBox(10, lblNum, tfBox, btnOpt);
        row.setAlignment(Pos.CENTER);
        row.setMaxWidth(450);
        row.setPadding(new Insets(10, 20, 10, 20));
        row.setStyle(
            "-fx-border-color: " + playerColors.get(i) + "; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 10px; " +
            "-fx-background-radius: 10px;"
        );

        if (i == 1 && playerNames.size() < MAX_PLAYERS) {
            Button btnAdd = new Button("+");
            btnAdd.setStyle(btnGhostStyle);
            btnAdd.setOnAction(e -> {
                playerNames.add("");
                playerColors.add("rgba(168,216,168,0.3)");
                refreshRows();
            });
            row.getChildren().add(btnAdd);
        }

        if (i >= 2) {
            Button btnRemove = new Button("×");
            btnRemove.setStyle(btnGhostStyle +
                "-fx-border-color: rgba(255,100,100,0.4); " +
                "-fx-text-fill: rgba(255,120,120,0.8);"
            );
            btnRemove.setOnAction(e -> {
                playerNames.remove(i);
                playerColors.remove(i);
                refreshRows();
            });
            row.getChildren().add(btnRemove);
        }

        return row;
    }

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

        Label lblColor = new Label("Couleur :");
        lblColor.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 12px;");

        Button btnRed = new Button("🔴");
        Button btnBlue = new Button("🔵");
        Button btnGreen = new Button("🟢");
        Button btnYellow = new Button("🟡");

        btnRed.setStyle(btnColorStyle);
        btnBlue.setStyle(btnColorStyle);
        btnGreen.setStyle(btnColorStyle);
        btnYellow.setStyle(btnColorStyle);

        btnRed.setOnAction(e    -> { playerColors.set(currentPlayerIndex, "#ff5555"); refreshRows(); });
        btnBlue.setOnAction(e   -> { playerColors.set(currentPlayerIndex, "#55aaff"); refreshRows(); });
        btnGreen.setOnAction(e  -> { playerColors.set(currentPlayerIndex, "#55ff55"); refreshRows(); });
        btnYellow.setOnAction(e -> { playerColors.set(currentPlayerIndex, "#ffff55"); refreshRows(); });

        GridPane colorGrid = new GridPane();
        colorGrid.setHgap(8);
        colorGrid.setVgap(8);
        colorGrid.add(btnRed, 0, 0);
        colorGrid.add(btnBlue, 1, 0);
        colorGrid.add(btnGreen, 2, 0);
        colorGrid.add(btnYellow, 3, 0);

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

        btnFox.setOnAction(e    -> System.out.println("Icône Renard pour joueur "  + (currentPlayerIndex + 1)));
        btnDragon.setOnAction(e -> System.out.println("Icône Dragon pour joueur "  + (currentPlayerIndex + 1)));
        btnEagle.setOnAction(e  -> System.out.println("Icône Aigle pour joueur "   + (currentPlayerIndex + 1)));
        btnWolf.setOnAction(e   -> System.out.println("Icône Loup pour joueur "    + (currentPlayerIndex + 1)));

        HBox iconRow = new HBox(8, btnFox, btnDragon, btnEagle, btnWolf);

        Button btnClose = new Button("← Fermer");
        btnClose.setStyle(btnGhostStyle);
        btnClose.setOnAction(e -> closeAdvanced());

        box.getChildren().addAll(lblTitle, lblColor, colorGrid, lblIcon, iconRow, btnClose);

        return box;
    }

    private void openAdvanced(int idx) {
        this.currentPlayerIndex = idx;
        centerBox.setMouseTransparent(true);

        TranslateTransition slideLeftPlayers = new TranslateTransition(Duration.millis(420), playersBox);
        slideLeftPlayers.setToX(-200);
        TranslateTransition slideLeftBtns = new TranslateTransition(Duration.millis(420), hbButtons);
        slideLeftBtns.setToX(-200);
        TranslateTransition slideLeftTitle = new TranslateTransition(Duration.millis(420), lblTitle);
        slideLeftTitle.setToX(-200);

        new ParallelTransition(slideLeftPlayers, slideLeftBtns, slideLeftTitle).play();

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(420), advancedBox);
        slideIn.setToX(0);
        slideIn.play();
    }

    private void closeAdvanced() {
        centerBox.setMouseTransparent(false);

        TranslateTransition slideBackPlayers = new TranslateTransition(Duration.millis(420), playersBox);
        slideBackPlayers.setToX(0);
        TranslateTransition slideBackBtns = new TranslateTransition(Duration.millis(420), hbButtons);
        slideBackBtns.setToX(0);
        TranslateTransition slideBackTitle = new TranslateTransition(Duration.millis(420), lblTitle);
        slideBackTitle.setToX(0);

        new ParallelTransition(slideBackPlayers, slideBackBtns, slideBackTitle).play();

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(420), advancedBox);
        slideOut.setToX(400);
        slideOut.play();
    }

    private boolean handleStart() {
    	boolean correct = true;
    	
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).isEmpty()) {
                // playerNames.set(i, "Joueur " + (i + 1));
            	errorLabels.get(i).setVisible(true);
            	correct = false;
            } else {
                errorLabels.get(i).setVisible(false);
            }
            
        } if (correct) {
            System.out.println("[GameMenu] Démarrage avec : " + playerNames);
        }

        return correct;
}

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}