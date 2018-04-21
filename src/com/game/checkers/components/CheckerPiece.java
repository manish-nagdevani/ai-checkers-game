package com.game.checkers.components;

import javafx.scene.image.Image;

public class CheckerPiece {
	private boolean hasMoved;
	private Color color;
	private Image image;
	private Square belongsTo;
	
	public CheckerPiece(Color color, Square belongsTo) {
		this.color = color;
		this.belongsTo = belongsTo;
		hasMoved = false;
		String filePath = "images/"+color.toString().toLowerCase()+"-checker.png";
		System.out.println(filePath);
		this.image = new Image(filePath);
	}
	
	public CheckerPiece(CheckerPiece checkerPiece) {
		this.hasMoved = checkerPiece.isHasMoved();
		this.color = checkerPiece.getColor();
		this.image = null;
		this.belongsTo = null;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public Color getColor() {
		return color;
	}
	
	public Image getImage() {
		return image;
	}

	public Square getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Square belongsTo) {
		this.belongsTo = belongsTo;
	}

	@Override
	public String toString() {
		return "CheckerPiece @ " + this.hashCode() +" [color=" + color + " Belongs To: "+belongsTo.hashCode()+"]";
	}
	
	

}
