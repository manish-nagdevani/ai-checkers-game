package com.game.checkers.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.checkers.GameCommonUtils;
import com.game.checkers.GamePlay;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.players.Player;

import javafx.scene.layout.GridPane;

public class CheckerBoard extends GridPane {

	private Square[][] squares;
	private final int size;
	private Square activeSquare;
	private Map<Square, Move> legalMoves;
	private Map<Color, Set<CheckerPiece>> playerPieces;

	public CheckerBoard(final int size) {
		this.size = size;
		this.squares = new Square[size][size];
	}

	public CheckerBoard(CheckerBoard board) {
		this.size = board.size;
		this.squares = new Square[size][size];
		playerPieces = new HashMap<Color, Set<CheckerPiece>>();
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				squares[row][col] = new Square(board.getSquare(row, col));
				if (squares[row][col].getCheckerPiece() != null) {
					squares[row][col].getCheckerPiece().setBelongsTo(squares[row][col]);
					Set<CheckerPiece> set = playerPieces.get(squares[row][col].getCheckerPiece().getColor());
					if (set == null) {
						set = new HashSet<CheckerPiece>();
					}
					set.add(squares[row][col].getCheckerPiece());
					playerPieces.put(squares[row][col].getCheckerPiece().getColor(), set);
				}
			}
		}
		this.legalMoves = null;
	}

	public void init() {
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
	}

	public void placeInitialPieces() {
		Set<CheckerPiece> whitePieces = new HashSet<CheckerPiece>();
		Set<CheckerPiece> blackPieces = new HashSet<CheckerPiece>();

		for (int row = 0; row < this.size; row++)
			for (int col = 0; col < this.size; col++) {
				Square sq = this.squares[row][col];
				if (sq.getColor() == Color.BLACK && (row == 0 || row == 1 || row == size - 1 || row == size - 2)) {
					if (row == 0 || row == 1) {
						sq.setCheckerPiece(new CheckerPiece(Color.WHITE, this.squares[row][col]));
						whitePieces.add(sq.getCheckerPiece());
					} else {
						sq.setCheckerPiece(new CheckerPiece(Color.BLACK, this.squares[row][col]));
						blackPieces.add(sq.getCheckerPiece());
					}
				}
			}
		setPieces(Color.BLACK, blackPieces);
		setPieces(Color.WHITE, whitePieces);
	}

	private void onSquareClickEvent(int xVal, int yVal) {
		Square clickedSquare = this.squares[xVal][yVal];
		if (clickedSquare.hasCheckerPiece()) {
			if (clickedSquare.getCheckerPiece().getColor() == Color.WHITE) {
				System.out.println("That's not your Piece");
				return;
			}
		}

		// 1st click capture
		if (activeSquare == null) {
			if (clickedSquare.hasCheckerPiece()) {
				activeSquare = clickedSquare;
				setActiveSquare(clickedSquare);
				legalMoves = LegalMoveGenerator.generateLegalMoves(activeSquare, this);
				for (Move m : legalMoves.values()) {
					if (!m.getDest().hasCheckerPiece()) {
						this.getSquare(m.getDest().getX(), m.getDest().getY()).getStyleClass()
								.removeAll("checker-square-legal-suggestion");
						this.getSquare(m.getDest().getX(), m.getDest().getY()).getStyleClass()
								.add("checker-square-legal-suggestion");
						System.out.println(
								m.getType().toString() + "(" + m.getDest().getX() + ", " + m.getDest().getY() + ")");
					} else {
						legalMoves.remove(m);
					}
				}
			} else {
				System.out.println("No Piece on this cell");
			}
		} else {
			if (!clickedSquare.hasCheckerPiece()) {
				if (this.legalMoves.keySet().contains(clickedSquare)) {
					Move decidedMove = this.legalMoves.get(clickedSquare);
					Player.performMove(decidedMove, this);
					this.activeSquare.getStyleClass().removeAll("checker-square-active");
					this.activeSquare = null;
					GamePlay.getInstance().switchActivePlayer();
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
		System.out.println("----------------------------------------");
	}

	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square[][] squares) {
		this.squares = squares;
	}

	public Square getSquare(int x, int y) {
		if (GameCommonUtils.isWithinLimits(x, y, size))
			return this.squares[x][y];
		return null;

	}

	public int getSize() {
		return size;
	}

	public Square getActiveSquare() {
		return activeSquare;
	}

	public Set<CheckerPiece> getPieces(Color playerColor) {
		return playerPieces.get(playerColor);
	}

	public void killCheckerPiece(CheckerPiece piece) {
		Set<CheckerPiece> pieces = playerPieces.get(piece.getColor());
		pieces.remove(piece);
	}

	public int getPieceCount(Color color) {
		return playerPieces.get(color) == null ? 0 : playerPieces.get(color).size();
	}

	public void setPieces(Color color, Set<CheckerPiece> pieces) {
		if (playerPieces == null) {
			playerPieces = new HashMap<Color, Set<CheckerPiece>>();
		}
		playerPieces.put(color, pieces);
	}

	@Override
	public String toString() {
		return "CheckerBoard @" + hashCode() + " [squares=" + Arrays.toString(squares) + ", size=" + size
				+ ", activeSquare=" + activeSquare + ", legalMoves=" + legalMoves + ", playerPieces=" + playerPieces
				+ "]";
	}

}
