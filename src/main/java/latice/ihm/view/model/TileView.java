package latice.ihm.view.model;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.model.exceptions.InvalidImagePathException;
import latice.model.tile.Tile;
import latice.util.ImageLoader;

public class TileView extends StackPane {

    private static final int SIZE = 50;
    private Tile tile;
    public static TileView selectedTile = null;
    public TileView(Tile tile) {
        this.tile = tile;

        Rectangle rect = new Rectangle(SIZE, SIZE);
        rect.setFill(getTileColor(tile));
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1.5);
        
        String color = tile.getColor().name().toLowerCase();
        String shape = tile.getShape().name().toLowerCase();
        String path = "/images/game/" + shape + "_" + color.substring(0, 1) + ".png";  
        
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(ImageLoader.load(path));
        } catch (InvalidImagePathException e) {
            System.err.println("ERROR : image introuvable ! " + e.getMessage());
        }
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        getChildren().addAll(rect, imageView);


        // DRAG AND DROP
        setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(tile.getColor().name() + "," + tile.getShape().name());
            db.setContent(content);
            event.consume();
        });
        //click
        setOnMouseClicked(event -> {
            if (selectedTile != null) {
                selectedTile.setStyle(""); // désélectionner l'ancienne tuille
            }
            selectedTile = this;
            setStyle("-fx-effect: dropshadow(gaussian, #FFD700, 10, 0.8, 0, 0);"); // highlight de la tuille selectionnée
            event.consume();
        });
        //TODO : on peut selectionner une tuille sur le board, bug uniquement visuel a regler

    }
    public Tile getTile() {
        return tile;
    }
    
    /*
     *   TODO : TileView.resetSelection(); afin qu'entre deux parties on reset la selection, par ailleurs pour la  V8 on pourrait implémenter
     *	un système  de classement
     *	
     */
    


    private Color getTileColor(Tile tile) {
        switch (tile.getColor()) {
            case YELLOW:   return Color.GOLD;
            case NAVY:     return Color.NAVY;
            case MAGENTA:  return Color.MAGENTA;
            case REDGREEN: return Color.DARKGREEN;
            case TEAL:     return Color.TEAL;
            default:       return Color.GRAY;
        }
    }
}