package com.game.checkers.moves;

import java.util.HashMap;
import java.util.Map;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.Move.MoveType;

public class LegalMoveGenerator {

	public static Map<Square, Move> generateLegalMoves(Square srcSquare, final CheckerBoard board) {
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
		Square dest = null;
		dest = board.getSquare(src.getX() + direction, src.getY() - 1);
		if(dest != null) {
			if(!dest.hasCheckerPiece() || (dest.getCheckerPiece().getColor() != src.getCheckerPiece().getColor())) {
				System.out.println("I was left");
				Move diagLeft = new Move(src, dest, MoveType.REGULAR);
				normalMoves.put(dest, diagLeft);
			}
		}
		
		dest = board.getSquare(src.getX() + direction, src.getY() + 1);
		if(dest != null) {
			if(!dest.hasCheckerPiece() || (dest.getCheckerPiece().getColor() != src.getCheckerPiece().getColor())) {
				System.out.println("I was right");
				Move diagLeft = new Move(src, dest, MoveType.REGULAR);
				normalMoves.put(dest, diagLeft);
			}
		}

		return normalMoves;
	}

	private static Map<Square, Move> generateJumpMove(Square src, CheckerBoard board,
			final Map<Square, Move> normalMoves) {
		Map<Square, Move> jumpMoves = new HashMap<Square, Move>();
		int direction = 2; // Moving down on the board
		if (src.getCheckerPiece().getColor() == Color.BLACK)
			direction = -2; // Moving Up on the board

		for (Move m : normalMoves.values()) {
			if (m.getDest().hasCheckerPiece()) {
				if (src.isToRight(m.getDest())) {
					Square dest = board.getSquare(src.getX() + direction, src.getY() - 2);
					if (dest != null && !dest.hasCheckerPiece()) {
						Move diagLeft = new Move(src, dest, MoveType.JUMP);
						jumpMoves.put(dest, diagLeft);
					}
				} else if (src.isToLeft(m.getDest())) {
					Square dest = board.getSquare(src.getX() + direction, src.getY() + 2);
					if (dest != null && !dest.hasCheckerPiece()) {
						Move diagRight = new Move(src, dest, MoveType.JUMP);
						jumpMoves.put(dest, diagRight);
					}
				}
			}
		}
		return jumpMoves;
	}
}