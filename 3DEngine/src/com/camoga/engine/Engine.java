package com.camoga.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.camoga.engine.geom.Point2D;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.input.Camera;
import com.camoga.engine.input.Key;

/**
 * Main class of the Engine.
 * Still needs some refactorization and cleaning up
 * @author usuario
 *
 */
public abstract class Engine extends Canvas implements Runnable {

	public static String TITLE = "3D Engine";
	public static int SCALE = 1;
	public static int WIDTH = 1280/SCALE, HEIGHT = 720/SCALE;
	public static Dimension DIMENSION = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
	public static int FOCAL = 1000/SCALE;
	public static double UPS = 30;
	
	public static long clock = 0;
	public static double time = 0;
	
	public boolean running;
	
	public Thread thread;
	public JFrame frame;
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
//	public BufferedImage image2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	public Key key;
	public Camera cam;
	public Scene scene;
	public Screen screen;
	
	//TODO create Window class
	public Engine() {
		frame = new JFrame(TITLE);
		
		frame.setSize(DIMENSION);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());
		frame.add(this);
		key = new Key(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		cam = new Camera(key);
		scene = new Scene(cam, this);
		screen = new Screen(WIDTH, HEIGHT);
	}
	
	public void run() {
		long last = System.nanoTime();
		
		double delta = 0;
		double ns = 1e9/UPS;
		
		int frames = 0;
		int ticks = 0;
		
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now-last)/ns;
			last = now;
			
			while(delta >= 1) {
				tick(1.0/UPS);
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.out.format("fps: %d, ups: %d\n", frames, ticks);
				frame.setTitle(TITLE+ "- fps: " + frames + ", ups: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick(double delta) {
		clock++;
		time += delta;
		key.tick();
		cam.tick();
		scene.tick();
		FOCAL = 700/SCALE;
	}
	
	public void render() {
		BufferStrategy buffer = getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = buffer.getDrawGraphics();
		
		screen.clear();
		predraw(screen);
		scene.render(g);
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(image, 0, 0, (int)DIMENSION.getWidth(), (int)DIMENSION.getHeight(), null);
//		g.drawImage(image2, 0, 0, (int)DIMENSION.getWidth(), (int)DIMENSION.getHeight(),null);
		postdraw(g);
		
		cam.render(g,0,0);

		g.dispose();
//		image2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		buffer.show();
	}
	
	/**
	 * draw before rendering the scene (raster)
	 * @param screen Screen object
	 */
	public abstract void predraw(Screen screen);
	
	/**
	 * draw after rendering the scene (Graphics)
	 * @param g
	 * @param screen
	 */
	public abstract void postdraw(Graphics g);
	
	//TODO remove random color
	/**
	 * projects and renders a set of vertices onto the screen
	 * @param g graphics object
	 * @param vertices array of vertices
	 * @param dotSize size of dots (squares)
	 * @param color color of the dots
	 */
	public void renderPoint(Graphics g, Vec4d[] vertices, double dotSize, int color) {
		Vec3[] pos = worldToScreen(vertices);
		for(int i = 0; i < vertices.length; i++) {
			
			if(pos[i].z <= 0) continue;
			
			double distance = cam.pos.getDistance(vertices[i].normalize());
			double apparentSize = dotSize/distance;
			if(apparentSize < 2) apparentSize = 2;
			screen.drawPoint((int)pos[i].x, (int)pos[i].y, pos[i].z, (int)apparentSize, (int) (0xff000000 + 0xffffff*(double)i/(double)vertices.length));
			
//			Graphics2D g2d = image2.createGraphics();
//			g2d.setColor(new Color(0xff, 0xff, 0xff, 0xff));
//			g2d.drawString(""+i, xp, yp);
		
		}
	}
	
	/**
	 * projects and renders a set of edges onto the screen
	 * @param vertices array of vertices
	 * @param edges array of vertices pairs (edges)
	 * @param lineSize edge thickness
	 * @param color edge color
	 */
	public void renderHollowModel(Vec4d[] vertices, int[][] edges, double lineSize, int color) {
		Vec3[] pos = worldToScreen(vertices);
		for(int i = 0; i < edges.length; i++) {
			boolean draw = true;
			for(int j = 0; j < edges[i].length; j++) {
				if(pos[edges[i][j]].z <= 0) {
					draw = false;
					break;
				}
//				
//				double distance = cam.pos.getDistance(vec);
//				double apparentSize = dotSize/distance;
//				if(apparentSize < 2) apparentSize = 2;
//				screen.drawPoint(xp, yp, (int) Z, (int)apparentSize, color);
			}
			if(draw) {
				screen.drawLine(pos[edges[i][0]], pos[edges[i][1]], (int)lineSize, color);
			}
		}
	}
	
	//TODO add method to render polygon with solid color faces
	/**
	 * Projects and renders a set of faces onto the screen
	 * 
	 * @param vertices array of transformed vertices
	 * @param faces array of faces to be drawn: int[face][vertices]
	 * @param textureCoords coordinates of the texture for each vertex
	 * @param color ???
	 */
	public void renderPolygons(Vec4d[] vertices, int[][] faces, double[][] textureCoords, Sprite sprite) {
		Vec3[] pos = worldToScreen(vertices);
		int index = 0;
		for(int i = 0; i < faces.length; i++) {
//			sprite = new Sprite(16,16,(int) (0xff000000 + 0xffffff*(double)i/faces.length));
			//Decompose face into triangles (vertices - 2 = num of triangles)
//				pos = new int[faces[i].length][3];
				boolean draw = true;
				//Compute x, y and z position with respect to the camera of all the points in a face
				for(int n = 0; n < faces[i].length; n++) {
					
					if(pos[faces[i][n]].z <= 0) {
						draw = false;
						break;
					};
					
				}
			for(int j = 0; j < faces[i].length-2; j++) {
				if(draw) {
//					g.setColor(new Color((int) (color*Math.log(i+2)*(i*i-i-1))));
					screen.fillTriangle(
							pos[faces[i][0]], pos[faces[i][j+1]], pos[faces[i][j+2]], 
							new Point2D(textureCoords[index]), new Point2D(textureCoords[index+1+j]), new Point2D(textureCoords[index+2+j]), sprite);				
				}
			}
			index+=faces[i].length;
		}
	}
	
	public Vec3[] worldToScreen(Vec4d[] vertices) {
		Vec3[] pos = new Vec3[vertices.length];
		
		for(int i = 0; i < pos.length; i++) {
			vertices[i].normalize();
			double z = vertices[i].z-cam.pos.z;
			
			double x = (vertices[i].x-cam.pos.x)*FOCAL/z + WIDTH/2;
			double y = -(vertices[i].y-cam.pos.y)*FOCAL/z + HEIGHT/2;
			pos[i] = new Vec3(x, y, z);
		}
		
		return pos;
	}
	
	/**
	 * creates and runs the thread
	 */
	public void start() {
		if(running) return;
		running = true;
		thread = new Thread(this, "Main");
		thread.start();
	}
	
	/**
	 * destroys the thread
	 */
	public void stop() {
		if(!running) return;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}