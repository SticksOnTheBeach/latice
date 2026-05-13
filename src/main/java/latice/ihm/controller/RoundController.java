package latice.ihm.controller;

import java.util.ArrayList;

import latice.model.Player;

public class RoundController {
	// gère les tours des joueurs, c'est cette classe qui vas faire en sorte de gérer à qui c'est de jouer etc...
	private int currentPlayerIndex = 0;
	private ArrayList<Player> players;
	private int roundCount;

	
	 public RoundController(ArrayList<Player> players) {
	        this.players = players;
	        this.currentPlayerIndex = 0;
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
			roundCount++;
		}
	}
	
	public int getRoundCount() {
        return roundCount;
    }
	
	/**
     * Retourne la liste de tous les joueurs.
     *
     * @return La liste des joueurs.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
