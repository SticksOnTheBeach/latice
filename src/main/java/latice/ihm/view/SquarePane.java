package latice.ihm.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.model.Position;
import latice.model.square.Square;

public class SquarePane extends StackPane {

    private static final int SIZE = 60;
    private Position position;
    private Square square;
    private boolean isTilePlaced = false;

    public SquarePane(Square square, Position position) {
        this.square = square;
        this.position = position;

        Rectangle rect = new Rectangle(SIZE, SIZE);

        switch (square.getType()) {
            case SUN:    
            	rect.setFill(Color.GOLD);        
            	break;
            case MOON:   
            	rect.setFill(Color.MEDIUMPURPLE); 
            	break;
            case NORMAL: 
            	default: 
            		rect.setFill(Color.LIGHTGRAY); 
            		break;
        }
        
        // SYMBOL FOR THE SQUARES
        Image image = new Image(getClass().getResourceAsStream(square.getType().getImagePath()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        getChildren().addAll(imageView);

        setOnDragOver(event -> {
            if (!isTilePlaced && event.getGestureSource() instanceof TileView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        
        setOnDragDropped(event -> {
            if (!isTilePlaced) {
                TileView tileView = (TileView) event.getGestureSource();
                placeTile(tileView);
                event.setDropCompleted(true);
            }
            event.consume();
        });
    }
    
    private void placeTile(TileView tileView) {
        getChildren().clear();
        getChildren().add(tileView);
        isTilePlaced = true;
        tileView.setOnDragDetected(event -> event.consume());
    }


    public Position getPosition() {
        return position;
    }
}