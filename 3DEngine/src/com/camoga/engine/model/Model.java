package com.camoga.engine.model;

import com.camoga.engine.Engine;
import com.camoga.engine.Renderable;
import com.camoga.engine.Scene;
import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.Sprite;

public class Model implements Renderable {

	public Vec4d[] vertices;
	public int[][] faces;
	public double[][] textureCoords;

	public Vec3[] vertexColor;
	public Sprite sprite;
	public Sprite normal;
	public Material mat;
	
	public Vec4d[] transform;
	public double scale;
	public Vec3 pos = new Vec3();
	public Vec3 rot = new Vec3();
	
	public Matrix rotMat;
	
	public Model(double[][] vertices, int[][] faces, double[][] textureCoords, double scale, Sprite normal, Sprite sprite, Material mat) {
		setVertices(vertices);
//		faceNormals(vertices, faces);
		this.faces = faces;
		this.normal = normal;
		this.textureCoords = textureCoords;
		this.scale = scale;
		this.mat = mat;
		this.sprite = sprite;
		vertexColor = new Vec3[vertices.length];
		transform = new Vec4d[vertices.length];
		rotate(rot);
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i]!=null)
			transform[i] = new Vec4d(vertices[i][0],vertices[i][1],vertices[i][2]);
			else transform[i] = new Vec4d(0,0,0);
		}
	}
	
	public Model(double[][] vertices, int[][] faces, double[][] textureCoords, double scale, Sprite sprite, Material mat) {
		this(vertices, faces, textureCoords, scale, null, sprite, mat);
	}
		
	
//	protected void faceNormals(double[][] vertices, int[][] faces) {
//		normals = new Vec3[faces.length];
//		for(int i = 0; i < faces.length; i++) {
//			normals[i] = Vec3.crossNorm(new Vec3(vertices[faces[i][0]]), new Vec3(vertices[faces[i][1]]), new Vec3(vertices[faces[i][2]]));
//		}
//	}
	
	public void setVertices(double[][] vertices) {
		this.vertices = new Vec4d[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
//			if(vertices[i] != null)
			this.vertices[i] = new Vec4d(vertices[i][0], vertices[i][1], vertices[i][2]);
//			else this.vertices[i] = new Vec4d(0,0,0);
		}
	}
	
	public void render(Engine main) {
		if(textureCoords != null) {
//			main.renderPoint(transform, 2, 0xffff0000);
//			main.renderPolygons(transform, faces, textureCoords, normals, sprite, mat);
			main.renderPolygons(transform, faces, textureCoords, normal, sprite, vertexColor, mat);
		}
	}

	public void translate(Vec3 pos) {
		this.pos = pos;
	}
	
	public void rotate(Vec3 rot) {
		rotMat = Scene.rotX(-rot.x).multiply(Scene.rotY(-rot.y)).multiply(Scene.rotZ(-rot.z));
	}

	public void transform(Matrix rotation, Scene scene) {
		for(int i = 0; i < vertices.length; i++) {
			transform[i] = rotation
					.scale(scale)
					.multiply(Scene.translate(pos.x, pos.y, pos.z))
//					.multiply(Scene.translate(-scene.cam.pos.x, -scene.cam.pos.y, -scene.cam.pos.z))
					.multiply(rotMat)
					.multiply(vertices[i]);
		}
	}
	
	public int[][] getFaces() {
		return faces;
	}
}