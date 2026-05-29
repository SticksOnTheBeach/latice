package latice.model;

public class Player {
    private String name;
    private int score;
    private Rack rack;
    private Deck deck;
    public Player(Rack rack, String name) {
        this.rack = rack;
        this.score = 0;
        this.name = name;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
    
    public Rack getRack() {
        return rack;
    }
    
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", rack=" + rack +
                ", deck=" + deck.toString() +
                '}';
    }

}

