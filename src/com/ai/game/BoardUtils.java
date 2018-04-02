package com.ai.game;

public class BoardUtils {
	
	protected static boolean isWithinLimits(int x, int y, int size) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}

}
