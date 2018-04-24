package com.game.checkers.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.game.checkers.GameCommonUtils;
import com.game.checkers.moves.Move;
import com.game.checkers.players.CPU;
import com.game.checkers.players.Player;
import com.game.checkers.players.User;

public class AI {
	public Move bestMove;
	public State root;
	private final Player player = CPU.getInstance();
	private final Player adversary = User.getInstance();
	private static int nodesCreated = 0;
	private static int numberOfMaxPruning = 0;
	private static int numberOfMinPruning = 0;
	private static int maxDepth = 0;
	private static int searchCount = 0;
	public AI(State root) {
		this.root = root;
		nodesCreated = 1;
		searchCount++;
	}

	public static int getNodesCreated() {
		return nodesCreated;
	}

	public int alphaBeta(State state, int depth, int alpha, int beta, boolean isMaxPlayer) {
		if (depth == 0) {
			System.out.println("Reached depth = 0");
			int heuristicValue = GameCommonUtils.evaluation(state, player, adversary);
			state.getBoard().show();
			System.out.println(heuristicValue);
			return heuristicValue;
		}
		if (GameCommonUtils.isTerminalState(state)) {
			System.out.println("Terminal State found");
			int utilityValue = GameCommonUtils.evaluateTerminalState(state, player, adversary);
			state.getBoard().show();
			System.out.println(utilityValue);
			return utilityValue;
		}
		
		int localMaxDepth = 0;
		if (isMaxPlayer) {
			int v = Integer.MIN_VALUE;
			List<State> childrenStates = generateAllStates(state, CPU.getInstance());
			for (State child : childrenStates) {
				nodesCreated++;
				localMaxDepth++;
				int temp = alphaBeta(child, depth - 1, alpha, beta, false);
				if (v < temp) {
					v = temp;
					if (state == root) {
						bestMove = child.getLeadingAction();
					}
				}
				alpha = Math.max(alpha, v);
				if (beta <= alpha) {
					numberOfMaxPruning++;
					break;
				}
			}
			maxDepth = Math.max(maxDepth, localMaxDepth);
			return v;
		} else {
			int v = Integer.MAX_VALUE;
			List<State> childrenStates = generateAllStates(state, User.getInstance());
			for (State child : childrenStates) {
				nodesCreated++;
				localMaxDepth++;
				int temp = alphaBeta(child, depth - 1, alpha, beta, true);
				v = Math.min(v, temp);
				if (v > temp) {
					v = temp;
					if (state == root) {
						bestMove = child.getLeadingAction();
					}
				}
				beta = Math.min(beta, v);
				if (beta <= alpha) {
					numberOfMinPruning++;
					break;
				}
			}
			maxDepth = Math.max(maxDepth, localMaxDepth);
			return v;
		}
	}

	private List<State> generateAllStates(final State initialState, Player player) {
		Set<Move> allPossibleMoves = new HashSet<Move>();
		if (player instanceof CPU) {
			allPossibleMoves = ((CPU) CPU.getInstance()).getNextMove(initialState.getBoard());
		} else {
			allPossibleMoves = ((User) User.getInstance()).getNextMove(initialState.getBoard());
		}

		List<State> childrenStates = new ArrayList<State>();
		for (Move m : allPossibleMoves) {
			State resultingState = new State(initialState.getBoard());
			if (player instanceof CPU) {
				CPU.performMove(m, resultingState.getBoard(), CPU.getInstance(), User.getInstance());
				resultingState.setLeadingAction(m);
				childrenStates.add(resultingState);
			} else {
				User.performMove(m, resultingState.getBoard(), User.getInstance(), CPU.getInstance());
				resultingState.setLeadingAction(m);
				childrenStates.add(resultingState);
			}
		}
		return childrenStates;
	}

	public static int getNumberOfMaxPruning() {
		return numberOfMaxPruning;
	}

	public static int getNumberOfMinPruning() {
		return numberOfMinPruning;
	}

	public static int getSearchCount() {
		return searchCount;
	}
	
	public static int getMaxDepth() {
		return maxDepth;
	}

	public static void setMaxDepth(int maxDepth) {
		AI.maxDepth = maxDepth;
	}

	public void resetCounters() {
		nodesCreated = 0;
		numberOfMaxPruning = 0;
		numberOfMinPruning = 0;
		maxDepth = 0;
	}

	public void clean() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
