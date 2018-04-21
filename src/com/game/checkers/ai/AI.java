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
	public AI(State root) {
		this.root = root;
	}

	public int alphaBeta(State state, int depth, int alpha, int beta, boolean isMaxPlayer) {
		if (depth == 0 || GameCommonUtils.isTerminalState(state)) {
			System.out.println("Terminal State found");
			state.evaluateState();
			return state.getUtilityValue();
		}
		if (isMaxPlayer) {
			int v = Integer.MIN_VALUE;
			List<State> childrenStates = generateAllStates(state, CPU.getInstance());
			for(State child : childrenStates) {
				System.out.println("CPU: ");
				child.getBoard().show();
				int temp = alphaBeta(child, depth - 1, alpha, beta, false);
				if(v < temp) {
					v = temp;
					if(state == root) {
						bestMove = child.getLeadingAction();
					}
				}
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
					break;
			}
			return v;
		} else {
			int v = Integer.MAX_VALUE;
			List<State> childrenStates = generateAllStates(state, User.getInstance());
			for(State child : childrenStates) {
				System.out.println("User: ");
				child.getBoard().show();
				int temp = alphaBeta(child, depth - 1, alpha, beta, true);
				v = Math.min(v, temp);
				if(v > temp) {
					v = temp;
					if(state == root) {
						bestMove = child.getLeadingAction();
					}
				}
				beta = Math.min(beta, v);
				if (beta <= alpha)
					break;
			}
			return v;
		}
	}

	private List<State> generateAllStates(final State initialState, Player player) {
		Set<Move> allPossibleMoves = new HashSet<Move>();
		if(player instanceof CPU) {
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

}
