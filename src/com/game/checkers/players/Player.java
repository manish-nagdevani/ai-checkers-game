package com.game.checkers.players;

import com.game.checkers.GamePlay;
import com.game.checkers.ai.DifficultyLevel;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;

import javafx.application.Platform;

/**
 * Abstract class representing a Player
 * 
 * @author Manish
 *
 */
public abstract class Player {
	protected String name;
	protected Color color;
	protected int score;
	private DifficultyLevel level = null;

	/**
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	protected abstract void makeMove(CheckerBoard board);

	/**
	 * Given a Move, this function actually performs it
	 * 
	 * @param move
	 * @param board
	 */
	public static void performMove(Move move, CheckerBoard board) {
		Square srcSq = move.getSrc();
		srcSq.getStyleClass().add("checker-square-active");
		Square destSq = move.getDest();
		if (GamePlay.getInstance().getActivePlayer() == CPU.getInstance()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		destSq.getStyleClass().add("checker-square-legal-suggestion");
		System.out.println(
				GamePlay.getInstance().getActivePlayer().getName() + " played move:" + move.getType().toString() + "( "
						+ srcSq.getX() + " , " + srcSq.getY() + " => " + destSq.getX() + " , " + destSq.getY() + " )");
		if (srcSq != null && destSq != null) {
			if (move.getType() == MoveType.JUMP) {
				int direction = 1;
				if (srcSq.getCheckerPiece().getColor() == Color.BLACK)
					direction = -1;
				// Kill opponent's Piece
				CheckerPiece opponentPiece = null;
				if (srcSq.isToRight(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() - 1).releasePiece();
				} else if (srcSq.isToLeft(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() + 1).releasePiece();
				}
				board.killCheckerPiece(opponentPiece);

			}

			// Move player's piece to the destination square
			CheckerPiece currentPlayerPiece = srcSq.releasePiece();
			destSq.setCheckerPiece(currentPlayerPiece);
		}
		srcSq.getStyleClass().removeAll("checker-square-active");
		destSq.getStyleClass().removeAll("checker-square-legal-suggestion");
		enableAllDisableSquares(board);
	}

	/**
	 * Enables all the disabled squares
	 * 
	 * @param board
	 */
	private static void enableAllDisableSquares(CheckerBoard board) {
		Platform.runLater(() -> {
			for (Square[] sq_arr : board.getSquares()) {
				for (Square sq : sq_arr) {
					if (sq.isDisable())
						sq.setDisable(false);
				}
			}
		});
	}

	/**
	 * Given a Move, this function actually performs it -- used by alphabeta
	 * function
	 * 
	 * @param move
	 * @param board
	 * @param activePlayer
	 * @param opponent
	 */
	public static void performMove(Move move, CheckerBoard board, Player activePlayer, Player opponent) {
		enableAllDisableSquares(board);
		Square srcSq = board.getSquare(move.getSrc().getX(), move.getSrc().getY());
		Square destSq = board.getSquare(move.getDest().getX(), move.getDest().getY());
		if (srcSq != null && destSq != null) {
			if (move.getType() == MoveType.JUMP) {
				int direction = 1;
				if (srcSq.getCheckerPiece().getColor() == Color.BLACK)
					direction = -1;

				// Kill opponent's Piece
				CheckerPiece opponentPiece = null;
				if (srcSq.isToRight(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() - 1).releasePiece();
				} else if (srcSq.isToLeft(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() + 1).releasePiece();
				}

				board.killCheckerPiece(opponentPiece);

			}

			// Move player's piece to the destination square
			CheckerPiece currentPlayerPiece = srcSq.releasePiece();
			destSq.setCheckerPiece(currentPlayerPiece);
		}
	}

	/**
	 * @return color of the player
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return difficulty level of the player
	 */
	public DifficultyLevel getLevel() {
		return level;
	}

	/**
	 * @param level
	 */
	public void setLevel(DifficultyLevel level) {
		this.level = level;
	}
}
