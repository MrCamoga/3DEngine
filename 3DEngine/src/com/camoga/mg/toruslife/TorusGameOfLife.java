package com.camoga.mg.toruslife;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Sphere;
import com.camoga.engine.model.Torus;
import com.camoga.mg.toruslife.life.GameOfLife;

/**
 * This is the most simple program that you may do.
 * All you have to do is call the constructor and start() method,
 * Set the engine ticks (UPS) and load a model to the scene
 * 
 * 
 * @author MrCamoga
 *
 */
public class TorusGameOfLife extends Engine {

	public GameOfLife life;
	public Torus torus;
	
	public TorusGameOfLife() {
		super();
		UPS = 60;
		int width = 80;
		int height = 40;
		life = new GameOfLife(false, width, height);
		torus = new Torus(3, 2, width, height, 1, new Sprite(width,height,0xffff0000));
		scene.add(torus);
//		scene.add(new Sphere(5, 25, 25, 1, new Sprite(1,1,0xffff0000)));
	}
	
	public void tick(double dt) {
		super.tick(dt);
		if(Engine.clock%1==0)
		life.tick();
	}
	
	public static void main(String[] args) {
		new TorusGameOfLife().start();
	}

	@Override
	public void predraw(Screen screen) {
		torus.sprite.set(life.level.getImage(true,0xff0000));
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}
