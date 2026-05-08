package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.model.Rack;
import latice.model.tile.Tile;

public class RackBox extends HBox {
    private static final int MAX_TILES_IN_RACK = 5;

    public RackBox(Rack rack) {
        setSpacing(12); // L'espace entre chaques tuiles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(15));
        
        // CSS pour les bords arrondis et la couleur "Acajou"
        setStyle("-fx-background-color: #A67B5B; " + 
                 "-fx-background-radius: 15; " + 
                 "-fx-border-radius: 15; " + 
                 "-fx-border-color: #3e2723; " + 
                 "-fx-border-width: 3; " +
                 "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 5);");

        // --- LES EMPLACEMENTS POUR CHACUN DES TUILES ---
        for (int i = 0; i < MAX_TILES_IN_RACK; i++) {
            StackPane slot = new StackPane();
            
            // Forme de "creux" dans chaque emplacements
            Rectangle emptySlot = new Rectangle(56, 56);
            emptySlot.setFill(Color.rgb(60, 35, 20));
            emptySlot.setArcWidth(10);
            emptySlot.setArcHeight(10);
            emptySlot.setStyle("-fx-effect: innershadow(gaussian, rgba(0,0,0,0.8), 5, 0, 0, 0);");

            slot.getChildren().add(emptySlot);

            // --- PLACEMENT DE LA TUILE ---
            // vérifie pour chaque emplacement, si il y'a un tuile à attribuer
            if (i < rack.getTiles().size()) {
                Tile tile = rack.getTiles().get(i);
                TileView tileView = new TileView(tile);
                slot.getChildren().add(tileView);
            }
            
            /*
            for (Tile tile : rack.getTiles()) {
                getChildren().add(new TileView(tile));
            }
			*/
            getChildren().add(slot);
        }
    }
}