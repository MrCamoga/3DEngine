package com.camoga._4d.model;

import com.camoga._4d.geom.VecNd;
import com.camoga.engine.Engine;
import com.camoga.engine.Renderable;
import com.camoga.engine.Scene;
import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;

public class Polytope implements Renderable {

	public VecNd[] vertices;
	public int[][] edges;
	public int[][] faces;
	public double[][] textureCoords;
	public double scale;
	public Vec3[] vertexColor;
	public Sprite sprite;
	public int color;
	public Material mat;
	public double dotSize;
	public VecNd[] transform5d;
	public Vec4d[] transform4d;
	
	/**
	 * Num of dimensions
	 */
	public int N;
	
	public Polytope(double[][] vertices, int[][] edges, int[][] faces, double[][] textureCoords, double dotSize, double scale, int color, Material mat) {
		this.vertices = new VecNd[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			this.vertices[i] = new VecNd(vertices[i]);
		}
		N = vertices[0].length;
		
		this.edges = edges;
		this.faces = faces;
		this.mat = mat;
		this.textureCoords = textureCoords;
		this.scale = scale;
		this.dotSize = dotSize;
		this.color = color;
		this.sprite = new Sprite("/textures/wic.png");
		vertexColor = new Vec3[vertices.length];
		transform4d = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			transform4d[i] = new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]);
		}
		transform5d = new VecNd[vertices.length];
	}
	
	public void render(Engine main) {
		main.renderPoint(transform4d, dotSize, color);
//		if(faces!=null)
//		main.renderPolygons(transform4d, faces, textureCoords, null, sprite, vertexColor, mat);
		if(edges!=null)
		main.renderWireframe(transform4d, edges, 20, color);
	}

	public void transform(Matrix rotation, Scene scene) {
		//TODO
	}
}