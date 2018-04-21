package com.game.checkers.ai;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Color;
import com.game.checkers.components.Square;
import com.game.checkers.moves.Move;

public class State {
	private CheckerBoard board;
	private Move leadingAction;
	private boolean isUtilityState;
	private int utilityValue;
	
	
	public State(CheckerBoard board) {
		super();
		this.board = new CheckerBoard(board);
	}
	
	public CheckerBoard getBoard() {
		return board;
	}
	public void setBoard(CheckerBoard board) {
		this.board = board;
	}
	public Move getLeadingAction() {
		return leadingAction;
	}
	public void setLeadingAction(Move leadingAction) {
		this.leadingAction = leadingAction;
	}
	public boolean isUtilityState() {
		return isUtilityState;
	}
	public void setUtilityState(boolean isUtilityState) {
		this.isUtilityState = isUtilityState;
	}
	public int getUtilityValue() {
		return utilityValue;
	}
	public void setUtilityValue(int utilityValue) {
		this.utilityValue = utilityValue;
	}
	
	public void evaluateState() {
		int whitePiece = 0, blackPiece = 0;
		for(Square[] sq_arr : board.getSquares()) {
			for(Square sq : sq_arr) {
				if(sq.hasCheckerPiece()) {
					if(sq.getCheckerPiece().getColor() == Color.WHITE)
						whitePiece++;
					else
						blackPiece++;
				}
			}
		}
		int utilityValue = 0;
		if(whitePiece > blackPiece)
			utilityValue = 1000;
		else if(blackPiece < whitePiece)
			utilityValue = -1000;
		
		setUtilityValue(utilityValue);
	}
}
