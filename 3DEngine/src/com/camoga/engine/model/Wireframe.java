package com.camoga.engine.model;

import java.util.ArrayList;

import com.camoga.engine.Engine;
import com.camoga.engine.Renderable;
import com.camoga.engine.Scene;
import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Vec4d;

public class Wireframe implements Renderable {

	public ArrayList<Vec4d> vertices = new ArrayList<Vec4d>();
	public ArrayList<Integer> vertexColor = new ArrayList<Integer>();
	public ArrayList<int[]> edges = new ArrayList<int[]>();
	
	
	public ArrayList<Vec4d> transform = new ArrayList<Vec4d>();
	public int color;
	public double scale;
	public double dotSize;
	
	public Wireframe(double[][] vertices, int[][] edges, double scale, double dotSize, int color) {
		this.color = color;
		for(double[] v : vertices) {
			this.vertices.add(new Vec4d(v[0],v[1],v[2]));
		}
		for(int[] e : edges) {
			this.edges.add(e);
		}
		this.scale = scale;
		this.dotSize = dotSize;
		for(int i = 0; i < vertices.length; i++) {
			transform.add(new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]));
		}
	}
	
	public Wireframe(double[][] vertices, double scale, double dotSize, int color) {
		this(vertices, new int[][] {}, scale, dotSize, color);
	}
	
	public Wireframe(double scale, double dotSize, int color) {
		this(new double[][] {}, new int[][] {}, scale, dotSize, color);
	}
	
	public Wireframe() {
		this(new double[][] {}, 1, 1, 0xffff0000);
	}
	
	public void addVertex(double x, double y, double z) {
		vertices.add(new Vec4d(x,y,z));
		transform.add(new Vec4d(x,y,z));
	}
	
	public void clear() {
		vertices.clear();
		transform.clear();
	}
	
	public void addEdge(int a, int b) {
		edges.add(new int[] {a,b});
	}
	
	public void transform(Matrix rotation, Scene scene) {
		for(int i = 0; i < vertices.size(); i++) {
			transform.set(i, rotation.scale(scale)
//					.multiply(Scene.translate(-scene.cam.pos.x, -scene.cam.pos.y, -scene.cam.pos.z))
					.multiply(vertices.get(i)));
		}
	}

	@Override
	public void render(Engine main) {
		main.renderPoint(transform.toArray(new Vec4d[] {}), dotSize, color);	
		main.renderWireframe(transform.toArray(new Vec4d[] {}), edges.toArray(new int[][] {}), 1, color);
	}
}