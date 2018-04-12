package com.game.checkers.components;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Square extends Button {

	private int x, y;
	private CheckerPiece checkerPiece;
	private final Color color;

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

	public CheckerPiece getCheckerPiece() {
		return checkerPiece;
	}

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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

	public boolean hasCheckerPiece() {
		return (getCheckerPiece() != null);
	}

	public CheckerPiece releasePiece() {
		//this.checkerPiece.setBelongsTo(null);
		CheckerPiece tmpPiece = this.checkerPiece;
		setCheckerPiece(null);
		return tmpPiece;
	}

	// Returns true is src is to Right of dest
	public boolean isToRight(Square dest) {
		return (this.getY() > dest.getY());
	}

	// Returns true is src is to Left of dest
	public boolean isToLeft(Square dest) {
		return (this.getY() < dest.getY());
	}

	@Override
	public String toString() {
		return "Square [x=" + x + ", y=" + y + ", checkerPiece=" + checkerPiece + ", color=" + color + "]";
	}

}
