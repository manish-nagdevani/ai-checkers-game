package com.game.checkers.players;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;

public class User extends Player {

	private static Player user;

	private User(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	protected void makeMove(CheckerBoard board) {
		// TODO Auto-generated method stub

	}

	public Set<Move> getNextMove(CheckerBoard board) {
		Set<CheckerPiece> pieces = board.getPieces(this.getColor());
		Set<Move> allPossibleMoves = new HashSet<Move>();
		for (CheckerPiece piece : pieces) {
			Square sq = piece.getBelongsTo();
			Map<Square, Move> moves = LegalMoveGenerator.generateLegalMoves(sq, board);
			for (Move m : moves.values()) {
				allPossibleMoves.add(m);
			}
		}
		Set<Move> allRegularMoves = allPossibleMoves;
		Set<Move> jumpMoves = new HashSet<Move>();
		for (Move m : allPossibleMoves) {
			if (m.getType() == MoveType.JUMP) {
				jumpMoves.add(m);
			}
		}
		
		return jumpMoves.isEmpty() ? allRegularMoves : jumpMoves;
	}

	public static Player getInstance() {
		if (user == null) {
			user = new User("User", Color.BLACK);
		}
		return user;
	}
}
