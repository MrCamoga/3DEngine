package com.camoga.mg;

import java.awt.Color;
import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Polyhedron;

public class Mario extends Engine {

	public Vec3 gravity = new Vec3(0, 0.05, 0);
	public Vec3 pos = new Vec3(0, 1, 0);
	public Vec3 velocity = new Vec3(-0.4, -0.5, 0);
	
	private Polyhedron icosahedron;
	
	public Mario() {
		super();
		UPS = 60;
		double phi = (Math.sqrt(5)+1)/2;
		double[][] vertices = {
			{0.5,0,phi/2},{0.5,0,-phi/2},
			{-0.5,0,phi/2},{-0.5,0,-phi/2},
			{phi/2,0.5,0},{phi/2,-0.5,0},
			{-phi/2,0.5,0},{-phi/2,-0.5,0},
			{0,phi/2,0.5},{0,phi/2,-0.5},
			{0,-phi/2,0.5},{0,-phi/2,-0.5},
		};
			
		int[][] faces = {
			{0,2,10},{0,10,5},{0,5,4},{0,4,8},{0,8,2},
			{3,1,11},{3,11,7},{3,7,6},{3,6,9},{3,9,1},
			{2,6,7},{2,7,10},{10,7,11},{10,11,5},{5,11,1},
			{5,1,4},{4,1,9},{4,9,8},{8,9,6},{8,6,2}
		};
		
		double[][] tc = {
				{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},
				{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},
				{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},
				{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},
				{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},{0,0},{0,1},{1,0},
		};
		
		Sprite sprite = new Sprite("/wic.png");
		icosahedron = new Polyhedron(vertices, faces, tc, 2, sprite);
		scene.add(icosahedron);
	}
	
	public void tick(double dt) {
		super.tick(dt);
		pos.add(velocity.mul(dt));
		velocity.add(gravity.mul(dt));
		
		icosahedron.translate(pos);
		
		collide();
	}
	
	public void collide() {
		
	}

	public static void main(String[] args) {
		new Mario().start();
	}

	@Override
	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}