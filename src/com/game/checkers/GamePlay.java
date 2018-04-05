package com.game.checkers;

import java.util.Scanner;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Color;
import com.game.checkers.moves.Move;
import com.game.checkers.players.CPU;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GamePlay {
	
	private Player cpu = null;
	private Player activePlayer = null;
	private CheckerBoard board = null;
	private Player user = null;
	
	private static GamePlay gamePlay = null;
	
	private GamePlay() {
	}

	public void init(Stage primaryStage) {
		try {
			board = CheckerBoard.getInstance(6);
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter Your Name: ");
			String name = sc.nextLine();

			user = User.getInstance(name, Color.BLACK);
			cpu = CPU.getInstance(name, Color.WHITE);

			System.out.println("Do you want to play first? (y/n)");
			String playFirst = sc.nextLine();

			sc.close();
			
			initializeBoard(board, user, cpu);

			// Display Board
			displayBoard(primaryStage, board);
			
			if (playFirst.equalsIgnoreCase("y")) {
				activePlayer = user;
			} else {
				playCPU(board);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playCPU(CheckerBoard board) {
		activePlayer = cpu;
		board.setDisable(true);
		Move move = ((CPU) cpu).getNextMove(board);
		if (move == null) {
			// Game Over Logic
			System.out.println("Game Terminated");
		} else {
			// Make move
			CPU.performMove(move, board);
		}
		activePlayer = user;
		board.setDisable(false);
	}

	private void initializeBoard(CheckerBoard board, Player user, Player cpu) {
		// Initialize Board
		board.init();
		board.placeInitialPieces(user, cpu);
		System.out.println(user.getPieces().size());
		System.out.println(cpu.getPieces().size());
		board.show();
	}

	private void displayBoard(Stage primaryStage, CheckerBoard board) {
		primaryStage.setTitle("Checkers Game");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		scene.getStylesheets().add("styles/application.css");
		root.setCenter(board);
		primaryStage.show();
	}

	public Player getActivePlayer() {
		return activePlayer;
	}
	
	
	public static GamePlay getInstance() {
		if(gamePlay == null) {
			gamePlay = new GamePlay();
		}
		return gamePlay;
			
	}
}
