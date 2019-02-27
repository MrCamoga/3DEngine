package com.camoga.examples.rubikscube;

import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Cubie {
	Model cubie = new Model(new double[][]{
		{-1,-1,-1},{-1,-1,1},
		{-1,1,-1},{-1,1,1},
		{1,-1,-1},{1,-1,1},
		{1,1,-1},{1,1,1},
	}, new int[][]{
		{0,1,3,2},{4,5,7,6},
		{0,1,5,4},{2,3,7,6},
		{0,2,6,4},{1,3,7,5}
	}, new double[][] {
		{0, 0.67},{0.5,0.67},{0.5,1},{0,1},
		{0,0},{0,0},{0,0},{0,0},
		{0.5,0},{0.5,0.33},{1,0.33},{1,0},
		{0.5,0.67},{0.67,1},{1,1},{1,0.67},
		{0,0.34},{0.5,0.34},{0.5,0.66},{0,0.66},
		{0.5,0.34},{1,0.34},{1,0.66},{0.5,0.66},
	}, 1, null, new Sprite("/textures/rubikscube.png"), Material.plastic);
	
	int id;
	
	public Cubie(int id) {
		this.id = id;
	}
	
	public void setPos(Vec3 pos) {
		cubie.pos = pos;
	}
}
