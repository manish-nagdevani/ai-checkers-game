package com.game.checkers.components;

import javafx.scene.image.Image;

/**
 * Represents a Checker Piece on the board
 * 
 * @author Manish
 *
 */
public class CheckerPiece {
	private boolean hasMoved;
	private Color color;
	private Image image;
	private Square belongsTo;

	/**
	 * Returns a new Checkerpiece object
	 * 
	 * @param color
	 * @param belongsTo
	 */
	public CheckerPiece(Color color, Square belongsTo) {
		this.color = color;
		this.belongsTo = belongsTo;
		hasMoved = false;
		String filePath = "images/" + color.toString().toLowerCase() + "-checker.png";
		System.out.println(filePath);
		this.image = new Image(filePath);
	}

	/**
	 * Copy constructor
	 * 
	 * @param checkerPiece
	 */
	public CheckerPiece(CheckerPiece checkerPiece) {
		this.hasMoved = checkerPiece.isHasMoved();
		this.color = checkerPiece.getColor();
		this.image = null;
		this.belongsTo = null;
	}

	/**
	 * @return
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * @param hasMoved
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * @return color of the piece
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return Image of the piece
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return Square this piece belongs to
	 */
	public Square getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 */
	public void setBelongsTo(Square belongsTo) {
		this.belongsTo = belongsTo;
	}

	@Override
	public String toString() {
		return "CheckerPiece @ " + this.hashCode() + " [color=" + color + " Belongs To: " + belongsTo.hashCode() + "]";
	}

}
