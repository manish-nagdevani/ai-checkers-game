package com.game.checkers.players;

import java.util.Set;

import com.game.checkers.GamePlay;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;

public abstract class Player {
	protected String name;
	protected Color color;
	protected int score;
	protected Set<CheckerPiece> pieces;
	
	protected abstract void makeMove(CheckerBoard board);
	
	
	//Given a Move, this function actually performs it
	public static void performMove(Move move, CheckerBoard board) {
		if(GamePlay.getInstance().getActivePlayer() instanceof CPU) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Square srcSq = move.getSrc();
		Square destSq = move.getDest();
		if(srcSq != null && destSq != null) {
			if(move.getType() == MoveType.JUMP) {
				int direction = 1;
				if(srcSq.getCheckerPiece().getColor() == Color.BLACK)
					direction = -1;

				//Kill opponent's Piece
				CheckerPiece opponentPiece = null;
				if(srcSq.isToRight(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() - 1).releasePiece();
				} else if(srcSq.isToLeft(destSq)) {
					opponentPiece = board.getSquare(srcSq.getX() + direction, srcSq.getY() + 1).releasePiece();
				}
				
				Player opponent = null;
				if(GamePlay.getInstance().getActivePlayer() instanceof CPU) {
					opponent = User.getInstance();
				} else {
					opponent = CPU.getInstance();
				}
				opponent.killCheckerPiece(opponentPiece);
				
			}
			
			//Move player's piece to the destination square
			CheckerPiece currentPlayerPiece = srcSq.releasePiece();
			destSq.setCheckerPiece(currentPlayerPiece);
		}
	}
	public Set<CheckerPiece> getPieces() {
		return pieces;
	}
	public void setPieces(Set<CheckerPiece> pieces) {
		this.pieces = pieces;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void killCheckerPiece(CheckerPiece piece) {
		this.pieces.remove(piece);
	}
}
