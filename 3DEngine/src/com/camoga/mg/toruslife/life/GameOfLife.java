package com.camoga.mg.toruslife.life;

public class GameOfLife {

	public Level level;
	
	public GameOfLife(boolean windowed, int width, int height) {
		if(windowed) new Window(this, width, height);
		level = new Level(width, height);
	}
	
	public void tick() {
		level.tick();
	}
	
	public int[] render() {		
		return level.getImage(true,0xffffff);
	}
	
	public static void main(String[] args) {
		new GameOfLife(true, 100, 100);
	}
}