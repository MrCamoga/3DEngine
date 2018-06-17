package com.camoga.engine.model;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;

public class Model {

	public Vec4d[] vertices;
	public int[][] faces;
	public double[][] textureCoords;
	public double scale;
	public Sprite sprite;
	public Vec4d[] transform;
	public Vec3 pos = new Vec3();
	
	public Model(double[][] vertices, int[][] faces, double[][] textureCoords, double scale, Sprite sprite) {
		this.vertices = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			this.vertices[i] = new Vec4d(vertices[i][0], vertices[i][1], vertices[i][2]);
		}
		
		this.faces = faces;
		this.textureCoords = textureCoords;
		this.scale = scale;
		this.sprite = sprite;
		transform = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			transform[i] = new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]);
		}
	}
	
	public void render(Engine main, Graphics g) {
		if(textureCoords != null) {
//			main.renderPoint(g, transform, 20, 0xffff0000);
			main.renderPolygons(transform, faces, textureCoords, sprite);
		}
	}

	public void translate(Vec3 pos) {
		this.pos = pos;
	}
}