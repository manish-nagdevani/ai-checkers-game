package com.game.checkers.players;

import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.Color;

public class User extends Player{
	
	private static Player user;
	private User(final String name, final Color color) {
		super();
		this.color = color;
		this.name = name;
	}


	public String getName() {
		return name;
	}

	@Override
	protected void makeMove(CheckerBoard board) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static Player getInstance(String name, Color color) {
		if(user == null) {
			user = new User(name, color);
		}
		return user;
	}
	
	public static Player getInstance() {
		return user;
	}
}
