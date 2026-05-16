package latice.model;

public class Referee {
    private Player player;
    private Board gameboard;

    public Referee(){};

    public Referee(Player player, Board gameboard) {
        this.player = player;
        this.gameboard = gameboard;
    }
    
    /* TODO : méthodes qui se charge de savoir si le placement de la tuile est correcte
     * si correct, on évrifie si y'a des tuiles aux alentours "valides"
     * si valides : alors on donne le nombres de points correspondant
     * puis on fait un getteur des points
     * implémentation dans les paramètres du constructeur : roundController afin de gérer
     * qui est entrain de jouer actuellement etc..
   */
    
}
