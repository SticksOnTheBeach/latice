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
import javafx.util.Duration;
import latice.ihm.view.style.Style;

public class PlayerMenu extends BorderPane {
    /* TODO : 2 possibilités pour les lignes des joueurs
     * soit faire que joueur 3 et joueur 4 soit déjà déclaré mais non affiché et donc que dès
     * que l'on clique sur le "+" ça s'affiche et donc un écouteur
     * INCONVÉNIENTS :
     *  - c'est "hardcoder", si plus tard on veut augmenter et laisser place à plus de joueurs,
     * on aura un problème qui est que ce sera moins modulaire, et moins optimisé
     *  - Les objets Player 3 et 4 existent en mémoire même s'ils ne jouent pas
     *  - Plus difficile à maintenir
     *
     * soit faire comme déjà implémenté, réaliser une boucle qui parcours la listes des joueurs et donc que pour
     * le i-ième joueur tu fais joueur i + 1, et que la liste des joueurs est géré dynamiquement
     */
    private static final int MAX_PLAYERS = 4;

    private ArrayList<String> playerNames = new ArrayList<>();
    private int currentPlayerIndex = -1;
    private ArrayList<String> playerColors = new ArrayList<>();
    private ArrayList<String> playersIcon = new ArrayList<>();

    private Label lblTitle;
    private VBox playersBox;
    private VBox advancedBox;
    private VBox centerBox;
    private HBox hbButtons;

    private ArrayList<Label> errorLabels = new ArrayList<>();

    private EventHandler<ActionEvent> onBack;
    private EventHandler<ActionEvent> onStart;

    public PlayerMenu(EventHandler<ActionEvent> onBack, EventHandler<ActionEvent> onStart) {
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

        btnBack.setStyle(Style.BTN_GHOST_STYLE);
        btnBack.setOnMouseEntered(e -> btnBack.setStyle(Style.BTN_GHOST_HOVER_STYLE));
        btnBack.setOnMouseExited(e  -> btnBack.setStyle(Style.BTN_GHOST_STYLE));
        
        btnStart.setStyle(Style.BTN_STYLE);
        btnStart.setOnMouseEntered(e -> btnStart.setStyle(Style.BTN_HOVER_STYLE));
        btnStart.setOnMouseExited(e  -> btnStart.setStyle(Style.BTN_STYLE));

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
        tf.setStyle(Style.INPUT_STYLE);
        tf.setPrefWidth(200);
        tf.textProperty().addListener((obs, oldVal, newVal) -> playerNames.set(i, newVal));

        Button btnOpt = new Button("⚙ options");
        btnOpt.setStyle(Style.BTN_GHOST_STYLE);
        btnOpt.setOnMouseEntered(e -> btnOpt.setStyle(Style.BTN_GHOST_HOVER_STYLE));
        btnOpt.setOnMouseExited(e -> btnOpt.setStyle(Style.BTN_GHOST_STYLE));
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
            btnAdd.setStyle(Style.BTN_GHOST_STYLE);
            String hoverAddStyle = Style.BTN_GHOST_STYLE + 
            	    "-fx-background-color: rgba(85,255,85,0.2); " +
            	    "-fx-border-color: #55ff55; " +
            	    "-fx-text-fill: #55ff55;";
            
            
            btnAdd.setOnMouseEntered(e -> btnAdd.setStyle(hoverAddStyle));
            btnAdd.setOnMouseExited(e -> btnAdd.setStyle(Style.BTN_GHOST_STYLE));
            btnAdd.setOnAction(e -> {
                playerNames.add("");
                playerColors.add("rgba(168,216,168,0.3)");
                refreshRows();
            });
            row.getChildren().add(btnAdd);
        }

        if (i >= 2) {
            Button btnRemove = new Button("×");
            String defaultRemoveStyle = Style.BTN_GHOST_STYLE + 
            	    "-fx-border-color: rgba(255,100,100,0.4); " + 
            	    "-fx-text-fill: rgba(255,120,120,0.8);";
            	    
            String hoverRemoveStyle = Style.BTN_GHOST_STYLE + 
            	    "-fx-background-color: rgba(255,100,100,0.2); " +
            	    "-fx-border-color: #ff5555; " + 
            	    "-fx-text-fill: #ff5555;";

            btnRemove.setStyle(defaultRemoveStyle);
            btnRemove.setOnMouseEntered(e -> btnRemove.setStyle(hoverRemoveStyle));
            btnRemove.setOnMouseExited(e -> btnRemove.setStyle(defaultRemoveStyle));
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

        Button btnRed    = new Button("🔴");
        Button btnBlue   = new Button("🔵");
        Button btnGreen  = new Button("🟢");
        Button btnYellow = new Button("🟡");

        btnRed.setStyle(Style.BTN_COLOR_STYLE);
        btnRed.setOnMouseEntered(e -> btnRed.setStyle(Style.BTN_COLOR_HOVER_STYLE));
        btnRed.setOnMouseExited(e -> btnRed.setStyle(Style.BTN_COLOR_STYLE));
        
        btnBlue.setStyle(Style.BTN_COLOR_STYLE);
        btnBlue.setOnMouseEntered(e -> btnBlue.setStyle(Style.BTN_COLOR_HOVER_STYLE));
        btnBlue.setOnMouseExited(e -> btnBlue.setStyle(Style.BTN_COLOR_STYLE));
        
        btnGreen.setStyle(Style.BTN_COLOR_STYLE);
        btnGreen.setOnMouseEntered(e -> btnGreen.setStyle(Style.BTN_COLOR_HOVER_STYLE));
        btnGreen.setOnMouseExited(e -> btnGreen.setStyle(Style.BTN_COLOR_STYLE));
        
        btnYellow.setStyle(Style.BTN_COLOR_STYLE);
        btnYellow.setOnMouseEntered(e -> btnYellow.setStyle(Style.BTN_COLOR_HOVER_STYLE));
        btnYellow.setOnMouseExited(e -> btnYellow.setStyle(Style.BTN_COLOR_STYLE));
        
        btnRed.setOnAction(e    -> { playerColors.set(currentPlayerIndex, "#ff5555"); refreshRows(); });
        btnBlue.setOnAction(e   -> { playerColors.set(currentPlayerIndex, "#55aaff"); refreshRows(); });
        btnGreen.setOnAction(e  -> { playerColors.set(currentPlayerIndex, "#55ff55"); refreshRows(); });
        btnYellow.setOnAction(e -> { playerColors.set(currentPlayerIndex, "#ffff55"); refreshRows(); });

        GridPane colorGrid = new GridPane();
        colorGrid.setHgap(8);
        colorGrid.setVgap(8);
        colorGrid.add(btnRed,    0, 0);
        colorGrid.add(btnBlue,   1, 0);
        colorGrid.add(btnGreen,  2, 0);
        colorGrid.add(btnYellow, 3, 0);

        Label lblIcon = new Label("Icône :");
        lblIcon.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 12px;");

        Button btnClose = new Button("← Fermer");
        btnClose.setStyle(Style.BTN_GHOST_STYLE);
        btnClose.setOnMouseEntered(e -> btnClose.setStyle(Style.BTN_GHOST_HOVER_STYLE));
        btnClose.setOnMouseExited(e -> btnClose.setStyle(Style.BTN_GHOST_STYLE));
        btnClose.setOnAction(e -> closeAdvanced());

        box.getChildren().addAll(lblTitle, lblColor, colorGrid, lblIcon, btnClose);

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
    
    /*public boolean colorIsAlreadyPick() {
    	if 
    }*/
    
    private boolean handleStart() {
        boolean correct = true;

        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).isEmpty()) {
                errorLabels.get(i).setVisible(true);
                correct = false;
            } else {
                errorLabels.get(i).setVisible(false);
            }
        }

        if (correct) {
            System.out.println("[GameMenu] Démarrage avec : " + playerNames);
        }

        return correct;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}