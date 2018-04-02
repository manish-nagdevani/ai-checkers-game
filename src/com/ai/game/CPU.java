package com.ai.game;

public class CPU extends Player {
	//private MoveGenerator moveGen = new MoveGenerator();
	//private Difficulty level = Difficulty.PRO;

	public CPU(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}

	@Override
	protected void makeMove(CheckerBoard board) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}

}
