package com.game.checkers.main;

import com.game.checkers.GamePlay;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private GamePlay gamePlay;

	@Override
	public void start(Stage primaryStage) {
		gamePlay = GamePlay.getInstance();
		gamePlay.init(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
