package com.camoga.examples;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;
import com.camoga.engine.model.models.Sphere;

public class TestModels extends Engine {
	
	public TestModels() {
		super();
//		Cylinder cylinder = new Cylinder(2, 8, 100, 1, null, new Sprite(16,16,0xffff2020), Material.plastic);
//		scene.add(cylinder);
//		Torus t = new Torus(2, 1, 10, 5, 1, null, new Sprite(16,16,0xffff2020), Material.brick);
//		scene.add(t);

		Sphere earth = new Sphere(1, 40, 20, 1, null, new Sprite("/textures/earthHQ.jpg"), Material.brick);
//		System.out.println(sphere2.textureCoords[3][0]);
		scene.add(earth);
		Sphere moon = new Sphere(1/3.66D, 40, 20, 1, null, new Sprite("/textures/moonHQ.jpg"), Material.brick);

		moon.translate(new Vec3(56.66,0,0));
		scene.add(moon);
	}
	
	public static void main(String[] args) {
		new TestModels().start();
	}

	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}

}
