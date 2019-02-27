package com.camoga.examples.lorenzattractor;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Wireframe;
import com.camoga.engine.model.models.Sphere;

public class LorenzAttractor extends Engine {

	private Wireframe attractor = new Wireframe(1,1,0x3fff0000);
	
	public LorenzAttractor() {
		super();
		
		cam.speed = 1;
		cam.rot.y = 0.75;
		cam.pos.z = -100;
		scene.add(attractor);
//		scene.add(new Sphere(10, 10, 10, 1, null, new Sprite(1, 1, 0xffffff00), Material.metallic));
	}
	
	public static void main(String[] args) {
		new LorenzAttractor().start();
	}
	
	double x = 1, y = 1, z = 1;
	
	double a = 10, b = 28, c = 8/3.0;
	
	int count = 0;
	
	double angle = Math.PI;
	public void tick(double dt) {
		super.tick(dt);
		angle +=0.01;
//		cam.pos.z = 86*Math.cos(-angle);
//		cam.pos.x = 86*Math.sin(-angle);
		cam.rot.y = -angle+Math.PI;
		for(int i = 0; i < 10; i++) {
			double dt2 = 0.003;
			
			double dx = a*(y-x);
			double dy = x*(b-z) - y;
			double dz = x*y - c*z;
			
			x += dx*dt2;
			y += dy*dt2;
			z += dz*dt2;
			if(count%8 == 0) {
				attractor.addVertex(x,y,z);
			}
			int vertices = attractor.vertices.size();
			if(vertices >= 2) {
				attractor.addEdge(vertices-2, vertices-1);
			}
			
			count++;
		}
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		
	}
	
}