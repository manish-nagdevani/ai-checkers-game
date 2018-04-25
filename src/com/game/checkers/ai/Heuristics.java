package com.game.checkers.ai;

import com.game.checkers.GameCommonUtils;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Square;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

public class Heuristics {

	// H1
	public static int pieceCountHeuristic(CheckerBoard board, Player player, Player adversary) {
		return board.getPieceCount(player.getColor()) - board.getPieceCount(adversary.getColor());
	}

	// H2
	public static int movesToDestHeuristic(CheckerBoard board, Player player, Player adversary) {
		return (calcSumOfMovesToDest(board, player) - calcSumOfMovesToDest(board, adversary));
	}

	// H3
	public static int availJumpMovesHeuristic(CheckerBoard board, Player player, Player adversary) {
		return (GameCommonUtils.allJumpMovesPossible(board, player).size()
				- GameCommonUtils.allJumpMovesPossible(board, adversary).size());
	}

	// H4
	public static int piecesOnDestHeuristic(CheckerBoard board, Player player, Player adversary) {
		return (calcSumOfPiecesToDest(board, player) - calcSumOfPiecesToDest(board, adversary));
	}

	// H5
	public static int numOfInvulnerablePiecesHeuristic(CheckerBoard board, Player player, Player adversary) {
		return (calcNumOfInvulnerablePieces(board, player) - calcNumOfInvulnerablePieces(board, adversary));
	}

	// H6
	public static int supportPiecesHeuristic(CheckerBoard board, Player player, Player adversary) {
		return (calcSupportPieces(board, player) - calcSupportPieces(board, adversary));
	}

	private static int calcSupportPieces(CheckerBoard board, Player player) {
		int numOfSupportPieces = 0;
		for (CheckerPiece piece : board.getPieces(player.getColor())) {
			int x = piece.getBelongsTo().getX();
			int y = piece.getBelongsTo().getY();
			if (player instanceof User) {
				Square diagDownLeft = board.getSquare(x + 1, y - 1);
				Square diagDownRight = board.getSquare(x + 1, y + 1);
				if (diagDownLeft != null && diagDownLeft.hasCheckerPiece()) {
					// Here color of Checker piece doesn't matter coz the
					// adversary can't attack anyway
					numOfSupportPieces++;
				}
				if (diagDownRight != null && diagDownRight.hasCheckerPiece()) {
					// Here color of Checker piece doesn't matter coz the
					// adversary can't attack anyway
					numOfSupportPieces++;
				}
			} else {
				Square diagUpLeft = board.getSquare(x - 1, y - 1);
				Square diagUpRight = board.getSquare(x - 1, y + 1);
				if (diagUpLeft != null && diagUpLeft.hasCheckerPiece()) {
					// Here color of Checker piece doesn't matter coz the
					// adversary can't attack anyway
					numOfSupportPieces++;
				}
				if (diagUpRight != null && diagUpRight.hasCheckerPiece()) {
					// Here color of Checker piece doesn't matter coz the
					// adversary can't attack anyway
					numOfSupportPieces++;
				}
			}
		}
		return numOfSupportPieces;
	}

	private static int calcNumOfInvulnerablePieces(CheckerBoard board, Player player) {
		int numOfInvulnerablePieces = 0;
		final int boardSize = board.getSize() - 1;
		for (CheckerPiece piece : board.getPieces(player.getColor())) {
			int x = piece.getBelongsTo().getX();
			int y = piece.getBelongsTo().getY();

			if (x == 0 || x == boardSize || y == 0 || y == boardSize) {
				numOfInvulnerablePieces++;
			}
		}
		return numOfInvulnerablePieces;
	}

	private static int calcSumOfPiecesToDest(CheckerBoard board, Player player) {
		int sumToDest = 0;
		for (CheckerPiece piece : board.getPieces(player.getColor())) {
			if (player instanceof User) {
				sumToDest += (piece.getBelongsTo().getY() == 0) ? 1 : 0;
			} else {
				sumToDest += (piece.getBelongsTo().getY() == board.getSize() - 1) ? 1 : 0;
			}
		}
		return sumToDest;
	}

	private static int calcSumOfMovesToDest(CheckerBoard board, Player player) {
		int sumToDest = 0;
		for (CheckerPiece piece : board.getPieces(player.getColor())) {
			if (player instanceof User) {
				sumToDest += piece.getBelongsTo().getY();
			} else {
				sumToDest += ((board.getSize() - 1) - piece.getBelongsTo().getY());
			}
		}
		return sumToDest;
	}
}
