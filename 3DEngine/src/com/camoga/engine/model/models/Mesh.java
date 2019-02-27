package com.camoga.engine.model.models;

import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Mesh extends Model {

	public Mesh(int width, int height, double scale, Material mat) {
		super(meshVertices(width, height), meshFaces(width, height), meshText(width, height), scale, null, mat);
		for(int i = 0; i < vertexColor.length; i++) {
			vertexColor[i] = new Vec3(Math.random(), Math.random(), Math.random());
		}
	}
	
	private static double[][] meshVertices(int width, int height) {
		double[][] vertices = new double[height*width][];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				vertices[i+j*width] = new double[] {i-width/2,0,j-height/2};
			}
		}
		return vertices;
	}
	
	private static double[][] meshText(int width, int height) {
		double[][] textureCoords = new double[(width-1)*(height-1)*4][];
		for(int i = 0; i < textureCoords.length/4; i++) {
			textureCoords[4*i] = new double[] {0,0};
			textureCoords[4*i+1] = new double[] {1,0};
			textureCoords[4*i+2] = new double[] {1,1};
			textureCoords[4*i+3] = new double[] {0,1};
		}
		return textureCoords;
	}
	
	private static int[][] meshFaces(int width, int height) {
		int[][] faces = new int[(width-1)*(height-1)][];
		for(int i = 0; i < height-1; i++) {
			for(int j = 0; j < width-1; j++) {
				faces[j+i*(width-1)] = new int[]{j+i*width,j+1+i*width,j+(i+1)*width+1,j+(i+1)*width};
			}
		}
		return faces;
	}
}