package latice.model;

public class Player {
    private String name;
    private int score;
    private Rack rack;
    public Player(Deck deck, Rack rack, int score, String name) {
        this.deck = deck;
        this.rack = rack;
        this.score = score;
        this.name = name;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    private Deck deck;


}

