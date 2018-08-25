package com.camoga.engine.model.models;

import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Cube extends Model {

	public Cube(double[][] vertices, int[][] faces, double[][] textureCoords, double scale, Sprite normal, Sprite sprite) {
		super(vertices, faces, textureCoords, scale, normal, sprite, Material.brick);
	}

}
