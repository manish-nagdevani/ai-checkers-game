package com.game.checkers;

import java.util.HashSet;
import java.util.Set;

import com.game.checkers.ai.State;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.players.CPU;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

public class GameCommonUtils {
	
	public static boolean hasMoreMoves(Player p, CheckerBoard board) {
		Set<Move> allLegalMoves = new HashSet<Move>();
		for(CheckerPiece piece : board.getPieces(p.getColor())) {
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
		}
		return (allLegalMoves.size() > 0);
	}
	
	private static boolean hasMoreMoves(CheckerBoard board) {
		Set<Move> allLegalMoves = new HashSet<Move>();
		int destCount = 0;
		for(CheckerPiece piece : board.getPieces(CPU.getInstance().getColor())) {
			//Check if any moves possible
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
			
			//check if all pieces are at destination
			if(piece.getBelongsTo().getX() == board.getSize()-1) {
				destCount++;
			}
		}
		
		if(destCount == board.getPieceCount(CPU.getInstance().getColor())) {
			return false;
		}
		
		destCount = 0;
		
		for(CheckerPiece piece : board.getPieces(User.getInstance().getColor())) {
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
			
			//check if all pieces are at destination
			if(piece.getBelongsTo().getX() == 0) {
				destCount++;
			}
		}
		
		if(destCount == board.getPieceCount(User.getInstance().getColor())) {
			return false;
		}
		
		
		return (allLegalMoves.size() > 0);
	}
	
	public static boolean hasMoreMoves(Set<Move> moves) {
		return (moves.size() > 0);
	}
	
	public static boolean hasWon(Player p, CheckerBoard board) {
		Player opponent = null;
		if(p instanceof User) {
			opponent = CPU.getInstance();
		} else {
			opponent = User.getInstance();
		}
		
		return (board.getPieceCount(p.getColor()) > board.getPieceCount(opponent.getColor()));
	}
	
	public static boolean isWithinLimits(int x, int y, int size) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}
	
	public static boolean isTerminalState(State state) {
		return !hasMoreMoves(state.getBoard());
	}

}
