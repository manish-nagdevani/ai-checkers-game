package com.game.checkers.eval;

/**
 * Represents the summary of the board
 * 
 * @author Manish
 *
 */
public class BoardSummary {
	private int cpuPieceCount;
	private int userPieceCount;

	/**
	 * @return piece count for cpu
	 */
	public int getCpuPieceCount() {
		return cpuPieceCount;
	}

	/**
	 * @param cpuPieceCount
	 */
	public void setCpuPieceCount(int cpuPieceCount) {
		this.cpuPieceCount = cpuPieceCount;
	}

	/**
	 * @return piece count for user
	 */
	public int getUserPieceCount() {
		return userPieceCount;
	}

	/**
	 * @param userPieceCount
	 */
	public void setUserPieceCount(int userPieceCount) {
		this.userPieceCount = userPieceCount;
	}
}
