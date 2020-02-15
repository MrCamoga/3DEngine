package com.camoga.examples.toruslife.eca;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ElementaryCA extends Canvas {

	private BufferedImage image;
	private int[] pixels;
	
	public ElementaryCA(int rule, int width, int height) {
		this.rule = rule;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		init();
	}
	
	public void init() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffffff;
		}
		pixels[image.getWidth()/2] = 0;
	}
	
	int y = 1;
	int rule = 30;  // 30 = 0b11110
	public void iterate() {
		for(int x = 0; x < image.getWidth(); x++) {
			boolean left = getPixel(x-1, (y-1)%image.getHeight())==0;
			boolean center = getPixel(x, (y-1)%image.getHeight())==0;
			boolean right = getPixel(x+1, (y-1)%image.getHeight())==0;
			boolean white = (left&&center&&right && (((rule&1) >> 0) == 1)) 
					|| (left&&center&&!right && ((rule&2) == 2))
					|| (left&&!center&&right && ((rule&4)==4))
					|| (left&&!center&&!right && ((rule&8) ==8))
					|| (!left&&center&&right && ((rule&16) ==16))
					|| (!left&&center&&!right && ((rule&32) == 32))
					|| (!left&&!center&&right && ((rule&64) == 64))
					|| (!left&&!center&&!right && ((rule&128)  == 128));
			
			pixels[x+(y%image.getHeight())*image.getWidth()] = white ? 0:0xffffff;
		}
		y++;
	}
	
	// returns 1 or 0
	public int getPixel(int x, int y) {
		if(x == -1) x = image.getWidth()-1;
		if(x == image.getWidth()) x = 0;
		if(x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) return 0;
		return 1-pixels[x+y*image.getWidth()]/0xffffff;
	}
	
	public int[] getImage() {
		return pixels;
	}
}