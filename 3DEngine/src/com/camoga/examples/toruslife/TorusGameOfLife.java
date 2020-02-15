package com.camoga.examples.toruslife;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.models.Torus;
import com.camoga.examples.toruslife.life.GameOfLife;

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
		torus = new Torus(3, 2, width, height, 1, null, new Sprite(width,height,0xff000000), Material.plastic);
		scene.add(torus);
	}
	
	public void tick(double dt) {
		super.tick(dt);
		if(Engine.clock%1==0)
		life.tick();
//		Torus t = ((Torus)scene.getSelectedModel());
//		if(t!=null)
//		System.out.println(t.sprite.getPixels()[0]);
	}
	
	public static void main(String[] args) {
		new TorusGameOfLife().start();
	}

	@Override
	public void predraw(Screen screen) {

		screen.background(0xff30ffc4);
		torus.sprite.set(life.level.getImage(true,0xffffff));
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}
