package com.camoga.mg.toruslife.life;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.management.GarbageCollectorMXBean;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	public static int SCALE = 6;
	public static int WIDTH, HEIGHT;
	public static Dimension DIMENSION;
	
	private Canvas canvas;
	private Thread thread;
	private GameOfLife life;
	
	private BufferedImage image;
	private int[] pixels;
	
	public Window(GameOfLife life, int width, int height) {
		super("Game of Life");
		this.life = life;
		WIDTH = width;
		HEIGHT = height;
		DIMENSION = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		canvas = new Canvas();
		
		setSize(DIMENSION);
		setResizable(false);
		
		add(canvas);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		setVisible(true);
		thread = new Thread(() -> run(),"GL");
		thread.start();
	}
	
	public void run() {
		long last = System.nanoTime();
		
		double ns = 1e9/100D;
		
		int ticks = 0;
		int frames = 0;
		
		double delta = 0;
		
		long timer = System.currentTimeMillis();
		
		while(true) {
			long now = System.nanoTime();
			delta += (now-last)/ns;
			last = now;
			
			while(delta >= 1) {
				delta--;
				tick();
			}
			
			render();
		}
	}
	
	public void tick() {
		life.tick();
	}
	
	public void render() {
		BufferStrategy bf = canvas.getBufferStrategy();
		if(bf == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bf.getDrawGraphics();
		
		int[] p = life.render();
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = p[i];
		}
		
		g.drawImage(image, 0, 0, DIMENSION.width, DIMENSION.height, null);
		g.dispose();
		bf.show();
	}
}
