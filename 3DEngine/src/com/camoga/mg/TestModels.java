package com.camoga.mg;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.models.Cylinder;
import com.camoga.engine.model.models.Torus;

public class TestModels extends Engine {
	
	public TestModels() {
		super();
		Cylinder cylinder = new Cylinder(2, 8, 100, 1, null, new Sprite(16,16,0xffff2020), Material.plastic);
		scene.add(cylinder);
//		Torus t = new Torus(2, 1, 10, 5, 1, null, new Sprite(16,16,0xffff2020), Material.brick);
//		scene.add(t);
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
