package com.camoga.engine.model.models;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;

public class Sphere extends Model {

	/**
	 * Create a sphere
	 * @param R radius
	 * @param longNum number of vertices along the longitude
	 * @param shortNum number of vertices along the latitude
	 * @param scale 
	 * @param sprite texture
	 */
	public Sphere(double R, int longNum, int shortNum, double scale, Sprite normal, Sprite sprite, Material mat) {
		super(vertices(longNum, shortNum, R), 
				faces(longNum, shortNum), 
				quadTexCoords(longNum,shortNum), scale, normal, sprite, mat);
	}
	
	private static double[][] vertices(int longitude, int latitude, double R) {
//		if(latitude%2!=0) throw new RuntimeException("latitude can't be odd");
		double[][] vert = new double[longitude*(latitude-1)+2][];
//		System.out.println(vert.length);
		vert[0]= new double[] {0,R,0};
		vert[vert.length-1] = new double[] {0,-R,0};
		for(int i = 1; i < latitude; i++) {
			double Ro = R*sin(PI*i/latitude);
			double h = R*cos(PI*i/latitude);
			for(int j = 0; j < longitude; j++) {
				vert[j+1+(i-1)*longitude] = new double[] {Ro*sin(2*PI*j/longitude),h,-Ro*cos(2*PI*j/longitude)};
//				System.out.println(vert[j+1+(i-1)*longitude]+", " + (j+1+(i-1)*longitude));
				
			}
		}
		return vert;
	}
	
	private static double[][] quadTexCoords(int longNum, int shortNum) {
		double[][] textureCoords = new double[longNum*shortNum*4][];
		for(int i = 0; i < shortNum-1; i++) {
			for(int j = 0; j < longNum; j++) {
				textureCoords[4*(j+i*longNum)] = new double[] {(double)j/longNum,(double)i/shortNum};
				textureCoords[4*(j+i*longNum)+1] = new double[] {((double)(j+1))/longNum,(double)i/shortNum};
				textureCoords[4*(j+i*longNum)+2] = new double[] {((double)(j+1))/longNum,(double)(i+1)/shortNum};
				textureCoords[4*(j+i*longNum)+3] = new double[] {(double)j/longNum,(double)(i+1)/shortNum};
			}
		}
		for(int j = 0; j < longNum; j++) {
			textureCoords[4*(j+(shortNum-1)*longNum)] = new double[] {(double)j/longNum,1};
			textureCoords[4*(j+(shortNum-1)*longNum)+1] = new double[] {((double)(j+1))/longNum,1};
			textureCoords[4*(j+(shortNum-1)*longNum)+3] = new double[] {((double)(j+1))/longNum,1-1.0/shortNum};
			textureCoords[4*(j+(shortNum-1)*longNum)+2] = new double[] {(double)j/longNum,1-1.0/shortNum};
		}
		return textureCoords;
	}
	
	private static int[][] faces(int longitude, int latitude) {
		int[][] fac = new int[longitude*latitude][3];
//		System.out.println(fac.length);
		for(int j = 0; j < longitude; j++) {
			fac[j] = new int[] {0,0,(j+1)%longitude+1,j%longitude+1};
		}
		for(int i = 1; i <latitude-1; i++) {
			for(int j = 0; j < longitude; j++) {
				fac[j+(i)*longitude] = new int[] {j+1+(i-1)*longitude,(j+1)%longitude+1+(i-1)*longitude,(j+1)%longitude+1+i*longitude,j+1+i*longitude};
			}
		}
		for(int j = 0; j < longitude; j++) {
			fac[j+(latitude-1)*longitude] = new int[] {longitude*(latitude-1)+1,longitude*(latitude-1)+1,j%longitude+1+longitude*(latitude-2),(j+1)%longitude+1+longitude*(latitude-2)};
		}
		
		return fac;
	}
}
