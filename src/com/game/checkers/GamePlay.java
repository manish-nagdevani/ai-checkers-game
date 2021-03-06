package com.game.checkers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.game.checkers.ai.DifficultyLevel;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.Move;
import com.game.checkers.players.CPU;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Singleton Class Responsible to conduct game play.
 * Builds the board, checker pieces, Player objects
 * Responsible for Turn Taking OR Game Loop
 * 
 * @author Manish
 * 
 */
public class GamePlay {

	private Player cpu = null;
	private Player activePlayer = null;
	private CheckerBoard board = null;
	private Player user = null;
	private TextArea loggingArea = null;

	private static GamePlay gamePlay = null;

	private GamePlay() {
	}

	
	/**
	 * Initializes the board, Player objects and renders the game GUI
	 * Marks the start of the game
	 * 
	 * @param primaryStage
	 */
	public void init(Stage primaryStage) {
		try {
			
			//Ask user for the difficulty level
			String choice = promptDifficulty();
			DifficultyLevel cpuLevel = DifficultyLevel.valueOf(choice);
			
			//Create board
			board = new CheckerBoard(6);
			
			//Initialise Player objects
			user = User.getInstance();
			cpu = CPU.getInstance();
			
			//Set cpu level as per user's preference
			cpu.setLevel(cpuLevel);
			
			//Ask user if he wants to play first or second
			promptUserToTakeTurn();

			//Initialise board with initial configuration
			initializeBoard(board, user, cpu);
			
			//Display GUI
			displayBoard(primaryStage, board);
			
			CpuTurn cpuTurn = new CpuTurn();
			Thread t = new Thread(cpuTurn);
			t.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ask user to choose the Game Difficulty level
	 * 
	 * @return
	 */
	private String promptDifficulty() {
		List<String> options = GameCommonUtils.getDificultyLevels();
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(options.get(0), options);
		dialog.setTitle("Checkers Board Game");
		dialog.setHeaderText("Choose Difficulty Level");
		dialog.setContentText("Choose an option");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	/**
	 * Ask User to choose whether to take turn first or second
	 */
	private void promptUserToTakeTurn() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Checkers Board Game");
		alert.setHeaderText("Do you want to Play first?");
		alert.setContentText("Choose an option");

		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		ButtonType exitButton = new ButtonType("Exit");
		alert.getButtonTypes().setAll(yesButton, noButton, exitButton);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButton) {
			activePlayer = user;
		} else if (result.get() == noButton) {
			activePlayer = cpu;
		} else {
			System.exit(0);
		}

	}

	/**
	 * Returns true is player is still taking it's turn
	 * 
	 * @param player
	 * @return
	 */
	private boolean isPlaying(final Player player) {
		return activePlayer == player;
	}

	/**
	 * Initialise board with inital configuration
	 * 
	 * @param board
	 * @param user
	 * @param cpu
	 */
	private void initializeBoard(CheckerBoard board, Player user, Player cpu) {
		board.init();
		board.placeInitialPieces();
		board.show();
	}

	/**
	 * Render board GUI
	 * 
	 * @param primaryStage
	 * @param board
	 */
	private void displayBoard(Stage primaryStage, CheckerBoard board) {
		primaryStage.setTitle("Checkers Game");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 400);
		primaryStage.setScene(scene);
		scene.getStylesheets().add("styles/application.css");
		root.setCenter(board);
		VBox vbox = generateLoggerSpace();
		root.setRight(vbox);
		primaryStage.show();
	}

	/**
	 * Generates the Blank Text Area on the board GUI for logging statements
	 * 
	 * @return
	 */
	private VBox generateLoggerSpace() {
		loggingArea = new TextArea();
		loggingArea.setEditable(false);
		loggingArea.getStyleClass().add("checker-logger-area");
		VBox vbox = new VBox(20, loggingArea);
		vbox.getStyleClass().add("vbox");
		return vbox;
	}

	/**
	 * Returns the player taking turn currently
	 * 
	 * @return
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Change Active Player
	 */
	public void switchActivePlayer() {
		if (activePlayer instanceof CPU)
			this.activePlayer = user;
		else
			this.activePlayer = cpu;
	}

	/**
	 * Returns Singleton object of the GamePlay class
	 * 
	 * @return
	 */
	public static GamePlay getInstance() {
		if (gamePlay == null) {
			gamePlay = new GamePlay();
		}
		return gamePlay;

	}

	/**
	 * Determines the winner of the board
	 * 
	 * @param player
	 */
	private void outputWinner(Player player) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Result");
		if (player == null) {
			alert.setHeaderText("Game Draw");
		} else {
			if (player instanceof User)
				alert.setHeaderText("You Won");
			else
				alert.setHeaderText("Computer Won");
		}

		ButtonType okButton = new ButtonType("OK");

		alert.getButtonTypes().setAll(okButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton) {
			System.exit(0);
		} else {
			System.exit(0);
		}
	}

	public TextArea getLoggingArea() {
		return loggingArea;
	}

	/**
	 * Class implements Runnable, Main logic for Game Loop
	 * Implemented Logic for turn taking and CPU/AI turn control
	 * 
	 * @author Manish
	 *
	 */
	class CpuTurn implements Runnable {
		@Override
		public void run() {
			boolean gameOver = false;
			while (!gameOver) {

				// CPU's Turn
				if (activePlayer == cpu) {
					board.setDisable(true);

					// Either Normal moves or Jump Moves - Always
					Set<Move> allPossibleMoves = ((CPU) cpu).getNextMove(board);

					// Selecting the Move to take if any
					Move move = null;

					// Game Over
					if(allPossibleMoves.size() == 0) {
						System.out.println("Game Over");
						gameOver = true;
						continue;
					}
					else if (allPossibleMoves.size() == 1) {
						move = allPossibleMoves.iterator().next();
					} else {
						Move bestMove = ((CPU) cpu).calculateBestMove(board);
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
						Set<Move> allPossibleJumpMoves = GameCommonUtils.allJumpMovesPossible(board, user);

						if (!allPossibleJumpMoves.isEmpty()) {
							Set<CheckerPiece> userPieces = board.getPieces(user.getColor());
							Iterator<CheckerPiece> itr = userPieces.iterator();
							Set<Square> jumpSrcSquares = allPossibleJumpMoves.stream()
									.map(jumpMove -> jumpMove.getSrc()).collect(Collectors.toSet());
							Platform.runLater(() -> {
								while (itr.hasNext()) {
									Square src = itr.next().getBelongsTo();
									if (!jumpSrcSquares.contains(src)) {
										src.setDisable(true);
									}
								}
							});
						}
						switchActivePlayer();
						board.setDisable(false);
					} else {
						if (board.getPieceCount(Color.WHITE) > 0 && board.getPieceCount(Color.BLACK) > 0
								&& GameCommonUtils.hasMoreMoves(cpu, board)) {
							continue;
						} else {
							// Game Over
							System.out.println("Game Terminated");
							gameOver = true;
							continue;
						}
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
			
			//Output Winner
			if (GameCommonUtils.hasWon(user, board)) {
				Platform.runLater(() -> {
					outputWinner(user);
				});
			} else if (GameCommonUtils.hasWon(cpu, board)) {
				Platform.runLater(() -> {
					outputWinner(cpu);
				});
			} else {
				Platform.runLater(() -> {
					outputWinner(null);
				});
			}

		}

	}

}
