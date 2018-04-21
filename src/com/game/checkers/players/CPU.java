package com.game.checkers.players;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.checkers.ai.AI;
import com.game.checkers.ai.State;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;

public class CPU extends Player {
	private static Player cpu = null;

	private CPU(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}

	@Override
	protected void makeMove(CheckerBoard board) {

	}

	// For all WHITE Color pieces, generate all Possible Moves
	// If a Jump move is possible, filter all other moves
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

	public Move calculateBestMove(Set<Move> allPossibleMoves, CheckerBoard board) {
		return askAI(new State(board));
	}

	public Move askAI(State state) {
		AI ai = new AI(state);
		for(int i = 2; i < 15; i++)
			ai.alphaBeta(state, i, -1000, 1000, true);
		return ai.bestMove;
	}

	public static Player getInstance() {
		if (cpu == null) {
			cpu = new CPU("CPU", Color.WHITE);
		}
		return cpu;
	}
}
