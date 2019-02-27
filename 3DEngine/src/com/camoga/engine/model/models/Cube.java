package com.camoga.engine.model.models;

import com.camoga.engine.Maths;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Cube extends Model {

	//TODO Cube model
	public Cube(double scale, Sprite normal, Sprite sprite) {
		super(Vertices(), Faces(), TC(), scale, normal, sprite, Material.brick);
	}

	static double[][]TC() {
		double[][] tc = new double[(int) (Math.pow(2, 3-2)*Maths.factorial(3)/Maths.factorial(3-2)/Maths.factorial(2))*4][];
		for(int i = 0; i < tc.length; i+=4) {
			tc[i] = new double[]{0,0};
			tc[i+1] = new double[]{0.999,0};
			tc[i+2] = new double[]{0.999,.999};
			tc[i+3] = new double[]{0,.999};
		}
		return tc;
	}

	static int[][] Edges() {
		int[][] edges = new int[(int) (Math.pow(2, 3-1)*3)][];
		int index = 0;
		for(int i = 0; i < (1<<3); i++) {
			for(int j = 0; j < 3; j++) {
				if((i & (1<<j) ) == (1<<j)) continue;
				edges[index] = new int[] {i,(1<<j)+i};
				index++;
			}
		}
		return edges;
	}
	
	static int[][] Faces() {
		int numOfFaces = (int) (Math.pow(2, 3-2)*Maths.factorial(3)/Maths.factorial(3-2)/Maths.factorial(2));
		int[][] faces = new int[numOfFaces][];
		
		int index = 0;
		
		for(int i = 0; i < 1<<3; i++) {
			for(int j = 0; j < 3; j++) {
				int jp = (1<<j);
				if((i & jp) == jp) continue;
				

				for(int k = j+1; k < 3; k++) {
					int kp = (1<<k);
					if((i & kp) == kp) continue;

					faces[index] = new int[] {i,i+jp,i+kp+jp,i+kp};
					index++;
				}
			}
		}
		
		return faces;
	}
	
	static double[][] Vertices() {
		double[][] vertices = new double[(int) Math.pow(2, 3)][3];
		for(int i = 0; i < vertices.length; i++) {
			for(int j = 0; j < 3; j++) {
				
				vertices[i][3-j-1] = (i & (1<<j)) != 0 ? -1:1;
			}
		}
		return vertices;
	}
}
