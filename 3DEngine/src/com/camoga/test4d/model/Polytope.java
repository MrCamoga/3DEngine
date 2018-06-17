package com.camoga.test4d.model;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Vec4d;
import com.camoga.test4d.geom.VecNd;

public class Polytope {

	public VecNd[] vertices;
	public int[][] edges;
	public int[][] faces;
	public double[][] textureCoords;
	public double scale;
	public Sprite sprite;
	public int color;
	public double dotSize;
	public VecNd[] transform5d;
	public Vec4d[] transform4d;
	
	/**
	 * Num of dimensions
	 */
	public int N;
	
	public Polytope(double[][] vertices, int[][] edges, int[][] faces, double[][] textureCoords, double dotSize, double scale, int color) {
		this.vertices = new VecNd[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			this.vertices[i] = new VecNd(vertices[i]);
		}
		N = vertices[0].length;
		
		this.edges = edges;
		this.faces = faces;
		this.textureCoords = textureCoords;
		this.scale = scale;
		this.dotSize = dotSize;
		this.color = color;
		this.sprite = new Sprite("/triangle.png");
		transform4d = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			transform4d[i] = new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]);
		}
		transform5d = new VecNd[vertices.length];
	}
	
	public void render(Engine main) {
//		main.renderObj(g, transform4d, dotSize, color);
		if(faces!=null)
		main.renderPolygons(transform4d, faces, textureCoords, sprite);
		if(edges!=null)
		main.renderHollowModel(transform4d, edges, 20, color);
	}
}