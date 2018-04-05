package com.game.checkers;

import java.util.Set;

import com.game.checkers.components.CheckerPiece;
import com.game.checkers.players.Player;

public class GameCommonUtils {
	
	public static int getPieceCountForPlayer(Player p) {
		Set<CheckerPiece> pieces = p.getPieces();
		return (pieces == null) ? 0 : p.getPieces().size();
	}
	
	public static boolean isWithinLimits(int x, int y, int size) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}

}
