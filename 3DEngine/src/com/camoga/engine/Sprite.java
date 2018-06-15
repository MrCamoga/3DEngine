package com.camoga.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private int[] pixels;
	public int width, height;
	
	public Sprite(String path) {
		load(path);
	}
	
	public Sprite(int width, int height, int color) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}
	
	public void load(String path) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getPixels() {
		return pixels;
	}
}