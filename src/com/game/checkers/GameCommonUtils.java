package com.game.checkers;

import java.util.HashSet;
import java.util.Set;

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
		for(CheckerPiece piece : p.getPieces()) {
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
		}
		return (allLegalMoves.size() > 0);
	}
	
	public static boolean hasMoreMoves(Set<Move> moves) {
		return (moves.size() > 0);
	}
	
	public static boolean hasWon(Player p) {
		Player opponent = null;
		if(p instanceof User) {
			opponent = CPU.getInstance();
		} else {
			opponent = User.getInstance();
		}
		
		return (p.getPieceCount() > opponent.getPieceCount());
	}
	
	public static boolean isWithinLimits(int x, int y, int size) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}

}
