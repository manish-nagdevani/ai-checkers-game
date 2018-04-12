package com.game.checkers.players;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;

public class CPU extends Player {
	//private MoveGenerator moveGen = new MoveGenerator();
	//private Difficulty level = Difficulty.PRO;
	
	private static Player cpu = null;
	private CPU(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}

	@Override
	protected void makeMove(CheckerBoard board) {

	}
	
	public Set<Move> getNextMove(CheckerBoard board) {
		Set<CheckerPiece> pieces = this.getPieces();
		Set<Move> allPossibleMoves = new HashSet<Move>();
		for(CheckerPiece piece : pieces) {
			Square sq = piece.getBelongsTo();
			Map<Square, Move> moves= LegalMoveGenerator.generateLegalMoves(sq, board);
			for(Move m : moves.values()) {
				allPossibleMoves.add(m);
			}
		}
		return allPossibleMoves;
	}

	public Move calculateBestMove(Set<Move> allPossibleMoves) {
		Set<Move> availJumpMoves = new HashSet<Move>();
		Iterator<Move> itr = allPossibleMoves.iterator();
		while(itr.hasNext()) {
			Move m = itr.next();
			if(m.getType() == MoveType.JUMP) {
				availJumpMoves.add(m);
				itr.remove();
			}
		}
		if(!availJumpMoves.isEmpty()) {
			allPossibleMoves = availJumpMoves;
		}
		
		Random rand = new Random();
		int needle = rand.nextInt(allPossibleMoves.size());
		int i = 0;
		for(Move m : allPossibleMoves) {
			if(i == needle)
				return m;
			i++;
		}
		return null;
	}
	
	public static Player getInstance(String name, Color color) {
		if(cpu == null) {
			cpu = new CPU(name, color);
		}
		return cpu;
	}

	public static Player getInstance() {
		return cpu;
	}

}
