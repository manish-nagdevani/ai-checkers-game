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
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	protected Color color;
	protected int score;
	protected Set<CheckerPiece> pieces;
	
	protected abstract void makeMove(CheckerBoard board);
	
	
	//Given a Move, this function actually performs it
	public static void performMove(Move move, CheckerBoard board) {
		Square srcSq = move.getSrc();
		srcSq.getStyleClass().add("checker-square-active");
		Square destSq = move.getDest();
		if(GamePlay.getInstance().getActivePlayer() instanceof CPU) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		destSq.getStyleClass().add("checker-square-legal-suggestion");
		System.out.println(GamePlay.getInstance().getActivePlayer().getName()+" played move:"+ move.getType().toString() +"( "+srcSq.getX()+" , "+srcSq.getY()+" => "+destSq.getX() + " , "+destSq.getY()+" )");
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
		srcSq.getStyleClass().removeAll("checker-square-active");
		destSq.getStyleClass().removeAll("checker-square-legal-suggestion");
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
	
	public int getPieceCount() {
		return (pieces == null) ? 0 : pieces.size();
	}
}
