package com.camoga.engine.model;

import java.util.ArrayList;

import com.camoga.engine.Engine;
import com.camoga.engine.Renderable;
import com.camoga.engine.Scene;
import com.camoga.engine.geom.Matrix4d;
import com.camoga.engine.geom.Vec4d;

public class HollowModel implements Renderable {

	public ArrayList<Vec4d> vertices = new ArrayList<Vec4d>();
	public ArrayList<Vec4d> transform = new ArrayList<Vec4d>();
	public int color;
	public double scale;
	public double dotSize;
	
	public HollowModel(double[][] vertices, double scale, double dotSize, int color) {
		this.color = color;
		for(double[] v : vertices) {
			this.vertices.add(new Vec4d(v[0],v[1],v[2]));
		}
		this.scale = scale;
		this.dotSize = dotSize;
		for(int i = 0; i < vertices.length; i++) {
			transform.add(new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]));
		}
	}
	
	public void addVertex(double x, double y, double z) {
		vertices.add(new Vec4d(x,y,z));
		transform.add(new Vec4d(x,y,z));
	}
	
	public void transform(Matrix4d rotation, Scene scene) {
		for(int i = 0; i < vertices.size(); i++) {
			transform.set(i, rotation.multiply(Scene.scale(scale))
//					.multiply(Scene.translate(-scene.cam.pos.x, -scene.cam.pos.y, -scene.cam.pos.z))
					.multiply(vertices.get(i)));
		}
	}

	@Override
	public void render(Engine main) {
		main.renderPoint(transform.toArray(new Vec4d[] {}), dotSize, color);	
	}
}