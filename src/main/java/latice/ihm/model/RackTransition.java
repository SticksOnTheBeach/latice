package latice.ihm.model;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.view.model.DeckBox;
import latice.ihm.view.model.RackBox;
import latice.model.Player;
import latice.model.Referee;

public class RackTransition {
    private static final int ANIM_DURATION = 500;
    private static final double BLUR_RADIUS = 15.0;

    private static final double OFFSET_BAS    =  360;
    private static final double OFFSET_HAUT   = -360;
    private static final double OFFSET_GAUCHE = -440;
    private static final double OFFSET_DROITE =  440;

    private ArrayList<HBox>    playerSlots;
    private ArrayList<RackBox> rackBoxes;
    private ArrayList<DeckBox> deckBoxes;

    private ArrayList<Player> players;
    private int               currentPlayerIndex;
    private StackPane         container;
    private RoundController   roundController;
    private GameController    gameController;

    public RackTransition(StackPane container, ArrayList<Player> players, int currentPlayerIndex,
                          RoundController roundController, GameController gameController, Referee referee) {
        this.container          = container;
        this.players            = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.roundController    = roundController;
        this.gameController     = gameController;
        this.playerSlots        = new ArrayList<>();
        this.rackBoxes          = new ArrayList<>();
        this.deckBoxes          = new ArrayList<>();

        for (Player player : players) {
            RackBox rackBox = new RackBox(player.getRack(), player.getColor());
            DeckBox deckBox = new DeckBox(player);

            HBox slot = new HBox(15, deckBox, rackBox);
            slot.setAlignment(Pos.CENTER);
            slot.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            slot.setPickOnBounds(false);

            rackBoxes.add(rackBox);
            deckBoxes.add(deckBox);
            playerSlots.add(slot);
            container.getChildren().add(slot);
        }

        firstRackPosition();
    }

    /**
     * Applique ou retire le flou sur un slot selon s'il est actif ou non.
     */
    private void applyBlurForOthersPlayers(HBox slot, boolean isCurrentPlayer) {
        if (isCurrentPlayer) {
            slot.setEffect(null);
        } else {
            slot.setEffect(new GaussianBlur(BLUR_RADIUS));
        }
    }

    private void firstRackPosition() {
        int nbJoueurs = players.size();
        for (int i = 0; i < nbJoueurs; i++) {
            HBox     slot             = playerSlots.get(i);
            int      positionRelative = (i - currentPlayerIndex + nbJoueurs) % nbJoueurs;
            double[] offsets          = getOffsets(positionRelative, nbJoueurs);

            slot.setTranslateX(offsets[0]);
            slot.setTranslateY(offsets[1]);
            slot.setRotate(getTargetAngle(positionRelative, nbJoueurs));

            if (i == currentPlayerIndex) {
                slot.setMouseTransparent(false);
                slot.setPickOnBounds(false);
                slot.setOpacity(1.0);
                applyBlurForOthersPlayers(slot, true);
            } else {
                slot.setMouseTransparent(true);
                slot.setOpacity(0.5);
                applyBlurForOthersPlayers(slot, false);
            }
        }
    }

    public void animateToPlayer(int nouvelIndex) {
        this.currentPlayerIndex = nouvelIndex;
        int nbPlayers = players.size();
        ParallelTransition allTransitions = new ParallelTransition();

        for (int i = 0; i < nbPlayers; i++) {
            HBox     slot        = playerSlots.get(i);
            int      relativePos = (i - currentPlayerIndex + nbPlayers) % nbPlayers;
            double[] offsets     = getOffsets(relativePos, nbPlayers);

            TranslateTransition tt = new TranslateTransition(Duration.millis(ANIM_DURATION), slot);
            tt.setToX(offsets[0]);
            tt.setToY(offsets[1]);

            double currentAngle = slot.getRotate();
            double targetAngle  = getTargetAngle(relativePos, nbPlayers);
            double angleDiff    = targetAngle - (currentAngle % 360);
            if (angleDiff >  180) angleDiff -= 360;
            if (angleDiff < -180) angleDiff += 360;

            RotateTransition rt = new RotateTransition(Duration.millis(ANIM_DURATION), slot);
            rt.setByAngle(angleDiff);

            FadeTransition ft = new FadeTransition(Duration.millis(ANIM_DURATION), slot);
            if (i == currentPlayerIndex) {
                slot.setMouseTransparent(false);
                slot.setPickOnBounds(false);
                ft.setToValue(1.0);
                applyBlurForOthersPlayers(slot, true);
            } else {
                slot.setMouseTransparent(true);
                ft.setToValue(0.5);
                applyBlurForOthersPlayers(slot, false);
            }

            allTransitions.getChildren().addAll(tt, rt, ft);
        }

        allTransitions.play();
    }

    private double[] getOffsets(int positionRelative, int nbJoueurs) {
        if (nbJoueurs == 2) {
            switch (positionRelative) {
                case 0:  return new double[]{ 0, OFFSET_BAS };
                case 1:  return new double[]{ 0, OFFSET_HAUT };
                default: return new double[]{ 0, 0 };
            }
        } else if (nbJoueurs == 3) {
            switch (positionRelative) {
                case 0:  return new double[]{ 0,             OFFSET_BAS };
                case 1:  return new double[]{ OFFSET_DROITE, 0 };
                case 2:  return new double[]{ OFFSET_GAUCHE, 0 };
                default: return new double[]{ 0, 0 };
            }
        } else {
            switch (positionRelative) {
                case 0:  return new double[]{ 0,             OFFSET_BAS };
                case 1:  return new double[]{ OFFSET_DROITE, 0 };
                case 2:  return new double[]{ 0,             OFFSET_HAUT };
                case 3:  return new double[]{ OFFSET_GAUCHE, 0 };
                default: return new double[]{ 0, 0 };
            }
        }
    }

    private double getTargetAngle(int positionRelative, int nbJoueurs) {
        if (nbJoueurs == 2) {
            return positionRelative == 1 ? 180 : 0;
        } else if (nbJoueurs == 3) {
            switch (positionRelative) {
                case 0:  return 0;
                case 1:  return 90;
                case 2:  return -90;
                default: return 0;
            }
        } else {
            switch (positionRelative) {
                case 0:  return 0;
                case 1:  return 90;
                case 2:  return 180;
                case 3:  return -90;
                default: return 0;
            }
        }
    }

    public void rafraichirRack(int playerIndex) {
        HBox    slot    = playerSlots.get(playerIndex);
        RackBox oldRack = rackBoxes.get(playerIndex);
        slot.getChildren().remove(oldRack);

        RackBox newRack = new RackBox(players.get(playerIndex).getRack(), players.get(playerIndex).getColor());
        rackBoxes.set(playerIndex, newRack);
        slot.getChildren().add(1, newRack);
        deckBoxes.get(playerIndex).refresh();
        if (playerIndex == currentPlayerIndex) {
            slot.setMouseTransparent(false);
            slot.setPickOnBounds(false);
            slot.setOpacity(1.0);
        } else {
            slot.setMouseTransparent(true);
            slot.setOpacity(0.5);
        }
    }
}