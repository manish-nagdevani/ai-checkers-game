package com.game.checkers.moves;

import com.game.checkers.components.Square;

public class Move {

	public static enum MoveType {
		JUMP, REGULAR;
	}

	private Square src, dest;
	private MoveType type;

	public Move(Square src, Square dest, MoveType type) {
		super();
		this.src = src;
		this.dest = dest;
		this.type = type;
	}

	public MoveType getType() {
		return type;
	}

	public void setType(MoveType type) {
		this.type = type;
	}

	public Square getSrc() {
		return src;
	}

	public void setSrc(Square src) {
		this.src = src;
	}

	public Square getDest() {
		return dest;
	}

	public void setDest(Square dest) {
		this.dest = dest;
	}

	@Override
	public String toString() {
		return "Move [src=" + src + ", dest=" + dest + ", type=" + type + "]";
	}
	
	
}