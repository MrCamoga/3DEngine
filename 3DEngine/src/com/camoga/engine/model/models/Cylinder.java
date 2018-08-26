package com.camoga.engine.model.models;

import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Cylinder extends Model {

	public Cylinder(double R, double h, int longNum, double scale, Sprite normal, Sprite sprite, Material mat) {
		super(vertices(longNum,R, h), 
				faces(longNum), 
				quadTexCoords(longNum), 
				scale, normal, sprite, mat);
	}
	
	//TODO add top and bottom
	private static double[][] vertices(int longNum, double R, double h) {
		double[][] vert = new double[longNum*2][];
		
		for(int i = 0; i < 2; i++) {
			System.out.println(i);
			for(int j = 0; j < longNum; j++) {
				double angle = 2*Math.PI*j/longNum;
				vert[j+i*longNum] = new double[] 
						{R*Math.sin(angle),
						h*i,
						R*Math.cos(angle)};
			}
		}
		return vert;
	}
	
	private static double[][] quadTexCoords(int longNum) {
		double[][] textureCoords = new double[longNum*4][];
		for(int j = 0; j < longNum; j++) {
//			System.out.println(j+","+longNum);
			textureCoords[4*j] = new double[] {(double)j/(longNum),0};
			textureCoords[4*j+1] = new double[] {((double)(j+1))/longNum,0};
			textureCoords[4*j+2] = new double[] {(double)j/longNum,1};
			textureCoords[4*j+3] = new double[] {((double)(j+1))/longNum,1};
		}
		return textureCoords;
	}
	
	private static int[][] faces(int longNum) {
		int[][] fac = new int[longNum][];
		
		for(int j = 0; j < longNum; j++) {
			fac[j] = new int[] {
				j,(j+1)%longNum,(j+1)%longNum+longNum,j+longNum
			};
		}
		
		return fac;
	}
}