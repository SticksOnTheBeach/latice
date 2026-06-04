package latice.ihm.controller;

import java.util.ArrayList;
import java.util.Random;

import latice.model.Player;
import latice.model.Referee;

public class RoundController {
	// gère les tours des joueurs, c'est cette classe qui vas faire en sorte de gérer à qui c'est de jouer etc...
	private int currentPlayerIndex = 0;
	private ArrayList<Player> players;

	private Referee referee;
	public RoundController(ArrayList<Player> players, Referee referee) {
	     this.players = players;
	     this.currentPlayerIndex = new Random().nextInt(players.size()); // on choisit aléatoirement le joueur qui joue ( LE PREMIER ) 
		 this.referee = referee;
	}

	
	public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

	/**
     * Retourne l'index du joueur courant.
     *
     * @return L'index courant.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
	
	public void nextPlayerTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		
		if (currentPlayerIndex == 0) {
			referee.setRoundCount(referee.getRoundCount() + 1);
		}
	}

	public int getRoundCount() {
        return referee.getRoundCount();
    }
	
	/**
     * Retourne la liste de tous les joueurs.
     *getTilesRemainingInDeck
     * @return La liste des joueurs.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
