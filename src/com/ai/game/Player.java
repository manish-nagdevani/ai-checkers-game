package com.ai.game;

import com.ai.game.Move.MoveType;

public abstract class Player {
	protected String name;
	protected Color color;
	protected int score;
	
	protected abstract void makeMove(CheckerBoard board);
	protected abstract boolean hasWon();
	
	
	protected static void performMove(Move move, CheckerBoard board) {
		Square srcSq = move.getSrc();
		Square destSq = move.getDest();
		if(srcSq != null && destSq != null) {
			if(move.getType() == MoveType.JUMP) {
				int direction = 1;
				if(srcSq.getCheckerPiece().getColor() == Color.BLACK)
					direction = -1;

				
				if(srcSq.isToRight(destSq)) {
					board.getSquare(srcSq.getX() + direction, srcSq.getY() - 1).releasePiece();
				} else if(srcSq.isToLeft(destSq)) {
					board.getSquare(srcSq.getX() + direction, srcSq.getY() + 1).releasePiece();
				}
				
			}
			
			CheckerPiece currentPlayerPiece = srcSq.releasePiece();
			destSq.setCheckerPiece(currentPlayerPiece);
		}
	}
}
