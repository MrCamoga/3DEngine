package com.camoga.engine.model.models;

import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Torus extends Model {

	/**
	 * Create a torus
	 * @param R major radius
	 * @param r minor radius
	 * @param longNum number of vertices along the longitude
	 * @param shortNum number of vertices along the latitude
	 * @param scale 
	 * @param sprite texture
	 */
	public Torus(double R, double r, int longNum, int shortNum, double scale, Sprite normal, Sprite sprite, Material mat) {
		super(vertices(longNum, shortNum, R, r), 
				faces(longNum, shortNum), 
				quadTexCoords(longNum,shortNum), 
				scale, normal, sprite, mat);
	}
	
	private static double[][] vertices(int longNum, int shortNum, double R, double r) {
		double[][] vert = new double[longNum*shortNum][];
		
		for(int i = 0; i < shortNum; i++) {
			double Ro = R-r*Math.sin(2*Math.PI*i/shortNum);
			for(int j = 0; j < longNum; j++) {
				vert[j+i*longNum] = new double[] 
						{Ro*Math.sin(2*Math.PI*j/longNum),
						r*Math.cos(2*Math.PI*i/shortNum),
						-Ro*Math.cos(2*Math.PI*j/longNum)};
			}
		}
		return vert;
	}
	
	private static double[][] quadTexCoords(int longNum, int shortNum) {
		double[][] textureCoords = new double[longNum*shortNum*4][];
		for(int i = 0; i < shortNum; i++) {
			for(int j = 0; j < longNum; j++) {
				System.out.println(j+","+longNum);
				textureCoords[4*(j+i*longNum)] = new double[] {(double)j/(longNum),(double)i/shortNum};
				textureCoords[4*(j+i*longNum)+1] = new double[] {((double)(j+1))/longNum,(double)(i+1)/shortNum};
				textureCoords[4*(j+i*longNum)+2] = new double[] {(double)j/longNum,(double)(i+1)/shortNum};
				textureCoords[4*(j+i*longNum)+3] = new double[] {((double)(j+1))/longNum,(double)i/shortNum};
			}
		}
		return textureCoords;
	}
	
	private static int[][] faces(int longNum, int shortNum) {
		int[][] fac = new int[longNum*shortNum][];
		
		for(int i = 0; i < shortNum; i++) {
			for(int j = 0; j < longNum; j++) {
				fac[j+i*longNum] = new int[] {(j%longNum+i*longNum)%(longNum*shortNum),(j%longNum+(i+1)*longNum)%(longNum*shortNum),((j+1)%longNum+(i+1)*longNum)%(longNum*shortNum),((j+1)%longNum+i*longNum)%(longNum*shortNum)};
				if(i == 0 && j == 0) {
					for(int k = 0; k < 4; k++) {
						System.out.println(fac[0][k]);
					}
				}
				
			}
		}
		
		return fac;
	}
}
