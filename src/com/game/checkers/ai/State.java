package com.game.checkers.ai;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.moves.Move;

/**
 * Represents a state of the board
 * 
 * @author Manish
 *
 */
public class State {
	private CheckerBoard board;
	private Move leadingAction;
	private boolean isUtilityState;
	private int utilityValue;

	/**
	 * Returns new State for a given board
	 * 
	 * @param board
	 */
	public State(CheckerBoard board) {
		super();
		this.board = new CheckerBoard(board);
	}

	/*
	 * Setter and Getter
	 */

	/**
	 * @return
	 */
	public CheckerBoard getBoard() {
		return board;
	}

	/**
	 * @param board
	 */
	public void setBoard(CheckerBoard board) {
		this.board = board;
	}

	/**
	 * @return
	 */
	public Move getLeadingAction() {
		return leadingAction;
	}

	/**
	 * @param leadingAction
	 */
	public void setLeadingAction(Move leadingAction) {
		this.leadingAction = leadingAction;
	}

	/**
	 * @return
	 */
	public boolean isUtilityState() {
		return isUtilityState;
	}

	/**
	 * @param isUtilityState
	 */
	public void setUtilityState(boolean isUtilityState) {
		this.isUtilityState = isUtilityState;
	}

	/**
	 * @return
	 */
	public int getUtilityValue() {
		return utilityValue;
	}

	/**
	 * @param utilityValue
	 */
	public void setUtilityValue(int utilityValue) {
		this.utilityValue = utilityValue;
	}
}
