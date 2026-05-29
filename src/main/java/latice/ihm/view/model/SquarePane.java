package latice.ihm.view.model;

import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.controller.TileController;
import latice.ihm.view.GamePane;
import latice.model.Position;
import latice.model.exceptions.InvalidImagePathException;
import latice.model.square.Square;
import latice.util.ImageLoader;

public class SquarePane extends StackPane {

    private static final int SIZE = 60;
    private Position position;
    private boolean isOccupied = false;

    public SquarePane(Square square, Position position, TileController tileController,
                      RoundController roundController, GamePane mainPane, GameController gameController) {
        this.position = position;

        try {
            ImageView imageView = new ImageView(ImageLoader.load(square.getType().getImagePath()));
            imageView.setFitWidth(SIZE);
            imageView.setFitHeight(SIZE);
            imageView.setPreserveRatio(false);
            getChildren().add(imageView);
        } catch (InvalidImagePathException e) {
            System.out.println(e.getMessage());
        }

        setOnDragOver(event -> {
            if (!isOccupied && event.getGestureSource() instanceof TileView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            if (!isOccupied) {
                TileView tileView = (TileView) event.getGestureSource();
                boolean success = gameController.playTile(tileView.getTile(), position);
                if (success) {
                    placeTile(tileView);
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            }
            event.consume();
        });

        setOnMouseClicked(event -> {
            if (!isOccupied && TileView.selectedTile != null) {
                TileView tileView = TileView.selectedTile;
                boolean success = gameController.playTile(tileView.getTile(), position);
                if (success) {
                    tileView.setStyle("");
                    placeTile(tileView);
                    TileView.selectedTile = null;
                }
            }
            event.consume();
        });
    }

    private void placeTile(TileView tileView) {
        getChildren().clear();
        getChildren().add(tileView);
        isOccupied = true;
        tileView.setOnDragDetected(event -> event.consume());
    }

    public Position getPosition() {
        return position;
    }
}