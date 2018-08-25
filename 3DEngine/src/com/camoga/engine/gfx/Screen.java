package com.camoga.engine.gfx;

import java.util.Arrays;

import com.camoga.engine.Engine;
import com.camoga.engine.Scene;
import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Point2D;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.lighting.LightSource;
import com.camoga.engine.model.Material;

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
	
	//FIXME same vertices transform from world to screen multiple times
	//FIXME clip near
	/**
	 * Draws a point onto the screen on xp and yp coordinates
	 * @param xp x position
	 * @param yp y position
	 * @param z depth
	 * @param size 
	 * @param color
	 */
	public void drawPoint(Vec3 vs, int size, int color) {
		
//		Vec3 vs = Engine.worldToScreen(vw)[0];
		if(vs.z <= 0) return;
		for(int y = 0; y < size; y++) {
			int ya = (int) (y+vs.y-size/2);
			for(int x = 0; x < size; x++) {
				int xa = (int) (x+vs.x-size/2);
				if(xa < 0 || ya < 0 || xa >= width || ya >= height) continue;
				int i = xa+ya*width;
				float zBuff = zFunct(vs.z);
				if(depthBuffer[i] > zBuff) {
					pixels[i] = color;
					depthBuffer[i]= zBuff;
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
	 * @param a first world vertex
	 * @param b second world vertex
	 * @param c third world vertex
	 * @param at first texture coord
	 * @param bt second texture coord
	 * @param ct third texture coord
	 * @return whether or not the triangle has been drawn
	 */
	public boolean fillTriangle(Vec3[] vs, Vec3[] vw, Point2D at, Point2D bt, Point2D ct, Sprite normalmap, Sprite texturemap, Vec3 ac, Vec3 bc, Vec3 cc, int faceID, boolean highlight, boolean lighting, Material mat) {
		int xmin = min(vs[0].x,vs[1].x,vs[2].x);
		int ymin = min(vs[0].y,vs[1].y,vs[2].y);
		int xmax = max(vs[0].x,vs[1].x,vs[2].x);
		int ymax = max(vs[0].y,vs[1].y,vs[2].y);
		
		//Clip triangle
		if(xmin >= width || ymin >= height || xmax < 0 || ymax < 0) return false;
		if(xmin < 0) xmin = 0;
		if(xmax >= width) xmax = width;
		if(ymin < 0) ymin = 0;
		if(ymax >= height) ymax = height;
		
		double area = edgeFunction(vs[0], vs[1], vs[2]);
		boolean yInside = false;
		
		//Normal mapping vectors
		Matrix normalMatrix = null;
		if(normalmap != null && lighting) normalMatrix = normalMatrix(vw, at, bt, ct);
		
		yLabel:for(int y = ymin; y <= ymax; y++) {
			boolean xInside = false;
			for(int x = xmin; x <= xmax; x++) {
				//Compute weights
				double w0 = edgeFunction(new Vec3(x,y,0), vs[1], vs[2]);
				double w1 = edgeFunction(new Vec3(x,y,0), vs[2], vs[0]);
				double w2 = area - w0 - w1;
				if(w0>=0&&w1>=0&&w2>=0 || w0<=0&&w1<=0&&w2<=0) {
					xInside = true;
					if(x==xmin) yInside = true;
					if(x >= width || y >= height) continue;
					//normalize the areas
					w0 /= area;
					w1 /= area;
					w2 /= area;
					
					double Itotal = 0;
										
					//FIXME Perspective correct interpolation
					//Interpolate depth
					float z = (float)(w0*vs[0].z+w1*vs[1].z+w2*vs[2].z);
					//Interpolate texture coordinates
					double u = 0, v = 0;
					if(texturemap != null || (normalmap != null && lighting)) {
						u = at.x*w0 + bt.x*w1 + ct.x*w2;
						v = at.y*w0 + bt.y*w1 + ct.y*w2;
					}
					//FIXME texture coordinate cannot be 1
					if(u < 0 || v < 0 || u >= 1 || v >= 1) continue;

					int col = 0;
					
					if(texturemap != null) {
						int xT = (int) (u*texturemap.width);
						int yT = (int) (v*texturemap.height);
						col = texturemap.getPixels()[(xT+yT*texturemap.width)];						
					} else {
						double r = (w0*ac.x + w1*bc.x + w2*cc.x);
						double g = (w0*ac.y + w1*bc.y + w2*cc.y);
						double b = (w0*ac.z + w1*bc.z + w2*cc.z);
					
						col = (int)(r*0xff) << 16 | (int)(g*0xff) << 8 | (int)(b*0xff);
					}
					//Shading
					if(lighting) {
						if(normalmap != null) {
							int xN = (int) (u*normalmap.width);
							int yN = (int) (v*normalmap.height);
							Vec3 normal = getNormal(normalmap.getPixels()[(xN+yN*texturemap.width)]);
							normal = normalMatrix.multiply(normal);
							Itotal = flatShading(normal, Vec3.mul(vw[0], w0).add(Vec3.mul(vw[1], w1)).add(Vec3.mul(vw[2], w2)), mat);
						} else {
							Itotal = flatShading(vw[0],vw[1],vw[2], mat);												
						}
					}
					
					//pixels array index
					int i = x+y*width;
					
					float zBuff = zFunct(z);
					if(col != ALPHA)
					if(depthBuffer[i] > zBuff) {
						if(lighting) {
							float[] hsl = Color.RGBtoHSL(col);
//							System.out.println(hsl[1]);
							//TODO black color does not illuminate
							hsl[2] += 0.1;
							hsl[2] *= Itotal;
							
							if(highlight) {
								hsl[2] += .16f;
							}
							if(hsl[2]>1)hsl[2]=1;
							pixels[i] = Color.HSLtoRGB(hsl[0], hsl[1], hsl[2]);							
						} else {
							pixels[i] = col;
						}
						depthBuffer[i] = zBuff;
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
		return true;
	}
	
	private Vec3 getNormal(int rgb) {
		Vec3 normal = Color.RGB(rgb).sub(0.5).mul(2);
		
		return normal;
	}
	
	private float zFunct(double z) {
		return (float) z;
	}

	//DONE triangles with different lightning?
	/**
	 * Returns light intensity value for a triangle
	 * @param a triangle's first vertex
	 * @param b second vertex
	 * @param c third vertex
	 * @return light intensity
	 */
	public double flatShading(Vec3 normal, Vec3 pos, Material mat) {
		double Ka = mat.Ka;
		double Ia = Scene.Ia;
		double Iamb = Ka*Ia;
		double Itotal = Iamb;
		for(LightSource l : Engine.scene.getLights()) {
			double Kd = mat.Kd;
			//TODO I = l.getIntensity();
			double I = 1;
			double Ks = mat.Ks;

//			Vec3 normal = Vec3.crossNorm(a, b, c);
//			System.out.println(normal+", " +normal2);
			
			Vec3 light = null;
			switch(l.getType()) {
				case DIRECTIONAL:
					light = new Vec3(l.transform).normalize();
					break;
				case POINT:
					double d = new Vec3(l.transform).getDistance(pos);
					light = Vec3.sub(new Vec3(l.transform),pos).div(d);
					I *= 1/(0.0007*d*d+1);
					break;
			}
			
			double dot = Vec3.dot(normal, light);
//			if(dot  0) continue;
			double Idiff = Kd*dot;
			
			Vec3 Rm = (normal.mul(dot*2)).sub(light);
			double dot2 = Vec3.dotNorm(Rm,Vec3.sub(Engine.cam.pos,pos));
			double Ispec = dot2 < 0 ? 0:Ks*Math.pow(dot2,mat.alpha);
			if(Idiff < 0) Idiff = 0;
			if(Ispec < 0) Ispec = 0;
			Itotal += I*(Idiff+Ispec);
		}
//		if(Itotal > 1) Itotal = 1;
		return Itotal;
	}
	
	public double flatShading(Vec3 a, Vec3 b, Vec3 c, Material mat) {
		//FIXME when dividing polygons into triangles, each triangle is shaded according to its center. Flat polygons have discontinuity in the shading
//		return flatShading(Vec3.crossNorm(a, b, c), Vec3.add(a, Vec3.add(b, c)).div(3), mat);
		return flatShading(Vec3.crossNorm(a, b, c), a, mat);
	}
	
	public Matrix normalMatrix(Vec3[] vw, Point2D at, Point2D bt, Point2D ct) {
		Vec3 E1 = Vec3.sub(vw[1], vw[0]);
		Vec3 E2 = Vec3.sub(vw[2], vw[1]);
		Point2D dUV1 = Point2D.sub(bt, at);
		Point2D dUV2 = Point2D.sub(ct, bt);
		
		double f = 1/(dUV1.x*dUV2.y-dUV1.y*dUV2.x);
		
		Vec3 T = new Vec3(dUV2.y*E1.x-dUV1.y*E2.x,dUV2.y*E1.y-dUV1.y*E2.y,dUV2.y*E1.z-dUV1.y*E2.z).mul(f).normalize();
		Vec3 B = new Vec3(-dUV2.x*E1.x+dUV1.x*E2.x,-dUV2.x*E1.y+dUV1.x*E2.y,-dUV2.x*E1.z+dUV1.x*E2.z).mul(f).normalize();
		Vec3 N = Vec3.cross(T, B);
		return new Matrix(T, B, N);
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