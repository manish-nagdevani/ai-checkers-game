package com.ai.game;

import java.util.HashMap;
import java.util.Map;

import com.ai.game.Move.MoveType;

public class LegalMoveGenerator {

	protected static Map<Square, Move> generateLegalMoves(Square srcSquare, final CheckerBoard board) {
		Map<Square, Move> normalMoves = generateNormalMove(srcSquare, board);
		Map<Square, Move> jumpMoves = generateJumpMove(srcSquare, board, normalMoves);

		return jumpMoves.isEmpty() ? normalMoves : jumpMoves;
	}

	private static Map<Square, Move> generateNormalMove(Square src, final CheckerBoard board) {
		Map<Square, Move> normalMoves = new HashMap<Square, Move>();
		int direction = 1; // Moving down on the board
		if (src.getCheckerPiece().getColor() == Color.BLACK)
			direction = -1; // Moving Up on the board

		// calculating left diagonal square
		if (BoardUtils.isWithinLimits(src.getX() + direction, src.getY() - 1, board.getSize())) {
			Square dest = board.getSquare(src.getX() + direction, src.getY() - 1);
			if (!dest.hasCheckerPiece() || (dest.getCheckerPiece().getColor() != src.getCheckerPiece().getColor())) {
				Move diagLeft = new Move(src, dest, MoveType.NORMAL);
				normalMoves.put(dest, diagLeft);
			}
		}

		// calculating right diagonal square
		if (BoardUtils.isWithinLimits(src.getX() + direction, src.getY() + 1, board.getSize())) {
			Square dest = board.getSquare(src.getX() + direction, src.getY() + 1);
			if (!dest.hasCheckerPiece() || (dest.getCheckerPiece().getColor() != src.getCheckerPiece().getColor())) {
				Move diagRight = new Move(src, dest, MoveType.NORMAL);
				normalMoves.put(dest, diagRight);
			}
		}

		return normalMoves;
	}

	private static Map<Square, Move> generateJumpMove(Square src, CheckerBoard board, final Map<Square, Move> normalMoves) {
		Map<Square, Move> jumpMoves = new HashMap<Square, Move>();
		int direction = 2; // Moving down on the board
		if (src.getCheckerPiece().getColor() == Color.BLACK)
			direction = -2; // Moving Up on the board

		for (Move m : normalMoves.values()) {
			if (m.getDest().hasCheckerPiece()) {
				if (src.isToRight(m.getDest())) {
					Square dest = board.getSquare(src.getX() + direction, src.getY() - 2);
					if (BoardUtils.isWithinLimits(dest.getX(), dest.getY(), board.getSize())
							&& !dest.hasCheckerPiece()) {
						Move diagLeft = new Move(src, dest, MoveType.JUMP);
						jumpMoves.put(dest, diagLeft);
					}
				} else if (src.isToLeft(m.getDest())) {
					Square dest = board.getSquare(src.getX() + direction, src.getY() + 2);
					if (BoardUtils.isWithinLimits(dest.getX(), dest.getY(), board.getSize())
							&& !dest.hasCheckerPiece()) {
						Move diagRight = new Move(src, dest, MoveType.JUMP);
						jumpMoves.put(dest, diagRight);
					}
				}
			}
		}
		return jumpMoves;
	}
}
