package com.game.checkers.moves;

import com.game.checkers.components.Square;

/**
 * Class encapsulating move information
 * 
 * @author Manish
 *
 */
public class Move {

	/**
	 * Contains 2 types of moves: Regular and Jump moves
	 * 
	 * @author Manish
	 *
	 */
	public static enum MoveType {
		JUMP, REGULAR;
	}

	private Square src, dest;
	private MoveType type;

	/**
	 * Constructor, return new Move object
	 * 
	 * @param src
	 * @param dest
	 * @param type
	 */
	public Move(Square src, Square dest, MoveType type) {
		super();
		this.src = src;
		this.dest = dest;
		this.type = type;
	}

	/*
	 * Getters and Setters
	 */

	/**
	 * @return move type
	 */
	public MoveType getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(MoveType type) {
		this.type = type;
	}

	/**
	 * @return source square for a move
	 */
	public Square getSrc() {
		return src;
	}

	/**
	 * @param src
	 */
	public void setSrc(Square src) {
		this.src = src;
	}

	/**
	 * @return destination square for a move
	 */
	public Square getDest() {
		return dest;
	}

	/**
	 * @param dest
	 */
	public void setDest(Square dest) {
		this.dest = dest;
	}

	@Override
	public String toString() {
		return "Move [src=" + src + ", dest=" + dest + ", type=" + type + "]";
	}

}