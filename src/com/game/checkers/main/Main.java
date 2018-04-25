package com.game.checkers.main;

import com.game.checkers.GamePlay;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private GamePlay gamePlay;

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		gamePlay = GamePlay.getInstance();
		gamePlay.init(primaryStage);
	}

	/**
	 * Main Method -- Execution Starts here
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
