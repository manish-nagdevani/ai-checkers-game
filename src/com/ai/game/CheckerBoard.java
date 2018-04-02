package com.ai.game;

import java.util.List;
import java.util.Map;

import com.ai.game.Move.MoveType;

import javafx.scene.layout.GridPane;

public class CheckerBoard extends GridPane {

	private Square[][] squares;
	private final int size;
	private Square activeSquare;
	private Map<Square, Move> legalMoves;

	public CheckerBoard(final int size) {
		this.size = size;
		this.squares = new Square[size][size];
	}

	public CheckerBoard init() {
		boolean isWhite = true;
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				if (isWhite) {
					this.squares[row][col] = new Square(row, col, Color.WHITE);
				} else {
					this.squares[row][col] = new Square(row, col, Color.BLACK);
				}
				if (col != size - 1) {
					isWhite = !isWhite;
				}
				this.add(squares[row][col], col, row);

				final int xVal = row;
				final int yVal = col;

				this.squares[row][col].setOnAction(e -> onSquareClickEvent(xVal, yVal));
			}
		return this;
	}

	protected CheckerBoard placeInitialPieces() {
		for (int row = 0; row < this.size; row++)
			for (int col = 0; col < this.size; col++) {
				Square sq = this.squares[row][col];
				if (sq.getColor() == Color.BLACK && (row == 0 || row == 1 || row == size - 1 || row == size - 2)) {

					if (row == 0 || row == 1)
						sq.setCheckerPiece(new CheckerPiece(Color.WHITE));
					else
						sq.setCheckerPiece(new CheckerPiece(Color.BLACK));
				}
			}
		return this;
	}

	private void onSquareClickEvent(int xVal, int yVal) {
		Square clickedSquare = this.squares[xVal][yVal];

		if (activeSquare == null) {
			activeSquare = clickedSquare;
			setActiveSquare(clickedSquare);
			legalMoves = LegalMoveGenerator.generateLegalMoves(activeSquare, this);
			for (Move m : legalMoves.values()) {
				this.getSquare(m.getDest().getX(), m.getDest().getY()).getStyleClass()
						.removeAll("checker-square-legal-suggestion");
				this.getSquare(m.getDest().getX(), m.getDest().getY()).getStyleClass()
						.add("checker-square-legal-suggestion");
				System.out.println(m.getType().toString() + "(" + m.getDest().getX() + ", " + m.getDest().getY() + ")");
			}

		} else {
			if (!clickedSquare.hasCheckerPiece()) {
				if (this.legalMoves.keySet().contains(clickedSquare)) {
					Move decidedMove = this.legalMoves.get(clickedSquare);
					Player.performMove(decidedMove, this);
					this.activeSquare.getStyleClass().removeAll("checker-square-active");
					this.activeSquare = null;
				} else {
					System.out.println("Not a legal Move. Please try again");
				}
			} else if (clickedSquare.getCheckerPiece().getColor() == activeSquare.getCheckerPiece().getColor()) {
				this.activeSquare.getStyleClass().removeAll("checker-square-active");
				this.activeSquare = null;
			}
			for (Move m : legalMoves.values()) {
				this.getSquare(m.getDest().getX(), m.getDest().getY()).getStyleClass()
						.removeAll("checker-square-legal-suggestion");
			}
		}
	}

	public void setActiveSquare(Square s) {
		// Remove style from old active square
		if (this.activeSquare != null)
			this.activeSquare.getStyleClass().removeAll("checker-square-active");

		this.activeSquare = s;

		// Add style to new active square
		if (this.activeSquare != null)
			this.activeSquare.getStyleClass().add("checker-square-active");
	}

	public void show() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Square sq = squares[row][col];
				if (sq.hasCheckerPiece()) {
					if (sq.getCheckerPiece().getColor() == Color.WHITE)
						System.out.print("W");
					else
						System.out.print("B");
				} else {
					System.out.print("_");
				}
			}
			System.out.println();
		}
	}

	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square[][] squares) {
		this.squares = squares;
	}

	public Square getSquare(int x, int y) {
		if (BoardUtils.isWithinLimits(x, y, size))
			return this.squares[x][y];
		return null;

	}

	public int getSize() {
		return size;
	}

}
