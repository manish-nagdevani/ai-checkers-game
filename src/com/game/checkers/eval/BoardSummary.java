package com.game.checkers.eval;

public class BoardSummary {
	private int cpuPieceCount;
	private int userPieceCount;
	private int userDestStepCount;
	private int cpuDestStepCount;
	
	public int getCpuPieceCount() {
		return cpuPieceCount;
	}
	public void setCpuPieceCount(int cpuPieceCount) {
		this.cpuPieceCount = cpuPieceCount;
	}
	public int getUserPieceCount() {
		return userPieceCount;
	}
	public void setUserPieceCount(int userPieceCount) {
		this.userPieceCount = userPieceCount;
	}
	public int getUserDestStepCount() {
		return userDestStepCount;
	}
	public void setUserDestStepCount(int userDestStepCount) {
		this.userDestStepCount = userDestStepCount;
	}
	public int getCpuDestStepCount() {
		return cpuDestStepCount;
	}
	public void setCpuDestStepCount(int cpuDestStepCount) {
		this.cpuDestStepCount = cpuDestStepCount;
	}
	@Override
	public String toString() {
		return "BoardSummary [cpuPieceCount=" + cpuPieceCount + ", userPieceCount=" + userPieceCount
				+ ", userDestStepCount=" + userDestStepCount + ", cpuDestStepCount=" + cpuDestStepCount + "]";
	}
}
