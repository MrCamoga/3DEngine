package com.camoga.examples.toruslife.life;

public class Pattern {
	
	public static final int[][] GLIDER = new int[][] {
		{1,0,0},
		{0,1,1},
		{1,1,0},
	};
	
	public static final int[][] RPENT = new int[][] {
		{0,1,1},
		{1,1,0},
		{0,1,0}
	};
	
	public static final int[][] DIEHARD = new int[][] {
		{0,0,0,0,0,0,1},
		{1,1},
		{0,1,0,0,0,1,1,1}
	};
}