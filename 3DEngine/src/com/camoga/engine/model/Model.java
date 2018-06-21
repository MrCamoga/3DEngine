package com.camoga.engine.model;

import com.camoga.engine.Engine;
import com.camoga.engine.Renderable;
import com.camoga.engine.Scene;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Matrix4d;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;

public class Model implements Renderable {

	public Vec4d[] vertices;
	public int[][] faces;
	public double[][] textureCoords;
	public double scale;
	public Sprite sprite;
	public Vec4d[] transform;
	public Vec3 pos = new Vec3();
	
	public Model(double[][] vertices, int[][] faces, double[][] textureCoords, double scale, Sprite sprite) {
		setVertices(vertices);
		
		this.faces = faces;
		this.textureCoords = textureCoords;
		this.scale = scale;
		this.sprite = sprite;
		transform = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i]!=null)
			transform[i] = new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]);
			else transform[i] = new Vec4d(0,0,0);
		}
	}
	
	public void setVertices(double[][] vertices) {
		this.vertices = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i] != null)
			this.vertices[i] = new Vec4d(vertices[i][0], vertices[i][1], vertices[i][2]);
			else this.vertices[i] = new Vec4d(0,0,0);
		}
	}
	
	public void render(Engine main) {
		if(textureCoords != null) {
//			main.renderPoint(transform, 20, 0xffff0000);
			main.renderPolygons(transform, faces, textureCoords, sprite);
		}
	}

	public void translate(Vec3 pos) {
		this.pos = pos;
	}

	public void transform(Matrix4d rotation, Scene scene) {
		for(int i = 0; i < vertices.length; i++) {
			transform[i] = rotation
					.multiply(Scene.scale(scale))
//					.multiply(Scene.translate(-scene.cam.pos.x, -scene.cam.pos.z, -scene.cam.pos.z))
					.multiply(vertices[i]);
		}
	}
}