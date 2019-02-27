package com.camoga.examples.gravity;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.models.Sphere;

public class Gravity extends Engine {
	
	public ArrayList<Body> bodies = new ArrayList<>();

	private int focus = 0;
	
	public Gravity() {
		super();
		UPS = 60;
		setFocalLength(500);
		cam.pos = new Vec3(0,0,-400000);
		cam.speed = 10000;
		
		bodies.add(new Body(new Sphere(6371, 40, 20, 10, null, new Sprite("/textures/earthHQ.jpg"), Material.brick), new Vec3(), new Vec3(), 5.97e24));
		bodies.add(new Body(new Sphere(1737, 40, 20, 10, null, new Sprite("/textures/moonHQ.jpg"), Material.brick), new Vec3(405e3,0,0), new Vec3(0,0.970*Math.sin(5.145*Math.PI/180.0),0.97*Math.cos(5.145*Math.PI/180.0)), 7.34e22));
		
		for(Body b : bodies) {
			scene.add(b.sphere);
		}
		
	}
	
	public static void main(String[] args) {
		new Gravity().start();
	}
	
	public void tick(double dt) {
		super.tick(dt);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
//				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_M) {
					focus = (focus+1)%bodies.size();
					System.out.println(focus);
				}
			}
		});
//		for(int i = 0; i < 2000; i++) {
//			double dt2 = 1;
//			for(Body b : bodies) {
//				for(Body b2 : bodies) {
//					if(b2.equals(b)) continue;
//					b.attract(b2, dt2);
//				}
//			}
//			for(Body b : bodies) {
//				b.tick(dt2, bodies.get(focus).pos);
//			}
////			if(i%100 == 0)System.out.println(bodies.get(1).pos);			
//		}
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		
	}
}