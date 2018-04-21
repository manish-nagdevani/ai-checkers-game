package com.game.checkers;

import java.util.Scanner;
import java.util.Set;

import com.game.checkers.ai.State;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Square;
import com.game.checkers.eval.BoardSummary;
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
			board = new CheckerBoard(6);
			Scanner sc = new Scanner(System.in);

			user = User.getInstance();
			cpu = CPU.getInstance();

			System.out.println("Do you want to play first? (y/n)");
			String playFirst = sc.nextLine();

			sc.close();

			initializeBoard(board, user, cpu);

			// Display Board
			displayBoard(primaryStage, board);

			if (playFirst.equalsIgnoreCase("y")) {
				activePlayer = user;
			} else {
				activePlayer = cpu;
			}

			CpuTurn cpuTurn = new CpuTurn();
			Thread t = new Thread(cpuTurn);
			t.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isPlaying(final Player player) {
		return activePlayer == player;
	}

	private void initializeBoard(CheckerBoard board, Player user, Player cpu) {
		// Initialize Board
		board.init();
		board.placeInitialPieces();
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
		System.out.println(board.getParent());
		System.out.println("Board's Parent Hashcode = " + board.getParent().hashCode());
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public void switchActivePlayer() {
		if (activePlayer instanceof CPU)
			this.activePlayer = user;
		else
			this.activePlayer = cpu;
	}

	public static GamePlay getInstance() {
		if (gamePlay == null) {
			gamePlay = new GamePlay();
		}
		return gamePlay;

	}

	class CpuTurn implements Runnable {
		@Override
		public void run() {
			boolean gameOver = false;
			while (!gameOver) {

				// CPU's Turn
				if (activePlayer == cpu) {
					board.setDisable(true);

					// Either Normal moves or Jump Moves
					Set<Move> allPossibleMoves = ((CPU) cpu).getNextMove(board);

					// Selecting the Move to take if any
					Move move = null;

					// Game Over
					if (allPossibleMoves.size() == 0) {
						System.out.println("Game Over");
						gameOver = true;
						continue;
					} else if (allPossibleMoves.size() == 1) {
						move = allPossibleMoves.iterator().next();
					} else {
						Move bestMove = ((CPU) cpu).calculateBestMove(allPossibleMoves, board);
						if (bestMove != null) {
							Square srcSq = board.getSquare(bestMove.getSrc().getX(), bestMove.getSrc().getY());
							Square destSq = board.getSquare(bestMove.getDest().getX(), bestMove.getDest().getY());
							move = new Move(srcSq, destSq, bestMove.getType());
						}
					}

					// Game Over
					if (move == null) {
						System.out.println("Game Terminated");
						gameOver = true;
						continue;
					}

					// Make move
					else {
						CPU.performMove(move, board);
					}

					if (GameCommonUtils.hasMoreMoves(user, board)) {
						switchActivePlayer();
						board.setDisable(false);
					} else {
						// Game Over
						System.out.println("Game Terminated");
						gameOver = true;
						continue;
					}
				}
				// User's Turn
				else {
					while (isPlaying(user)) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

			BoardSummary bs = new BoardSummary();
			bs.setCpuPieceCount(board.getPieceCount(cpu.getColor()));
			bs.setUserPieceCount(board.getPieceCount(user.getColor()));
			System.out.println(bs);

			State state = new State(board);
			System.out.println("State Board Hashcode = " + state.getBoard().hashCode());
			System.out.println("Actual Board Hashcode = " + board.hashCode());
			System.out.println("State Board Squares array Hashcode = " + state.getBoard().getSquares().hashCode());
			System.out.println("Actual Board Squares array Hashcode = " + board.getSquares().hashCode());

			if (GameCommonUtils.hasWon(user, board)) {
				System.out.println("User Won");
			} else if (GameCommonUtils.hasWon(cpu, board)) {
				System.out.println("CPU Won");
			} else {
				System.out.println("Game Draw");
			}

		}

	}
}
