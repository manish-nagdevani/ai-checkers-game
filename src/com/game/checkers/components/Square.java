package com.game.checkers.components;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Represents a square on the checker board
 * 
 * @author Manish
 *
 */
public class Square extends Button {

	private int x, y;
	private CheckerPiece checkerPiece;
	private final Color color;

	/**
	 * Returns a new object of Square
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	public Square(int x, int y, final Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.getStyleClass().add("checker-square");
		if (color == Color.WHITE)
			this.getStyleClass().add("checker-square-white");
		else
			this.getStyleClass().add("checker-square-black");
	}

	/**
	 * Copy constructor
	 * 
	 * @param square
	 */
	public Square(Square square) {
		this.x = square.getX();
		this.y = square.getY();
		this.color = square.getColor();
		if (square.getCheckerPiece() != null) {
			this.checkerPiece = new CheckerPiece(square.getCheckerPiece());
		}
	}

	/**
	 * @return checker piece object on the square or null otherwise
	 */
	public CheckerPiece getCheckerPiece() {
		return checkerPiece;
	}

	/**
	 * Place piece on the square
	 * 
	 * @param checkerPiece
	 */
	public void setCheckerPiece(CheckerPiece checkerPiece) {
		this.checkerPiece = checkerPiece;
		if (checkerPiece != null) {
			ImageView iv = new ImageView(checkerPiece.getImage());
			iv.setFitHeight(30);
			iv.setFitWidth(35);
			this.checkerPiece.setBelongsTo(this);
			Platform.runLater(() -> {
				this.setGraphic(iv);
			});
		} else {
			javafx.application.Platform.runLater(() -> {
				this.setGraphic(new ImageView());
			});
		}
	}

	/**
	 * @return x co-ordinate of the square
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y co-ordinate of the square
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return color of the square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return true if square holds a piece, false otherwise
	 */
	public boolean hasCheckerPiece() {
		return (getCheckerPiece() != null);
	}

	/**
	 * @return Checkerpice and set to null
	 */
	public CheckerPiece releasePiece() {
		CheckerPiece tmpPiece = this.checkerPiece;
		setCheckerPiece(null);
		return tmpPiece;
	}

	/**
	 * @param dest
	 * @return true is src is to Right of dest
	 */
	public boolean isToRight(Square dest) {
		return (this.getY() > dest.getY());
	}

	/**
	 * @param dest
	 * @return true is src is to Left of dest
	 */
	public boolean isToLeft(Square dest) {
		return (this.getY() < dest.getY());
	}

	@Override
	public String toString() {
		return "Square @ " + this.hashCode() + " [x=" + x + ", y=" + y + ", checkerPiece=" + checkerPiece + ", color="
				+ color + "]";
	}

}
