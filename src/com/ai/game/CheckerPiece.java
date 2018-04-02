package com.ai.game;

import javafx.scene.image.Image;

public class CheckerPiece {
	private boolean hasMoved;
	private Color color;
	private Image image;
	
	
	public CheckerPiece(Color color) {
		this.color = color;
		hasMoved = false;
		String filePath = "images/"+color.toString().toLowerCase()+"-checker.png";
		System.out.println(filePath);
		this.image = new Image(filePath);
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
}
