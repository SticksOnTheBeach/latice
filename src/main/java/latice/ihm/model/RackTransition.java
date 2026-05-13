package latice.ihm.model;

import java.util.ArrayList;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
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
 */
public class RackTransition {
    private static final int ANIM_DURATION = 500;

    // Décalages en pixels pour chaque position autour du board
    // valeurs sont ajustées selon la taille du board (environ 540x540)
    private static final double OFFSET_BAS    =  360;
    private static final double OFFSET_HAUT   = -360;
    private static final double OFFSET_GAUCHE = -440;
    private static final double OFFSET_DROITE =  440;
    // ...
    private ArrayList<RackBox> rackBoxes;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private StackPane container;

    /**
     * Constructeur.
     *
     * @param container Le StackPane qui contient le board ET les racks
     * @param players La liste des joueurs dans l'ordre
     * @param currentPlayerIndex L'index du joueur qui commence
     */
    public RackTransition(StackPane container, ArrayList<Player> players, int currentPlayerIndex) {
        this.container = container;
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.rackBoxes = new ArrayList<>();

        // Crée une RackBox pour chaque joueur et l'ajoute au container
        for (Player player : players) {
            RackBox rackBox = new RackBox(player.getRack());
            rackBoxes.add(rackBox);
            container.getChildren().add(rackBox);
        }

        // Positionne les racks sans animation au démarrage
        firstRackPosition();
    }

    /**
     * Place les racks à leur position initiale sans animation.
     */
    private void firstRackPosition() {
    	int nbJoueurs = players.size();

        for (int i = 0; i < nbJoueurs; i++) {
            RackBox rack = rackBoxes.get(i);
            int positionRelative = (i - currentPlayerIndex + nbJoueurs) % nbJoueurs;
            double[] offsets = getOffsets(positionRelative, nbJoueurs);
            
            rack.setTranslateX(offsets[0]);
            rack.setTranslateY(offsets[1]);
            rack.setRotate(getTargetAngle(positionRelative, nbJoueurs));
        }
    }

    /**
     * Anime la transition vers le joueur suivant.
     * Tous les racks glissent d'une position dans le sens horaire.
     *
     * @param nouvelIndex L'index du nouveau joueur actif
     */
    public void animerVersJoueur(int nouvelIndex) {
        this.currentPlayerIndex = nouvelIndex;
        int nbPlayers = players.size();

        ParallelTransition allTransitions = new ParallelTransition();

        for (int i = 0; i < nbPlayers; i++) {
            RackBox rack = rackBoxes.get(i);
            
            int relativePos = (i - currentPlayerIndex + nbPlayers) % nbPlayers;
            double[] offsets = getOffsets(relativePos, nbPlayers);

            TranslateTransition tt = new TranslateTransition(Duration.millis(ANIM_DURATION), rack);
            tt.setToX(offsets[0]);
            tt.setToY(offsets[1]);

            double currentAngle = rack.getRotate();
            double targetAngle = getTargetAngle(relativePos, nbPlayers);

            double angleDiff = targetAngle - (currentAngle % 360);
            if (angleDiff > 180) {
            	angleDiff -= 360;
            }
            if (angleDiff < -180) {
            	angleDiff += 360;
            }

            RotateTransition rt = new RotateTransition(Duration.millis(ANIM_DURATION), rack);
            rt.setByAngle(angleDiff);

            allTransitions.getChildren().addAll(tt, rt);
        }

        allTransitions.play();
    }

    /**
     * Retourne les offsets X et Y pour une position relative donnée.
     * Position 0 = bas (joueur actif), puis dans le sens horaire.
     *
     * @param positionRelative 0 = bas, 1 = droite (ou haut), 2 = haut (ou gauche), 3 = gauche
     * @param nbJoueurs        Nombre total de joueurs
     * @return double[] { offsetX, offsetY }
     */
    private double[] getOffsets(int positionRelative, int nbJoueurs) {
        if (nbJoueurs == 2) {
            // 2 joueurs : bas et haut uniquement
            switch (positionRelative) {
                case 0:  
                	return new double[]{ 0, OFFSET_BAS };// bas (actif)
                case 1:  
                	return new double[]{ 0, OFFSET_HAUT };// haut
                default: 
                	return new double[]{ 0, 0 };
            }
        } else if (nbJoueurs == 3) {
            // 3 joueurs : bas, droite, gauche
            switch (positionRelative) {
        		// bas (actif)
                case 0:  
                	return new double[]{ 0,OFFSET_BAS }; 
                // droite
                case 1:
                	return new double[]{ OFFSET_DROITE, 0 }; 
                	
                // gauche
                case 2:  
                	return new double[]{ OFFSET_GAUCHE, 0 }; 
                
                default: 
                	return new double[]{ 0, 0 };
            }
        } else {
            // 4 joueurs : bas, droite, haut, gauche
            switch (positionRelative) {
            	// bas (joueur actuel ( celui qui joue)
                case 0:  
                	return new double[]{ 0,OFFSET_BAS };

                // droite
                case 1:  
                	return new double[]{ OFFSET_DROITE, 0 };
                	
                // haut
                case 2:  
                	return new double[]{ 0,OFFSET_HAUT };
                	
                // gauche
                case 3:  
                	return new double[]{ OFFSET_GAUCHE, 0 };
                default: 
                	return new double[]{ 0, 0 };
            }
        }
    }
    
    /**
     * Calcule l'angle exact que le rack doit avoir selon sa position.
     * Applique une rotation au rack selon sa position.
     * Les racks à gauche et à droite sont tournés de 90° pour s'afficher verticalement.
     *
     * @param rack             La RackBox à orienter
     * @param positionRelative La position du rack (0=bas, 1=droite, 2=haut/gauche, 3=gauche)
     * @param nbJoueurs        Nombre de joueurs
     */
    private double getTargetAngle(int positionRelative, int nbJoueurs) {
        if (nbJoueurs == 2) {
            return positionRelative == 1 ? 180 : 0;
        } 
        else if (nbJoueurs == 3) {
            switch (positionRelative) {
                case 0: return 0;
                case 1: return 90;
                case 2: return -90;
                default: return 0;
            }
        } 
        else {
            switch (positionRelative) {
                case 0: return 0;
                case 1: return 90;
                case 2: return 180;
                case 3: return -90;
                default: return 0;
            }
        }
    }

    

    /**
     * Met à jour les RackBox après qu'un joueur a posé une tuile.
     * Recrée la RackBox du joueur concerné pour refléter son nouveau rack.
     *
     * @param playerIndex L'index du joueur dont le rack a changé
     */
    public void rafraichirRack(int playerIndex) {
        RackBox oldRack = rackBoxes.get(playerIndex);
        container.getChildren().remove(oldRack);

        RackBox newRack = new RackBox(players.get(playerIndex).getRack());
        rackBoxes.set(playerIndex, newRack);
        container.getChildren().add(newRack);

        int positionRelative = (playerIndex - currentPlayerIndex + players.size()) % players.size();
        double[] offsets = getOffsets(positionRelative, players.size());
        newRack.setTranslateX(offsets[0]);
        newRack.setTranslateY(offsets[1]);
        newRack.setRotate(getTargetAngle(positionRelative, players.size()));
    }
}