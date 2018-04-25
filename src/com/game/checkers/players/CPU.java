package com.game.checkers.players;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.checkers.GameCommonUtils;
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

	public Move calculateBestMove(CheckerBoard board) {
		return askAI(new State(board));
	}

	public Move askAI(State state) {
		AI ai = new AI(state);
		int maxDepth = 0;
		switch (this.getLevel()) {
		case BEGINNER:
			maxDepth = 2;
			break;
		case INTERMEDIATE:
			maxDepth = 10;
			break;
		case PRO:
			maxDepth = 12;			
			break;
		default:
			maxDepth = 8;
			break;
		}		
		
		ai.alphaBeta(state, maxDepth, -1000, 1000, true);
		
		GameCommonUtils.log("1. Cutoff = " + maxDepth);
		GameCommonUtils.log("2. Total Number of Nodes Generated = " + AI.getNodesCreated());
		GameCommonUtils.log("3. Number of Times Pruning occured in MAX function = " + AI.getNumberOfMaxPruning());
		GameCommonUtils.log("4. Number of Times Pruning occured in MIN function = " + AI.getNumberOfMinPruning());
		
		ai.resetCounters();
		
		Move bestMove = ai.bestMove;
		
		ai.clean();
		return bestMove;
	}

	public static Player getInstance() {
		if (cpu == null) {
			cpu = new CPU("CPU", Color.WHITE);
		}
		return cpu;
	}
}
