package latice.ihm.model;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.view.model.DeckBox;
import latice.ihm.view.model.RackBox;
import latice.model.Player;

/**
 * RackTransition — gère le positionnement et l'animation des racks autour du board.
 *
 * Selon le nombre de joueurs :
 *   2 joueurs : bas (joueur qui joue ) + haut
 *   3 joueurs : bas (joueur qui joue ) + gauche + droite
 *   4 joueurs : bas (joueur qui joue ) + haut + gauche + droite
 *
 * Quand le tour change, tous les racks glissent vers leur nouvelle position
 * avec une TranslateTransition fluide.
 * Le joueur actif atterrit toujours en bas.
 *
 * Chaque joueur a un conteneur HBox contenant le DeckBox (le deck) et le rackbox ( le rack): [ DeckBox | RackBox ]
 * C'est ce HBox qui est animé et positionné autour du board.
 */
public class RackTransition {
    private static final int ANIM_DURATION = 500;

    // Décalages en pixels pour chaque position autour du board
    // valeurs sont ajustées selon la taille du board (environ 540x540)
    private static final double OFFSET_BAS =  360;
    private static final double OFFSET_HAUT = -360;
    private static final double OFFSET_GAUCHE = -440;
    private static final double OFFSET_DROITE =  440;

    // MODIFICATION : on anime les HBox qui contiennent le rack et le deck de chaque joueur(DeckBox + RackBox) au lieu de RackBox seuls
    private ArrayList<HBox> playerSlots;
    private ArrayList<RackBox> rackBoxes;

    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private StackPane          container;
    private RoundController    roundController;
    private GameController     gameController;

    /**
     * Constructeur.
     *
     * @param container          Le StackPane qui contient le board ET les racks
     * @param players            La liste des joueurs dans l'ordre
     * @param currentPlayerIndex L'index du joueur qui commence
     * @param roundController    Nécessaire pour DeckBox
     * @param gameController     Nécessaire pour DeckBox
     */
    public RackTransition(StackPane container, ArrayList<Player> players, int currentPlayerIndex, RoundController roundController, GameController gameController) {
        this.container = container;
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.roundController = roundController;
        this.gameController = gameController;
        this.playerSlots = new ArrayList<>();
        this.rackBoxes = new ArrayList<>();

        // Crée un slot HBox [ DeckBox | RackBox ] pour chaque joueur
        for (Player player : players) {
            RackBox rackBox = new RackBox(player.getRack());
            DeckBox deckBox = new DeckBox(roundController, gameController);

            // DeckBox à gauche, RackBox à droite
            HBox slot = new HBox(15, deckBox, rackBox);
            slot.setAlignment(Pos.CENTER);
            
            slot.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

            rackBoxes.add(rackBox);
            playerSlots.add(slot);
            container.getChildren().add(slot);
        }

        // Positionne les racks "aléatoirement" ( en fonction du joueur qui commence )
        firstRackPosition();
    }

    /**
     * Place les racks à leur position initiale sans animation.
     */
    private void firstRackPosition() {
        int nbJoueurs = players.size();

        for (int i = 0; i < nbJoueurs; i++) {
            HBox slot = playerSlots.get(i);
            int positionRelative = (i - currentPlayerIndex + nbJoueurs) % nbJoueurs;
            double[] offsets = getOffsets(positionRelative, nbJoueurs);

            slot.setTranslateX(offsets[0]);
            slot.setTranslateY(offsets[1]);
            slot.setRotate(getTargetAngle(positionRelative, nbJoueurs));

            // Le joueur actuel peut interagir, les autres non
            if (i == currentPlayerIndex) {
                slot.setMouseTransparent(false);
                slot.setOpacity(1.0);
            } else {
                slot.setMouseTransparent(true);
                slot.setOpacity(0.5);
            }
        }
    }

    /**
     * Anime la transition vers le joueur suivant.
     * Tous les slots glissent vers leur nouvelle position.
     *
     * @param nouvelIndex L'index du nouveau joueur actif
     */
    public void animateToPlayer(int nouvelIndex) {
        this.currentPlayerIndex = nouvelIndex;
        int nbPlayers = players.size();

        ParallelTransition allTransitions = new ParallelTransition();

        for (int i = 0; i < nbPlayers; i++) {
            HBox slot = playerSlots.get(i);

            int relativePos = (i - currentPlayerIndex + nbPlayers) % nbPlayers;
            double[] offsets = getOffsets(relativePos, nbPlayers);

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
                ft.setToValue(1.0);
            } else {
                slot.setMouseTransparent(true);
                ft.setToValue(0.5);
            }

            allTransitions.getChildren().addAll(tt, rt, ft);
        }

        allTransitions.play();
    }

    /**
     * Retourne les offsets X et Y pour une position relative donnée.
     * Position 0 = bas (joueur actif), puis dans le sens horaire.
     */
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

    /**
     * Calcule l'angle que le slot doit avoir selon sa position.
     */
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

    /**
     * Met à jour le rack d'un joueur après qu'il a posé une tuile.
     * Recrée la RackBox dans son slot.
     *
     * @param playerIndex L'index du joueur dont le rack a changé
     */
    public void rafraichirRack(int playerIndex) {
        HBox slot      = playerSlots.get(playerIndex);
        RackBox oldRack = rackBoxes.get(playerIndex);
        slot.getChildren().remove(oldRack);
        RackBox newRack = new RackBox(players.get(playerIndex).getRack());
        rackBoxes.set(playerIndex, newRack);

        slot.getChildren().add(newRack);

        if (playerIndex == currentPlayerIndex) {
            slot.setMouseTransparent(false);
            slot.setOpacity(1.0);
        } else {
            slot.setMouseTransparent(true);
            slot.setOpacity(0.5);
        }
    }
}