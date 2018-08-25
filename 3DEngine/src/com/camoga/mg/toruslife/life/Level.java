package com.camoga.mg.toruslife.life;

/**
 * Level is cyclic like if it were the surface of a torus
 *
 */
public class Level {
	
	private int steps = 0;
	
//	enum BorderType {
//		ALIVE(1), DEAD(0);
//		
//		private int state;
//		
//		BorderType(int state) {
//			this.state = state;
//		}
//		
//		public int getState() {
//			return state;
//		}
//	}
	
	private int[] pixels;
	private int[] newpixels;
	private int width, height;
//	private BorderType type = BorderType.ALIVE;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width*height];
		newpixels = new int[width*height];
		
//		randomInit();
		loadPattern(Pattern.GLIDER, 0, 0);
		loadPattern(Pattern.GLIDER, 10, 0);
		loadPattern(Pattern.GLIDER, 20, 0);
		loadPattern(Pattern.GLIDER, 30, 0);
		loadPattern(Pattern.GLIDER, 40, 0);
		loadPattern(Pattern.GLIDER, 50, 0);
		loadPattern(Pattern.GLIDER, 60, 0);
		loadPattern(Pattern.GLIDER, 70, 0);
		
		loadPattern(Pattern.GLIDER, 0, 7);
		loadPattern(Pattern.GLIDER, 10, 7);
		loadPattern(Pattern.GLIDER, 20, 7);
		loadPattern(Pattern.GLIDER, 30, 7);
		loadPattern(Pattern.GLIDER, 40, 7);
		loadPattern(Pattern.GLIDER, 50, 7);
		loadPattern(Pattern.GLIDER, 60, 7);
		loadPattern(Pattern.GLIDER, 70, 7);
		
		loadPattern(Pattern.GLIDER, 0, 14);
		loadPattern(Pattern.GLIDER, 10, 14);
		loadPattern(Pattern.GLIDER, 20, 14);
		loadPattern(Pattern.GLIDER, 30, 14);
		loadPattern(Pattern.GLIDER, 40, 14);
		loadPattern(Pattern.GLIDER, 50, 14);
		loadPattern(Pattern.GLIDER, 60, 14);
		loadPattern(Pattern.GLIDER, 70, 14);
		
		loadPattern(Pattern.GLIDER, 0, 21);
		loadPattern(Pattern.GLIDER, 10, 21);
		loadPattern(Pattern.GLIDER, 20, 21);
		loadPattern(Pattern.GLIDER, 30, 21);
		loadPattern(Pattern.GLIDER, 40, 21);
		loadPattern(Pattern.GLIDER, 50, 21);
		loadPattern(Pattern.GLIDER, 60, 21);
		loadPattern(Pattern.GLIDER, 70, 21);
		
		loadPattern(Pattern.GLIDER, 0, 28);
		loadPattern(Pattern.GLIDER, 10, 28);
		loadPattern(Pattern.GLIDER, 20, 28);
		loadPattern(Pattern.GLIDER, 30, 28);
		loadPattern(Pattern.GLIDER, 40, 28);
		loadPattern(Pattern.GLIDER, 50, 28);
		loadPattern(Pattern.GLIDER, 60, 28);
		loadPattern(Pattern.GLIDER, 70, 28);
		
		loadPattern(Pattern.GLIDER, 0, 35);
		loadPattern(Pattern.GLIDER, 10, 35);
		loadPattern(Pattern.GLIDER, 20, 35);
		loadPattern(Pattern.GLIDER, 30, 35);
		loadPattern(Pattern.GLIDER, 40, 35);
		loadPattern(Pattern.GLIDER, 50, 35);
		loadPattern(Pattern.GLIDER, 60, 35);
		loadPattern(Pattern.GLIDER, 70, 35);
	}
	
	public void randomInit() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = Math.random() < 0.5 ? 0:1;
		}
	}
	
	public void tick() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int numOfNeighbours = countAliveNeighbours(x, y, 1);
				switch (getState(x,y)) {
					case 0:
						if(numOfNeighbours == 3) newpixels[x+y*width] = 1;
						else newpixels[x+y*width] = 0;
						break;
					case 1:
						if(numOfNeighbours != 2 && numOfNeighbours != 3) newpixels[x+y*width] = 0;
						else newpixels[x+y*width] = 1;
						break;
				}
			}
		}
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = newpixels[i];
		}
		steps++;
	}
	
	public int countAliveNeighbours(int xo, int yo, int SIZE) {
		int total = 0;
		for(int y = -SIZE; y <= SIZE; y++) {
			for(int x = -SIZE; x <= SIZE; x++) {
				if(x==0&&y==0) continue;
				total += getState(xo+x, yo+y);
			}
		}
		
		return total;
	}
	
	public void loadPattern(int[][] pattern, int xo, int yo) {
		for(int y = 0; y < pattern.length; y++) {
			int ya = y + yo;
			for(int x = 0; x < pattern[y].length; x++) {
				int xa = x + xo;
				pixels[indexOf(xa, ya)] = pattern[y][x];
			}
		}
	}
	
	public int indexOf(int x, int y) {
		return (x+width)%width + (y+height)%height*width;
	}
	
	public int getState(int x, int y) {
		return pixels[(x+width)%width + ((y+height)%height)*width];
	}
	
	public int[] getImage(boolean inverse, int color) {
		int[] result = new int[pixels.length];
		for(int i = 0; i < result.length; i++) {
			result[i] = (inverse ? (1-pixels[i]):pixels[i])*color;
		}
		return result;
	}
	
}
