package com.camoga.examples.toruslife;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.models.Torus;
import com.camoga.examples.toruslife.eca.ElementaryCA;

public class TorusCA extends Engine {

	public ElementaryCA ca;
	public Torus torus;
	
	public TorusCA() {
		super();
		UPS = 60;
		int width = 400;
		int height = 250;
		ca = new ElementaryCA(30, width, height);
		torus = new Torus(5, 2, 20, 20, 1, null, new Sprite(width,height,0xff000000), Material.plastic);
		scene.add(torus);
	}
	
	public void tick(double dt) {
		super.tick(dt);
		if(Engine.clock%1==0)
		for(int it = 0; it < 4; it++)
		ca.iterate();
	}
	
	public static void main(String[] args) {
		new TorusCA().start();
	}

	@Override
	public void predraw(Screen screen) {

		screen.background(0xff30ffc4);
		torus.sprite.set(ca.getImage());
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}
