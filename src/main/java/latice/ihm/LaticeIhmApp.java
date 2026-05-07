package latice.ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import latice.ihm.view.BoardPane;
import latice.ihm.view.MainPane;
import latice.model.Board;

public class LaticeIhmApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Label lbltours = new Label("pipi");
		Label lbltours2 = new Label("pipi");
		Label lbltours3 = new Label("pipi");
		Label lbltours4 = new Label("pipi");
		
	    Board board = new Board();
	    board.createGameBoard();

	    // BoardPane boardPane = new BoardPane(board);
	    MainPane mainPane = new MainPane(board);

	    mainPane.setCenter(boardPane);
	    mainPane.setTop(lbltours4);
	    mainPane.setRight(lbltours3);
	    mainPane.setLeft(lbltours2);
	    mainPane.setBottom(lbltours);
	    

	    Scene scene = new Scene(mainPane, 800, 700);
	    primaryStage.setTitle("Latice");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
