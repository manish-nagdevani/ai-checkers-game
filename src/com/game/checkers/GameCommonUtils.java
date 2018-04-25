package com.game.checkers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.game.checkers.ai.DifficultyLevel;
import com.game.checkers.ai.Heuristics;
import com.game.checkers.ai.State;
import com.game.checkers.components.CheckerBoard;
import com.game.checkers.components.CheckerPiece;
import com.game.checkers.components.Square;
import com.game.checkers.moves.LegalMoveGenerator;
import com.game.checkers.moves.Move;
import com.game.checkers.moves.Move.MoveType;
import com.game.checkers.players.CPU;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

import javafx.application.Platform;

public class GameCommonUtils {
	
	public static boolean hasMoreMoves(Player p, CheckerBoard board) {
		Set<Move> allLegalMoves = new HashSet<Move>();
		for(CheckerPiece piece : board.getPieces(p.getColor())) {
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
		}
		return (allLegalMoves.size() > 0);
	}
	
	private static boolean hasMoreMoves(CheckerBoard board) {
		Set<Move> allLegalMoves = new HashSet<Move>();
		int destCount = 0;
		for(CheckerPiece piece : board.getPieces(CPU.getInstance().getColor())) {
			//Check if any moves possible
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
			
			//check if all pieces are at destination
			if(piece.getBelongsTo().getX() == board.getSize()-1) {
				destCount++;
			}
		}
		
		if(destCount == board.getPieceCount(CPU.getInstance().getColor())) {
			return false;
		}
		
		destCount = 0;
		
		for(CheckerPiece piece : board.getPieces(User.getInstance().getColor())) {
			for(Move m : LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board).values()) {
				allLegalMoves.add(m);
			}
			
			//check if all pieces are at destination
			if(piece.getBelongsTo().getX() == 0) {
				destCount++;
			}
		}
		
		if(destCount == board.getPieceCount(User.getInstance().getColor())) {
			return false;
		}
		
		
		return (allLegalMoves.size() > 0);
	}
	
	public static boolean hasMoreMoves(Set<Move> moves) {
		return (moves.size() > 0);
	}
	
	public static boolean hasWon(Player p, CheckerBoard board) {
		Player opponent = null;
		if(p instanceof User) {
			opponent = CPU.getInstance();
		} else {
			opponent = User.getInstance();
		}
		
		return (board.getPieceCount(p.getColor()) > board.getPieceCount(opponent.getColor()));
	}
	
	public static boolean isWithinLimits(int x, int y, int size) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}
	
	public static boolean isTerminalState(State state) {
		return !hasMoreMoves(state.getBoard());
	}
	
	public static Set<Move> allJumpMovesPossible(CheckerBoard board, Player p) {
		Set<Move> allLegalMoves = new HashSet<Move>();
		for(CheckerPiece piece : board.getPieces(p.getColor())) {
			Map<Square, Move> moves = LegalMoveGenerator.generateLegalMoves(piece.getBelongsTo(), board);
			for(Move m : moves.values()) {
				allLegalMoves.add(m);
			}
		}
		
		Set<Move> jumpMoves = new HashSet<Move>();
		for (Move m : allLegalMoves) {
			if (m.getType() == MoveType.JUMP) {
				jumpMoves.add(m);
			}
		}
		
		return jumpMoves;
	}
	
	public static int evaluation(State state, Player player, Player adversary) {
		int h1 = Heuristics.pieceCountHeuristic(state.getBoard(), player, adversary);
		int h2 = Heuristics.movesToDestHeuristic(state.getBoard(), player, adversary);
		int h3 = Heuristics.availJumpMovesHeuristic(state.getBoard(), player, adversary);
		int h4 = Heuristics.piecesOnDestHeuristic(state.getBoard(), player, adversary);
		int h5 = Heuristics.numOfInvulnerablePiecesHeuristic(state.getBoard(), player, adversary);
		int h6 = Heuristics.supportPiecesHeuristic(state.getBoard(), player, adversary);
		int heuristicValue = h1 + h2 + h3 + h4 + h5 + h6;		
		return heuristicValue;
	}
	
	public static int evaluateTerminalState(State state, Player player, Player adversary) {
		int h1 = Heuristics.pieceCountHeuristic(state.getBoard(), player, adversary);
		//CPU Wins
		if(h1 > 0) {
			state.setUtilityValue(1000);
		}
		//User wins
		else if (h1 < 0) {
			state.setUtilityValue(-1000);
		}
		//Draw
		else {
			state.setUtilityValue(0);
		}
		return state.getUtilityValue();
	}

	public static List<String> getDificultyLevels() {
		List<String> difficultyLevels = new ArrayList<>();
		for(DifficultyLevel level : DifficultyLevel.values()) {
			difficultyLevels.add(level.toString());
		}
		return difficultyLevels;
	}
	
	public static void log(String message) {
		Platform.runLater(() -> {GamePlay.getInstance().getLoggingArea().appendText(message+"\n");});
	}

}
