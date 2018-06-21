package com.camoga.engine.gfx;

import java.util.Arrays;

import com.camoga.engine.Color;
import com.camoga.engine.Engine;
import com.camoga.engine.LightSource;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Point2D;
import com.camoga.engine.geom.Vec3;

public class Screen {
	
	public int width, height;
	
	private static final int ALPHA = 0xffff00ff;
	
	public int[] pixels;
	public float[] depthBuffer;
	public Integer[] faceIDbuffer;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width*height];
		depthBuffer = new float[width*height];
		faceIDbuffer = new Integer[width*height];
	}
	
	/**
	 * clears pixel array to be drawn again
	 */
	public void clear() {
		background(0xff30c4ff);
		Arrays.fill(depthBuffer, Float.MAX_VALUE);
		Arrays.fill(faceIDbuffer, null);
	}
	
	/**
	 * fills pixel array with a color
	 * @param color
	 */
	public void background(int color) {
		Arrays.fill(pixels, color);
	}
	
	/**
	 * Draws a point onto the screen on xp and yp coordinates
	 * @param xp x position
	 * @param yp y position
	 * @param z depth
	 * @param size 
	 * @param color
	 */
	public void drawPoint(int xp, int yp, double z, int size, int color) {
		for(int y = 0; y < size; y++) {
			int ya = y+yp-size/2;
			for(int x = 0; x < size; x++) {
				int xa = x+xp-size/2;
				if(xa < 0 || ya < 0 || xa >= width || ya >= height) continue;
				int i = xa+ya*width;
				if(depthBuffer[i] > z) {
					pixels[i] = color;
					depthBuffer[i]=(float) z;
				}
			}
		}
	}
	
	//TODO INTERPOLATE DEPTH
	/**
	 * Draws a line between two points
	 * @param a first point
	 * @param b second point
	 * @param size thickness
	 * @param color
	 */
	public void drawLine(Vec3 a, Vec3 b, int size, int color) {
		int ymin = (int) Math.min(a.y, b.y);
		int ymax = (int) Math.max(a.y, b.y);
		int xmin = (int) Math.min(a.x, b.x);
		int xmax = (int) Math.max(a.x, b.x);
		
		if(xmin >= width || ymin >= height || xmax < 0 || ymax < 0) {
			return;
		}
		if(xmin < 0) xmin = 0;
		if(xmax >= width) xmax = width;
		if(ymin < 0) ymin = 0;
		if(ymax >= height) ymax = height;
		
//		Vec3 c;
//		
//		if(a.x > b.x) {
//			c = new Vec3(a.x,a.y,a.z);
//			a = new Vec3(b.x,b.y,b.z);
//			b = c;
//		}
		
		double x, y, dx, dy, step;
		
		dx = (b.x-a.x);
		dy = (b.y-a.y);
		
		if(Math.abs(dx) >= Math.abs(dy)) step = Math.abs(dx);
		else step = Math.abs(dy);
		
		dx /= step;
		dy /= step;
		
		x = a.x;
		y = a.y;
		for(int i = 1; i <= step; i++) {
			if(!((int)x < 0 || (int)y < 0 || (int)x >= this.width || (int)y >= this.height)) { 
				pixels[(int)x+(int)y*this.width] = color;
				depthBuffer[(int)x+(int)y*this.width] = 0;
			}
			x += dx; 
			y += dy;
//				i += step > 0 ? 1:-1;
		}
		
//		
	}
	
	/**
	 * draws a sprite
	 * @param xp x starting position
	 * @param yp y starting position
	 * @param sprite to be drawn
	 */
	public void drawSprite(int xp, int yp, Sprite sprite) {
		for(int y = 0; y < sprite.height; y++) {
			int ya = y + yp;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				if(xa < 0 || ya < 0 || xa >= width || ya >= height) continue;
				int col = sprite.getPixels()[x+y*sprite.width];
				if(col != ALPHA)
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	/**
	 * Rasterizes a triangle and interpolates a texture over it
	 * @param a first vertex
	 * @param b second vertex
	 * @param c third vertex
	 * @param at first texture coord
	 * @param bt second texture coord
	 * @param ct third texture coord
	 */
	public void fillTriangle(Vec3 a, Vec3 b, Vec3 c, Point2D at, Point2D bt, Point2D ct, Sprite sprite, int faceID, boolean highlight) {
		int xmin = min(a.x,b.x,c.x);
		int ymin = min(a.y,b.y,c.y);
		int xmax = max(a.x,b.x,c.x);
		int ymax = max(a.y,b.y,c.y);
		
		//Clip triangle
		if(xmin >= width || ymin >= height || xmax < 0 || ymax < 0) return;
		if(xmin < 0) xmin = 0;
		if(xmax >= width) xmax = width;
		if(ymin < 0) ymin = 0;
		if(ymax >= height) ymax = height;
		
		double area = edgeFunction(a, b, c);
		boolean yInside = false;
		
		//pixel brightness
		double Itotal = flatShading(a, b, c);
//		Itotal = 1;
		
		yLabel:for(int y = ymin; y <= ymax; y++) {
			boolean xInside = false;
			for(int x = xmin; x <= xmax; x++) {
				//Compute weights
				double w0 = edgeFunction(new Vec3(x,y,0), a, b);
				double w1 = edgeFunction(new Vec3(x,y,0), b, c);
				double w2 = area - w0 - w1;
				if((w0<=0&&w1<=0&&w2<=0 || ((w0>=0&&w1>=0&&w2>=0)&&true))) {
					xInside = true;
					if(x==xmin) yInside = true;
					if(x >= width || y >= height) continue;
					//normalize the areas
					w0 /= area;
					w1 /= area;
					w2 /= area;
					
					
					//FIXME Perspective correct interpolation
					//Interpolate depth
					float z = (float)(w1*a.z+w2*b.z+w0*c.z);
					//Interpolate texture coordinates
					int xT = (int) ((at.x*w1 + bt.x*w2 + ct.x*w0)*sprite.width);
					int yT = (int) ((at.y*w1 + bt.y*w2 + ct.y*w0)*sprite.height);
					
					//pixels array index
					int i = x+y*width;
					
					if(xT < 0 || yT < 0 || xT >= sprite.width || yT >= sprite.height) continue;
					int col = sprite.getPixels()[(xT+yT*sprite.width)];
					if(col != ALPHA)
					if(depthBuffer[i] > z) {
						float[] hsl = Color.RGBtoHSL(col);
						
						hsl[2] *= Itotal;
						
						//DONE change HSB to HSL
						//FIXME black triangles do not illuminate
						if(highlight) {
							hsl[2] *= 1.3f;
						}
						if(hsl[2]>1)hsl[2]=1;
						pixels[i] = Color.HSLtoRGB(hsl[0], hsl[1], hsl[2]);
						depthBuffer[i] = z;
						faceIDbuffer[i] = faceID;
					}
				} 
				//Backtrack traversal
				else if(xInside) continue yLabel;
				else if(x==xmin && yInside) {
					yInside = false;
					xmin++;
				}
			}
		}
	}
	
	//DONE triangles with different lightning?
	/**
	 * Returns light intensity value for a triangle
	 * @param a triangle's first vertex
	 * @param b second vertex
	 * @param c third vertex
	 * @return light intensity
	 */
	public double flatShading(Vec3 a, Vec3 b, Vec3 c) {
		double Ka = 0.4;
		double Ia = 1;
		double Iamb = Ka*Ia;
		double Itotal = Iamb;
		for(LightSource l : Engine.scene.getLights()) {
			double Kd = 0.6;
			double I = 1;
			double Ks = 1;
			
			Vec3 normal = Vec3.crossNorm(b, a, c);
			if(Math.random()<0.01)
			drawLine(a, Vec3.add(a, normal), 2, 0xffffff00);
			
			double dot = Vec3.dotNorm(normal, l.transform);
			if(dot < 0) continue;
			double Idiff = Kd*dot;
			
			Vec3 Rm = (normal.mul(dot*2)).sub(Vec3.normalize(l.transform));
			double dot2 = Vec3.dotNorm(Rm,Vec3.sub(a,Engine.cam.pos));
//			System.out.println(dot2);
			
			double Ispec = Ks*Math.pow(dot2,128);
//			System.out.println(Ispec);
			if(Idiff < 0) Idiff = 0;
			if(Ispec < 0) Ispec = 0;
			Itotal += I*(Idiff+Ispec);
//			System.out.println(Itotal);
		}
//		if(Itotal > 1) Itotal = 1;
		return Itotal;
	}
	
	//DONE SPHERE AND TORUS MODEL
	//FIXME
	public int alphablending(int color1, int color2) {
		float factor = (float)((color2&0xff000000) >> 24)/255f;
		int r = (int) ((color1&0xff0000 >> 16)*(1-factor) + (color2&0xff0000 >> 16)*factor);
		int g = (int) ((color1&0xff00 >> 8)*(1-factor) + (color2&0xff00 >> 8)*factor);
		int b = (int) ((color1&0xff)*(1-factor) + (color2&0xff)*factor);
		return (r<<16)|(g<<8)|b;
	}
	
	//TODO Add flat shading and backtrack traversal
	/**
	 * Rasterizes a triangle and interpolates a color over it
	 * @param a first vertex
	 * @param b second vertex
	 * @param c third vertex
	 * @param ac first color
	 * @param bc second color
	 * @param cc third color
	 */
	public void fillTriangle(Vec3 a, Vec3 b, Vec3 c, Vec3 ac, Vec3 bc, Vec3 cc) {
		int xmin = min(a.x,b.x,c.x);
		int ymin = min(a.y,b.y,c.y);
		int xmax = max(a.x,b.x,c.x);
		int ymax = max(a.y,b.y,c.y);
		
		if(xmin >= width || ymin >= height || xmax < 0 || ymax < 0) return;
		if(xmin < 0) xmin = 0;
		if(xmax >= width) xmax = width;
		if(ymin < 0) ymin = 0;
		if(ymax >= height) ymax = height;
		double area = edgeFunction(a, b, c);
		for(int y = ymin; y < ymax; y++) {
			for(int x = xmin; x < xmax; x++) {
				double w0 = edgeFunction(new Vec3(x,y,0), a, b);
				double w1 = edgeFunction(new Vec3(x,y,0), b, c);
				double w2 = area - w0 - w1;
				if(w0<=0&&w1<=0&&w2<=0) {
					if(x < 0 || y < 0 || x >= width || y >= height) continue;
					w0 /= area;
					w1 /= area;
					w2 /= area;
					double red = (w1*ac.x + w2*bc.x + w0*cc.x);
					double green = (w1*ac.y + w2*bc.y + w0*cc.y);
					double blue = (w1*ac.z + w2*bc.z + w0*cc.z);
					float z = (float)(w0*a.z+w1*b.z+w2*c.z);
					int i = x+y*width;
					if(depthBuffer[i] > z) {
						pixels[i] = (int)(red*0xff) << 16 | (int)(green*0xff) << 8 | (int)(blue*0xff);
						depthBuffer[i] = z;						
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return min of the three
	 */
	public int min(double a, double b, double c) {
		return (int) Math.min(a, Math.min(b, c));
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return max of the three
	 */
	public int max(double a, double b, double c) {
		return (int) Math.max(a, Math.max(b, c));
	}
	
	/**
	 * Returns the area of the triangle between a point and two vectors 
	 * @param p
	 * @param v0
	 * @param v1
	 * @return
	 */
	public double edgeFunction(Vec3 p, Vec3 v0, Vec3 v1) {
		return (p.x - v0.x)*(v1.y - v0.y) - (p.y - v0.y)*(v1.x - v0.x);
	}
}