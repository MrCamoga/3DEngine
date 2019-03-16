package com.camoga.examples.optimizesphere;

import java.awt.Graphics;
import java.util.ArrayList;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Wireframe;
import com.camoga.engine.model.models.Sphere;

public class PointsArrangement extends Engine {

	ArrayList<Point> points = new ArrayList<>();
	
	Wireframe spherepoints = new Wireframe(1, 35, 0xffff0000);
	Sphere sphere = new Sphere(0.9, 20, 20, 1, null, new Sprite(1, 1, 0xcc0000ff), Material.plastic);
	
	public static int NUMOFPOINTS = 12;
	public static double VISCOUSFRICTION = 5.0;
	public static double REPULSIONFORCE = 0.1;
	
	public PointsArrangement() {
		super();
		UPS = 500;
		for(int i = 0; i < NUMOFPOINTS; i++) {
			points.add(new Point());
			spherepoints.addVertex(points.get(i).pos.x, points.get(i).pos.y, points.get(i).pos.z);
		}
		scene.add(spherepoints);
//		scene.add(sphere);
	}
	
	public static void main(String[] args) {
		new PointsArrangement().start();
	}

	public void tick(double dt) {
		super.tick(dt);
		
		for(Point p1 : points) {
			for(Point p2 : points) {
				if(p1.equals(p2)) continue;
				p1.repulse(p2);
			}
//			System.out.println(p1.pos);
		}
		
//		points.get(0).repulse(points.get(1));
//		System.out.println(points.get(0).vel.mod());
		
		
		
		spherepoints.clear();
		
		for(Point p : points) {
			p.move(1/5.0);
			spherepoints.addVertex(p.pos.x, p.pos.y, p.pos.z);
		}
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		
	}
	
}
