package com.camoga.mg;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Polyhedron;

/**
 * This is another example. I've rendered a square mesh.
 * You can change the y coordinate and draw functions dependent of time.
 * 
 * @author MrCamoga
 *
 */
public class Mesh extends Engine {

	public Polyhedron mesh;
	
	int width = 50;
	int height = 50;
	
	public Mesh() {
		super();
		UPS = 60;
		cam.rot.x = Math.PI+0.4;
		
		cam.pos.x = 0;
		cam.pos.y = 0;
		cam.pos.z = -50;		
		
		double[][] vertices = new double[height*width][];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				vertices[i+j*width] = new double[] {i-width/2,Math.cos(j)+Math.sin(i),j-height/2};
			}
		}
		
		int[][] faces = new int[(width-1)*(height-1)][];
		for(int i = 0; i < height-1; i++) {
			for(int j = 0; j < width-1; j++) {
				faces[j+i*(width-1)] = new int[]{j+i*width,j+(i+1)*width,j+(i+1)*width+1,j+1+i*width};
			}
		}
		
		double[][] textureCoords = new double[faces.length*4][];
		for(int i = 0; i < textureCoords.length/4; i++) {
			textureCoords[4*i] = new double[] {0,0};
			textureCoords[4*i+1] = new double[] {1,0};
			textureCoords[4*i+2] = new double[] {1,1};
			textureCoords[4*i+3] = new double[] {0,1};
		}
		
		mesh = new Polyhedron(vertices, faces, textureCoords, 2, new Sprite(16, 16, 0xff23c5e3));
		scene.add(mesh);
	}
	
	public void tick(double dt) {
		super.tick(dt);
		
		//Draw function which may be dependent of time (using Engine.time)
		for(int y = 0; y < height; y++) {
			double yo = (y-height/2)/10.0+Math.sin(Engine.time);
			for(int x = 0; x < width; x++) {
				double xo = (x-width/2)/10.0+Math.cos(Engine.time);
//				mesh.vertices[j+i*width].y = Math.cos(j+Engine.time)+0.5*Math.cos(2*j+Engine.time)+0.333333333*Math.cos(3*j+Engine.time)
//				+Math.sin(i+Engine.time)+0.5*Math.sin(2*i+Engine.time)+0.333333333*Math.sin(3*i+Engine.time);
				
				//hyperbolic paraboloid
				//mesh.vertices[x+y*width].y = xo*xo/25-yo*yo/25;
				
				//semisphere
//				mesh.vertices[x+y*width].y = -Math.sqrt(625-xo*xo-yo*yo);
				
				mesh.vertices[x+y*width].y = -Math.exp(-xo*xo-yo*yo)*10*Math.sin(Engine.time*Math.PI/3);
				
			}
		}
	}
	
	public static void main(String[] args) {
		new Mesh().start();
	}

	@Override
	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}