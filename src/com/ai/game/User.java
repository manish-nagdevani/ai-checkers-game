package com.ai.game;

public class User extends Player{
	public User(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public Color getColor() {
		return color;
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
