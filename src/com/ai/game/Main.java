package com.ai.game;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	private CheckerBoard board;
	private Player user = new User("Manish", Color.BLACK);
	private Player cpu = new CPU("CPU", Color.WHITE);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			board = new CheckerBoard(6);
			
			primaryStage.setTitle("Checkers Game");
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
						
			primaryStage.setScene(scene);
			
			scene.getStylesheets().add("styles/application.css");
			board = board.init();
			board = board.placeInitialPieces();
			board.show();
			
			root.setCenter(board);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
