package com.camoga.mg;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Model;
import com.camoga.engine.model.Torus;

/**
 * This is the most simple program that you may do.
 * All you have to do is call the constructor and start() method,
 * Set the engine ticks (UPS) and load a model to the scene
 * 
 * 
 * @author MrCamoga
 *
 */
public class Test extends Engine {

	public Test() {
		super();
		UPS = 60;
		
		scene.add(new Torus(5, 2, 100, 40, 1, new Sprite(1,1,0xffffffff)));
	}
	
	public void tick(double dt) {
		super.tick(dt);
	}
	
	public static void main(String[] args) {
		new Test().start();
	}

	@Override
	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}
