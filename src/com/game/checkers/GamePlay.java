package com.game.checkers;

import java.util.Scanner;
import java.util.Set;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Color;
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
			board = CheckerBoard.getInstance(6);
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter Your Name: ");
			String name = sc.nextLine();

			user = User.getInstance(name, Color.BLACK);
			cpu = CPU.getInstance("cpu", Color.WHITE);

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

//	public void playCPU(CheckerBoard board) {
//		new Thread() {
//			
//			@Override
//			public void run() {
//				super.run();
//				boolean gameOver = false;
//				while (!gameOver) {
//
//					// CPU's Turn
//					if (activePlayer == cpu) {
//						board.setDisable(true);
//						Set<Move> allPossibleMoves = ((CPU) cpu).getNextMove(board);
//						if (!GameCommonUtils.hasMoreMoves(allPossibleMoves)) {
//							// Game Over
//							System.out.println("Game Over");
//							gameOver = true;
//							continue;
//						}
//
//						Move move = ((CPU) cpu).calculateBestMove(allPossibleMoves);
//						if (move == null) {
//							// Game Over
//							System.out.println("Game Over");
//							gameOver = true;
//							continue;
//						} else {
//							// Make move
//							CPU.performMove(move, board);
//						}
//
//						if (GameCommonUtils.hasMoreMoves(user, board)) {
//							switchActivePlayer();
//							board.setDisable(false);
//						} else {
//							// Game Over
//							System.out.println("Game Over");
//							gameOver = true;
//							continue;
//						}
//					}
//					// User's Turn
//					else {
//						while (isPlaying(user)) {
//							try {
//								System.out.println("User playing.. Let's sleep for 2 sec: Thread: "+this.getName());
//								Thread.sleep(2000);
//								
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//				BoardSummary bs = new BoardSummary();
//				bs.setCpuPieceCount(cpu.getPieceCount());
//				bs.setCpuPieceCount(user.getPieceCount());
//				System.out.println(bs);
//				
//				if(GameCommonUtils.hasWon(user)) {
//					System.out.println("User Won");
//				} else if(GameCommonUtils.hasWon(user)) {
//					System.out.println("CPU Won");
//				} else {
//					System.out.println("Game Draw");
//				}
//			}
//		}.start();
//	}

	private boolean isPlaying(final Player player) {
		return activePlayer == player;
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
	
	public void switchActivePlayer() {
		if(activePlayer instanceof CPU)
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
	
	
	class CpuTurn implements Runnable{

		@Override
		public void run() {			
			boolean gameOver = false;
			while (!gameOver) {

				// CPU's Turn
				if (activePlayer == cpu) {
					board.setDisable(true);
					Set<Move> allPossibleMoves = ((CPU) cpu).getNextMove(board);
					if (!GameCommonUtils.hasMoreMoves(allPossibleMoves)) {
						// Game Over
						System.out.println("Game Over");
						gameOver = true;
						continue;
					}

					Move move = ((CPU) cpu).calculateBestMove(allPossibleMoves);
					if (move == null) {
						// Game Over
						System.out.println("Game Terminated");
						gameOver = true;
						continue;
					} else {
						// Make move
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
			bs.setCpuPieceCount(cpu.getPieceCount());
			bs.setUserPieceCount(user.getPieceCount());
			System.out.println(bs);
			
			if(GameCommonUtils.hasWon(user)) {
				System.out.println("User Won");
			} else if(GameCommonUtils.hasWon(cpu)) {
				System.out.println("CPU Won");
			} else {
				System.out.println("Game Draw");
			}
			
		}
		
	}
}



